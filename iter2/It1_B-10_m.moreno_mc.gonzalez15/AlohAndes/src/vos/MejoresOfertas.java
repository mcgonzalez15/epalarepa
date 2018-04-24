package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MejoresOfertas {

	@JsonProperty( value = "idOferta")
	private String idOferta;
	@JsonProperty( value = "operador")
	private String operador;
	@JsonProperty( value = "ubicacion")
	private String ubicacion;
	@JsonProperty( value = "numReservas")
	private String numReservas;
	
	public MejoresOfertas(@JsonProperty( value = "idOferta") String pIdOferta,
						   @JsonProperty( value = "operador") String pOperador,
						   @JsonProperty( value = "ubicacion") String pUbicacion,
						   @JsonProperty( value = "numReservas") String pNumReservas)
	{
		idOferta = pIdOferta;
		operador =pOperador;
		ubicacion = pUbicacion;
		numReservas = pNumReservas;
	}

	public String getIdOferta() {
		return idOferta;
	}

	public void setIdOferta(String idOferta) {
		this.idOferta = idOferta;
	}

	public String getOperador() {
		return operador;
	}

	public void setOperador(String operador) {
		this.operador = operador;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public String getNumReservas() {
		return numReservas;
	}

	public void setNumReservas(String numReservas) {
		this.numReservas = numReservas;
	}
	
	
}
