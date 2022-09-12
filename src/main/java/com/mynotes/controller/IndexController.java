package com.mynotes.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mynote.model.DateModel;
import com.mynote.model.NoteModel;
import com.mynote.repository.DateRepository;
import com.mynote.repository.NoteRepository;

@CrossOrigin
@Controller
@RequestMapping(value = "/mynotes")
public class IndexController {
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	DateRepository dateRepository;
	
	@PostMapping(value = "/salvar")
	public ResponseEntity<?> saveTest(@RequestBody NoteModel noteModel){ 
		
		DateModel dateModel = new DateModel(); //inicio um novo objeto DateModel (dateModel
		
		dateModel.setDate(noteModel.getDate()); //pego a data do meu noteModel, que vem da requição, e seto como propriedade do meu dateModel
		
		Long id =  dateRepository.findByDate(dateModel.getDate()); /*pela data que foi setada anteriormente, 
																     faço uma pesquisa pela própria data no meu db, 
																     pego o id dessa data e armazano num Long*/
		
		if(id != null) { //se caso já exista exista um id com esse valor no banco:
			
			dateModel.setId(id); /*seto esse id no meu objeto dateModel, para quando salvar, 
								   ele entenda que aquele id já existe e então, apenas associe a minha nota à aquela data já cadastrada no banco*/
			
			noteModel.setDateModel(dateModel); //seto um objeto dateModel no meu noteModel
			
			NoteModel dates = noteRepository.save(noteModel); //e mando salvar
			
			return new ResponseEntity<NoteModel>(dates, HttpStatus.CREATED); //após isso, ele me retorna uma nova nota
		}
		
		dateRepository.save(dateModel);
		
		noteModel.setDateModel(dateModel);
		
		NoteModel dates = noteRepository.save(noteModel);
		
		return new ResponseEntity<NoteModel>(dates, HttpStatus.CREATED);
	}
	
	@GetMapping(value = "listar")
	public ResponseEntity<List<DateModel>> listAll(){
		
		List<DateModel> dateModel = (List<DateModel>) dateRepository.findAll();
		
		return new ResponseEntity<List<DateModel>>(dateModel, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}/deletar", produces = "application/json")
	public ResponseEntity<String> delete(@PathVariable(value = "id")Long id){
		
		Optional<NoteModel> note = noteRepository.findById(id);
		
		String data = note.get().getDate();		
		
		noteRepository.deleteById(id);
		
		Long idd = dateRepository.findByDate(data);
		
		Optional<DateModel> date = dateRepository.findById(idd);
		
		if(date.get().getNoteModel().isEmpty()) {
			
			dateRepository.deleteById(date.get().getId());
			
			return new ResponseEntity<String>("DATA EXCLUÍDA", HttpStatus.OK);
		}
		
		return new ResponseEntity<String>("excluido", HttpStatus.OK);
	}
	

}
