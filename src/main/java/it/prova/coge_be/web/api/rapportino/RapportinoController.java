package it.prova.coge_be.web.api.rapportino;

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

import it.prova.coge_be.dto.rapportino.RapportinoEagerDTO;
import it.prova.coge_be.model.Rapportino;
import it.prova.coge_be.service.rapportino.RapportinoService;

@RestController
@RequestMapping("api/rapportino")
@CrossOrigin
public class RapportinoController {
	
	
		
		@Autowired
		private RapportinoService rapportinoService;
		
		@GetMapping
		public List<RapportinoEagerDTO> visualizzaRapportini(){
			return RapportinoEagerDTO.createRapportinoDTOListFromModelList(rapportinoService.listAll());
			
		}
		
		@GetMapping("/{id}")
		public RapportinoEagerDTO visualizza (@PathVariable(required = true) Long id) {
			return RapportinoEagerDTO.buildRapportinoDTOFromModel(rapportinoService.caricaSingoloElemento(id));
		}
		
		
		@PostMapping
		@ResponseStatus(HttpStatus.CREATED)
		public RapportinoEagerDTO createNew(@Valid @RequestBody RapportinoEagerDTO rapportinoInput) {
			
			if(rapportinoInput.getId() != null) {
				throw new RuntimeException();
			}
			
			Rapportino rapportinoInserito = rapportinoService.inserisciNuovo(rapportinoInput.buildRapportinoModel());
			
			
			return RapportinoEagerDTO.buildRapportinoDTOFromModel(rapportinoInserito);
		}
		
		@PutMapping("{id}")
		public RapportinoEagerDTO update (@Valid @RequestBody RapportinoEagerDTO rapportinoInput, @PathVariable(required = true) Long id) {
			Rapportino rapportino = rapportinoService.caricaSingoloElemento(id);
			
			if(rapportino == null) {
				throw new RuntimeException();
			}
			rapportinoInput.setId(id);
			
			Rapportino rapportinoAggiornato = rapportinoService.aggiorna(rapportinoInput.buildRapportinoModel());
			
			return RapportinoEagerDTO.buildRapportinoDTOFromModel(rapportinoAggiornato);
		
	    }
		
			@DeleteMapping("{id}")
		public void delete(@PathVariable(required = true) Long id) {
			rapportinoService.rimuovi(id);
		}
  }
	
	
	
	
