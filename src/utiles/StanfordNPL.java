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


	public  void prueba(){
	
		
		String text="buenos dias";
		try {
			 text = utiles.Translator.callUrlAndParseResult("es", "en", text);
			 System.out.println(utiles.Translator.callUrlAndParseResult("en", "es", text)); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
       Properties props = new Properties();
       props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse,ner");//,ner,regexner, sentiment");
       props.setProperty("ner.useSUTime", "0");
    // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution 

       StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
       
       CustomLemmaAnnotator prueba  =new CustomLemmaAnnotator("prueba",props);
       //C:\\Users\\dsalc\\git\\MusicBot\\
      
       
       //StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
       
       Annotation annotation = new Annotation("A");
       
       prueba.annotate(annotation);
       
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
		
	public static void sentiment(String text){

		String sentiment="" ;
		String text1="salcedo";
		try {
			 text1 = utiles.Translator.callUrlAndParseResult("es", "en", text1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
       Properties props = new Properties();
       props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse,ner");//,ner,regexner, sentiment");
       props.setProperty("ner.useSUTime", "0");
       props.setProperty("annotators", "tokenize, ssplit, parse, sentiment");//,ner,regexner, sentiment");
       //props.put("regexner.mapping", "D:\\DasiGit\\stanford-corenlp-full-2016-10-31\\sutime\\english.sutime.txt");
       //props.put("regexner.mapping", "D:\\DasiGit\\stanford-corenlp-full-2016-10-31\\sutime\\english.sutime.txt");

       CustomLemmaAnnotator prueba  =new CustomLemmaAnnotator("prueba",props);
       //C:\Users\dsalc\git\MusicBot
      
       
       StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
       
       Annotation annotation = pipeline.process(text1);
       
       List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
       for (CoreMap sentence : sentences) {
       	
          sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
            
           System.out.println(sentiment+ "\t" + "\t" + sentence);
           
           String[] sentimentText = { "Very Negative","Negative", "Neutral", "Positive", "Very Positive"};
           for (CoreMap sentence1 : annotation.get(CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree1 = sentence1.get(SentimentAnnotatedTree.class);
            int score = RNNCoreAnnotations.getPredictedClass(tree1);
            System.out.println(sentimentText[score]);
           }
       }
}
	
	
}