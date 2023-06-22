package it.prova.coge_be.dto.attachment;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import it.prova.coge_be.dto.risorsa.RisorsaEagerDTO;
import it.prova.coge_be.model.Attachment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AttachmentEagerDTO {

	private Long id;
	private String fileName;
	private String contentType;
	private String descrizione;
	private LocalDate dataCreazione;

	private byte[] payload;

	private RisorsaEagerDTO risorsa;

	public Attachment buildModel() {
		return Attachment.builder().id(this.id).fileName(this.fileName).contentType(this.contentType)
				.descrizione(this.descrizione).dataCreazione(this.dataCreazione).payload(this.payload).build();
	}

	public static AttachmentEagerDTO buildAttachmentDTOFromModel(Attachment attachmentModel) {
		return AttachmentEagerDTO.builder().id(attachmentModel.getId()).contentType(attachmentModel.getContentType())
				.descrizione(attachmentModel.getDescrizione()).dataCreazione(attachmentModel.getDataCreazione())
				.payload(attachmentModel.getPayload())
				.risorsa(AttachmentEagerDTO.buildAttachmentDTOFromModel(attachmentModel).getRisorsa()).build();
	}

	public static List<AttachmentEagerDTO> createAttachmentDTOListFromModelList(List<Attachment> modelListInput) {
		return modelListInput.stream().map(attachmentItem -> {
			return AttachmentEagerDTO.buildAttachmentDTOFromModel(attachmentItem);
		}).collect(Collectors.toList());
	}
}
