package it.prova.coge_be.dto.commessa;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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
public class CommessaLazyDTO {

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

	public Commessa buildCommessaModel() {
		Commessa result = Commessa.builder().id(this.id).descrizione(this.descrizione).codice(this.codice)
				.dataIn(this.dataIn).dataOut(this.dataOut).importo(this.importo)
				.build();
		return result;
	}

	public static CommessaLazyDTO buildCommessaDTOFromModel(Commessa commessaModel) {

		CommessaLazyDTO result = CommessaLazyDTO.builder().id(commessaModel.getId()).descrizione(commessaModel.getDescrizione())
				.codice(commessaModel.getCodice()).dataIn(commessaModel.getDataIn()).dataOut(commessaModel.getDataOut())
				.importo(commessaModel.getImporto()).build();
		return result;
	}

	public static List<CommessaLazyDTO> createCommessaDTOListFromModelList(List<Commessa> modelListInput ) {
		return modelListInput.stream().map(inputEntity -> {
			return CommessaLazyDTO.buildCommessaDTOFromModel(inputEntity);
		}).collect(Collectors.toList());
	}

	public static Set<CommessaLazyDTO> createCommessaDTOSetFromModelSet(Set<Commessa> modelListInput) {
		return modelListInput.stream().map(commessaItem -> {
			return CommessaLazyDTO.buildCommessaDTOFromModel(commessaItem);
		}).collect(Collectors.toSet());
	}

	public static List<CommessaLazyDTO> createCommessaDTOListFromModelSet(Set<Commessa> modelListInput) {
		return modelListInput.stream().map(commessaItem -> {
			return CommessaLazyDTO.buildCommessaDTOFromModel(commessaItem);
		}).collect(Collectors.toList());
	}

	public static Set<Commessa> createCommessaSetFromDTOList(List<CommessaLazyDTO> modelListInput) {
		return modelListInput.stream().map(commessaItem -> {
			return commessaItem.buildCommessaModel();
		}).collect(Collectors.toSet());
	}
	
	
}
