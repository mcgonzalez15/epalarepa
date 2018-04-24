package vos;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.Oferta.tipoAlojamiento;

public class Habitacion extends Oferta
{

	/**
	 * Boolean que indica si la habitación es individual.
	 */
	@JsonProperty( value = "individual")
	public boolean individual;
	
	/**
	 * Lista con todos los contratos de una habitación.
	 */
	@JsonProperty( value = "contratos")
	public List<Contrato> contratos;
	
	@JsonProperty( value = "costoServicios")
	public int costoServicios;


	/**
	 * Contructor de alojamiento.
	 * <b>post: </b> Crea un alojamiento con los valores que entran por parametro
	 */
	public Habitacion(@JsonProperty( value = "id")Long pId, @JsonProperty( value = "ubicacion")String pUbicacion,
			@JsonProperty( value = "precio")int pPrecio, @JsonProperty( value = "capacidad")int pCapacidad, 
			@JsonProperty( value = "vigente")boolean pVigente, @JsonProperty( value = "fechaRetiro")Date pFechaRetiro,
			@JsonProperty( value = "operador")Operador pOperador, @JsonProperty( value = "servicios") List<Servicio> pServicios,
			@JsonProperty( value = "reservas") List<Reserva> pReservas, @JsonProperty( value = "tipo") tipoAlojamiento pTipo,
			@JsonProperty( value = "numHabitaciones") int pNumHabitaciones, @JsonProperty( value = "cedido") boolean pCedido,
			@JsonProperty( value = "individual")boolean pIndividual, @JsonProperty( value = "contratos") List <Contrato> pContratos, @JsonProperty( value = "costoServicios") int costoServicios){
		
		super(pId, pUbicacion, pPrecio, pCapacidad, pVigente, pFechaRetiro, pOperador, pServicios, pReservas, pTipo);

		individual = pIndividual;
		contratos = pContratos;
	}


	public boolean isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

	public int getCosto() {
		return costoServicios;
	}

	public void setCosto(int costoServicios) {
		this.costoServicios = costoServicios;
	}

	public List<Contrato> getContratos() {
		return contratos;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}


}

