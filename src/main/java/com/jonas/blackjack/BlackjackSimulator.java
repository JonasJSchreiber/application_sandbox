package com.jonas.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.Vector;

/* Simulation of console-I/O program Blackjack,
using ConsoleApplet as a basis.  See the file
ConsoleApplet.java for more information.*/

@SuppressWarnings({"rawtypes", "unused"})

public class BlackjackSimulator {

	private static Scanner scanner = new Scanner(System.in);
	private List<Integer> shoe; // An array of 52 Cards, representing the deck.
	private List<Integer> discardedShoe;
	private int decksInShoe = 1;
	private int discardPosition;
	private int currentPosition; // Current position in the deck
	private Vector hand; // The cards in the hand.
	private int money = 120;
	private static int NUM_SIMULATIONS = 10000;

	public static void main(String[] args) {
		new BlackjackSimulator().run();
	}

	@SuppressWarnings("unlikely-arg-type")
	public void run() {

		/*
		 * This program lets the user play Blackjack. The computer acts as the dealer. The user has
		 * a stake of $100, and makes a bet on each game. The user can leave at any time, or will be
		 * kicked out when he loses all the money. House rules: The dealer hits on a total of 16 or
		 * less and stands on a total of 17 or more. Dealer wins ties. A new deck of cards is used
		 * for each game.
		 */

		int bet = 1; // Amount user bets on a game.
		boolean userWins; // Did the user win the game?
		int[] progression = {1, 1, 1, 2};
		int simulation = 0;

		shoe = new ArrayList<Integer>();
		discardedShoe = new ArrayList<Integer>();
		int cardCt = 0; // How many cards have been created so far.
		for (int i = 0; i < decksInShoe; i++) {
			for (int suit = 0; suit <= 3; suit++) {
				for (int value = 1; value <= 13; value++) {
					shoe.add(value);
					discardedShoe.add(value);
					cardCt++;
				}
			}
		}

		currentPosition = 0;
		shuffle();
		/* Shuffle the deck, then deal two cards to each player. */
		while (simulation < NUM_SIMULATIONS && money > 0) {

			if (currentPosition >= 52 * decksInShoe - 10) {
				System.out.println("Shuffling");
				shuffle();
			}
			userWins = playBlackjack();

			if (userWins) {
				if (Arrays.asList(progression).contains(bet)
						&& Arrays.asList(progression).indexOf(bet) < progression.length - 1) {
					bet = progression[Arrays.asList(progression).indexOf(bet) + 1];
				} else {
					bet++;
				}
				System.out.println("user wins");
				money = money + bet;
			} else {
				System.out.println("dealer wins");
				money = money - bet;
				bet = 1;
			}
			// System.out.println(currentPosition);
			if (money == 0) {
				System.out.println("Looks like you've are out of money!");
				break;
			}

			// shoe.forEach(i -> System.out.print(i + ","));
			simulation++;
		}
		System.out.println("You Made it through " + simulation + " simulations!");
		System.out.println("You leave with $" + money + '.');

	} // end main()

	public void returnHandsToDiscards(Vector dealer, Vector user) {
		for (int i = 0; i < dealer.size(); i++) {
			discardedShoe.add(getCard(dealer, i));
		}
		for (int i = 0; i < user.size(); i++) {
			discardedShoe.add(getCard(user, i));
		}
	}

	@SuppressWarnings("unchecked")
	private boolean playBlackjack() {
		// Let the user play one game of Blackjack.
		// Return true if the user wins, false if the user loses.
		Vector dealerHand; // The dealer's hand.
		Vector userHand; // The user's hand.

		dealerHand = new Vector();
		userHand = new Vector();

		dealerHand.addElement(dealCard());
		userHand.addElement(dealCard());
		dealerHand.addElement(dealCard());
		userHand.addElement(dealCard());

		/*
		 * Check if one of the players has Blackjack (two cards totaling to 21). The player with
		 * Blackjack wins the game. Dealer wins ties.
		 */

		if (value(userHand) == 21) {
			return true;
		}

		if (value(dealerHand) == 21) {
			return false;
		}

		/*
		 * If neither player has Blackjack, play the game. The user gets a chance to draw cards
		 * (i.e., to "Hit"). The while loop ends when the user chooses to "Stand" or when the user
		 * goes over 21.
		 */

		int myValue = value(userHand);
		int dealerShows = getCardValue(getCard(dealerHand, 1));

		if (showCard(getCard(userHand, 0)).equals("Ace")
				|| showCard(getCard(userHand, 1)).equals("Ace")) {
			if (myValue <= 17 || (myValue == 18 && dealerShows >= 9)) {
				int newCard = dealCard();
				userHand.addElement(newCard);
				myValue = value(userHand);
			}
		}

		while (myValue <= 11) {
			int newCard = dealCard();
			userHand.addElement(newCard);
			myValue = value(userHand);
		}

		if (myValue == 12 && (dealerShows == 2 || dealerShows == 3)) {
			int newCard = dealCard();
			userHand.addElement(newCard);
		}

		if (dealerShows >= 7) {
			while (myValue >= 12 && myValue <= 16) {
				int newCard = dealCard();
				userHand.addElement(newCard);
				myValue = value(userHand);
			}
		}

		if (myValue > 21) {
			return false;
		}
		/*
		 * If we get to this point, the user has Stood with 21 or less. Now, it's the dealer's
		 * chance to draw. Dealer draws cards until the dealer's total is > 16.
		 */

		while (value(dealerHand) <= 16) {
			int newCard = dealCard();
			dealerHand.addElement(newCard);
		}
		if (value(dealerHand) > 21) {
			return true;
		} else {
			if (value(dealerHand) == value(userHand)) {
				return false;
			} else {
				if (value(dealerHand) > value(userHand)) {
					return false;
				} else {
					return true;
				}
			}
		}

	} // end playBlackjack()

	public int dealCard() {
		// Deals one card from the deck and returns it.
		return shoe.get(currentPosition++);
	}

	public void shuffle() {
		// Put all the used cards back into the deck, and shuffle it into
		// a random order.
		Collections.shuffle(discardedShoe);
		shoe = discardedShoe;
		discardPosition = 0;
		currentPosition = 0;
	}

	public int getCard(Vector hand, int position) {
		// Get the card from the hand in given position, where positions
		// are numbered starting from 0. If the specified position is
		// not the position number of a card in the hand, then null
		// is returned.
		if (position >= 0 && position < hand.size()) {
			return ((Integer) hand.elementAt(position)).intValue();
		} else {
			return 0;
		}
	}

	public int value(Vector hand) {
		// Returns the value of this hand for the
		// game of Blackjack.

		int val; // The value computed for the hand.
		boolean ace; // This will be set to true if the
		// hand contains an ace.
		int cards; // Number of cards in the hand.

		val = 0;
		ace = false;
		cards = hand.size();

		for (int i = 0; i < cards; i++) {
			// Add the value of the i-th card in the hand.
			int card; // The i-th card;
			int cardVal; // The blackjack value of the i-th card.
			card = getCard(hand, i);
			cardVal = getCardValue(card); // The normal value, 1 to 13.
			if (cardVal > 10) {
				cardVal = 10; // For a Jack, Queen, or King.
			}
			if (cardVal == 1) {
				ace = true; // There is at least one ace.
			}
			val = val + cardVal;
		}

		// Now, val is the value of the hand, counting any ace as 1.
		// If there is an ace, and if changing its value from 1 to
		// 11 would leave the score less than or equal to 21,
		// then do so by adding the extra 10 points to val.

		if (ace == true && val + 10 <= 21) {
			val = val + 10;
		}

		return val;

	}
	public int getCardValue(int card) {
		int result = card;
		switch (card) {
			case 11 :
			case 12 :
			case 13 :
				result = 10;
		}
		return result;
	}
	public String showCard(int card) {
		switch (card) {
			case 1 :
				return "Ace";
			case 2 :
				return "2";
			case 3 :
				return "3";
			case 4 :
				return "4";
			case 5 :
				return "5";
			case 6 :
				return "6";
			case 7 :
				return "7";
			case 8 :
				return "8";
			case 9 :
				return "9";
			case 10 :
				return "10";
			case 11 :
				return "Jack";
			case 12 :
				return "Queen";
			case 13 :
				return "King";
			default :
				return "??";
		}
	}
} // end class
