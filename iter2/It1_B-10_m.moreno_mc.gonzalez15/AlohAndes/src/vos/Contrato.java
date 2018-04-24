package vos;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;


public class Contrato
{
	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del contrato
	 */
	@JsonProperty( value = "id")
	private Long id;
	/**
	 * Numero de dias de vigencia del contrato
	 */
	@JsonProperty( value = "numDias")
	private int numDias;


	/**
	 * Fecha del contrato
	 */
	@JsonProperty( value = "fecha")
	private Date fecha;

	/**
	 * Precio definitivo del arrendamiento
	 */
	@JsonProperty( value = "precioTotal")
	private double precioTotal;

	/**
	 * Cliente del contrato
	 */
	@JsonProperty( value = "cliente")
	private Cliente cliente;

	/**
	 * Alojamiento del contrato
	 */
	@JsonProperty( value = "alojamiento")
	private Oferta alojamiento;
	
	@JsonProperty( value = "valorSeguro")
	private double valorSeguro;

	//----------------------------------------------------------------------------------------------------------------------------------
	// CONSTRUCTOR
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Contructor de contrato.
	 * <b>post: </b> Crea un contrato con los valores que entran por parametro
	 */
	public Contrato(@JsonProperty( value = "id")Long pId, 
			@JsonProperty( value = "numDias") int pNumDias, 
	        @JsonProperty( value = "fecha") Date pFecha, 
			@JsonProperty( value = "precioTotal")double pPrecioTotal, @JsonProperty( value = "cliente") Cliente pCLiente,
			@JsonProperty( value = "alojamiento") Oferta pAlojamiento)
	{
		id = pId;
		numDias = pNumDias;

		fecha = pFecha;
	    precioTotal = pPrecioTotal;
		cliente = pCLiente;
		alojamiento = pAlojamiento;
	}

	//----------------------------------------------------------------------------------------------------------------------------------
	// METODOS
	//----------------------------------------------------------------------------------------------------------------------------------

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getNumDias() {
		return numDias;
	}

	public void setNumDias(int numDias) {
		this.numDias = numDias;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public double getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(double precioTotal) {
		this.precioTotal = precioTotal;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Oferta getAlojamiento() {
		return alojamiento;
	}

	public void setAlojamiento(Oferta alojamiento) {
		this.alojamiento = alojamiento;
	}

}

