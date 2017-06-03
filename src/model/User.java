package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the users database table.
 * 
 */
@Entity
@Table(name="users")
@NamedQuery(name="User.findAll", query="SELECT u FROM User u")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int chatId;

	private byte admin;

	private int caso;

	private String lastMessage;

	private String password;

	private int sentimientoNegativo;

	private int sentimientoNeutral;

	private int sentimientoPositivo;

	private String state;

	//bi-directional many-to-one association to UserMusic
	@OneToMany(mappedBy="user")
	private List<UserMusic> userMusics;

	public User() {
	}
	
	public User(int chatID2, String state, String password, byte admin,int SentimientoNegativo, int SentimientoPositivo,String lastMessage, int sentimientoNeutral, int caso) {
		this.chatId = chatID2;
		this.state = state;
		this.password = password;
		this.admin = admin;
		this.sentimientoNegativo = SentimientoNegativo;
		this.sentimientoPositivo = SentimientoPositivo;
		this.lastMessage = lastMessage;
		this.sentimientoNeutral = sentimientoNeutral;
		this.caso = caso;
	}

	public int getChatId() {
		return this.chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public byte getAdmin() {
		return this.admin;
	}

	public void setAdmin(byte admin) {
		this.admin = admin;
	}

	public int getCaso() {
		return this.caso;
	}

	public void setCaso(int caso) {
		this.caso = caso;
	}

	public String getLastMessage() {
		return this.lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getSentimientoNegativo() {
		return this.sentimientoNegativo;
	}

	public void setSentimientoNegativo(int sentimientoNegativo) {
		this.sentimientoNegativo = sentimientoNegativo;
	}

	public int getSentimientoNeutral() {
		return this.sentimientoNeutral;
	}

	public void setSentimientoNeutral(int sentimientoNeutral) {
		this.sentimientoNeutral = sentimientoNeutral;
	}

	public int getSentimientoPositivo() {
		return this.sentimientoPositivo;
	}

	public void setSentimientoPositivo(int sentimientoPositivo) {
		this.sentimientoPositivo = sentimientoPositivo;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<UserMusic> getUserMusics() {
		return this.userMusics;
	}

	public void setUserMusics(List<UserMusic> userMusics) {
		this.userMusics = userMusics;
	}

	public UserMusic addUserMusic(UserMusic userMusic) {
		getUserMusics().add(userMusic);
		userMusic.setUser(this);

		return userMusic;
	}

	public UserMusic removeUserMusic(UserMusic userMusic) {
		getUserMusics().remove(userMusic);
		userMusic.setUser(null);

		return userMusic;
	}

}