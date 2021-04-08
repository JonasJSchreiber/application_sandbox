package com.jonas.nlp;

//import java.util.List;
import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
//import edu.stanford.nlp.ling.*;
//import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
//import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
//import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
//import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
//import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.pipeline.*;
//import edu.stanford.nlp.util.*;

public class CoreNLP {

	public static void main(String[] args) {
		// read some text in the text variable
//		String text = "Competition is heating up in Spain's crowded bank market. Banco Exterior de Espana is seeking to shed its image of a state-owned bank and move into new activities.";
		String text = "Bob is refusing to help. He thinks only of himself.";
		Properties props = new Properties();
	    props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    // create an empty Annotation just with the given text
	    Annotation document = new Annotation(text);

	    // run all Annotators on this text
	    pipeline.annotate(document);

	    // This is the coreference link graph
	    // Each chain stores a set of mentions that link to each other,
	    // along with a method for getting the most representative mention
	    // Both sentence and token offsets start at 1!
	    Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
	    for(Map.Entry<Integer, CorefChain> entry : graph.entrySet()) {
	    	System.out.println(entry.getValue().toString());
	    }
	    

	    // these are all the sentences in this document
	    // a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
//	    List<CoreMap> sentences = document.get(SentencesAnnotation.class);
//	    for(CoreMap sentence: sentences) {
//	      // traversing the words in the current sentence
//	      // a CoreLabel is a CoreMap with additional token-specific methods
//	      for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
//	        // this is the text of the token
//	        String word = token.get(TextAnnotation.class);
//	        // this is the POS tag of the token
//	        String pos = token.get(PartOfSpeechAnnotation.class);
//	        // this is the NER label of the token
//	        String ne = token.get(NamedEntityTagAnnotation.class);
//	        System.out.println(word + " " + pos + " " + ne);
//	      }
//
//	      // this is the parse tree of the current sentence
//	      Tree tree = sentence.get(TreeAnnotation.class);
//	      tree.pennPrint(out);
//	      
//	      
//	    }


	}
}
