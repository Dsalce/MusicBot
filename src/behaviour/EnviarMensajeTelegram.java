package behaviour;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import jade.core.behaviours.OneShotBehaviour;

public class EnviarMensajeTelegram extends OneShotBehaviour{
	
	static final long serialVersionUID = 1L;

	public void action() {
		//Enviar mensaje al usuario
		//Se crea el mensaje a enviar
    	/*SendMessage message = new SendMessage()
                .setChatId(chatID)
                .setText(mensaje);
    	try {
            sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }*/
	}
	
}
