package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.Oferta.tipoAlojamiento;

public class Hotel extends Oferta
{
	public final static String ESTANDAR = "ESTANDAR";
	public final static String SEMISUITE = "SEMISUITE";
	public final static String SUITE = "SUITE";
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * tipo de la habitacion que ofrece el hotel.
	 */
	@JsonProperty( value = "tipoHabitacion")
	private String tipoHabitacion;
	
	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Contructor de una habitacion de hotel.
	 * <b>post: </b> Crea una habitacion de hotel con los valores que entran por parametro
	 */
	public Hotel(@JsonProperty( value = "id")Long pId, @JsonProperty( value = "ubicacion")String pUbicacion,
			@JsonProperty( value = "precio")int pPrecio, @JsonProperty( value = "capacidad")int pCapacidad,
			@JsonProperty( value = "disponible")boolean pDisponible, @JsonProperty( value = "fechaDeRetiro")Date pFechaDeRetiro,
			@JsonProperty( value = "operador")Operador pOperador, @JsonProperty( value = "servicios") List<Servicio> pServicios,
			@JsonProperty( value = "reservas") List<Reserva> pReservas, @JsonProperty( value = "tipo") tipoAlojamiento pTipo,
			@JsonProperty( value = "tipoHabitacion") String pTipoHabitacion)
	{
		super(pId, pUbicacion, pPrecio,  pCapacidad, pDisponible, pFechaDeRetiro,  pOperador, pServicios, pReservas,pTipo);
		tipoHabitacion = pTipoHabitacion;
	}	

	public String getTipoHabitacion() {
		return tipoHabitacion;
	}

	public void setTipoHabitacion(String tipoHabitacion) {
		this.tipoHabitacion = tipoHabitacion;
	}



}

