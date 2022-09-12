package com.mynote.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class DateModel implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String date;
	
	@OneToMany(mappedBy = "dateModel", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<NoteModel> noteModel = new ArrayList<NoteModel>();


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<NoteModel> getNoteModel() {
		return noteModel;
	}

	public void setNoteModel(List<NoteModel> noteModel) {
		this.noteModel = noteModel;
	}

}
