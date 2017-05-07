package behaviour;

import jade.core.behaviours.CyclicBehaviour;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import control.Telegram;
import control.main;

public class LeerMensajeTelegram extends CyclicBehaviour{

	private static final long serialVersionUID = 1L;
	
	private Telegram tele = main.telegram;
	
	//Variable para identificar si es usuario(1) o administrador(2)
	private int opcion;   
	
	public void action() {
		// TODO Auto-generated method stub
		SendMessage mensaje = tele.leerMensaje(); 
		
		/******
		 * 
		 * TRATAMIENTO DEL MENSAJE PARTE DE SALCEDO
		 * 
		 */
		
		switch(opcion){
			//Caso de ser usuario
			case 1:
				
				break;
				
			//Caso de ser administrador
			case 2:
				break;
				
		}
		
	}

}
