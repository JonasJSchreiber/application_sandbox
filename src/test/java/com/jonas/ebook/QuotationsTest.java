package com.jonas.ebook;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuotationsTest {

    Quotations quotations;
    @Before
    public void setup() {
        this.quotations = new Quotations();    
    }

    @Test
    public void test() {
        String s = "<p id=\"hea0001722\" class=\"calibre6\">‘I’m not quite sure,’ he said, looking round the room with the curiosity of someone who had never been in a police cell before, despite the fact that he had spent a couple of years brooding over his fraudulent tax activities in the ’Joy. ‘I was in the bank when the Gardaí arrived and, I must admit, when they walked into my office I got a bit of a fright. I thought I was in trouble again! But, no, it was just to tell me that you were being held here and they needed a parent or guardian to be present while they questioned you and I suppose I’m the closest thing to either one of those. How are you anyway, Cyril?’</p>";
        System.out.println(quotations.specialProcessing(s));
    }

    @Test
    public void test2() {
        String s = "<p id=\"tit001877\" class=\"calibre10\">I saw the expression on your face harden as you digested this piece of information. Of course, I hadn’t yet spoken to you about staying on at UEA and had no idea how you might feel about it. But you didn’t pursue the conversation and, when George finally let go of your hand, you leaned down to kiss me on the cheek with a ‘Hello, gorgeous’ that was completely out of character for you.</p>";
        System.out.println(quotations.specialProcessing(s));
    }

    @Test
    public void test3() {
        String s = "<p class=\"indent\">‘At first, perhaps,’ she admitted, laughing and putting a hand over her mouth to contain her mirth. ‘But I shall meet a wealthy man soon enough. A prince, perhaps. And he will fall in love with <a id=\"page29\"></a>me and we will live together in a palace and I will have all the servants that I require and wardrobes filled with beautiful dresses. I’ll wear different jewellery every day – opals, sapphires, rubies, diamonds – and during the season we will dance together in the throne room of the Winter Palace, and everyone will look at me from morning till night and admire me and wish that they could stand in my place.’</p>";
        System.out.println(quotations.specialProcessing(s));
    }

    @Test
    public void test4() {
        String s = "OEBPS/Text/part0002.xhtml";
        if (s.matches(".*htm.*")) {
            System.out.println("true");
        }
    }

    @Test
    public void test5() {
        String s = "<p class=\"indent-para\">‘Given all that, some say calling a false alarm is shameful,’ Commander Ironfist had said. ‘But I say a Blackguard who doesn’t shout a Nine Kill once in their life isn’t working on edge. We protect the most important people in the world. Work on edge.’</p>";
        String expected = "<p class=\"indent-para\">“Given all that, some say calling a false alarm is shameful,” Commander Ironfist had said. “But I say a Blackguard who doesn’t shout a Nine Kill once in their life isn’t working on edge. We protect the most important people in the world. Work on edge.”</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test6() {
        String s = "<p class=\"para\">‘But you curse well,’ Christophe says, encouragingly. ‘Perhaps the best I have heard. Better than my father, who as you know was a great robber and feared through his province.’</p>";
        String expected = "<p class=\"para\">“But you curse well,” Christophe says, encouragingly. “Perhaps the best I have heard. Better than my father, who as you know was a great robber and feared through his province.”</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test7() {
        String s = "<p class=\"indented\">‘I was going to force her to deal, yeah. I didn’t have anything to lose. Then all this shit happened.’ He flapped his hands at the cell. ‘And Lolo – I mean&#160;.&#160;.&#160;.&#160;fuck!’</p>";
        String expected = "<p class=\"indented\">“I was going to force her to deal, yeah. I didn’t have anything to lose. Then all this shit happened.” He flapped his hands at the cell. “And Lolo – I mean&#160;.&#160;.&#160;.&#160;fuck!”</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test8() {
        String s = "<p class=\"indented\">The crew had set up an umbilical leading the three metres or so between hulls. It led ‘down’ to the <em class=\"calibre5\">Vulture</em>’s<em class=\"calibre5\"> </em>crew – though its direction was meaningless to the dead spaces of the <em class=\"calibre5\">Gamin</em>. Medvig’s armless tripod frame was already squatting at the <em class=\"calibre5\">Vulture</em> end, a metal assemblage of dull bronze and copper, their cylindrical body dominated by four square openings. Their long head was featureless save for a couple of mismatched yellow lights – humans liked to have something to focus on. Medvig’s ‘hands’ could act as his own personal remotes and he had already sent these small spiderlike assemblages down the pipe and into the freighter to help Olli with the fine work.</p>";
        String expected = "<p class=\"indented\">The crew had set up an umbilical leading the three metres or so between hulls. It led “down” to the <em class=\"calibre5\">Vulture</em>’s<em class=\"calibre5\"> </em>crew – though its direction was meaningless to the dead spaces of the <em class=\"calibre5\">Gamin</em>. Medvig’s armless tripod frame was already squatting at the <em class=\"calibre5\">Vulture</em> end, a metal assemblage of dull bronze and copper, their cylindrical body dominated by four square openings. Their long head was featureless save for a couple of mismatched yellow lights – humans liked to have something to focus on. Medvig’s “hands” could act as his own personal remotes and he had already sent these small spiderlike assemblages down the pipe and into the freighter to help Olli with the fine work.</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test9() {
        String s = "<p class=\"indented\">‘All is achieved,’ he said cheerfully to the two station guards. One opened the cell so a Voyenni trooper could pull Idris out; the other ensured Rollo stayed inside. Then they hauled him out to the clerks’ office, where the staff did their best not to notice what was going on.</p>";
        String expected = "<p class=\"indented\">“All is achieved,” he said cheerfully to the two station guards. One opened the cell so a Voyenni trooper could pull Idris out; the other ensured Rollo stayed inside. Then they hauled him out to the clerks’ office, where the staff did their best not to notice what was going on.</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test10() {
        String s = "<p class=\"indented\">‘We don’t know that,’ Rollo said hurriedly. Everyone goggled at him, mutely indicating the evidence on the screens. ‘Look, my children,’ he told them, voice shaking, ‘we don’t know. I mean, the Arch&#160;.&#160;.&#160;.&#160;the Architects .&#160;.&#160;.’ His voice went hoarse and whispery as he tried to say the word, as though it might summon them anew. ‘They went <em class=\"calibre5\">somewhere.</em> Maybe the <em class=\"calibre5\">Oumaru</em> stumbled on them there. Maybe they were intruders, they were punished and left here. A warning for us, perhaps. “Steer clear.” But it doesn’t mean they’re <em class=\"calibre5\">back</em>. It doesn’t mean&#160;.&#160;.&#160;.’</p>";
        String expected = "<p class=\"indented\">“We don’t know that,” Rollo said hurriedly. Everyone goggled at him, mutely indicating the evidence on the screens. “Look, my children,” he told them, voice shaking, “we don’t know. I mean, the Arch&#160;.&#160;.&#160;.&#160;the Architects .&#160;.&#160;.” His voice went hoarse and whispery as he tried to say the word, as though it might summon them anew. “They went <em class=\"calibre5\">somewhere.</em> Maybe the <em class=\"calibre5\">Oumaru</em> stumbled on them there. Maybe they were intruders, they were punished and left here. A warning for us, perhaps. ‘Steer clear.’ But it doesn’t mean they’re <em class=\"calibre5\">back</em>. It doesn’t mean&#160;.&#160;.&#160;.”</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test11() {
        String s = "<p class=\"indented\">‘Go!’ said Rollo, taking his own advice and making a run for his ship. Solace registered a growing shudder throughout the bay and realized that someone on board had engaged the <em class=\"calibre5\">Vulture</em>’s mass drives, ready to rip the vessel – and its salvage – free of the station’s hold.</p>";
        String expected = "<p class=\"indented\">“Go!” said Rollo, taking his own advice and making a run for his ship. Solace registered a growing shudder throughout the bay and realized that someone on board had engaged the <em class=\"calibre5\">Vulture</em>’s mass drives, ready to rip the vessel – and its salvage – free of the station’s hold.</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test12() {
        String s = "<p class=\"indented\">‘Doesn’t mean we do what you say,’ rejoined the truculent Rostand. ‘’Specially not here.’</p>";
        String expected = "<p class=\"indented\">“Doesn’t mean we do what you say,” rejoined the truculent Rostand. “’Specially not here.”</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }

    @Test
    public void test13() {
        String s = "<p class=\"indented\">Telemmier broke in. ‘It did happen. And it was recent. I expect you’ve seen the <em class=\"calibre5\">Oumaru</em>’s departure logs. <em class=\"calibre5\">Something</em> pulled her off course into the deep void and did&#160;.&#160;.&#160;.&#160;<em class=\"calibre5\">that</em> to her.’</p>";
        String expected = "<p class=\"indented\">Telemmier broke in. “It did happen. And it was recent. I expect you’ve seen the <em class=\"calibre5\">Oumaru</em>’s departure logs. <em class=\"calibre5\">Something</em> pulled her off course into the deep void and did&#160;.&#160;.&#160;.&#160;<em class=\"calibre5\">that</em> to her.”</p>";
        s = quotations.specialProcessing(s);
        assertEquals(expected, s);
    }
}
