package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the musics database table.
 * 
 */
@Entity
@Table(name="musics")
@NamedQuery(name="Music.findAll", query="SELECT m FROM Music m")
public class Music implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idMusic;

	private String name;

	private String url;

	//bi-directional many-to-one association to State
	@ManyToOne
	@JoinColumn(name="IdState")
	private State state;

	//bi-directional many-to-one association to UserMusic
	@OneToMany(mappedBy="music")
	private List<UserMusic> userMusics;

	public Music() {
	}

	public int getIdMusic() {
		return this.idMusic;
	}

	public void setIdMusic(int idMusic) {
		this.idMusic = idMusic;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public State getState() {
		return this.state;
	}

	public void setState(State state) {
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
		userMusic.setMusic(this);

		return userMusic;
	}

	public UserMusic removeUserMusic(UserMusic userMusic) {
		getUserMusics().remove(userMusic);
		userMusic.setMusic(null);

		return userMusic;
	}

}