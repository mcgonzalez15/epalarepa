package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Documentacion {

	/**
	 * Indica si la reserva hace parte de una reserva colectiva
	 */
	
	@JsonProperty( value = "informe")
	private List<String> informe;
	
	public Documentacion (@JsonProperty( value = "informe")List<String>pInforme)
	{
		informe = pInforme;
	}

	public List<String> getInforme() {
		return informe;
	}

	public void setInforme(List<String> informe) {
		this.informe = informe;
	}
	
	
}
