package vos;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;


public class Operador
{
	

	//----------------------------------------------------------------------------------------------------------------------------------
	// ATRIBUTOS
	//----------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Id del operador
	 */
	@JsonProperty( value = "id")
	private Long id;

	/**
	 * Nombre del operador
	 */
	@JsonProperty( value = "nombre")
	private String nombre;

	/**
	 * Correo del operador
	 */
	@JsonProperty( value = "correo")
	private String correo;

	/**
	 * Ofertas que ofrece el operador
	 */
	@JsonProperty( value = "ofertas")
	private List<Oferta> ofertas;

	/**
	 * Relacion que mantiene el operador con uniandes.
	 */
	@JsonProperty( value = "vinculoUniandes")
	private VinculoUniandes vinculoUniandes;


	public Operador(@JsonProperty( value = "id") Long pId,
				    @JsonProperty( value = "nombre")String pNombre,
				    @JsonProperty( value = "correo") String pCorreo,
				    @JsonProperty( value = "ofertas")List<Oferta>pOfertas,
				    @JsonProperty( value = "vinculoUniandes")VinculoUniandes pVinculoUniandes)
	{
		id = pId;
		nombre=pNombre;
		correo = pCorreo;
		ofertas = pOfertas;
		vinculoUniandes = pVinculoUniandes;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	public String getNombre() {
		return nombre;
	}


	public String getCorreo() {
		return correo;
	}


	public List<Oferta> getOfertas() {
		return ofertas;
	}


	public VinculoUniandes getVinculoUniandes() {
		return vinculoUniandes;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public void setOfertas(List<Oferta> ofertas) {
		this.ofertas = ofertas;
	}

	public void setVinculoUniandes(VinculoUniandes vinculoUniandes) {
		this.vinculoUniandes = vinculoUniandes;
	}

	
}

