package it.prova.coge_be.dto.commessa;

import java.time.LocalDate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.coge_be.dto.azienda.AziendaEagerDTO;
import it.prova.coge_be.dto.risorsa.RisorsaEagerDTO;
import it.prova.coge_be.model.Commessa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
// Json include, se un dato è nullo non cerrà messo nell'output
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommessaEagerDTO {

	private Long id;

	@NotBlank(message = "{descrizione.notblank}")
	private String descrizione;

	@NotBlank(message = "{codice.notblank}")
	private String codice;

	@NotNull(message = "{dataIn.notnull}")
	private LocalDate dataIn;
	@NotNull(message = "{dataOut.notnull}")
	private LocalDate dataOut;

	@NotNull(message = "{importo.notnull}")
	@Min(0)
	private Double importo;

	private AziendaEagerDTO azienda;

	private List<RisorsaEagerDTO> risorse;

	public Commessa buildCommessaModel() {
		Commessa result = Commessa.builder().id(this.id).descrizione(this.descrizione).codice(this.codice)
				.dataIn(this.dataIn).dataOut(this.dataOut).importo(this.importo).azienda(this.azienda.buildAziendaModel())
				.build();
		return result;
	}

	public static CommessaEagerDTO buildCommessaDTOFromModel(Commessa commessaModel) {

		CommessaEagerDTO result = CommessaEagerDTO.builder().id(commessaModel.getId()).descrizione(commessaModel.getDescrizione())
				.codice(commessaModel.getCodice()).dataIn(commessaModel.getDataIn()).dataOut(commessaModel.getDataOut())
				.importo(commessaModel.getImporto()).build();
			result.setAzienda(AziendaEagerDTO.buildAziendaDTOFromModel(commessaModel.getAzienda()));
			result.setRisorse(RisorsaEagerDTO.createRisorsaDTOListFromModelSet(commessaModel.getRisorse()));
		return result;
	}

	public static List<CommessaEagerDTO> createCommessaDTOListFromModelList(List<Commessa> modelListInput ) {
		return modelListInput.stream().map(inputEntity -> {
			return CommessaEagerDTO.buildCommessaDTOFromModel(inputEntity);
		}).collect(Collectors.toList());
	}

	public static Set<CommessaEagerDTO> createCommessaDTOSetFromModelSet(Set<Commessa> modelListInput) {
		return modelListInput.stream().map(commessaItem -> {
			return CommessaEagerDTO.buildCommessaDTOFromModel(commessaItem);
		}).collect(Collectors.toSet());
	}

	public static List<CommessaEagerDTO> createCommessaDTOListFromModelSet(Set<Commessa> modelListInput) {
		return modelListInput.stream().map(commessaItem -> {
			return CommessaEagerDTO.buildCommessaDTOFromModel(commessaItem);
		}).collect(Collectors.toList());
	}

	public static Set<Commessa> createCommessaSetFromDTOList(List<CommessaEagerDTO> modelListInput) {
		return modelListInput.stream().map(commessaItem -> {
			return commessaItem.buildCommessaModel();
		}).collect(Collectors.toSet());
	}
	
	
	

}
