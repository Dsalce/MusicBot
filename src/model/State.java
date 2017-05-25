package model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the states database table.
 * 
 */
@Entity
@Table(name="states")
@NamedQuery(name="State.findAll", query="SELECT s FROM State s")
public class State implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private int idState;

	private String state;

	//bi-directional many-to-one association to Music
	@OneToMany(mappedBy="state")
	private List<Music> musics;

	public State() {
	}
	
	public State(String state){
		this.state = state;
	}

	public int getIdState() {
		return this.idState;
	}

	public void setIdState(int idState) {
		this.idState = idState;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Music> getMusics() {
		return this.musics;
	}

	public void setMusics(List<Music> musics) {
		this.musics = musics;
	}

	public Music addMusic(Music music) {
		getMusics().add(music);
		music.setState(this);

		return music;
	}

	public Music removeMusic(Music music) {
		getMusics().remove(music);
		music.setState(null);

		return music;
	}

}