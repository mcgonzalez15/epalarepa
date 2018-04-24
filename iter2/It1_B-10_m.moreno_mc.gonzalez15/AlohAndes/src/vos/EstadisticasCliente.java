package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class EstadisticasCliente {

	@JsonProperty( value = "id")
	private String id;
	
	@JsonProperty( value = "nombre")
	private String nombre;
	
	@JsonProperty( value = "tipoAlojamiento")
	private String tipoAlojamiento;
	
	@JsonProperty( value = "dineroPago")
	private String dineroPago;
	
	@JsonProperty( value = "diasContratados")
	private String diasContratados;
	
	public EstadisticasCliente (@JsonProperty( value = "id") String pId, 
					 @JsonProperty( value = "nombre") String pNombre,
					 @JsonProperty( value = "tipoAlojamiento") String pTipoAlojamiento,
					 @JsonProperty( value = "dineroPago") String pDineroPago,
					 @JsonProperty( value = "diasContratados") String pDiasContratados)
	{
		id = pId;
		nombre = pNombre;
		tipoAlojamiento = pTipoAlojamiento;
		dineroPago = pDineroPago;
		diasContratados= pDiasContratados;
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

	public String getTipoAlojamiento() {
		return tipoAlojamiento;
	}

	public void setTipoAlojamiento(String tipoAlojamiento) {
		this.tipoAlojamiento = tipoAlojamiento;
	}

	public String getDineroPago() {
		return dineroPago;
	}

	public void setDineroPago(String dineroPago) {
		this.dineroPago = dineroPago;
	}

	public String getDiasContratados() {
		return diasContratados;
	}

	public void setDiasContratados(String diasContratados) {
		this.diasContratados = diasContratados;
	}
	
}
