package utiles;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;


public class EnviarMensajeTelegram extends TelegramLongPollingBot{
	
	public void enviar(String chatID, String mensaje) {
		//Enviar mensaje al usuario
		//Se crea el mensaje a enviar
    	SendMessage message = new SendMessage()
                .setChatId(chatID)
                .setText(mensaje);
    	try {
            sendMessage(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
	}

	@Override
	public String getBotUsername() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onUpdateReceived(Update arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getBotToken() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
