package it.prova.coge_be.dto.risorsa;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.coge_be.dto.attachment.AttachmentEagerDTO;
import it.prova.coge_be.dto.commessa.CommessaEagerDTO;
import it.prova.coge_be.model.Risorsa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RisorsaLazyDTO {

	private Long id;
	@NotBlank(message = "{nome.notblank}")
	private String nome;
	@NotBlank(message = "{cognome.notblank}")
	private String cognome;
	private LocalDate dataIn;
	private LocalDate dataOut;
	@NotBlank(message = "{cf.notblank}")
	private String cf;
	private String email;
	private Integer costoGiornaliero;
	
	
	public Risorsa buildModelFromDTO() {
		return Risorsa.builder().id(this.id).nome(this.nome).cognome(this.cognome).dataIn(this.dataIn)
				.dataOut(this.dataOut).cf(this.cf).email(this.email).costoGiornaliero(this.costoGiornaliero)
				.build();
	}

	public static RisorsaLazyDTO buildRisorsaDTOFromModel(Risorsa risorsaModel) {
		RisorsaLazyDTO result = RisorsaLazyDTO.builder().id(risorsaModel.getId()).nome(risorsaModel.getNome())
				.cognome(risorsaModel.getCognome()).dataIn(risorsaModel.getDataIn()).dataOut(risorsaModel.getDataOut())
				.cf(risorsaModel.getCf()).email(risorsaModel.getEmail())
				.costoGiornaliero(risorsaModel.getCostoGiornaliero()).build();
		return result;
	}

	public static List<RisorsaLazyDTO> createRisorsaDTOListFromModelList(List<Risorsa> modelListInput) {
		return modelListInput.stream().map(risorsaItem -> {
			return RisorsaLazyDTO.buildRisorsaDTOFromModel(risorsaItem);
		}).collect(Collectors.toList());
	}

	public static List<RisorsaLazyDTO> createRisorsaDTOListFromModelSet(Set<Risorsa> modelListInput) {
		return modelListInput.stream().map(risorsaItem -> {
			return RisorsaLazyDTO.buildRisorsaDTOFromModel(risorsaItem);
		}).collect(Collectors.toList());
	}

	public static Set<Risorsa> createRisorsaSetFromDTOList(List<RisorsaEagerDTO> modelListInput) {
		return modelListInput.stream().map(risorsaItem -> {
			return risorsaItem.buildModelFromDTO();
		}).collect(Collectors.toSet());
	}
}
