package it.prova.coge_be.web.api.azienda;

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

import it.prova.coge_be.dto.azienda.AziendaEagerDTO;
import it.prova.coge_be.dto.azienda.AziendaLazyDTO;
import it.prova.coge_be.model.Azienda;
import it.prova.coge_be.service.azienda.AziendaService;

@RestController
@RequestMapping("api/azienda")
@CrossOrigin
public class AziendaController {
	
	
	@Autowired
	private AziendaService aziendaService;
	
	@GetMapping
	public List<AziendaLazyDTO> getAll() {
		return AziendaLazyDTO.createAziendaDTOListFromModelList(aziendaService.listAll());
	}
	
	@GetMapping("/{id}")
	public AziendaLazyDTO caricaSingolo(@PathVariable(required = true) Long id) {
		return AziendaLazyDTO.buildAziendaDTOFromModel(aziendaService.caricaSingolo(id));
	}	

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AziendaLazyDTO inserisciNuovo(@Valid @RequestBody AziendaLazyDTO aziendaInstance) {

		if (aziendaInstance.getId() != null)
			throw new RuntimeException("impossibile creare un id per la creazione.");

		return AziendaLazyDTO.buildAziendaDTOFromModel(aziendaService.inserisciNuovo(aziendaInstance.buildAziendaModel()));
	}
	
	

	@PutMapping("/{id}")
	public AziendaLazyDTO update(@Valid @RequestBody AziendaLazyDTO aziendaInstance,@PathVariable(required = true) Long id) {
		if (aziendaInstance.getId() != null)
			throw new RuntimeException("impossibile creare un id per la modifica.");
		aziendaInstance.setId(id);
		return AziendaLazyDTO.buildAziendaDTOFromModel(aziendaService.aggiorna(aziendaInstance.buildAziendaModel()));
		
	}
	
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(required = true) Long id) {
		Azienda aziendaDaEliminare = aziendaService.caricaSingolo(id);
		aziendaService.rimuovi(aziendaDaEliminare);
	}
	
	
	
	

}
