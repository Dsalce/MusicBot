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
	
	public User(int chatId,String state,String password,boolean admin) {
		this.chatId = chatId;
		this.state = state;
		this.password = password;
		this.admin = admin;
		
	}

	@Id
	private int chatId;

	private String state;
	
	private String password;
	
	private boolean admin;


	public boolean isAdmin() {
		return admin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	//bi-directional many-to-one association to UserMusic
	@OneToMany(mappedBy="user")
	private List<UserMusic> userMusics;

	
	public int getIdUser() {
		return this.chatId;
	}

	public void setIdUser(int chatId) {
		this.chatId = chatId;
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