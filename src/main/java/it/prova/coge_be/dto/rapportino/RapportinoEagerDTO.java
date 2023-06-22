package it.prova.coge_be.dto.rapportino;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.coge_be.dto.commessa.CommessaEagerDTO;
import it.prova.coge_be.dto.risorsa.RisorsaEagerDTO;
import it.prova.coge_be.model.Rapportino;
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
public class RapportinoEagerDTO {
	
	private Long id;
	
	@NotNull(message = "{numeroGiorni.notnull}")
	@Min(0)
	private Integer numeroGiorni;
	
	
	private CommessaEagerDTO commessa;
	
	
	private RisorsaEagerDTO risorsa;
	
	public Rapportino buildRapportinoModel() {
		Rapportino result = Rapportino.builder().id(this.id).numeroGiorni(this.numeroGiorni).risorsa(this.risorsa.buildModelFromDTO()).build();
		return result;
	}
	
	public static RapportinoEagerDTO buildRapportinoDTOFromModel(Rapportino rapportinoModel) {
		RapportinoEagerDTO result = RapportinoEagerDTO.builder().id(rapportinoModel.getId()).numeroGiorni(rapportinoModel.getNumeroGiorni()).build();
			result.setCommessa(CommessaEagerDTO.buildCommessaDTOFromModel(rapportinoModel.getCommessa()));
			result.setRisorsa(RisorsaEagerDTO.buildRisorsaDTOFromModel(rapportinoModel.getRisorsa()));
		return result;
	}
	
	public static List<RapportinoEagerDTO> createRapportinoDTOListFromModelList(List<Rapportino> modelListInput){
		return modelListInput.stream().map(inputEntity -> {
			return RapportinoEagerDTO.buildRapportinoDTOFromModel(inputEntity);
		}).collect(Collectors.toList());
	}
	
	public static List<RapportinoEagerDTO> createRapportinoDTOListFromModelSet(Set<Rapportino> modelListInput, boolean includeCommessa, boolean includeRisorsa) {
		return modelListInput.stream().map(rapportinoItem -> {
		return RapportinoEagerDTO.buildRapportinoDTOFromModel(rapportinoItem);
		}).collect(Collectors.toList());
		}
	
	
	public static Set<Rapportino> createRapportinoSetFromDTOList(List<RapportinoEagerDTO> modelListInput) {
		return modelListInput.stream().map(rapportinoItem -> {
		return rapportinoItem.buildRapportinoModel();
		}).collect(Collectors.toSet());
		}
	
	
}
