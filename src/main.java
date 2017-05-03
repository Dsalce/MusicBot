import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import Agentes.AgenteUsuario;



public class main {

		
		public static void main(String[] args) {

	        ApiContextInitializer.init();
	        //hola
/*
	        TelegramBotsApi botsApi = new TelegramBotsApi();

	        try {
	            botsApi.registerBot(new prueba());
	        } catch (TelegramApiException e) {
	            e.printStackTrace();
	        }
	        */
	        AgenteUsuario agente = new AgenteUsuario();
	        
	        
	    }


	

	 
}
