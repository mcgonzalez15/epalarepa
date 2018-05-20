package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class CondicionF10 {

	@JsonProperty( value = "fechaInicio")
	private String fechaInicio;
	
	@JsonProperty( value = "fechaFin")
	private String fechaFin;
	
	@JsonProperty( value = "idAlojamiento")
	private int idAlojamiento;
	
	@JsonProperty( value = "condicionDeOrdenamiento")
	private String condicionDeOrdenamiento;

	public CondicionF10(@JsonProperty( value = "fechaInicio") String pFechaInicio,
							@JsonProperty( value = "fechaFin") String pFechaFin,
							@JsonProperty( value = "idAlojamiento")int pIdAlojamiento,
							@JsonProperty( value = "condicionDeOrdenamiento")String pCondicionDeOrdenamiento)
	{
		fechaInicio = pFechaInicio;
		fechaFin = pFechaFin;
		idAlojamiento = pIdAlojamiento;
		condicionDeOrdenamiento = pCondicionDeOrdenamiento;
	}
	


	public String getFechaInicio() {
		return fechaInicio;
	}



	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}



	public String getFechaFin() {
		return fechaFin;
	}



	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}



	public int getIdAlojamiento() {
		return idAlojamiento;
	}

	public void setIdAlojamiento(int idAlojamiento) {
		this.idAlojamiento = idAlojamiento;
	}

	public String getCondicionDeOrdenamiento() {
		return condicionDeOrdenamiento;
	}

	public void setCondicionDeOrdenamiento(String condicionDeOrdenamiento) {
		this.condicionDeOrdenamiento = condicionDeOrdenamiento;
	}
	
	
}
