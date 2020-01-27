package businessespacoges;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import businessespacoges.enums.ReservationStatus;

@Entity

@NamedQueries({
		@NamedQuery(name = Reservation.FIND_BY_ID, query = "SELECT r FROM Reservation r WHERE r.id = :"
				+ Reservation.RESERVATION_ID),
		@NamedQuery(name = Reservation.FIND_BY_USER_TYPE_AND_MODALITY, query = "SELECT r FROM Reservation r WHERE r.user IN ( SELECT u.id FROM User u WHERE u.dtype LIKE :"
				+ Reservation.USER_TYPE
				+ " AND u = r.user) AND r.modality IN (SELECT m FROM Modality m WHERE m.designation LIKE :"
				+ Reservation.MODALITY_DESIGNATION + " AND m = r.modality)"),
		@NamedQuery(name = Reservation.FIND_BY_USER_TYPE, query = "SELECT r FROM Reservation r WHERE r.user IN ( SELECT u FROM User u WHERE u.dtype LIKE :"
				+ Reservation.USER_TYPE + " AND u = r.user) AND  r.status LIKE 'PENDING'") })
public class Reservation {

	/**
	 * Named Query name constants
	 */
	public static final String FIND_BY_ID = "Reservation.findById";
	public static final String FIND_BY_USER_TYPE_AND_MODALITY = "Reservation.findByUserTypeAndModality";
	public static final String FIND_BY_USER_TYPE = "Reservation.findByUserType";
	
	public static final String RESERVATION_ID = "id";
	public static final String USER_TYPE = "utype";
	public static final String MODALITY_DESIGNATION = "modalityname";
	
	/**
	 * id of the reservation
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private int id;

	/**
	 * id of the user who made the reservation
	 */
	@OneToOne
	private User user;

	/**
	 * modality of the reservation
	 */
	@OneToOne
	private Modality modality;

	/**
	 * small description of the activity
	 */
	private String description;

	/**
	 * the start date of the reservation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date inicio;

	/**
	 * the end date of the reservation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date fim;

	/**
	 * the number of participants
	 */
	private int nParticipants;

	/**
	 * status of the reservation
	 */
	@Enumerated(EnumType.STRING)
	private ReservationStatus status;

	/**
	 * the creation date of this reservation
	 */
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;

	// 1. constructor
	/**
	 * 
	 * @param id            - id of the reservation
	 * @param idUser        - id of the user who made the reservation
	 * @param modality      - modality of this reservation
	 * @param description   - description of the reservation
	 * @param inicio        - start of the reservation
	 * @param fim           - end of the reservation
	 * @param nParticipants - number of participants
	 */
	public Reservation(User user, Modality modality, String description, Date inicio, Date fim, int nParticipants) {

		this.user = user;
		this.modality = modality;
		this.description = description;
		this.inicio = inicio;
		this.fim = fim;
		this.nParticipants = nParticipants;
		this.status = ReservationStatus.PENDING;
		this.creationDate = new Date();
	}

	/**
	 * Needed by JPA
	 */
	public Reservation() {
	}

	// 2. getters and setters

	/**
	 * sets a new status for this reservation
	 */
	public void setStatus(String status) {
		if (status.equals("PENDING"))
			this.status = ReservationStatus.PENDING;
		else if (status.equals("ACCEPTED"))
			this.status = ReservationStatus.ACCEPTED;
		else if (status.equals("REFUSED"))
			this.status = ReservationStatus.REFUSED;
	}

	/**
	 * @return the id of the reservation
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return the id of the user who made reservation
	 */
	public User getUser() {
		return this.user;
	}
	
	public int getUserId() {
		return this.user.getID();
	}

	/**
	 * @return the modality of the reservation
	 */
	public Modality getModality() {
		return this.modality;
	}

	/**
	 * @return the creation date of this reservation
	 */
	public Date getCreationDate() {
		return this.creationDate;
	}

	/**
	 * @return the number of participants in this reservation
	 */
	public int getNumberOfParticipants() {
		return this.nParticipants;
	}

	/**
	 * @return the description of this reservation
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return the schedule of the reservation
	 */
	public static long getMaximiumDuration(LocalDateTime inicio, LocalDateTime fim) {
		Duration diff = Duration.between(inicio, fim);
		return diff.toMinutes();
	}

	/**
	 * @return the start date of this reservation
	 */
	public Date getStartDate() {
		return inicio;
	}

	/**
	 * @return the end date of this reservation
	 */
	public Date getEndDate() {
		return fim;
	}

	/**
	 * Gets the Start Date of this reservation as a LocalDateTime
	 * @return the start date as a LocalDateTime
	 */
	public LocalDateTime getLDTStartDate() {
		return LocalDateTime.ofInstant(inicio.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * Gets the End Date of this reservation as a LocalDateTime
	 * @return the end date as a LocalDateTime
	 */
	public LocalDateTime getLDTEndDate() {
		return LocalDateTime.ofInstant(fim.toInstant(), ZoneId.systemDefault());
	}

	/**
	 * @return the status of the reservation
	 */
	public ReservationStatus getStatus() {
		return this.status;
	}

	/**
	 * compares two reservations by date
	 * 
	 * @param value reservation to compare
	 * @return
	 */
	public int compareTo(Reservation value) {
		if (this.creationDate.before(value.getCreationDate()))
			return 1;
		return -1;
	}

	/**
	 * Gets the Creation Date of this reservation as a LocalDateTime
	 * @return the creation date as a LocalDateTime
	 */
	public LocalDateTime getLDTCreationDate() {
		return LocalDateTime.ofInstant(creationDate.toInstant(), ZoneId.systemDefault());
	}

}
