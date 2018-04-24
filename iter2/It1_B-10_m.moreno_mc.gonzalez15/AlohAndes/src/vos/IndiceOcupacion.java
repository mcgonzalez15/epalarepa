package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IndiceOcupacion {

	@JsonProperty( value = "id")
	private String id;
	
	@JsonProperty( value = "nombre")
	private String nombre;
	
	@JsonProperty( value = "porcentajeOcupacion")
	private String porcentajeOcupacion;
	
	public IndiceOcupacion (@JsonProperty( value = "id") String pIdAlojamiento, 
							@JsonProperty( value = "nombre") String pNombreOperador,
							@JsonProperty( value = "porcentajeOcupacion") String pPorcentajeOcupacion)
	{
		id=pIdAlojamiento;
		nombre = pNombreOperador;
		porcentajeOcupacion = pPorcentajeOcupacion;
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

	public String getPorcentajeOcupacion() {
		return porcentajeOcupacion;
	}

	public void setPorcentajeOcupacion(String porcentajeOcupacion) {
		this.porcentajeOcupacion = porcentajeOcupacion;
	}

	
	
}
