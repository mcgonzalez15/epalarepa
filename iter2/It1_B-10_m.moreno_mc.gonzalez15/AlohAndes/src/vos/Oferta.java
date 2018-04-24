package vos;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class Oferta
{

	public enum tipoAlojamiento
	{
		HOSTAL
		{
			public String toString()
			{
				return "Hostal";
			}
		},	
		HOTEL
		{
			public String toString()
			{
				return "Hotel";
			}
		}, 
		VIVIENDA_UNIVERSITARIA
		{
			public String toString()
			{
				return "Vivienda Universitaria";
			}
		},
		HABITACION
		{
			public String toString()
			{
				return "Habitación";
			}
		},
		APARTAMENTO
		{
			public String toString()
			{
				return "Apartamento";
			}
		},
	}

	/**
	 * Id del oferta
	 */
	@JsonProperty( value = "id")
	private Long id;

	/**
	 * Tipo de oferta
	 */
	@JsonProperty( value = "tipo")
	private tipoAlojamiento tipo;

	/**
	 * Precio del oferta
	 */
	@JsonProperty( value = "precio")
	private double precio;

	/**
	 * Ubicacion del oferta
	 */
	@JsonProperty( value = "ubicacion")
	private String ubicacion;

	/**
	 * Capacidad del oferta
	 */
	@JsonProperty( value = "capacidad")
	private int capacidad;

	/**
	 * Operador del oferta
	 */
	@JsonProperty( value = "operador")
	private Operador operador;

	/**
	 * Servicios que provee la oferta
	 */
	@JsonProperty( value = "servicios")
	private List<Servicio> servicios;

	/**
	 * Todas las reservas de la oferta
	 */
	@JsonProperty( value = "reservas")
	private List<Reserva> reservas;

	/**
	 * Disponibilidad de la oferta
	 */
	@JsonProperty( value = "disponible")
	private boolean disponible;

	/**
	 * Fecha de retiro del oferta
	 */
	@JsonProperty( value = "fechaDeRetiro")
	private Date fechaDeRetiro;

	/**
	 * Contructor de oferta.
	 * <b>post: </b> Crea un oferta con los valores que entran por parametro
	 */
	public Oferta(@JsonProperty( value = "id")Long pId, @JsonProperty( value = "ubicacion")String pUbicacion,
			@JsonProperty( value = "precio") double pPrecio, @JsonProperty( value = "capacidad")int pCapacidad,
			@JsonProperty( value = "disponible")boolean pDisponible,@JsonProperty( value = "fechaDeRetiro")Date pFechaDeRetiro,
			@JsonProperty( value = "operador")Operador pOperador,@JsonProperty( value = "servicios") List<Servicio> pServicios,
			@JsonProperty( value = "reservas") List<Reserva> pReservas, @JsonProperty( value = "tipoAlojamiento") tipoAlojamiento pTipo){
		id = pId;
		ubicacion = pUbicacion;
		precio = pPrecio;
		capacidad = pCapacidad;
		disponible = pDisponible;
		fechaDeRetiro = pFechaDeRetiro;
		operador = pOperador;
		servicios = pServicios;
		reservas = pReservas;
		tipo = pTipo;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * @return the ubicacion
	 */
	public String getUbicacion() {
		return ubicacion;
	}

	/**
	 * @param ubicacion the ubicacion to set
	 */
	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	/**
	 * @return the capacidad
	 */
	public int getCapacidad() {
		return capacidad;
	}

	/**
	 * @param capacidad the capacidad to set
	 */
	public void setCapacidad(int capacidad) {
		this.capacidad = capacidad;
	}

	/**
	 * @return the operador
	 */
	public Operador getOperador() {
		return operador;
	}

	/**
	 * @param operador the operador to set
	 */
	public void setOperador(Operador operador) {
		this.operador = operador;
	}

	/**
	 * @return the servicios
	 */
	public List<Servicio> getServicios() {
		return servicios;
	}

	/**
	 * @param servicios the servicios to set
	 */
	public void setServicios(List<Servicio> servicios) {
		this.servicios = servicios;
	}

	/**
	 * @return the reservas
	 */
	public List<Reserva> getReservas() {
		return reservas;
	}

	/**
	 * @param reservas the reservas to set
	 */
	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	/**
	 * @return the disponible
	 */
	public boolean isDisponible() {
		return disponible;
	}

	/**
	 * @param disponible the disponible to set
	 */
	public void setDisponible(boolean disponible) {
		this.disponible = disponible;
	}

	/**
	 * @return the fechaDeRetiro
	 */
	public Date getFechaDeRetiro() {
		return fechaDeRetiro;
	}

	/**
	 * @param fechaDeRetiro the fechaDeRetiro to set
	 */
	public void setFechaDeRetiro(Date fechaDeRetiro) {
		this.fechaDeRetiro = fechaDeRetiro;
	}

	/**
	 * @return the tipo
	 */
	public tipoAlojamiento getTipo() {
		return tipo;
	}

	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(tipoAlojamiento tipo) {
		this.tipo = tipo;
	}

	

}

