package vos;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;



public class Apartamento extends Oferta
{
	public final static String ALQUILER_DIAS = "Alquiler dias";
	public final static String ALQUILER_MES = "Alquiler mes";
	
	@JsonProperty( value = "amoblado")
	private boolean amoblado;
	
	@JsonProperty( value = "numHabitaciones")
	private int numHabitaciones;
	
	@JsonProperty( value = "incluyeAdmi")
	private boolean incluyeAdmi;
	
	@JsonProperty( value = "serviciosPublicos")
	private boolean serviciosPublicos;
	
	@JsonProperty( value = "tipoAlquiler")
	private String tipoAlquiler;
	
	

	public Apartamento(@JsonProperty( value = "id")Long pId, @JsonProperty( value = "ubicacion")String pUbicacion,
			@JsonProperty( value = "precio")int pPrecio, @JsonProperty( value = "capacidad")int pCapacidad,
			@JsonProperty( value = "disponible")boolean pDisponible, @JsonProperty( value = "fechaRetiro")Date pFechaRetiro,
			@JsonProperty( value = "operador")Operador pOperador, @JsonProperty( value = "servicios") List<Servicio> pServicios,
			@JsonProperty( value = "reservas") List<Reserva> pReservas, @JsonProperty( value = "tipo") tipoAlojamiento pTipo,
			@JsonProperty( value = "amoblado") boolean pAmoblado, @JsonProperty( value = "numHabitaciones")int pNumHabitaciones,
			@JsonProperty( value = "incluyeAdmi") boolean pIncluye, @JsonProperty( value = "serviciosPublicos") boolean pServiciosPublicos,
			@JsonProperty( value = "tipoAlquiler") String pTipoAlquiler)
	{
		super(pId, pUbicacion, pPrecio,  pCapacidad,  pDisponible, pFechaRetiro,  pOperador, pServicios, pReservas,pTipo);
		amoblado = pAmoblado;
		numHabitaciones = pNumHabitaciones;
		incluyeAdmi = pIncluye;
		serviciosPublicos = pServiciosPublicos;
		tipoAlquiler = pTipoAlquiler;

	}

	public boolean isAmoblado() {
		return amoblado;
	}

	public void setAmoblado(boolean amoblado) {
		this.amoblado = amoblado;
	}

	/**
	 * @return the numHabitaciones
	 */
	public int getNumHabitaciones() {
		return numHabitaciones;
	}

	/**
	 * @param numHabitaciones the numHabitaciones to set
	 */
	public void setNumHabitaciones(int numHabitaciones) {
		this.numHabitaciones = numHabitaciones;
	}

	/**
	 * @return the incluyeAdmi
	 */
	public boolean isIncluyeAdmi() {
		return incluyeAdmi;
	}

	/**
	 * @param incluyeAdmi the incluyeAdmi to set
	 */
	public void setIncluyeAdmi(boolean incluyeAdmi) {
		this.incluyeAdmi = incluyeAdmi;
	}

	public boolean isServiciosPublicos() {
		return serviciosPublicos;
	}

	public void setServiciosPublicos(boolean serviciosPublicos) {
		this.serviciosPublicos = serviciosPublicos;
	}

	/**
	 * @return the tipoAlquiler
	 */
	public String getTipoAlquiler() {
		return tipoAlquiler;
	}

	/**
	 * @param tipoAlquiler the tipoAlquiler to set
	 */
	public void setTipoAlquiler(String tipoAlquiler) {
		this.tipoAlquiler = tipoAlquiler;
	}

	


	
}

