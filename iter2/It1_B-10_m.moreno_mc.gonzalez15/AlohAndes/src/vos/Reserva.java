package vos;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class Reserva
{

	@JsonProperty( value = "id")
	private Long id;

	@JsonProperty( value = "numDias")
	private int numDias;

	@JsonProperty( value = "fechaInicio")
	private Date fechaInicio;

	@JsonProperty( value = "fechaFin")
	private Date fechaFin;

	private boolean cancelada;

	@JsonProperty( value = "numPersonas")
	private int numPersonas;

	@JsonProperty( value = "fechaCancelacion")
	private Date fechaCancelacion;

	@JsonProperty( value = "precioTotal")
	private double precioTotal;

	@JsonProperty( value = "termino")
	private boolean termino;
	

	/**
	 * Reserva hace parte de una reserva colectiva
	 */
	@JsonProperty( value = "colectiva")
	private boolean colectiva;

	/**
	 * Id de la reserva colectiva
	 */
	@JsonProperty( value = "idColectiva")
	private Long idColectiva;
	

	@JsonProperty( value = "cancelacionOportuna")
	private Date cancelacionOportuna;

	@JsonProperty( value = "oferta")
	private Oferta oferta;

	@JsonProperty( value = "cliente")
	private Cliente cliente;

	@JsonProperty( value = "serviciosAdicionales")
	private List<Servicio> serviciosAdicionales;
	
	
	public Reserva(@JsonProperty( value = "id") Long pId,
//			@JsonProperty( value = "numDias") int pNumDias, 
			@JsonProperty( value = "fechaInicio") Date pFechaInicio,
			@JsonProperty( value = "fechaFin") Date pFechaFin,
			@JsonProperty( value = "cancelada") boolean pCancelada,
			@JsonProperty( value = "numPersonas")int pNumPersonas,
			@JsonProperty( value = "fechaCancelacion") Date pFechaCancelacion,
			@JsonProperty( value = "costoDefinitivo") double pCostoDefinitivo,
			@JsonProperty( value = "termino") boolean pTermino,
			@JsonProperty( value = "cancelacionOportuna") Date pCancelacionOportuna,
			@JsonProperty( value = "oferta") Oferta pOferta,
			@JsonProperty( value = "colectiva") boolean pColectiva,
			@JsonProperty( value = "idColectiva") Long pIdColectiva,
			@JsonProperty( value = "cliente") Cliente pCliente,
			@JsonProperty( value = "serviciosAdicionales") List<Servicio> pServiciosAdicionales )
	{
		id=pId;
//		numDias = pNumDias;
		fechaInicio = pFechaInicio;
		fechaFin = pFechaFin;
		cancelada = pCancelada;
		numPersonas = pNumPersonas;
		fechaCancelacion= pFechaCancelacion;
		precioTotal = pCostoDefinitivo;
		termino = pTermino;
		cancelacionOportuna = pCancelacionOportuna;
		oferta = pOferta;
		colectiva = pColectiva;
		idColectiva = pIdColectiva;
		cliente = pCliente;
		serviciosAdicionales = pServiciosAdicionales;
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
	 * @return the numDias
	 */
	public int getNumDias() {
		return numDias;
	}


	/**
	 * @param numDias the numDias to set
	 */
	public void setNumDias(int numDias) {
		this.numDias = numDias;
	}


	/**
	 * @return the fechaInicio
	 */
	public Date getFechaInicio() {
		return fechaInicio;
	}


	/**
	 * @param fechaInicio the fechaInicio to set
	 */
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}


	/**
	 * @return the fechaFin
	 */
	public Date getFechaFin() {
		return fechaFin;
	}


	/**
	 * @param fechaFin the fechaFin to set
	 */
	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}


	/**
	 * @return the cancelada
	 */
	public boolean isCancelada() {
		return cancelada;
	}


	/**
	 * @param cancelada the cancelada to set
	 */
	public void setCancelada(boolean cancelada) {
		this.cancelada = cancelada;
	}


	/**
	 * @return the numPersonas
	 */
	public int getNumPersonas() {
		return numPersonas;
	}


	/**
	 * @param numPersonas the numPersonas to set
	 */
	public void setNumPersonas(int numPersonas) {
		this.numPersonas = numPersonas;
	}


	/**
	 * @return the fechaCancelacion
	 */
	public Date getFechaCancelacion() {
		return fechaCancelacion;
	}


	/**
	 * @param fechaCancelacion the fechaCancelacion to set
	 */
	public void setFechaCancelacion(Date fechaCancelacion) {
		this.fechaCancelacion = fechaCancelacion;
	}


	/**
	 * @return the precioTotal
	 */
	public double getPrecioTotal() {
		return precioTotal;
	}


	/**
	 * @param precioTotal the precioTotal to set
	 */
	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}


	/**
	 * @return the termino
	 */
	public boolean isTermino() {
		return termino;
	}


	/**
	 * @param termino the termino to set
	 */
	public void setTermino(boolean termino) {
		this.termino = termino;
	}


	/**
	 * @return the cancelacionOportuna
	 */
	public Date getCancelacionOportuna() {
		return cancelacionOportuna;
	}


	/**
	 * @param cancelacionOportuna the cancelacionOportuna to set
	 */
	public void setCancelacionOportuna(Date cancelacionOportuna) {
		this.cancelacionOportuna = cancelacionOportuna;
	}


	/**
	 * @return the oferta
	 */
	public Oferta getOferta() {
		return oferta;
	}


	/**
	 * @param oferta the oferta to set
	 */
	public void setOferta(Oferta oferta) {
		this.oferta = oferta;
	}


	/**
	 * @return the cliente
	 */
	public Cliente getCliente() {
		return cliente;
	}


	/**
	 * @param cliente the cliente to set
	 */
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}


	/**
	 * @return the serviciosAdicionales
	 */
	public List<Servicio> getServiciosAdicionales() {
		return serviciosAdicionales;
	}


	/**
	 * @param serviciosAdicionales the serviciosAdicionales to set
	 */
	public void setServiciosAdicionales(List<Servicio> serviciosAdicionales) {
		this.serviciosAdicionales = serviciosAdicionales;
	}


	public boolean isColectiva() {
		return colectiva;
	}

	public void setColectiva(boolean colectiva) {
		this.colectiva = colectiva;
	}

	public Long getIdColectiva() {
		return idColectiva;
	}

	public void setIdColectiva(Long idColectiva) {
		this.idColectiva = idColectiva;
	}

	
	
}

