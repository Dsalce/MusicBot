package utiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Lexico implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String word;
    private String tipo;
    private List<String> tag = new ArrayList<String>();
    
	public Lexico (String word,String pos,String ner){
		this.word=word;
		this.tipo=pos;
		if(!ner.equals("")){
			this.tag.add(ner);
		}
	}
	public String getWord(){
		return this.word;
	}
	public String getTipo(){
		return this.tipo;
	}
	public void setTipo(String tipo){
		this.tipo = tipo;
	}
	public List<String> getTag(){
	    return this.tag;
	}	
	
	public void setTag(String tag){
		if(!tag.equals("")){
			this.tag.add(tag);
		}
	}	
	
	
  }
/*

        CC Coordinating conjunction
        CD Cardinal number
        DT Determiner
        EX Existential there
        FW Foreign word
        IN Preposition or subordinating conjunction
        JJ Adjective
        JJR Adjective, comparative
        JJS Adjective, superlative
        LS List item marker
        MD Modal
        NN Noun, singular or mass
        NNS Noun, plural
        NNP Proper noun, singular
        NNPS Proper noun, plural
        PDT Predeterminer
        POS Possessive ending
        PRP Personal pronoun
        PRP$ Possessive pronoun
        RB Adverb
        RBR Adverb, comparative
        RBS Adverb, superlative
        RP Particle
        SYM Symbol
        TO to
        UH Interjection
        VB Verb, base form
        VBD Verb, past tense
        VBG Verb, gerund or present participle
        VBN Verb, past participle
        VBP Verb, non­3rd person singular present
        VBZ Verb, 3rd person singular present
        WDT Wh­determiner
        WP Wh­pronoun
        WP$ Possessive wh­pronoun
        WRB Wh­adverb



*/