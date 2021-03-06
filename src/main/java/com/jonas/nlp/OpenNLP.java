package com.jonas.nlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import opennlp.tools.cmdline.parser.ParserTool;
import opennlp.tools.coref.DefaultLinker;
import opennlp.tools.coref.DiscourseEntity;
import opennlp.tools.coref.Linker;
import opennlp.tools.coref.LinkerMode;
import opennlp.tools.coref.mention.DefaultParse;
import opennlp.tools.coref.mention.Mention;
import opennlp.tools.namefind.NameFinderME;
import opennlp.tools.namefind.TokenNameFinder;
import opennlp.tools.namefind.TokenNameFinderModel;
import opennlp.tools.parser.AbstractBottomUpParser;
import opennlp.tools.parser.Parse;
import opennlp.tools.parser.Parser;
import opennlp.tools.parser.ParserFactory;
import opennlp.tools.parser.ParserModel;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTagger;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetector;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.Span;



import com.jonas.nlp.FileUtilities;

@SuppressWarnings("unused")
public class OpenNLP {

	/**
	 * Open NLP properties
	 */
	static final private String PROPERTIES_FILENAME = "C:/Workspace/application_sandbox/data/opennlp.properties";
	static final private String COREF_DIR = "opennlp.coref.dir";

	/**
	 * The Named Entity Types of interest. Each requires a corresponding Open NLP
	 * model file.
	 */
	private static final String[] NAME_TYPES =
		{ "person", "organization", "location" };

	/**
	 * Properties containing the Open NLP binary paths
	 */
	private Properties _properties = null;

	/**
	 * OpenNLP components, each is lazily initialized.  Don't directly access, but use:
	 * <ul>
	 * <li>{@link #detectSentences(String)}</li>
	 * <li>{@link #tokenize(String)}</li>
	 * <li>{@link #nameFinders()}</li>
	 * <li>{@link #parse(Parse)}</li>
	 * <li>{@link #linker()}</li>
	 * </ul>
	 */
	private SentenceDetector _sentenceDetector = null;
	private Tokenizer _tokenizer = null;
	private POSTagger _posTagger = null;
	final private Map<String, TokenNameFinder> _nameFinderMap =
			new HashMap<String, TokenNameFinder>();
	private Parser _parser = null;
	private Linker _linker = null;

	/**
	 * Read the content from the specified file and return a list of detected
	 * sentences.
	 * 
	 * @param file
	 *           the file to read
	 * @param cs
	 *           the file charset
	 * @return the detected sentences
	 * 
	 * @throws IOException
	 *            If an error occurs while loading the file
	 */
	public String[] detectSentences(final File file, final Charset cs)
			throws IOException {
		// reading individual lines instead of raw content because with news stories,
		// some sentence lines don't end in punctuation (especially headings, etc.)
		final List<String> lines = FileUtilities.loadLines(file, cs);
		final ArrayList<String> sentences = new ArrayList<String>();
		for (final String content : lines) {
			final String[] detected = detectSentences(content);
			for (int idx=0; idx < detected.length; idx++) {
				final String sentence = detected[idx].trim();
				// check for ending with punctuation
				if (sentence.matches(".*\\p{P}$")) {
					sentences.add(sentence);
				} else {
					sentences.add(sentence + ".");
				}
			}
		}
		return sentences.toArray(new String[0]);
	}

	/**
	 * Break the given content into sentences.
	 * <p>
	 * The sentence detector is lazily initialized on first use.
	 * </p>
	 * 
	 * @param content the content to break into sentences
	 * @return the detected sentences
	 */
	public String[] detectSentences(final String content) {
		if (_sentenceDetector == null) {
			// lazy initialize
			InputStream modelIn = null;
			try {
				// sentence detector
				final String location = getProperty("opennlp.sentence");
				modelIn = new FileInputStream(new File((location)));
				final SentenceModel sentenceModel = new SentenceModel(modelIn);
				modelIn.close();
				_sentenceDetector = new SentenceDetectorME(sentenceModel);
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			} finally {
				if (modelIn != null) {
					try {
						modelIn.close();
					} catch (final IOException e) {}
				}
			}
		}

		// detect sentences
		return _sentenceDetector.sentDetect(content);
	}

	/**
	 * Tokenize the given sentence.
	 * <p>
	 * The tokenizer is lazily initialized on first use.
	 * </p>
	 * 
	 * @param sentence
	 *           a sentence to tokenize
	 * @return the individual tokens
	 */
	public String[] tokenize(final String sentence) {
		// tokenize
		return tokenizer().tokenize(sentence);
	}

	/**
	 * @return the lazily-initialized tokenizer
	 */
	private Tokenizer tokenizer() {
		if (_tokenizer == null) {
			// lazy initialize
			InputStream modelIn = null;
			try {
				// tokenizer
				modelIn = new FileInputStream(new File((
						getProperty("opennlp.tokenizer"))));
				final TokenizerModel tokenModel = new TokenizerModel(modelIn);
				modelIn.close();
				_tokenizer = new TokenizerME(tokenModel);
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			} finally {
				if (modelIn != null) {
					try {
						modelIn.close();
					} catch (final IOException e) {}
				}
			}
		}
		return _tokenizer;
	}

	/**
	 * Detect the part of speech tags for the given tokens in a sentence.
	 * <p>
	 * The tagger is lazily initialized on first use.
	 * </p>
	 * 
	 * @param tokens
	 *           an array of sentence tokens to tag
	 * @return the individual part-of-speech tags
	 */
	public String[] tagPartOfSpeech(final String[] tokens) {
		if (_posTagger == null) {
			// lazy initialize
			InputStream modelIn = null;
			try {
				// tagger
				modelIn = new FileInputStream(new File((
						getProperty("opennlp.pos"))));
				final POSModel posModel = new POSModel(modelIn);
				modelIn.close();
				_posTagger = new POSTaggerME(posModel);
			} catch (final IOException ioe) {
				ioe.printStackTrace();
			} finally {
				if (modelIn != null) {
					try {
						modelIn.close();
					} catch (final IOException e) {}
				}
			}
		}
		return _posTagger.tag(tokens);
	}


	/**
	 * Find named entities in a tokenized sentence.
	 * <p>
	 * Must call {@link #clearNamedEntityAdaptiveData()} after finding all named
	 * entities in a single document.
	 * </p>
	 *
	 *
	 * @param sentence
	 *           the sentence text
	 * @param tokens
	 *           the sentence tokens
	 * @return a collection of named entity references
	 */
	public List<Span> findNamedEntities(final String sentence, final String[] tokens) {
		final List<Span> entities = new LinkedList<Span>();

		// use each type of finder to identify named entities 
		for (final TokenNameFinder finder : nameFinders()) {
			entities.addAll(Arrays.asList(finder.find(tokens)));
		}

		return entities;
	}

	/**
	 * Must be called between documents or can negatively impact detection rate.
	 */
	public void clearNamedEntityAdaptiveData() {
		for (final TokenNameFinder finder : nameFinders()) {
			finder.clearAdaptiveData();
		}
	}

	/**
	 * @return the lazily-initialized token name finders
	 */
	private TokenNameFinder[] nameFinders() {
		final TokenNameFinder[] finders = new TokenNameFinder[NAME_TYPES.length];
		// one for each name type
		for (int i = 0; i < NAME_TYPES.length; i++) {
			finders[i] = nameFinder(NAME_TYPES[i]);
		}
		return finders;
	}

	/**
	 * @param type the name type recognizer to load
	 * @return the lazily-initialized name token finder
	 */
	private TokenNameFinder nameFinder(final String type) {
		if (!_nameFinderMap.containsKey(type)) {
			final TokenNameFinder finder = createNameFinder(type);
			_nameFinderMap.put(type, finder);
		}
		return _nameFinderMap.get(type);
	}

	/**
	 * @param type the name type recognizer to load
	 * @return the lazily-initialized name token finder
	 */
	private TokenNameFinder createNameFinder(final String type) {
		InputStream modelIn = null;
		try {
			modelIn = new FileInputStream(new File((
					String.format(getProperty("opennlp.namefinder.format"), type))));
			final TokenNameFinderModel nameFinderModel = new TokenNameFinderModel(modelIn);
			modelIn.close();
			return new NameFinderME(nameFinderModel);
		} catch (final IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if (modelIn != null) {
				try {
					modelIn.close();
				} catch (final IOException e) {}
			}
		}
		return null;
	}

	/**
	 * Find Discourse entities (entity mentions) in a document.
	 * 
	 * @param sentences the document sentences
	 * @return the recognized discourse entities.
	 */
	public DiscourseEntity[] findEntityMentions(final String[] sentences) {

		// list of document mentions
		final List<Mention> document = new ArrayList<Mention>();

		for (int i=0; i < sentences.length; i++) {
			// generate the sentence parse tree
			final Parse parse = parseSentence(sentences[i]);

			final DefaultParse parseWrapper = new DefaultParse(parse, i);
			final Mention[] extents = linker().getMentionFinder().getMentions(parseWrapper);

			//Note: taken from TreebankParser source...
			for (int ei=0, en=extents.length; ei<en; ei++) {
				// construct new parses for mentions which don't have constituents.
				if (extents[ei].getParse() == null) {
					// not sure how to get head index, but its not used at this point
					final Parse snp = new Parse(parse.getText(), extents[ei].getSpan(), "NML", 1.0, 0);
					parse.insert(snp);
					extents[ei].setParse(new DefaultParse(snp, i));
				}
			}
			document.addAll(Arrays.asList(extents));
		}

		if (!document.isEmpty()) {
			return linker().getEntities(document.toArray(new Mention[0]));
		}
		return new DiscourseEntity[0];
	}

	/**
	 * @return the lazily-initialized linker
	 */
	private Linker linker() {
		if (_linker == null) {
			try {
				// linker
				_linker = new DefaultLinker(
						// LinkerMode should be TEST
						//Note: I tried EVAL for a long time before realizing that was the problem


						getProperty(COREF_DIR), LinkerMode.TEST);

			} catch (final IOException ioe) {
			}
		}
		return _linker;
	}

	/**
	 * Convert the provided sentence and corresponding tokens into a parse tree.
	 * 
	 * @param text the sentence text
	 * @return the parse tree
	 */
	public Parse parseSentence(final String text) {

		final Parse p = new Parse(text,
				// a new span covering the entire text
				new Span(0, text.length()),
				// the label for the top if an incomplete node
				AbstractBottomUpParser.INC_NODE,
				// the probability of this parse...uhhh...? 
				1,
				// the token index of the head of this parse
				0);

		final Span[] spans = tokenizer().tokenizePos(text);

		for (int idx=0; idx < spans.length; idx++) {
			final Span span = spans[idx];
			// flesh out the parse with token sub-parses
			p.insert(new Parse(text, span,
					AbstractBottomUpParser.TOK_NODE,
					0,
					idx));
		}

		return parse(p);
	}

	/**
	 * Parse the given parse object.
	 * <p>
	 * The parser is lazily initialized on first use.
	 * </p>
	 * 
	 * @param p the parse object
	 * @return the parsed parse
	 */
	private Parse parse(final Parse p) {
		return parser().parse(p);
	}

	private Parser parser() {
		if (_parser == null) {
			// lazily initialize the parser
			InputStream modelIn = null;
			try {
				// parser
				modelIn = new FileInputStream(new File((
						getProperty("opennlp.parser"))));
				final ParserModel parseModel = new ParserModel(modelIn);
				modelIn.close();
				_parser = ParserFactory.create(parseModel);
			} catch (final IOException ioe) {
			} finally {
				if (modelIn != null) {
					try {
						modelIn.close();
					} catch (final IOException e) {}
				}
			}
		}
		// return the parser
		return _parser;
	}   

	/**
	 * Gets the specified application property.
	 * 
	 * @param property
	 *           property of interest
	 * @return value of the specified property
	 */
	private String getProperty(final String property) {
		if (_properties == null) {
			_properties = new Properties();

			InputStream input = null;
			try {
				input = new FileInputStream(new File(PROPERTIES_FILENAME));
				_properties.load(input);
			}
			catch (final IOException ioe) {
				ioe.printStackTrace();
				System.exit(1);
			}
			finally {
				if (input != null) {
					try {
						input.close();
					}
					catch (final IOException ioe) {
					}
				}
			}
		}

		return _properties.getProperty(property);
	}
}