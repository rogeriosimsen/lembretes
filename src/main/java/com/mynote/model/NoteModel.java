package com.mynote.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ForeignKey;

import com.fasterxml.jackson.annotation.JsonIgnore;

@SuppressWarnings("deprecation")
@Entity
public class NoteModel implements Serializable{
	
	public static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String noteText;
	
	private String date;
	
	@JsonIgnore
	@ForeignKey(name = "dateModel_id")
	@ManyToOne(optional = true)
	private DateModel dateModel;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNoteText() {
		return noteText;
	}

	public void setNoteText(String noteText) {
		this.noteText = noteText;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}

	public DateModel getDateModel() {
		return dateModel;
	}

	public void setDateModel(DateModel dateModel) {
		this.dateModel = dateModel;
	}
	
	
	
	

}
