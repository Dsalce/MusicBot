package model;

import java.io.Serializable;
import java.util.List;
import utiles.Lexico;

public class SerializableObject implements Serializable{

	private static final long serialVersionUID = 1L;

	private String chatId;
	
	private List<Lexico> lista;

	private String texto;
	
	
	public SerializableObject(String chatId,List<Lexico> lista, String texto){
		this.chatId = chatId;
		this.lista = lista;
		this.texto = texto;
	}


	public String getTexto() {
		return texto;
	}


	public void setTexto(String texto) {
		this.texto = texto;
	}


	public String getChatId() {
		return chatId;
	}


	public void setChatId(String chatId) {
		this.chatId = chatId;
	}


	public List<Lexico> getLista() {
		return lista;
	}


	public void setLista(List<Lexico> lista) {
		this.lista = lista;
	}
}
