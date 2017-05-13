package model;

import java.io.Serializable;
import java.util.List;
import utiles.Lexico;

public class SerializableObject implements Serializable{
	
	private String chatId;
	
	private List<Lexico> lista;
	
	
	public SerializableObject(String chatId,List<Lexico> lista){
		this.chatId = chatId;
		this.lista = lista;
	}
}
