package vos;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import vos.Oferta.tipoAlojamiento;


public class Hostal extends Oferta
{

	/**
	 * horario de apertura de la recepcion del hostal.
	 */
	@JsonProperty( value = "horarioApertura")
	private Date horarioApertura;

	/**
	 * horario de cierre de la recepcion del hostal.
	 */
	@JsonProperty( value = "horarioCierre")
	private Date horarioCierre;

	/**
	 * boolean que indica si la habitacion es individual.
	 */
	@JsonProperty( value = "individual")
	private boolean individual;

	
	/**
	 * Constructor de un hostal.
	 * <b>post: </b> Crea un hostal con los valores que entran por parametro
	 */
	public Hostal(@JsonProperty( value = "id")Long pId,
			@JsonProperty( value = "ubicacion")String pUbicacion,
			@JsonProperty( value = "precio")int pPrecio,
			@JsonProperty( value = "capacidad")int pCapacidad,
			@JsonProperty( value = "vigente")boolean pVigente,
			@JsonProperty( value = "fechaDeRetiro")Date pFechaDeRetiro,
			@JsonProperty( value = "operador")Operador pOperador,
			@JsonProperty( value = "servicios") List<Servicio> pServicios,
			@JsonProperty( value = "reservas") List<Reserva> pReservas,
			@JsonProperty( value = "tipo") tipoAlojamiento pTipo,
			@JsonProperty( value = "horarioApertura") Date pHorarioApertura,
			@JsonProperty( value = "horarioCierre") Date pHorarioCierre,
			@JsonProperty( value = "individual")boolean pIndividual)
	{
		super(pId, pUbicacion, pPrecio,  pCapacidad, pVigente, pFechaDeRetiro,  pOperador, pServicios, pReservas,pTipo);
		horarioApertura =pHorarioApertura;
		horarioCierre = pHorarioCierre;
		individual = pIndividual;
	}

	public Date getHorarioApertura() {
		return horarioApertura;
	}

	public void setHorarioApertura(Date horarioApertura) {
		this.horarioApertura = horarioApertura;
	}

	public Date getHorarioCierre() {
		return horarioCierre;
	}

	public void setHorarioCierre(Date horarioCierre) {
		this.horarioCierre = horarioCierre;
	}

	public boolean isIndividual() {
		return individual;
	}

	public void setIndividual(boolean individual) {
		this.individual = individual;
	}

	
}

