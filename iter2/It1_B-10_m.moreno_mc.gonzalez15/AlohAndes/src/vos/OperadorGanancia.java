package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class OperadorGanancia {

	@JsonProperty( value = "id")
	private String id;
	@JsonProperty( value = "nombre")
	private String nombre;
	@JsonProperty( value = "id")
	private String ganancia;
	
	public OperadorGanancia(@JsonProperty( value = "id") String pId, @JsonProperty( value = "nombre")String pNombre,@JsonProperty( value = "ganancia")String pGanancia)
	{
		id= pId;
		nombre=pNombre;
		ganancia = pGanancia;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGanancia() {
		return ganancia;
	}

	public void setGanancia(String ganancia) {
		this.ganancia = ganancia;
	}
	
	
}
