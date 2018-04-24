package vos;

import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Condicionn {


	@JsonProperty( value = "tipoAlojamiento")
	private Oferta tipo;
	
	@JsonProperty( value = "fechaInicio")
	private Date fechaInicio;
	
	@JsonProperty( value = "fechaFin")
	private Date fechaFin;

	public Oferta getTipo() {
		return tipo;
	}

	public void setTipo(Oferta tipo) {
		this.tipo = tipo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}
	
	
	
	
	
}
