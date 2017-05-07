package behaviour;

import jade.core.behaviours.Behaviour;
import jade.core.behaviours.OneShotBehaviour;

/*
 * Este comportamiento se encarga de seleccionar el agente para hablar con el usuario
 */
public class DescubrirTipoUsusuario extends Behaviour {

	private static final long serialVersionUID = 1L;
	
	
	
	//Se comienza por el primer paso
	private int step = 1;

	public void action() {
		
		
		
		//Leer los argumentos donde se encuentran los agentes encontrados
		Object[] prueba = myAgent.getArguments();
		switch (step) {
			
	      	case 1:
	      		
	      		myAgent.addBehaviour(new OneShotBehaviour(myAgent) {
		          public void action() {
		            System.out.println("");
		          } 
		        });
	      		break;
		        
		      case 2:
		    	// El segundo paso es hablar con el usuario
		        
		    	  break;
		        
		      case 3:
		    	// El segundo paso es identificar que tipo de usuario esta hablando
		        System.out.println("Operation 2. Adding one-shot behaviour");
		        
		        break;
		        
		      case 4:
		        // Perform operation 3: print out a message
		        System.out.println("Operation 4");
		        break;
		}
		step++;
    } 

	//Se encarga de finalizar las operaciones a realizar
    public boolean done() {
      return step == 5;
    } 

    //Se encarga de finalizar el comportamiento
    public int onEnd() {
      return super.onEnd();
    } 
  }    