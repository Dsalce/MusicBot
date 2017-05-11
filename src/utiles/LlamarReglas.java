package utiles;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import model.Message;
import model.TypeMessage;

public class LlamarReglas {

	
	public String parseWord(String palabra){
		Message message = new Message();
		try {
            // load up the knowledge base
	        KieServices ks = KieServices.Factory.get();
    	    KieContainer kContainer = ks.getKieClasspathContainer();
        	KieSession kSession = kContainer.newKieSession("ksession-rules");

            // go !
           
            message.setMessage(palabra);
            
            kSession.insert(message);
            kSession.fireAllRules();
        } catch (Throwable t) {
            t.printStackTrace();
        }
		return message.getTagMessage();
	}
	
	
}
