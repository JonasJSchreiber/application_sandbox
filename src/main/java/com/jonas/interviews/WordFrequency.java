package com.jonas.interviews;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/*
 * Write an algorithm that take a list of n words and an integer m, 
 * and retrieves the mth most frequent word in that list.
 */

public class WordFrequency {

	public String retrieveMthMostFrequentWord(String[] array, int m) {
		HashMap<String, Integer> map = new HashMap<String,Integer>();
		for (String s : Arrays.asList(array)) {
			if (map.containsKey(s)) {
				map.put(s, map.get(s) + 1);
			} else {
				map.put(s, 1);
			}
		} 
		Map<String, Integer> sortedMap = sortByValue(map);
		return (String) sortedMap.keySet().toArray()[m];
	}
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map ) {
    List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
			public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> entry : list) {
			result.put(entry.getKey(), entry.getValue());
		}
		return result;
	}
	
	@Test
	public void retrieveMthMostFrequentWordTest() {
		String bigString = "Is it true? she wondered. Would the gods be so cruel? Her mother was one of Joffreys enemies now, her brother Robb another. Her father had died by the kings command. Must Robb and her lady mother die next? The comet was red, but Joffrey was Baratheon as much as Lannister, and their sigil was a black stag on a golden field. Shouldnt the gods have sent Joff a golden comet?Sansa closed the shutters and turned sharply away from the window. You look very lovely today, my lady, Ser Arys said.Thank you, ser. Knowing that Joffrey would require her to attend the tourney in his honor, Sansa had taken special care with her face and clothes. She wore a gown of pale purple silk and a moonstone hair net that had been a gift from Joffrey. The gown had long sleeves to hide the bruises on her arms. Those were Joffreys gifts as well. When they told him that Robb had been proclaimed King in the North, his rage had been a fearsome thing, and he had sent Ser Boros to beat her.Shall we go? Ser Arys offered his arm and she let him lead her from her chamber. If she must have one of the Kingsguard dogging her steps, Sansa preferred that it be him. Ser Boros was short-tempered, Ser Meryn cold, and Ser Mandons strange dead eyes made her uneasy, while Ser Preston treated her like a lackwit child. Arys Oakheart was courteous, and would talk to her cordially. Once he even objected when Joffrey commanded him to hit her. He did hit her in the end, but not hard as Ser Meryn or Ser Boros might have, and at least he had argued. The others obeyed without question . . . except for the Hound, but Joff never asked the Hound to punish her. He used the other five for that.Ser Arys had light brown hair and a face that was not unpleasant to look upon. Today he made quite the dashing figure, with his white silk cloak fastened at the shoulder by a golden leaf, and a spreading oak tree worked upon the breast of his tunic in shining gold thread. Who do you think will win the days honors? Sansa asked as they descended the steps arm in arm.I will, Ser Arys answered, smiling. Yet I fear the triumph will have no savor. This will be a small field, and poor. No more than two score will enter the lists, including squires and freeriders. There is small honor in unhorsing green boys.The last tourney had been different, Sansa reflected. King Robert had staged it in her fathers honor. High lords and fabled champions had come from all over the realm to compete, and the whole city had turned out to watch. She remembered the splendor of it: the field of pavilions along the river with a knights shield hung before each door, the long rows of silken pennants waving in the wind, the gleam of sunlight on bright steel and gilded spurs. The days had rung to the sounds of trumpets and pounding hooves, and the nights had been full of feasts and song. Those had been the most magical days of her life, but they seemed a memory from another age now. Robert Baratheon was dead, and her father as well, beheaded for a traitor on the steps of the Great Sept of Baelor. Now there were three kings in the land, and war raged beyond the Trident while the city filled with desperate men. Small wonder that they had to hold Joffs tournament behind the thick stone walls of the Red Keep.Will the queen attend, do you think? Sansa always felt safer when Cersei was there to restrain her son.I fear not, my lady. The council is meeting, some urgent business. Ser Arys dropped his voice. Lord Tywin has gone to ground at Harrenhal instead of bringing his army to the city as the queen commanded. Her Grace is furious. He fell silent as a column of Lannister guardsmen marched past, in crimson cloaks and lion-crested helms. Ser Arys was fond of gossip, but only when he was certain that no one was listening.The carpenters had erected a gallery and lists in the outer bailey. It was a poor thing indeed, and the meager throng that had gathered to watch filled but half the seats. Most of the spectators were guardsmen in the gold cloaks of the City Watch or the crimson of House Lannister; of lords and ladies there were but a paltry few, the handful that remained at court. Grey-faced Lord Gyles Rosby was coughing into a square of pink silk. Lady Tanda was bracketed by her daughters, placid dull Lollys and acid-tongued Falyse. Ebon-skinned Jalabhar Xho was an exile who had no other refuge, Lady Ermesande a babe seated on her wet nurses lap. The talk was she would soon be wed to one of the queens cousins, so the Lannisters might claim her lands.The king was shaded beneath a crimson canopy, one leg thrown negligently over the carved wooden arm of his chair. Princess Myrcella and Prince Tommen sat behind him. In the back of the royal box, Sandor Clegane stood at guard, his hands resting on his swordbelt. The white cloak of the Kingsguard was draped over his broad shoulders and fastened with a jeweled brooch, the snowy cloth looking somehow unnatural against his brown roughspun tunic and studded leather jerkin. Lady Sansa, the Hound announced curtly when he saw her. His voice was as rough as the sound of a saw on wood. The burn scars on his face and throat made one side of his mouth twitch when he spoke.";
		String[] array = bigString.toLowerCase().split(" ");
		System.out.println("Most Frequent Words: "); 
		for (int i = 0; i < 15; i++) {
			System.out.println((i + 1) + ": " + retrieveMthMostFrequentWord(array, i));
		}
	}
}
