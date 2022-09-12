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
		
		DateModel dateModel = new DateModel(); //inicio um novo objeto DateModel (dateModel)
		
		dateModel.setDate(noteModel.getDate()); //pego a data do meu noteModel, que vem da requisição, e seto como propriedade do meu dateModel
		
		Long id =  dateRepository.findByDate(dateModel.getDate()); /*pela data que foi setada anteriormente, 
																     faço uma pesquisa pela própria data no meu db, 
																     pego o id dessa data e armazano numa variavel*/
		
		if(id != null) { //SE caso já exista exista um id com esse valor no banco:
			
			dateModel.setId(id); /*seto esse id no meu objeto dateModel, para quando salvar, 
								   ele entenda que aquele id já existe e então, apenas associe a minha nota à aquela data já cadastrada no banco*/
			
			noteModel.setDateModel(dateModel); //seto um objeto dateModel no meu noteModel
			
			NoteModel dates = noteRepository.save(noteModel); //e mando salvar
			
			return new ResponseEntity<NoteModel>(dates, HttpStatus.CREATED); //após isso, ele me retorna uma nova nota
		}
		
		dateRepository.save(dateModel); //ELSE ele salva uma data nova no banco
		
		noteModel.setDateModel(dateModel); //seto um objeto dateModel no meu noteModel, para associar o lembrete novo à data cadastrada anteriormente
		
		NoteModel note = noteRepository.save(noteModel); // mando salvar
		
		return new ResponseEntity<NoteModel>(note, HttpStatus.CREATED); //recebo de volta um objeto note
	}
	
	@GetMapping(value = "listar")
	public ResponseEntity<List<DateModel>> listAll(){
		
		List<DateModel> dateModel = (List<DateModel>) dateRepository.findAll();
		
		return new ResponseEntity<List<DateModel>>(dateModel, HttpStatus.OK);
	}
	
	@PostMapping(value = "/{id}/deletar", produces = "application/json")
	public ResponseEntity<String> delete(@PathVariable(value = "id")Long id){
		
		Optional<NoteModel> note = noteRepository.findById(id); //procuro pelo id, um lembrete no banco, pesquisando pelo id que foi passado na requisição
		
		String data = note.get().getDate();	//pego a data desse lembrete que foi retornado e armazeno numa variavel 	
		
		noteRepository.deleteById(id); //deleto o meu lembrete
		
		Long idRsult = dateRepository.findByDate(data); /*no meu db, faço uma busca pela data armazenada anteriormente (String data),
		 												quando a data for encontrada, vou salvar o id dessa data numa variavel (idResult)*/
		
		Optional<DateModel> date = dateRepository.findById(idRsult); /*novamente, faço uma busca no banco, mas dessa vez, 
																	  a busca é feita pelo id que foi salvo na variavel idResult.
																	  Quando a busca é concluída, recebo um único objeto do tipo DateModel (date)*/
		
		if(date.get().getNoteModel().isEmpty()) { //verifico se o objeto NoteModel, que está dentro do objeto date, está vazio
			
			dateRepository.deleteById(date.get().getId()); /*SE estiver vazio, ele pode também excluir a data do meu db, 
															pois não preciso de uma data sem nenhum lembrete associado
															ELSE, ele va apenas exluir o lembrete e dar um retorno*/
		}
		
		return new ResponseEntity<String>("excluido", HttpStatus.OK);
	}
	

}
