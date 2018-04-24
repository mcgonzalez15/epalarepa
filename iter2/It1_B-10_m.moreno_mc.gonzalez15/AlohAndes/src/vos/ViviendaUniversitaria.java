package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class ViviendaUniversitaria extends Oferta
{

	@JsonProperty( value = "numNoches")
	public int numNoches;
	
	@JsonProperty( value = "individual")
	public boolean individual;


	/**
	 * Constructor de una vivienda universitaria.
	 * <b>post: </b> Crea una vivienda universitaria con los valores que entran por parametro
	 */
	public ViviendaUniversitaria(@JsonProperty( value = "id")Long pId, @JsonProperty( value = "ubicacion")String pUbicacion,
			@JsonProperty( value = "precio") int pPrecio, @JsonProperty( value = "capacidad")int pCapacidad,
			@JsonProperty( value = "disponible")boolean pDisponible,@JsonProperty( value = "fechaDeRetiro")Date pFechaDeRetiro,
			@JsonProperty( value = "operador")Operador pOperador,@JsonProperty( value = "servicios") List<Servicio> pServicios,
			@JsonProperty( value = "reservas") List<Reserva> pReservas, @JsonProperty( value = "tipo") tipoAlojamiento pTipo,
			@JsonProperty( value = "numNoches")int pNumNoches, @JsonProperty( value = "individual")boolean pIndividual ){
		super(pId, pUbicacion, pPrecio, pCapacidad, pDisponible, pFechaDeRetiro, pOperador, pServicios, pReservas, pTipo);
		
		numNoches = pNumNoches;
		individual = pIndividual;
		
	}


	/**
	 * @return the numNoches
	 */
	public int getNumNoches() {
		return numNoches;
	}


	/**
	 * @param numNoches the numNoches to set
	 */
	public void setNumNoches(int numNoches) {
		this.numNoches = numNoches;
	}


	/**
	 * @return the individual
	 */
	public boolean isIndividual() {
		return individual;
	}


	/**
	 * @param individual the individual to set
	 */
	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

	

}

