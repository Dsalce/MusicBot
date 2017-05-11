package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the type_message database table.
 * 
 */
@Entity
@Table(name="type_message")
@NamedQuery(name="TypeMessage.findAll", query="SELECT t FROM TypeMessage t")
public class TypeMessage implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idTypeMessage;

	private String typeMessage;

	//bi-directional many-to-one association to Message
	@OneToMany(mappedBy="typeMessage")
	private List<Message> messages;

	public TypeMessage() {
	}

	public int getIdTypeMessage() {
		return this.idTypeMessage;
	}

	public void setIdTypeMessage(int idTypeMessage) {
		this.idTypeMessage = idTypeMessage;
	}

	public String getTypeMessage() {
		return this.typeMessage;
	}

	public void setTypeMessage(String typeMessage) {
		this.typeMessage = typeMessage;
	}

	public List<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public Message addMessage(Message message) {
		getMessages().add(message);
		message.setTypeMessage(this);

		return message;
	}

	public Message removeMessage(Message message) {
		getMessages().remove(message);
		message.setTypeMessage(null);

		return message;
	}

}