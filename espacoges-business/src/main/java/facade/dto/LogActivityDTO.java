package facade.dto;

import java.io.Serializable;
import java.util.Date;

public class LogActivityDTO implements Serializable {

	private static final long serialVersionUID = -369166444207217157L;
	private final int reservationID;
	private final String inicio;
	private final String fim;
	
	public LogActivityDTO(int reservationID, String inicio, String fim) {
		this.reservationID = reservationID;
		this.inicio = inicio;
		this.fim = fim;
		
	}

	public int getReservationID() {
		return reservationID;
	}

	public String getInicio() {
		return inicio;
	}

	public String getFim() {
		return fim;
	}

}
