package it.prova.coge_be.web.api.attachment;

import java.util.List;

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

import it.prova.coge_be.dto.attachment.AttachmentEagerDTO;
import it.prova.coge_be.dto.attachment.AttachmentLazyDTO;
import it.prova.coge_be.model.Attachment;
import it.prova.coge_be.service.attachment.AttachmentService;

@RestController
@RequestMapping("/api/attachment")
@CrossOrigin
public class AttachmentController {

	@Autowired
	private AttachmentService attachmentService;

	@GetMapping
	public List<AttachmentLazyDTO> listAll() {
		return AttachmentLazyDTO.createAttachmentDTOListFromModelList(attachmentService.listAllElements());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AttachmentEagerDTO createNew(@RequestBody AttachmentEagerDTO attachmentDTO) {
		if(attachmentDTO.getRisorsa() == null)
			throw new RuntimeException("deve cntenere risorsa");
		Attachment attachmentTransient = attachmentDTO.buildModel();
		attachmentService.inserisciNuovo(attachmentTransient);
		return AttachmentEagerDTO.buildAttachmentDTOFromModel(attachmentTransient);
	}

	@PutMapping("/edit//{id}")
	@ResponseStatus(HttpStatus.OK)
	public AttachmentEagerDTO update(@RequestBody AttachmentEagerDTO attachmentDTO,
			@PathVariable(required = true, value = "id") Long id) {
		if(attachmentDTO.getRisorsa() == null)
			throw new RuntimeException("deve cntenere risorsa");
		if(attachmentDTO.getId() != null)
			throw new RuntimeException("nella busta non si deve inserire id");
		attachmentDTO.setId(id);
		attachmentService.aggiorna(attachmentDTO.buildModel());
		Attachment allegato = attachmentService.caricaSingoloElemento(id);
		return AttachmentEagerDTO.buildAttachmentDTOFromModel(allegato);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable(required = true, value = "id") Long id) {
		attachmentService.rimuoviById(id);
	}
}
