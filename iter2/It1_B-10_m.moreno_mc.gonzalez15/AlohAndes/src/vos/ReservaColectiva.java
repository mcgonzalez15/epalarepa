package vos;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class ReservaColectiva {

	@JsonProperty( value = "idReservaColectiva")
	private Long idReservaColectiva;
	
	@JsonProperty( value = "reserva")
	private Reserva reserva;
	
	@JsonProperty( value = "cantidadReserva")
	private int cantidadReserva;
	
	public ReservaColectiva(@JsonProperty( value = "idReservaColectiva") Long pIdReservaColectiva,
			@JsonProperty( value = "reserva") Reserva pReserva, 
			@JsonProperty( value = "cantidadReserva") int pCantidad)
	{
		idReservaColectiva = pIdReservaColectiva;
		reserva = pReserva;
		cantidadReserva = pCantidad;
	}

	public Long getIdReservaColectiva() {
		return idReservaColectiva;
	}

	public void setIdReservaColectiva(Long idReservaColectiva) {
		this.idReservaColectiva = idReservaColectiva;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public int getCantidad() {
		return cantidadReserva;
	}

	public void setCantidad(int cantidadReserva) {
		this.cantidadReserva = cantidadReserva;
	}
	
	
}
