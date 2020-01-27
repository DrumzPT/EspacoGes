package businessespacoges;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class LogActivity {

	/**
	 * this Activity reservation
	 */
	@ManyToOne
	private Reservation reservation;

	/**
	 * Activity start
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;

	/**
	 * Activity end
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fim;

	/**
	 * Constructor for LogActivity
	 * 
	 * @param reservation - this logs Reservation
	 * @param inicio      - Logs start date
	 * @param fim         - logs ending date
	 */
	public LogActivity(Reservation reservation, LocalDateTime inicio, LocalDateTime fim) {
		this.reservation = reservation;
		this.inicio = Date.from(inicio.atZone(ZoneId.systemDefault()).toInstant());
		this.fim = Date.from(fim.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * Needed by JPA
	 */
	public LogActivity() {
	}

	/**
	 * @return Logs start date
	 */
	public LocalDateTime getInicio() {
		return LocalDateTime.ofInstant(inicio.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * @return Logs start date
	 */
	public Date getInicioD() {
		return inicio;
	}

	/**
	 * @return logs end date
	 */
	public LocalDateTime getFim() {
		return LocalDateTime.ofInstant(fim.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * @return this logs Reservation
	 */
	public Reservation getReservation() {
		return reservation;
	}

	/**
	 * Compares two logs Activities by their start date
	 * 
	 * @param o1 - the other logActivity
	 * @return 1 if is before, -1 if not
	 */
	public int compareTo(LogActivity o1) {
		if (inicio.before(o1.getInicioD()))
			return 1;
		return -1;
	}
}
