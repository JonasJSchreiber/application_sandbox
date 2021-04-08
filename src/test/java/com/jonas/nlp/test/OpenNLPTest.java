package com.jonas.nlp.test;


import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
//import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.jonas.nlp.OpenNLP;

import opennlp.tools.coref.DiscourseEntity;
import opennlp.tools.coref.mention.MentionContext;
import opennlp.tools.parser.Parse;
import opennlp.tools.util.Span;

/**
 * Test fixture for {@link OpenNLP}
 */
public class OpenNLPTest {

	@Test
	public void testMe() {
		int msgQueueId = 2;
		List<String> appointments = new ArrayList<>();
		appointments.add("asdf");
		System.out.println(String.format(
				"Looping over appointments we need to set pre/post messaging tags for msg_queue_id: %d, number of appointments: %d",
				msgQueueId, appointments.size()));
	}
   /**
    * Test method for both sentence detection and tokenization
    */
   @Test
   public void testSentenceDetectionAndTokenization() {
      final OpenNLP toolkit = new OpenNLP();
      
      // Example taken from:
      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Sentence_Detector
      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Tokenizer
      final String content =
            "Pierre Vinken, 61 years old, will join the board as a nonexecutive"
            + " director Nov. 29. Mr. Vinken is chairman of Elsevier N.V., the"
            + " Dutch publishing group. Rudolph Agnew, 55 years old and former"
            + " chairman of Consolidated Gold Fields PLC, was named a director"
            + " of this British industrial conglomerate."
            // added this for more boundary cases
            + " Those contraction-less sentences don't have boundary/odd"
            + " cases...this one does.";
      
      final String[][] expected = new String[][] {
            new String[] {"Pierre", "Vinken", ",", "61", "years", "old", ",",
                  "will", "join", "the", "board", "as", "a", "nonexecutive",
                  "director", "Nov.", "29", "."},
            new String[] {"Mr.", "Vinken", "is", "chairman", "of", "Elsevier",
                  "N.V.", ",", "the", "Dutch", "publishing", "group", "."},
            new String[] {"Rudolph", "Agnew", ",", "55", "years", "old", "and",
                  "former", "chairman", "of", "Consolidated", "Gold", "Fields",
                  "PLC", ",", "was", "named", "a", "director", "of", "this",
                  "British", "industrial", "conglomerate", "."},
            new String[] {"Those", "contraction-less", "sentences", "do",
                  "n't", "have", "boundary/odd", "cases", "...this", "one",
                  "does", "."}
      };
  
      final String[] sentences = toolkit.detectSentences(content);
      assertEquals("Incorrect number of sentences detected.",
            expected.length, sentences.length);

      for (int i=0; i < sentences.length; i++) {

         final String[] tokens = toolkit.tokenize(sentences[i]);
         
         assertEquals(
               "Incorrect number of tokens detected for sentence at index " + i,
               expected[i].length, tokens.length);
         // compare each token against expectations
         for (int j=0; j < expected[i].length; j++) {
            assertEquals(
                  "Unexpected token at sentence index " + i
                  + ", token index " + j,
                  expected[i][j], tokens[j]);
         }
      }
   }

   /**
    * Test method for {@link OpenNLP#detectSentences(String)}.
    */
   @Test
   public void testDetectSentences() {
      final OpenNLP toolkit = new OpenNLP();
      
      // Example taken from:
      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Sentence_Detector
      final String content =
            "Pierre Vinken, 61 years old, will join the board as a nonexecutive"
            + " director Nov. 29. Mr. Vinken is chairman of Elsevier N.V., the"
            + " Dutch publishing group. Rudolph Agnew, 55 years old and former"
            + " chairman of Consolidated Gold Fields PLC, was named a director"
            + " of this British industrial conglomerate."
            // added this for more boundary cases
            + " Those contraction-less sentences don't have boundary/odd"
            + " cases...this one does.";
      
      final String[] expected = new String[] {
            "Pierre Vinken, 61 years old, will join the board as a nonexecutive"
               + " director Nov. 29.",
            "Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing"
               + " group.",
            "Rudolph Agnew, 55 years old and former chairman of Consolidated"
               + " Gold Fields PLC, was named a director of this British"
               + " industrial conglomerate.",
            "Those contraction-less sentences don't have boundary/odd"
               + " cases...this one does."
      };
      
      final String[] sentences = toolkit.detectSentences(content);
      // compare each sentence against expectations
      assertEquals("Incorrect number of sentences detected.",
            expected.length, sentences.length);
      for (int i=0; i < expected.length; i++) {
         assertEquals("Unexpected sentence content",
               expected[i], sentences[i]);
      }
   }
   
   /**
    * Test method for {@link OpenNLP#tokenize(String)}.
    */
   @Test
   public void testTokenize() {
      final OpenNLP toolkit = new OpenNLP();

      // Example taken (and corrected) from:
      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Tokenizer
      final String[] sentences = new String[] {
            "Pierre Vinken, 61 years old, will join the board as a nonexecutive"
            + " director Nov. 29.",
         "Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing"
            + " group.",
         "Rudolph Agnew, 55 years old and former chairman of Consolidated"
            + " Gold Fields PLC, was named a director of this British"
            + " industrial conglomerate."
      };
      
      final String[][] expected = new String[][] {
            new String[] {"Pierre", "Vinken", ",", "61", "years", "old", ",",
                  "will", "join", "the", "board", "as", "a", "nonexecutive",
                  "director", "Nov.", "29", "."},
            new String[] {"Mr.", "Vinken", "is", "chairman", "of", "Elsevier",
                  "N.V.", ",", "the", "Dutch", "publishing", "group", "."},
            new String[] {"Rudolph", "Agnew", ",", "55", "years", "old", "and",
                  "former", "chairman", "of", "Consolidated", "Gold", "Fields",
                  "PLC", ",", "was", "named", "a", "director", "of", "this",
                  "British", "industrial", "conglomerate", "."}
      };
  
      for (int i=0; i < sentences.length; i++) {
         final String[] tokens = toolkit.tokenize(sentences[i]);
         
         assertEquals(
               "Incorrect number of tokens detected for sentence at index " + i,
               expected[i].length, tokens.length);
         // compare each token against expectations
         for (int j=0; j < expected[i].length; j++) {
            assertEquals(
                  "Unexpected token at sentence index " + i
                  + ", token index " + j,
                  expected[i][j], tokens[j]);
         }
      }
   }
   
   /**
    * Test method for {@link OpenNLP#tagPartOfSpeech(String[])}.
    */
   @Test
   public void testPartOfSpeechTagger() {
      final OpenNLP toolkit = new OpenNLP();

      // Example taken (and corrected) from:
      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=POS_Tagger
      final String[][] tokens = new String[][] {
            new String[] {"Pierre", "Vinken", ",", "61", "years", "old", ",",
                  "will", "join", "the", "board", "as", "a", "nonexecutive",
                  "director", "Nov.", "29", "."},
            new String[] {"Mr.", "Vinken", "is", "chairman", "of", "Elsevier",
                  "N.V.", ",", "the", "Dutch", "publishing", "group", "."},
      };
      
      final String[][] expected = new String[][] {
            new String[] {"NNP", "NNP", ",", "CD", "NNS", "JJ", ",", "MD",
                  "VB", "DT", "NN", "IN", "DT", "JJ", "NN", "NNP", "CD", "."},
            new String[] {"NNP", "NNP", "VBZ", "NN", "IN", "NNP", "NNP", ",",
                  "DT", "JJ", "NN", "NN", "."},
      };
  
      for (int i=0; i < tokens.length; i++) {
         final String[] tags = toolkit.tagPartOfSpeech(tokens[i]);
         
         assertEquals(
               "Incorrect number of tags detected for sentence at index " + i,
               expected[i].length, tags.length);
         // compare each tag against expectations
         for (int j=0; j < expected[i].length; j++) {
            assertEquals(
                  "Unexpected tag at sentence index " + i
                  + ", token index " + j,
                  expected[i][j], tags[j]);
         }
      }
   }
   
   /**
    * Test method for {@link OpenNLP#}.
    */
   @Test
   public void testParser() {
      final OpenNLP toolkit = new OpenNLP();

      // Example taken (and corrected) from:
      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Parser
      final String sentence = "The quick brown fox jumps over the lazy dog.";
      
      // Parse
      final Parse actual = toolkit.parseSentence(sentence);
      
      final StringBuffer buf = new StringBuffer();
      actual.show(buf);
      
      // expected
      //(TOP (NP (NP (DT The) (JJ quick) (JJ brown) (NN fox) (NNS jumps)) (PP (IN over) (NP (DT the) (JJ lazy) (NN dog)))(. .)))
      assertEquals("The created parse tree does not match the expected string.",
            "(TOP " +
                  "(NP " +
                     "(NP (DT The) (JJ quick) (JJ brown) (NN fox) (NNS jumps)) " +
                     "(PP " +
                        "(IN over) " +
                        "(NP (DT the) (JJ lazy) (NN dog))" +
                     ")" +
                     "(. .)" +
                  ")" +
            ")",
            buf.toString());
   }

   
   /**
    * Test method for {@link OpenNLP#findNamedEntities(String, String[])}.
    */
   @Test
   public void testFindNamedEntities() {
      final OpenNLP toolkit = new OpenNLP();

      // Example taken (and corrected) from:
      // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Name_Finder
      final String text = 
            "Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.  " +
      		"Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing group.  " +
      		"Rudolph Agnew, 55 years old and former chairman of Consolidated Gold Fields PLC, was named a director of this British industrial conglomerate.";
      
      final String[] tokens = toolkit.tokenize(text);
      
      final List<Span> spans = toolkit.findNamedEntities(text, tokens);
      
      // expected
      final String[][] expected = {
            new String[] { "Pierre", "Vinken" },
            new String[] { "Rudolph", "Agnew" },
            new String[] { "Consolidated", "Gold", "Fields", "PLC" }
      };
      
      assertEquals("Unexpected number of spans", expected.length, spans.size());
      
      for (int i=0; i < spans.size(); i++) {
         final Span s = spans.get(i);
         int j = 0;
         for (int tok = s.getStart(); tok < s.getEnd(); tok++) {
            assertEquals("Unexpected Named Entity token found", expected[i][j], tokens[tok]);
            j++;
         }
      }
   }

   /**
    * Test method for {@link OpenNLP#findEntityMentions(String[])}.
    * <p>
    * <strong>NOTE:</strong> Requires Java VM param <code>-DWNSEARCHDIR=lib/wordnet/dict</code>
    * </p>
    */
   @Test
   public void testFindEntityMentions() {
       final OpenNLP toolkit = new OpenNLP();

       // Example taken (and corrected) from:
       // http://sourceforge.net/apps/mediawiki/opennlp/index.php?title=Name_Finder
       final String[] sentences = {
               "Pierre Vinken, 61 years old, will join the board as a nonexecutive director Nov. 29.",
               "Mr. Vinken is chairman of Elsevier N.V., the Dutch publishing group.",
               "Rudolph Agnew, 55 years old and former chairman of Consolidated Gold Fields PLC, was named a director of this British industrial conglomerate."
       };

       final DiscourseEntity[] entities = toolkit.findEntityMentions(sentences);

       // expected
       final String[][] expected = {
               new String[]{"this British industrial conglomerate"},
               new String[]{"a director"},
               new String[]{"Consolidated Gold Fields PLC"},
               new String[]{"former chairman"},
               new String[]{"55 years"},
               new String[]{"Rudolph Agnew"},
               new String[]{"Elsevier N.V.", "the Dutch publishing group"},
               new String[]{"chairman"},
               new String[]{"Pierre Vinken", "Mr. Vinken"},
               new String[]{"Nov. 29"},
               new String[]{"a nonexecutive director"}, 
               new String[]{"the board"},
               new String[]{"61 years"}
       };
       
       assertEquals("Unexpected number of entities", expected.length, entities.length);

       for (int i = 0; i < entities.length; i++) {
           final DiscourseEntity ent = entities[i];

           assertEquals("Unexpected number of mentions at index " + i,
                   expected[i].length, ent.getNumMentions());
           final Iterator<MentionContext> mentions = ent.getMentions();
           int j = 0;
           while (mentions.hasNext()) {
               final MentionContext mc = mentions.next();
               System.out.println("[" + mc.toString() + "]");
               assertEquals("Unexpected Entity Mention found", expected[i][j], mc.toString().trim());
               j++;
           }
       }
   }
   
   @Test
   public void testCoref() {
       final OpenNLP toolkit = new OpenNLP();
	   String content = "SHE WONDERED WHAT IT WOULD HAVE BEEN LIKE TO BE PERFECT. On the day that she had hatched, she had been captured before she could wriggle over the sand to the cool and salty embrace of the sea. She Who Remembers was doomed to recall every detail of that day with clarity. It was her entire function and the reason for her existence. She was a vessel for memories. Not just her own life, from the moment when she began forming in the egg, but the linked lives of those who had gone before her were nested inside her. From egg to serpent to cocoon to dragon to egg, all memory of her line was hers. Not every serpent was so gifted, or so burdened. Only a relative few were imprinted with the full record of their species, but only a few were needed. She had begun perfect. Her tiny, smooth body, lithe and scaled, had been flawless. She had cut her way out of the leathery shell with the egg tooth atop her snout. She was a late hatcher. The others in her clutch had already broken free of their shells and the heaped dry sand. They had left their wallowing trails for her to follow. The sea had beckoned her insistently. Every lap of every wave beguiled her. She had begun her journey, slithering across the dry sand under the beating sun. She had smelled the wet tang of the ocean. The moving light on its dazzling surface had lured her. She had never finished her journey. The Abominations had found her. They had surrounded her, interposing their heavy bodies between her and the beckoning ocean. Plucked wriggling from the sand, she had been imprisoned in a tide-fed pool inside a cave in the cliffs. There they had kept her, feeding her only dead food and never allowing her to swim free. She had never migrated south with the others to the warm seas where food was plentiful. She had never achieved the bulk and strength that a free life would have granted her. Nevertheless, she grew, until the pool in the cave was little more than a cramped puddle to her, a space barely sufficient to keep her skin and gills wet. Her lungs were pinched always inside her folded coils. The water that surrounded her was constantly befouled with her poisons and wastes. The Abominations had kept her prisoner. How long had they confined her there? She could not measure it, but she felt certain that she had been captive for several ordinary lifetimes of her kind. Time and again, she had felt the call of the season of migration. A restless energy would come over her, followed by a terrible desire to seek out her own kind. The poison glands in her throat would swell and ache with fullness. There was no rest for her at such times, for the memories permeated her and clamored to be released. She had shifted restlessly in the torment of her small pool and vowed endless revenge against the Abominations who held her so. At such times, her hatred of them was most savage. When her overflowing glands flavored the water with her ancestral memories, when the water became so toxic with the past that her gasping gills poisoned her with history, then the Abominations came. They came to her prison, to draw water from her pool and inebriate themselves with it. Drunken, they prophesied to one another, ranting and raving in the light of the full moon. They stole the memories of her kind, and used them to extrapolate the future. Then the two-legs, Wintrow Vestrit, had freed her. He had come to the island of the Abominations, to gather for them the treasures the sea left on the shore. In exchange, he had expected them to prophesy his future for him. Even now, that thought made her mane grow turgid with poison. The Abominations prophesied only what they sensed of the future from stealing her pasts! They had no true gifts of Seeing. If they had, she reflected, they would have known that the two-legs brought their doom. They would have stopped Wintrow Vestrit. Instead, he had discovered her and freed her. Although she had touched skins with him, although their memories had mingled through her toxins, she did not understand what had motivated the two-legs to free her. He was such a short-lived creature that most of his memories could not even leave an imprint on her. She had sensed his worry and pain. She had known that he risked his brief existence to free her. The courage of such a brief spasm of life had moved her. She had slain the Abominations when they would have recaptured both of them. Then, when the two-legs would have died in the mothering sea, she had aided him to return to his ship. She Who Remembers opened wide her gills once more. She tasted a mystery in the waves. She had restored the two-legs to his ship, but the ship both frightened and attracted her. The silvery gray hull of the vessel flavored the water ahead of her. She followed it, drinking in the elusive tang of memories. The ship smelled, not like a ship, but like one of her own kind. She had followed it now for twelve tides, and was no closer to understanding how such a thing could be. She knew well what ships were; the Elderlings had had ships, though not such as this one. Her dragon memories told her that her kind had often flown over such vessels, and playfully set them to rocking wildly with a gust from wide wings. Ships were no mystery, but this one was. How could a ship give off the scent of a serpent? Moreover, it smelled like no ordinary serpent. It smelled like One Who Remembers. Again, her duty tugged at her: it was an instinct stronger than the drive to feed or mate. It was time, and past time. She should have been among her own kind by now, leading them in the migration path that her memories knew so well. She should be nourishing their own lesser recall with her potent toxins that would sting their dormant memories to wakefulness. The biological imperative clamored in her blood. Time to change. She cursed again her crooked green-gold body that wallowed and lashed through the water so awkwardly. She had no endurance to call upon. It was easier to swim in the wall of the ship’s wake, and allow its motion to help draw her through the water. She compromised with herself. As long as the silver ship’s course aligned with her own, she would follow it. She would use its momentum to help her move as she gained strength and endurance of her own. She would ponder its mystery and solve it if she could. Yet, she would not let this puzzle distract her from her primary goal. When they drew closer to shore, she would leave the ship and seek out her own kind. She would find tangles of serpents and guide them up the great river to the cocooning grounds. By this time next year, young dragons would try their wings on the summer winds. So she had promised herself for the first twelve tides that she followed the ship. Midway through the swelling of the thirteenth tide, a sound at once foreign and heart-wrenchingly familiar vibrated her skin. It was the trumpeting of a serpent. Immediately she broke free of the ship’s wake and dove down, away from the distractions of the surface waves. She Who Remembers sounded a reply, then held herself in absolute stillness, waiting. No answer came. Disappointment weighted her. Had she deceived herself? During her captivity, there had been periods when in her anguish she had cried out over and over again, trumpeting until the walls of the cavern rang with her misery. Recalling that bitterness, she lidded her eyes briefly. She would not torment herself. She opened her eyes to her solitude. Resolutely she turned to pursue the ship that represented the only pallid hint of companionship she had known. The brief pause had only made her more aware of her hampered body’s weariness. It took all of her will to make her push on. An instant later, all weariness fled as a white serpent flashed by her. He did not seem to notice her in his single-minded pursuit of the ship. The odd scent of the vessel must have confused him. Her hearts thundered wildly. “Here I am!” she called after him. “Here. I am She Who Remembers. I have come to you at last!” The white swam on in effortless undulations of his thick, pale body. He did not even turn his head to her call. She stared in shock, then hastened after him, her weariness temporarily forgotten. She dragged herself after him, gasping with the effort. She found him shadowing the ship. He slipped about in the dimness beneath it, muttering and mewling incomprehensibly at the planks of the ship’s hull. His mane of poisonous tendrils was semierect; a faint stream of bitter toxins tainted the water around him. A slow horror grew in She Who Remembers as she watched his senseless actions. From the depths of her soul every instinct she had warned against him. Such strange behavior hinted of disease or madness. But he was the first of her own kind that she had seen since the day she had hatched. The drawing of that kinship was more powerful than any revulsion and so she eased closer to him. “Greetings,” she ventured timidly. “Do you seek One Who Remembers? I am She.” In reply, his great red eyes spun antagonistically, and he darted a warning snap at her. “Mine!” he trumpeted hoarsely. “Mine. My food.” He pressed his erect mane against the ship, leaking toxins against her hull. “Feed me,” he demanded of the ship. “Give food.” She retreated hastily. The white serpent continued his nuzzling quest along the ship’s hull. She Who Remembers caught a faint scent of anxiety from the ship. Peculiar. The whole situation was as odd as a dream, and like a dream, it teased her with possible meanings and almost understandings. Could the ship actually be reacting to the white serpent’s toxins and calls? No, that was ridiculous. The mysterious scent of the vessel was confusing both of them. She Who Remembers shook out her own mane and felt it grow turgid with her potent poisons. The act gave her a sense of power. She matched herself against the white serpent. He was larger than she was, and more muscled, his body fit and knowledgeable. But that did not matter. She could kill him. Despite her stunted body and inexperience, she could paralyze him and send him drifting to the bottom. In the next moment, despite the powerful intoxication of her own body’s secretions, she knew she was even stronger than that. She could enlighten him and let him live. “White serpent!” she trumpeted. “Heed me! I have memories to share with you, memories of all our race has been, memories to sharpen your own recollections. Prepare to receive them.” He paid no heed to her words. He did not make himself ready, but she did not care. This was her destiny. For this, she had been hatched. He would be the first recipient of her gift, whether he welcomed it or not. Awkwardly, hampered by her stunted body, she launched herself toward him. He turned to her supposed attack, mane erect, but she ignored his petty toxins. With an ungainly thrust, she wrapped him. At the same moment, she shook her mane, releasing the most powerful intoxicant of them all, the deep poisons that would momentarily subdue his own mind and let the hidden mind behind his life open itself once more. He struggled frantically, then suddenly grew stiff as a log in her grip. His whirling ruby eyes grew still but unlidded, bulging from their sockets in shock. He made one abortive effort to gulp a final breath. It was all she could do to hold him. She wrapped his length in hers and kept him moving through the water. The ship began to pull away from them, but she let it go, almost without reluctance. This single serpent was more important to her than all the mysteries the ship concealed. She held him, twisting her neck to look into his face. She watched his eyes spin, then grow still again. Through a thousand lifetimes, she held him, as the past of his entire race caught up with him. For a time, she let him steep in that history. Then she eased him out of it, releasing the lesser toxins that quieted his deeper mind and let his own brief life come back to the forefront of his thoughts. “Remember.” She breathed out the word softly, charging him with the responsibilities of all his ancestors. “Remember and be.” He was quiescent in her coils. She felt his own life suddenly repossess him as a tremor shimmered down his length. His eyes suddenly spun and then focused on hers. He reared his head back from hers. She waited for his worshipful thanks. The gaze that met hers was accusing. “Why?” he demanded suddenly. “Why now? When it is too late for all of us? Why couldn’t I die ignorant of all that I could have been? Why could not you have left me a beast?” His words shocked her so that she laxed her grip on him. He whipped himself disdainfully free of her embrace and shot away from her through the water. She was not sure if he fled, or if he abandoned her. Either thought was intolerable. The awakening of his memories should have filled him with joy and purpose, not despair and anger. “Wait!” she cried after him, but the dim depths swallowed him. She wallowed clumsily after him, knowing she could never match his swiftness. “It can’t be too late! No matter what, we must try!” She trumpeted the futile words to the empty Plenty. He had left her behind. Alone again. She refused to accept it. Her stunted body floundered through the water in pursuit, her mouth open wide to taste the dispersing scent he had left behind. Faint, fainter, and then gone. He was too swift; she was too deformed. Disappointment welled in her, near stunning as her own poisons. She tasted the water again. Nothing of serpent tinged it now. She cut wider and wider arcs through the water in a desperate search for his scent trail. When she finally found it, both her hearts leapt with determination. She lashed her tail to catch up with him. “Wait!” she trumpeted. “Please. You and I, we are the only hope for our kind! You must listen to me!” The taste of serpent grew suddenly stronger. The only hope for our kind. The thought seemed to waft to her on the water, as if the words had been breathed to the air rather than trumpeted in the depths. It was the only encouragement she needed. “I come to you!” she promised, and drove herself on doggedly. But when she reached the source of the serpent scent, she saw no creature save for a silver hull cutting the waves above her.";
	   final String[] sentences = toolkit.detectSentences(content);
	   Long start = System.currentTimeMillis();
	   final DiscourseEntity[] entities = toolkit.findEntityMentions(sentences);
	   System.out.println("Processing took: " + (System.currentTimeMillis() - start) + "ms");
	   HashMap<String, Integer> characterMap = new HashMap<String, Integer>();
	   for (int i = 0; i < entities.length; i++) {
           final DiscourseEntity ent = entities[i];
           characterMap.put(ent.getLastExtent().getFirstTokenText(), ent.getNumMentions());
	   }
	   List<Entry<String, Integer>> characterFrequencies = sortHashMapByValue(characterMap);
	   for(int i = 0; i < 5; i++) {
		   System.out.println(characterFrequencies.get(i).getKey() + ": " + characterFrequencies.get(i).getValue());
	   }
   }
   
	public static List<Entry<String, Integer>> sortHashMapByValue(HashMap<String, Integer> map) {
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
    	Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
    		public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ) {
    			return (o2.getValue()).compareTo(o1.getValue());
    		}
    	});
    	return list;
	}
}