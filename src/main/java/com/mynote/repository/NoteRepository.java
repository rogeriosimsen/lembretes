package com.mynote.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.mynote.model.NoteModel;

@Repository
public interface NoteRepository extends CrudRepository<NoteModel, Long>{

}
