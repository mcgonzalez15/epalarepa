package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class UtilizacionAloh {

	@JsonProperty( value = "tipoUsuario")
	private String tipoUsuario;
	
	@JsonProperty( value = "tipoAlojamiento")
	private String tipoAlojamiento;
	
	@JsonProperty( value = "dineroPagado")
	private String dineroPagado;
	
	@JsonProperty( value = "diasContratados")
	private String diasContratados;
	
	public UtilizacionAloh (@JsonProperty( value = "tipoUsuario") String pTipoUsuario, 
						 @JsonProperty( value = "tipoAlojamiento") String pTipoAlojamiento,
						 @JsonProperty( value = "dineroPagado") String pDineroPagado,
						 @JsonProperty( value = "diasContratados") String pDiasContratados)
	{
		tipoUsuario = pTipoUsuario;
		tipoAlojamiento = pTipoAlojamiento;
		dineroPagado = pDineroPagado;
		diasContratados = pDiasContratados;
	}

	public String getTipoUsuario() {
		return tipoUsuario;
	}

	public void setTipoUsuario(String tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}

	public String getTipoAlojamiento() {
		return tipoAlojamiento;
	}

	public void setTipoAlojamiento(String tipoAlojamiento) {
		this.tipoAlojamiento = tipoAlojamiento;
	}

	public String getDineroPagado() {
		return dineroPagado;
	}

	public void setDineroPagado(String dineroPagado) {
		this.dineroPagado = dineroPagado;
	}

	public String getDiasContratados() {
		return diasContratados;
	}

	public void setDiasContratados(String diasContratados) {
		this.diasContratados = diasContratados;
	}
	
	
}
