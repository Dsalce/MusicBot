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

import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.RegexNERAnnotator;
import edu.stanford.nlp.util.CoreMap;


public class StanfordNPL {

	private static  StanfordCoreNLP pipeline_word;
	private static  StanfordCoreNLP pipeline_sent;
	
	public static void  loadModules(){
		Properties props;
		Properties propsSent;

	    props = new Properties();
	    props.setProperty("annotators", "tokenize, ssplit, pos, lemma,ner");//,ner,regexner, sentiment");
	    props.setProperty("ner.useSUTime", "0");
	   
	    propsSent= new Properties();
	    propsSent.setProperty("annotators", "tokenize, ssplit, parse, sentiment");
	    
	    
	    
	    pipeline_word = new StanfordCoreNLP(props);
	    pipeline_sent = new StanfordCoreNLP(propsSent);
	}

	public static List<Lexico> parser(String text){
	   List<Lexico> lex=new ArrayList<Lexico>();
		
		
		try {
			text = utiles.Translator.callUrlAndParseResult("es", "en", text);
			// System.out.println(utiles.Translator.callUrlAndParseResult("en", "es", text)); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
       
		String word="";
       Annotation annotation = pipeline_word.process(text);
       
       List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
       for (CoreMap sentence : sentences) {
       	
       	 for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
       	        // this is the text of the token
       	        if(token.get(LemmaAnnotation.class).contains("www")){
       	        	 word=token.get(TextAnnotation.class);
       	        }else{
       	        	 word=token.get(LemmaAnnotation.class).toUpperCase();
       	        }
       	        // this is the POS tag of the token
       	        String pos = token.get(PartOfSpeechAnnotation.class);
       	        // this is the NER label of the token
       	        String ne = token.get(NamedEntityTagAnnotation.class);
       	        if(ne.equals("O")){
       	        	ne="";
       	        }
       	      //  String reg= token.get(NormalizedNamedEntityTagAnnotation.class);
       	        lex.add(new Lexico(word,pos,ne));
    	      //  String ln= token.get(LemmaAnnotation.class);

       	        System.out.println("word: " + word + " pos: " + pos + " ne:" + ne );
       	     
       	 }    
      }
       return lex;
}
		
	public static String sentiment(String text){

		String sentiment="" ;
		try {
			text = utiles.Translator.callUrlAndParseResult("es", "en", text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
       Annotation annotation = pipeline_sent.process(text.toLowerCase());
       
       List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
       for (CoreMap sentence : sentences) {
          sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
       }
       return sentiment;
   }
	
}
	
	
