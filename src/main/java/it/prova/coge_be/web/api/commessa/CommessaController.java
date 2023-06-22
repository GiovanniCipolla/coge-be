package it.prova.coge_be.web.api.commessa;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.coge_be.dto.commessa.CommessaEagerDTO;
import it.prova.coge_be.model.Commessa;
import it.prova.coge_be.service.commessa.CommessaService;

@RestController
@RequestMapping("api/commessa")
@CrossOrigin
public class CommessaController {
	
	@Autowired
	private CommessaService commessaService;
	
	
	@GetMapping
	public List<CommessaEagerDTO> visualizzaCommesse(){
		return CommessaEagerDTO.createCommessaDTOListFromModelList(commessaService.listAll());
		
	}
	
	@GetMapping("/{id}")
	public CommessaEagerDTO visualizza (@PathVariable(required = true) Long id) {
		return CommessaEagerDTO.buildCommessaDTOFromModel(commessaService.caricaSingoloElemento(id));
	}
	
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public CommessaEagerDTO createNew(@Valid @RequestBody CommessaEagerDTO commessaInput) {
		if (commessaInput.getId() != null)
		throw new RuntimeException();
		Commessa commessaInserita = commessaService.inserisciNuovo(commessaInput.buildCommessaModel());
		return CommessaEagerDTO.buildCommessaDTOFromModel(commessaInserita);
	}
	
	@PutMapping("{id}")
	public CommessaEagerDTO update (@Valid @RequestBody CommessaEagerDTO commessaInput, @PathVariable(required = true) Long id) {
		Commessa commessa = commessaService.caricaSingoloElemento(id);
		
		if(commessa == null) {
			throw new RuntimeException();
		}
		
		commessaInput.setId(id);
		
		Commessa commessaAggiornata = commessaService.aggiorna(commessaInput.buildCommessaModel());
		
		return CommessaEagerDTO.buildCommessaDTOFromModel(commessaAggiornata);
	
    }
	
		@DeleteMapping("{id}")
	public void delete(@PathVariable(required = true) Long id) {
		commessaService.rimuovi(id);
	}
	
}
