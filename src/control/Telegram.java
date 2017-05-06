package control;
import java.util.Queue;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import jade.util.leap.LinkedList;



public class Telegram extends TelegramLongPollingBot {
	
	private SendMessage[] cola_mensajes = new SendMessage[20];
	private int numero_elementos = 0;
	
    @Override
    public void onUpdateReceived(Update update) {
    	// We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            cola_mensajes[numero_elementos] = message;
            numero_elementos++;
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
    	 return "MusicBotDasi_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "369821662:AAEI_zDBYEAqs5f01IuqBbyWQY4nDlg5NSU";
    }
    
    public SendMessage leerMensaje(){
    	if(cola_mensajes[0] != null){
    		numero_elementos--;
    		return cola_mensajes[0];
    	}else{
    		return null;
    	}
    	
    }
    
    public void enviarMensaje(SendMessage mensaje){
    	try {
            sendMessage(mensaje); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
    
    
}