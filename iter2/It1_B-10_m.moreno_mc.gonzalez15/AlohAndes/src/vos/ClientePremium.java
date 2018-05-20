package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClientePremium {

	@JsonProperty( value = "idCliente")
	private String idCliente;
	
	@JsonProperty( value = "razones")
	private List<String> razones;
	
	public ClientePremium(@JsonProperty( value = "idCliente") String pIdCliente,
						  @JsonProperty( value = "razones")List<String> pRazones)
	{
		idCliente = pIdCliente;
		razones = pRazones;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public List<String> getRazones() {
		return razones;
	}

	public void setRazones(List<String> razones) {
		this.razones = razones;
	}
	
	
}
