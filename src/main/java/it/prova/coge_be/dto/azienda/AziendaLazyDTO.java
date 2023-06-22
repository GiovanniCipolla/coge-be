package it.prova.coge_be.dto.azienda;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonInclude;

import it.prova.coge_be.dto.commessa.CommessaEagerDTO;
import it.prova.coge_be.model.Azienda;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AziendaLazyDTO {
	
	private Long id;
	@NotBlank(message = "{ragionesociale.notblank}")
	private String ragioneSociale;
	@NotBlank(message = "{partitaIva.notblank}")
	private String partitaIva;
	@NotBlank(message = "{indirizzo.notblank}")
	private String indirizzo;


	
	
	
	public Azienda buildAziendaModel() {
		return Azienda.builder().id(this.id).ragioneSociale(this.ragioneSociale).partivaIva(this.partitaIva)
				.indirizzo(this.indirizzo).build();
	}
	
	
	public static AziendaLazyDTO buildAziendaDTOFromModel(Azienda aziendaModel) {
		AziendaLazyDTO result= AziendaLazyDTO.builder().id(aziendaModel.getId()).ragioneSociale(aziendaModel.getRagioneSociale())
				.partitaIva(aziendaModel.getPartivaIva()).indirizzo(aziendaModel.getIndirizzo()).build();
		return result;
		
	}
	
	public static List<AziendaLazyDTO> createAziendaDTOListFromModelList(List<Azienda> modelListInput){
		return modelListInput.stream().map(aziendaEntity -> {
			AziendaLazyDTO result = AziendaLazyDTO.buildAziendaDTOFromModel(aziendaEntity);
			return result;
		}).collect(Collectors.toList());
	}
	
	public static Set<Azienda> createAziendaSetFromDTOList(List<AziendaLazyDTO> modelListInput) {
		return modelListInput.stream().map(aziendaItem -> {
		return aziendaItem.buildAziendaModel();
		}).collect(Collectors.toSet());
		}
	
	
	public static List<AziendaEagerDTO> createAziendaListFromModelSet(Set<Azienda> modelSetInput) {
		return modelSetInput.stream().map(aziendaItem -> {
		return AziendaEagerDTO.buildAziendaDTOFromModel(aziendaItem);
		}).collect(Collectors.toList());
		}
	
}
