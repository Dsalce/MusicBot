package control;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.LinkedList;




public class Telegram extends TelegramLongPollingBot {
		
	LinkedList cola_mensajes =new LinkedList();
	
    @Override
    public void onUpdateReceived(Update update) {
    	// We check if the update has a message and the message has text
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage message = new SendMessage() // Create a SendMessage object with mandatory fields
                    .setChatId(update.getMessage().getChatId())
                    .setText(update.getMessage().getText());
            cola_mensajes.add(message);
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
    	return (SendMessage)cola_mensajes.poll();    	
    }
    
    
    
}