package utiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations.TreeAnnotation;
import edu.stanford.nlp.io.IOUtils;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.NamedEntityTagAnnotation;
<<<<<<< HEAD
import edu.stanford.nlp.ling.CoreAnnotations.NormalizedNamedEntityTagAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TrueCaseAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TrueCaseTextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.ling.tokensregex.Env;
import edu.stanford.nlp.ling.tokensregex.TokenSequencePattern;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
=======
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TrueCaseAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TrueCaseTextAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
>>>>>>> branch 'master' of https://github.com/chgomezr/MusicBot.git
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.RegexNERAnnotator;
import edu.stanford.nlp.util.CoreMap;


public class StanfordNPL {

<<<<<<< HEAD
	public  void prueba(){
		
		String text="monday";
		try {
			 text = utiles.Translator.callUrlAndParseResult("es", "en", text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
       Properties props = new Properties();
       props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse,ner");//,ner,regexner, sentiment");
       props.setProperty("ner.useSUTime", "0");
       //props.setProperty("sutime.rules", "D:\\DasiGit\\stanford-corenlp-full-2016-10-31\\sutime\\english.sutime.txt");
      // props.put("regexner.mapping", "D:\\DasiGit\\stanford-corenlp-full-2016-10-31\\tokensregex\\colorrules.txt");
       Env env = TokenSequencePattern.getNewEnv(); 
       env.bind("$RELDAY", "/today|yesterday|tomorrow|tonight|tonite/");
      // env.bind("$RELDAY", TokenSequencePattern.compile(env, "/today|yesterday|tomorrow|tonight|tonite/"));
      // CustomLemmaAnnotator prueba  =new CustomLemmaAnnotator("prueba",props);
       //C:\\Users\\dsalc\\git\\MusicBot\\
      
       
       StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
       
       Annotation annotation = pipeline.process(text);
       
       List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
       for (CoreMap sentence : sentences) {
       	
       	 for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
       	        // this is the text of the token
       	        String word = token.get(TextAnnotation.class);
       	        // this is the POS tag of the token
       	        String pos = token.get(PartOfSpeechAnnotation.class);
       	        // this is the NER label of the token
       	        String ne = token.get(NamedEntityTagAnnotation.class);
       	        String reg= token.get(NormalizedNamedEntityTagAnnotation.class);
       	     
    	        String ln= token.get(LemmaAnnotation.class);

       	        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne +" reg:"+reg+" leN:"+ln);
       	
       	        String text1 = token.get(TextAnnotation.class);
       	        String trueCase = token.get(TrueCaseAnnotation.class);
       	        String trueCaseText = token.get(TrueCaseTextAnnotation.class);
       	        System.out.printf("input:%s state:%s output:%s\n", text1, trueCase, trueCaseText);
       	        
       	 }
       	
          
           
           Tree tree = sentence.get(TreeAnnotation.class);
           System.out.println("parse tree:\n" + tree);

           // this is the Stanford dependency graph of the current sentence
           SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
           System.out.println("dependency graph:\n" + dependencies);
           
           
          }
       
}
		

/*
	
	public  void prueba2(){
		
		
		Properties props = new Properties();
	    boolean useRegexner = true;
	    if (useRegexner) {
	      props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner");
	      props.put("regexner.mapping", "locations.txt");
	    } else {
	      props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
	    }
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    // // We're interested in NER for these things (jt->loc->sal)
	    String[] tests =
	        {
	            "Partial invoice (€100,000, so roughly 40%) for the consignment C27655 we shipped on 15th August to London from the Make Believe Town depot. INV2345 is for the balance.. Customer contact (Sigourney) says they will pay this on the usual credit terms (30 days)."
	        };
	    List tokens = new ArrayList<>();

	    for (String s : tests) {

	      // run all Annotators on the passed-in text
	      Annotation document = new Annotation(s);
	      pipeline.annotate(document);

	      // these are all the sentences in this document
	      // a CoreMap is essentially a Map that uses class objects as keys and has values with
	      // custom types
	      List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	      StringBuilder sb = new StringBuilder();
	      
	      //I don't know why I can't get this code out of the box from StanfordNLP, multi-token entities
	      //are far more interesting and useful..
	      //TODO make this code simpler..
	      for (CoreMap sentence : sentences) {
	        // traversing the words in the current sentence, "O" is a sensible default to initialise
	        // tokens to since we're not interested in unclassified / unknown things..
	        String prevNeToken = "O";
	        String currNeToken = "O";
	        boolean newToken = true;
	        for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
	          currNeToken = token.get(NamedEntityTagAnnotation.class);
	          String word = token.get(TextAnnotation.class);
	          // Strip out "O"s completely, makes code below easier to understand
	          if (currNeToken.equals("O")) {
	            // LOG.debug("Skipping '{}' classified as {}", word, currNeToken);
	            if (!prevNeToken.equals("O") && (sb.length() > 0)) {
	              handleEntity(prevNeToken, sb, tokens);
	              newToken = true;
	            }
	            continue;
	          }

	          if (newToken) {
	            prevNeToken = currNeToken;
	            newToken = false;
	            sb.append(word);
	            continue;
	          }

	          if (currNeToken.equals(prevNeToken)) {
	            sb.append(" " + word);
	          } else {
	            // We're done with the current entity - print it out and reset
	            // TODO save this token into an appropriate ADT to return for useful processing..
	            handleEntity(prevNeToken, sb, tokens);
	            newToken = true;
	          }
	          prevNeToken = currNeToken;
	        }
	      }
	      
	      //TODO - do some cool stuff with these tokens!
	    }
	  
	}
	private void handleEntity(String inKey, StringBuilder inSb, List inTokens) {
	    inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
	    inSb.setLength(0);
	  }
	
	class EmbeddedToken {

		  private String name;
		  private String value;

		  public String getName() {
		    return name;
		  }

		  public String getValue() {
		    return value;
		  }

		  public EmbeddedToken(String name, String value) {
		    super();
		    this.name = name;
		    this.value = value;
		  }
		}*/
	public static String sentiment(String text){
=======
	/*public static void prueba(){
		String sentiment="" ;
		String text="monday";
		try {
			 text = utiles.Translator.callUrlAndParseResult("es", "en", text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
       Properties props = new Properties();
       props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse,ner, sentiment");//,ner,regexner, sentiment");
       //props.put("regexner.mapping", "D:\\DasiGit\\stanford-corenlp-full-2016-10-31\\sutime\\english.sutime.txt");
       //props.put("regexner.mapping", "D:\\DasiGit\\stanford-corenlp-full-2016-10-31\\sutime\\english.sutime.txt");

       CustomLemmaAnnotator prueba  =new CustomLemmaAnnotator("prueba",props);
       //C:\Users\dsalc\git\MusicBot
      
       
       StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
       
       Annotation annotation = pipeline.process(text);
       
       List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
       for (CoreMap sentence : sentences) {
       	
       	 for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
       	        // this is the text of the token
       	        String word = token.get(TextAnnotation.class);
       	        // this is the POS tag of the token
       	        String pos = token.get(PartOfSpeechAnnotation.class);
       	        // this is the NER label of the token
       	        String ne = token.get(NamedEntityTagAnnotation.class);
       	        String reg= token.get(NormalizedNamedEntityTagAnnotation.class);
       	     
    	        String ln= token.get(LemmaAnnotation.class);

       	        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne +" reg:"+reg+" leN:"+ln);
       	
       	        String text1 = token.get(TextAnnotation.class);
       	        String trueCase = token.get(TrueCaseAnnotation.class);
       	        String trueCaseText = token.get(TrueCaseTextAnnotation.class);
       	        System.out.printf("input:%s state:%s output:%s\n", text1, trueCase, trueCaseText);
       	        
       	 }
       	
          sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            
           System.out.println(sentiment+ "\t" + "\t" + sentence);
           
           Tree tree = sentence.get(TreeAnnotation.class);
           System.out.println("parse tree:\n" + tree);

           // this is the Stanford dependency graph of the current sentence
           SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
           System.out.println("dependency graph:\n" + dependencies);
           
           String[] sentimentText = { "Very Negative","Negative", "Neutral", "Positive", "Very Positive"};
           for (CoreMap sentence1 : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree1 = sentence1.get(SentimentAnnotatedTree.class);
            int score = RNNCoreAnnotations.getPredictedClass(tree1);
            System.out.println(sentimentText[score]);
           }
       }
}*/
		


	
	public  void prueba2(){
		
		
		Properties props = new Properties();
	    boolean useRegexner = true;
	    if (useRegexner) {
	      props.put("annotators", "tokenize, ssplit, pos, lemma, ner, regexner");
	      //props.put("regexner.mapping", "locations.txt");
	    } else {
	      props.put("annotators", "tokenize, ssplit, pos, lemma, ner");
	    }
	    
	    props.setProperty("ner.useSUTime", "0");

/*
	    
	    var curDir = Environment.CurrentDirectory;
	    System.IO.Directory.SetCurrentDirectory(jarRoot);
	    var pipeline = new StanfordCoreNLP(props);
	    System.IO.Directory.SetCurrentDirectory(curDir);
	    
	    */
	    StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	    // // We're interested in NER for these things (jt->loc->sal)
	    String[] tests =
	        {
	            "Partial invoice (€100,000, so roughly 40%) for the consignment C27655 we shipped on 15th August to London from the Make Believe Town depot. INV2345 is for the balance.. Customer contact (Sigourney) says they will pay this on the usual credit terms (30 days)."
	        };
	    List tokens = new ArrayList<>();

	    for (String s : tests) {

	      // run all Annotators on the passed-in text
	      Annotation document = new Annotation(s);
	      pipeline.annotate(document);

	      // these are all the sentences in this document
	      // a CoreMap is essentially a Map that uses class objects as keys and has values with
	      // custom types
	      List<CoreMap> sentences = document.get(SentencesAnnotation.class);
	      StringBuilder sb = new StringBuilder();
	      
	      //I don't know why I can't get this code out of the box from StanfordNLP, multi-token entities
	      //are far more interesting and useful..
	      //TODO make this code simpler..
	      for (CoreMap sentence : sentences) {
	        // traversing the words in the current sentence, "O" is a sensible default to initialise
	        // tokens to since we're not interested in unclassified / unknown things..
	        String prevNeToken = "O";
	        String currNeToken = "O";
	        boolean newToken = true;
	        for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
	          currNeToken = token.get(NamedEntityTagAnnotation.class);
	          String word = token.get(TextAnnotation.class);
	          // Strip out "O"s completely, makes code below easier to understand
	          if (currNeToken.equals("O")) {
	            // LOG.debug("Skipping '{}' classified as {}", word, currNeToken);
	            if (!prevNeToken.equals("O") && (sb.length() > 0)) {
	              handleEntity(prevNeToken, sb, tokens);
	              newToken = true;
	            }
	            continue;
	          }

	          if (newToken) {
	            prevNeToken = currNeToken;
	            newToken = false;
	            sb.append(word);
	            continue;
	          }

	          if (currNeToken.equals(prevNeToken)) {
	            sb.append(" " + word);
	          } else {
	            // We're done with the current entity - print it out and reset
	            // TODO save this token into an appropriate ADT to return for useful processing..
	            handleEntity(prevNeToken, sb, tokens);
	            newToken = true;
	          }
	          prevNeToken = currNeToken;
	        }
	      }
	      
	      //TODO - do some cool stuff with these tokens!
	    }
	  
	}
	private void handleEntity(String inKey, StringBuilder inSb, List inTokens) {
	    inTokens.add(new EmbeddedToken(inKey, inSb.toString()));
	    inSb.setLength(0);
	  }
	
	class EmbeddedToken {

		  private String name;
		  private String value;

		  public String getName() {
		    return name;
		  }

		  public String getValue() {
		    return value;
		  }

		  public EmbeddedToken(String name, String value) {
		    super();
		    this.name = name;
		    this.value = value;
		  }
		}
	public static String parser(String text){
>>>>>>> branch 'master' of https://github.com/chgomezr/MusicBot.git
		String sentiment="" ;
	    //text=text.toLowerCase();
		try {
				 text = utiles.Translator.callUrlAndParseResult("es", "en", text);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
	        Properties props = new Properties();
	        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse,truecase, sentiment");
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
	        
	        Annotation annotation = pipeline.process(text);
	        
	        
	     //  Annotation annotation = pipeline.process(text);
	        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	        for (CoreMap sentence : sentences) {
	        	
<<<<<<< HEAD
	        	
=======
	        	 for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
	        	        // this is the text of the token
	        	        String word = token.get(TextAnnotation.class);
	        	        // this is the POS tag of the token
	        	        String pos = token.get(PartOfSpeechAnnotation.class);
	        	        // this is the NER label of the token
	        	        String ne = token.get(NamedEntityTagAnnotation.class);
	        	        
	        	        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne);
	        	
	        	        String text1 = token.get(TextAnnotation.class);
	        	        String trueCase = token.get(TrueCaseAnnotation.class);
	        	        String trueCaseText = token.get(TrueCaseTextAnnotation.class);
	        	        System.out.printf("input:%s state:%s output:%s\n", text1, trueCase, trueCaseText);
	        	        
	        	 }
>>>>>>> branch 'master' of https://github.com/chgomezr/MusicBot.git
	             sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	             
	            System.out.println(sentiment+ "\t" + "\t" + sentence);
	            
<<<<<<< HEAD
	          

	            String[] sentimentText = { "Very Negative","Negative", "Neutral", "Positive", "Very Positive"};
	            for (CoreMap sentence1 : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
	             Tree tree1 = sentence1.get(SentimentAnnotatedTree.class);
	             int score = RNNCoreAnnotations.getPredictedClass(tree1);
	             System.out.println(sentimentText[score]);
	            }
			 
	   }
=======
	            Tree tree = sentence.get(TreeAnnotation.class);
	            System.out.println("parse tree:\n" + tree);

	            // this is the Stanford dependency graph of the current sentence
	            SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
	            System.out.println("dependency graph:\n" + dependencies);
			 
	}
>>>>>>> branch 'master' of https://github.com/chgomezr/MusicBot.git
			return sentiment;
	
    }
}
}
