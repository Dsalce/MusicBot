package utiles;

import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.util.CoreMap;


public class StanfordNPL {

	public static String parser(String text){
		String sentiment="" ;
		try {
				 text = utiles.Translator.callUrlAndParseResult("es", "en", text);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        Properties props = new Properties();
	        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
	        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

	        Annotation annotation = pipeline.process(text);
	        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
	        for (CoreMap sentence : sentences) {
	             sentiment = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
	            System.out.println(sentiment + "\t" + sentence);
	        }
			return sentiment;
	}
	
}
