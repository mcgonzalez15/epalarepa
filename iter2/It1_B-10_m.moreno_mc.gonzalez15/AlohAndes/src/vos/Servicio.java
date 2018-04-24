package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Servicio
{

	@JsonProperty( value = "id")
	private Long id;
	

	@JsonProperty( value = "nombre")
	private String nombre;


	@JsonProperty( value = "descripcion")
	private String descripcion;


	@JsonProperty( value = "costo")
	private int costo;

	/*
	 * Constructor
	 */
	public Servicio(@JsonProperty( value = "id") Long pId, @JsonProperty( value = "nombre") String pNombre, 
			        @JsonProperty( value = "descripcion") String pDescripcion, @JsonProperty( value = "costo") int pCosto)
	{
		id = pId;
		nombre = pNombre;
		descripcion = pDescripcion;
		costo = pCosto;
	}

	/*
	 * Métodos 
	 */

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getCosto() {
		return costo;
	}

	public void setCosto(int costo) {
		this.costo = costo;
	}


}

