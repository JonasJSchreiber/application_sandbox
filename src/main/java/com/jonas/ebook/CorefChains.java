package com.jonas.ebook;

import java.util.Map;
import java.util.Properties;

import edu.stanford.nlp.dcoref.CorefChain;
import edu.stanford.nlp.dcoref.CorefCoreAnnotations.CorefChainAnnotation;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;

public class CorefChains {

	StanfordCoreNLP pipeline;
	
	public CorefChains() {
		Properties props = new Properties();
		props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
		this.pipeline = new StanfordCoreNLP(props);
	}

	public Map<Integer, CorefChain> getCoreferences(String str) {
		Annotation document = new Annotation(str);
		pipeline.annotate(document);
		Map<Integer, CorefChain> graph = document.get(CorefChainAnnotation.class);
		return graph;
	}
}
