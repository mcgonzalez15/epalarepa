package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Funcionamiento {
	
	@JsonProperty( value = "semana")
	private int semana;
	
	@JsonProperty( value = "idAlojamientosMin")
	private List<Integer> idAlojamientosMin;
	
	@JsonProperty( value = "ocupacionMin")
	private double ocupacionMin;
	
	@JsonProperty( value = "idAlojamientosMax")
	private List<Integer> idAlojamientosMax;
	
	@JsonProperty( value = "ocupacionMax")
	private double ocupacionMax;
	
	@JsonProperty( value = "idOperadoresMin")
	private List<Integer> idOperadoresMin;
	
	@JsonProperty( value = "numSolicitudesMin")
	private int numSolicitudesMin;
	
	@JsonProperty( value = "idOperadoresMax")
	private List<Integer> idOperadoresMax;
	
	@JsonProperty( value = "numSolicitudesMax")
	private int numSolicitudesMax;

	public Funcionamiento(@JsonProperty( value = "semana") int pSemana,
						  @JsonProperty( value = "idAlojamientosMin") List<Integer> pIdAlojamientosMin,
						  @JsonProperty( value = "ocupacionMin") double pOcupacionMin,
						  @JsonProperty( value = "idAlojamientosMax") List<Integer> pIdAlojamientosMax,
						  @JsonProperty( value = "ocupacionMax") double pOcupacionMax,
						  @JsonProperty( value = "idOperadoresMin") List<Integer> pIdOperadoresMin,
						  @JsonProperty( value = "numSolicitudesMin") int pNumSolicitudesMin,
						  @JsonProperty( value = "idOperadoresMax") List<Integer> pIdOperadoresMax,
						  @JsonProperty( value = "numSolicitudesMax") int pNumSolicitudesMax)
	{
		semana = pSemana;
		idAlojamientosMin = pIdAlojamientosMin;
		ocupacionMin = pOcupacionMin;
		idAlojamientosMax = pIdAlojamientosMax;
		ocupacionMax = pOcupacionMax;
		idOperadoresMin = pIdOperadoresMin;
		numSolicitudesMin = pNumSolicitudesMin;
		idOperadoresMax = pIdOperadoresMax;
		numSolicitudesMax= pNumSolicitudesMax;
	}

	public int getSemana() {
		return semana;
	}

	public void setSemana(int semana) {
		this.semana = semana;
	}

	public List<Integer> getIdAlojamientosMin() {
		return idAlojamientosMin;
	}

	public void setIdAlojamientosMin(List<Integer> idAlojamientosMin) {
		this.idAlojamientosMin = idAlojamientosMin;
	}

	public double getOcupacionMin() {
		return ocupacionMin;
	}

	public void setOcupacionMin(double ocupacionMin) {
		this.ocupacionMin = ocupacionMin;
	}

	public List<Integer> getIdAlojamientosMax() {
		return idAlojamientosMax;
	}

	public void setIdAlojamientosMax(List<Integer> idAlojamientosMax) {
		this.idAlojamientosMax = idAlojamientosMax;
	}

	public double getOcupacionMax() {
		return ocupacionMax;
	}

	public void setOcupacionMax(double ocupacionMax) {
		this.ocupacionMax = ocupacionMax;
	}

	public List<Integer> getIdOperadoresMin() {
		return idOperadoresMin;
	}

	public void setIdOperadoresMin(List<Integer> idOperadoresMin) {
		this.idOperadoresMin = idOperadoresMin;
	}

	public int getNumSolicitudesMin() {
		return numSolicitudesMin;
	}

	public void setNumSolicitudesMin(int numSolicitudesMin) {
		this.numSolicitudesMin = numSolicitudesMin;
	}

	public List<Integer> getIdOperadoresMax() {
		return idOperadoresMax;
	}

	public void setIdOperadoresMax(List<Integer> idOperadoresMax) {
		this.idOperadoresMax = idOperadoresMax;
	}

	public int getNumSolicitudesMax() {
		return numSolicitudesMax;
	}

	public void setNumSolicitudesMax(int numSolicitudesMax) {
		this.numSolicitudesMax = numSolicitudesMax;
	}
	
	public void agregarOperadorMin(int x)
	{
		if(!idOperadoresMin.contains(x))
		idOperadoresMin.add(x);
	}
	public void agregarOperadorMax(int x)
	{
		if(!idOperadoresMax.contains(x))
		idOperadoresMax.add(x);
	}
	public void agregarAlojamientoMin(int x)
	{
		if(!idAlojamientosMin.contains(x))
		idAlojamientosMin.add(x);
	}
	public void agregarAlojamientoMax(int x)
	{
		if(!idAlojamientosMax.contains(x))
		idAlojamientosMax.add(x);
	}
	
}
