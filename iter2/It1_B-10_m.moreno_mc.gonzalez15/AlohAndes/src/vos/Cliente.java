package vos;


import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class Cliente
{
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del cliente
	 */
	@JsonProperty( value = "id")
	private Long id;

	/**
	 * nombre del cliente
	 */
	@JsonProperty( value = "nombre")
	private String nombre;

	/**
	 * correo del cliente
	 */
	@JsonProperty( value = "correo")
	private String correo;

	/**
	 * lista de los contratos que tiene el cliente
	 */
	@JsonProperty( value = "contratos")
	private List <Contrato> contratos;

	/**
	 * Describe la relacion con uniandes que tiene el cliente
	 */
	@JsonProperty( value = "vinculoUniandes")
	private VinculoUniandes vinculoUniandes;
	
	/**
	 * Alojamientos preferidos por el cliente.
	 */
	@JsonProperty( value = "alojamientosPreferidos")
	private List<Oferta> alojamientosPreferidos;
	
	/**
	 * Lista de servicios preferidos por el cliente
	 */
	@JsonProperty( value = "serviciosPreferidos")
	private List<Servicio> serviciosPreferidos;


	/**
	 * lista de las reservas que tiene un cliente.
	 */
	@JsonProperty( value = "reservas")
	private List<Reserva> reservas;

	
	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Constructor de cliente.
	 * <b>post: </b> Crea el cliente con los valores que entran por parametro
	 */
	public Cliente(@JsonProperty( value = "id")Long pId, 
			       @JsonProperty( value = "nombre")String pNombre,
			       @JsonProperty( value = "correo")String pCorreo,
			       @JsonProperty( value = "relacionUniandes")VinculoUniandes pVinculo,
			       @JsonProperty( value = "contratos")List <Contrato> pContratos, 
			       @JsonProperty( value = "reservas")List <Reserva> pReservas, 
			       @JsonProperty( value = "serviciosPreferidos")List <Servicio> pServicios, 
			       @JsonProperty( value = "alojamientosPreferidos")List<Oferta> pAlojamientos  ){
		id = pId;
		nombre = pNombre;
		correo = pCorreo;
		contratos = pContratos;
		reservas = pReservas;
		vinculoUniandes= pVinculo;
		serviciosPreferidos= pServicios;
		alojamientosPreferidos = pAlojamientos;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS
	//----------------------------------------------------------------------------------------------------------------------------------

	public Long getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public String getCorreo() {
		return correo;
	}


	public List<Contrato> getContratos() {
		return contratos;
	}


	public List<Reserva> getReservas() {
		return reservas;
	}


	public VinculoUniandes getVinculoUniandes() {
		return vinculoUniandes;
	}


	public List<Servicio> getServiciosPreferidos() {
		return serviciosPreferidos;
	}


	public List<Oferta> getAlojamientosPreferidos() {
		return alojamientosPreferidos;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setContratos(List<Contrato> contratos) {
		this.contratos = contratos;
	}

	public void setReservas(List<Reserva> reservas) {
		this.reservas = reservas;
	}

	public void setRelacionUniandes(VinculoUniandes vinculoUniandes) {
		this.vinculoUniandes =vinculoUniandes;
	}

	public void setServiciosPreferidos(List<Servicio> serviciosPreferidos) {
		this.serviciosPreferidos = serviciosPreferidos;
	}

	public void setAlojamientosPreferidos(List<Oferta> alojamientosPreferidos) {
		this.alojamientosPreferidos = alojamientosPreferidos;
	}

	
}

