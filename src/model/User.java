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

	private String state;

	private String password;

	private int admin;

	public int getChatId() {
		return chatId;
	}

	public void setChatId(int chatId) {
		this.chatId = chatId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getAdmin() {
		return admin;
	}

	public void setAdmin(int admin) {
		this.admin = admin;
	}

	//bi-directional many-to-one association to UserMusic
	@OneToMany(mappedBy="user")
	private List<UserMusic> userMusics;

	public User() {
	}

	

	public User(int chatID2, String state, String password, int admin) {
		this.chatId = chatID2;
		this.state = state;
		this.password = password;
		this.admin = admin;
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