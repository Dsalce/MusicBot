package utiles;

public class Lexico {
    private String word;
    private String tipo;
    private String tag;
    
	public Lexico (String word,String pos,String ner){
		this.word=word;
		this.tipo=pos;
		this.tag=ner;
	}
	public String getWord(){
		return this.word;
	}
	public String getTipo(){
		return this.tipo;
	}
	public String getTag(){
	    return this.tag;
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