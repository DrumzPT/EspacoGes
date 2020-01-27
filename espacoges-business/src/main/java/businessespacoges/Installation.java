package businessespacoges;

import static javax.persistence.CascadeType.ALL;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import businessespacoges.enums.DayType;
import facade.exceptions.ApplicationException;

@Entity
@NamedQueries({
		@NamedQuery(name = Installation.FIND_BY_NAME, query = "SELECT i FROM Installation i WHERE i.name = :"
				+ Installation.NAME_NAME),
		@NamedQuery(name = Installation.FIND_ALL_INSTALLATIONS, query = "SELECT i FROM Installation i"),
		@NamedQuery(name = Installation.FIND_INSTALLATIONS_BY_MODALITY, query = "SELECT DISTINCT i FROM Installation i WHERE (SELECT m.id FROM Modality m WHERE m.designation = :" + Installation.MODALITY_NAME + ") MEMBER OF i.modalities")})
public class Installation {

	public static final String FIND_BY_NAME = "Installation.findByName";
	public static final String FIND_ALL_INSTALLATIONS = "Installation.findAllInstallations";
	public static final String FIND_INSTALLATIONS_BY_MODALITY = "Installation.findInstallationByModality";

	public static final String NAME_NAME = "name";
	public static final String MODALITY_NAME = "modality";
	
	@Id
	@GeneratedValue
	private int id;

	/**
	 * Name of the installation
	 */
	private String name;

	/**
	 * Maximum stocking for the installation
	 */
	private int maxStocking;

	/**
	 * Price per hour for the utilization of the installation
	 */
	private double priceHour;

	/**
	 * Schedule of the installation
	 */
	@ElementCollection
	private Map<DayType, Schedule> schedules;

	/**
	 * MOdalities allowed on this installation
	 */
	@ManyToMany(cascade = ALL)
	private List<Modality> modalities;

	/**
	 * LogActivies of this Installation
	 */
	@ElementCollection
	private List<LogActivity> logs;

	// 1. constructor

	/**
	 * Creates a new installation
	 * 
	 * @param name       - name of the installation
	 * @param maxStock   - max capacity of the installation
	 * @param priceHour  - price per hour of the installation
	 * @param schedules  - map with the schedules of the installation
	 * @param modalities - modalities that this installation offers
	 */
	public Installation(String name, int maxStock, double priceHour, HashMap<DayType, Schedule> schedules,
			List<Modality> modalities) {
		this.name = name;
		this.maxStocking = maxStock;
		this.priceHour = priceHour;
		this.schedules = schedules;
		this.modalities = modalities;
		this.logs = new ArrayList<>();
	}

	/**
	 * Needed by JPA
	 */
	public Installation() {

	}

	// 2. getters and setters
	/**
	 * Adds a new schedule to the installation
	 * 
	 * @param schedule
	 */
	public void addSchedule(Schedule schedule) {
		this.schedules.put(schedule.getDayType(), schedule);
	}
	
	/**
	 * @return the id of the reservation
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * @return the name of the installation
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the maximum stocking
	 */
	public int getMaxStocking() {
		return this.maxStocking;
	}

	/**
	 * @return the price per hour
	 */
	public double getPriceHour() {
		return this.priceHour;
	}

	/**
	 * @return the opening time of the installation on a weeks day
	 */
	public LocalTime getWorkDayStartDateTime() {
		return schedules.get(DayType.WORKINGDAY).getOpeningTime();
	}

	/**
	 * @return the closing time of the installation on a weeks day
	 */
	public LocalTime getWorkDayEndDateTime() {
		return schedules.get(DayType.WORKINGDAY).getClosingTime();
	}

	/**
	 * @return the opening time of the installation on a weekends day
	 */
	public LocalTime getNonWorkingdDayStartDateTime() {
		return schedules.get(DayType.NON_WORKINGDAY).getOpeningTime();
	}

	/**
	 * @return the opening time of the installation on a weekends day
	 */
	public LocalTime getNonWorkingdDayEndDateTime() {
		return schedules.get(DayType.NON_WORKINGDAY).getClosingTime();
	}

	/**
	 * @return the list of modalities of the installation
	 */
	public List<Modality> getModalitiesList() {
		return this.modalities;
	}

	/**
	 * Gets the logs of activities of this installation
	 * 
	 * @return the logs of this installation
	 */
	public List<LogActivity> getLogActivity() {
		return this.logs;
	}

	/**
	 * @throws ApplicationException
	 * @requires installation free on the entire period of the reservation
	 * @requires status of the reservation is PENDING
	 */
	public void generateLogs(Reservation r) throws ApplicationException {
		LocalDateTime reservationSD = r.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime reservationED = r.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		long pdays = ChronoUnit.DAYS.between(reservationSD.with(LocalTime.MIDNIGHT),
				reservationED.with(LocalTime.MIDNIGHT));
		LogActivity tmp;
		LocalDateTime tmpI = null, tmpF = null;
		// if the period is 0
		if (pdays == 0 && isNonWorkingDay(reservationSD)) {
			if (reservationSD.isBefore(reservationSD.with(getNonWorkingdDayStartDateTime())))
				tmpI = reservationSD.with(getNonWorkingdDayStartDateTime());
			else if (reservationSD.isBefore(reservationSD.with(getNonWorkingdDayEndDateTime())))
				tmpI = reservationSD;
			else if (reservationSD.isAfter(reservationSD.with(getNonWorkingdDayEndDateTime())))
				throw new ApplicationException("Impossivel criar Registo de atividade!!");
			if (reservationED.isBefore(reservationED.with(getNonWorkingdDayEndDateTime())))
				tmpF = reservationED;
			if (reservationED.isAfter(reservationED.with(getNonWorkingdDayEndDateTime())))
				tmpF = reservationED.with(getNonWorkingdDayEndDateTime());

			// Creates the log
			if (tmpF != null && tmpI != null) {
				tmp = new LogActivity(r, tmpI, tmpF);
				logs.add(tmp);
			}
		} else if (pdays == 0 && !isNonWorkingDay(reservationSD)) {
			if (reservationSD.isBefore(reservationSD.with(getWorkDayStartDateTime())))
				tmpI = reservationSD.with(getWorkDayStartDateTime());
			else if (reservationSD.isBefore(reservationSD.with(getWorkDayEndDateTime())))
				tmpI = reservationSD;
			else if (reservationSD.isAfter(reservationSD.with(getWorkDayEndDateTime())))
				throw new ApplicationException("Impossivel criar Registo de atividade!!\n Actividade s� tem um dia"
						+ " e come�a depois do fecho da Installa��o");
			if (reservationED.isBefore(reservationED.with(getWorkDayEndDateTime())))
				tmpF = reservationED;
			if (reservationED.isAfter(reservationED.with(getWorkDayEndDateTime())))
				tmpF = reservationED.with(getWorkDayEndDateTime());

			// Creates the log
			if (tmpF != null && tmpI != null) {
				tmp = new LogActivity(r, tmpI, tmpF);
				logs.add(tmp);
			}
		} else {
			for (int daysPassed = 0; daysPassed <= pdays; daysPassed++) {
				if (daysPassed == 0) {
					generateLogsAssistantFirstDay(reservationSD, r);
				} else if (daysPassed == pdays) {
					generateLogsLastDay(reservationED, r);
				} else {
					generateLogsFullDay(r, daysPassed, reservationSD);
				}
			}
		}
	}

	/**
	 * Check if it's a non working day
	 * 
	 * @param day2Check
	 * @return true if it a nonWorkingDay
	 */
	private boolean isNonWorkingDay(LocalDateTime day2Check) {
		return day2Check.getDayOfWeek().equals(DayOfWeek.SATURDAY) || day2Check.getDayOfWeek().equals(DayOfWeek.SUNDAY);
	}

	/**
	 * Responsible for generating an ActivityLog for the full day
	 * 
	 * @param r             - the reservation
	 * @param daysPassed    - days passed since the start of the reservation
	 * @param reservationSD - reservation Stating Date
	 */
	private void generateLogsFullDay(Reservation r, int daysPassed, LocalDateTime reservationSD) {
		LocalDateTime tmpDay = reservationSD.plusDays(daysPassed);
		LocalDateTime tmpI, tmpF;
		if (isNonWorkingDay(tmpDay)) {
			tmpI = tmpDay.with(getNonWorkingdDayStartDateTime());
			tmpF = tmpDay.with(getNonWorkingdDayEndDateTime());
			logs.add(new LogActivity(r, tmpI, tmpF));
		} else {
			tmpI = tmpDay.with(getWorkDayStartDateTime());
			tmpF = tmpDay.with(getWorkDayEndDateTime());
			logs.add(new LogActivity(r, tmpI, tmpF));
		}
	}

	/**
	 * Responsible for generating LastReservation Day Activity Log
	 * 
	 * @param reservationED - reservation End-Date
	 * @param r             - the reservation made
	 */
	private void generateLogsLastDay(LocalDateTime reservationED, Reservation r) {
		LocalDateTime tmpI = null;
		LocalDateTime tmpF = null;
		LogActivity ld = null;
		if (isNonWorkingDay(reservationED)) {
			if (!reservationED.isBefore(reservationED.with(getNonWorkingdDayStartDateTime()))) {
				tmpI = reservationED.with(getNonWorkingdDayStartDateTime());
				if (reservationED.isBefore(reservationED.with(getNonWorkingdDayEndDateTime()))) {
					tmpF = reservationED;
				} else {
					tmpF = reservationED.with(getNonWorkingdDayEndDateTime());
				}
				ld = new LogActivity(r, tmpI, tmpF);
			}
		} else {
			if (!reservationED.isBefore(reservationED.with(getWorkDayStartDateTime()))) {
				tmpI = reservationED.with(getWorkDayStartDateTime());
				if (reservationED.isBefore(reservationED.with(getWorkDayEndDateTime()))) {
					tmpF = reservationED;
				} else {
					tmpF = reservationED.with(getWorkDayEndDateTime());
				}
				ld = new LogActivity(r, tmpI, tmpF);
			}
		}
		if (ld != null) {
			logs.add(ld);
		}

	}

	/**
	 * Generates the First day of a reservation logActivity
	 * 
	 * @param reservationSD - reservation start date
	 * @param r             - the reservation
	 */
	private void generateLogsAssistantFirstDay(LocalDateTime reservationSD, Reservation r) {
		LocalDateTime tmpI = null;
		LocalDateTime tmpF = null;
		boolean thisDayAintNothing = false;
		if (isNonWorkingDay(reservationSD)) {
			if (reservationSD.isBefore(reservationSD.with(getNonWorkingdDayStartDateTime())))
				tmpI = reservationSD.with(getNonWorkingdDayStartDateTime());
			else if (reservationSD.isBefore(reservationSD.with(getNonWorkingdDayEndDateTime())))
				tmpI = reservationSD;
			else if (reservationSD.isAfter(reservationSD.with(getNonWorkingdDayEndDateTime())))
				thisDayAintNothing = true;
			if (tmpI != null && !thisDayAintNothing) {
				tmpF = reservationSD.with(getNonWorkingdDayEndDateTime());
				LogActivity fd = new LogActivity(r, tmpI, tmpF);
				logs.add(fd);
			}
		} else {
			if (reservationSD.isBefore(reservationSD.with(getWorkDayStartDateTime()))) {
				tmpI = reservationSD.with(getWorkDayStartDateTime());
			} else if (reservationSD.isBefore(reservationSD.with(getWorkDayEndDateTime())))
				tmpI = reservationSD;
			else if (reservationSD.isAfter(reservationSD.with(getWorkDayEndDateTime())))
				thisDayAintNothing = true;
			if (tmpI != null && !thisDayAintNothing) {
				tmpF = reservationSD.with(getWorkDayEndDateTime());
				LogActivity fd = new LogActivity(r, tmpI, tmpF);
				logs.add(fd);
			}
		}

	}

	/**
	 * Checks if reservation collide with any logActivity
	 * 
	 * @param reservation to check
	 * @return
	 */
	public boolean doReservationsCollide(Reservation reservation) {
		LocalDateTime inicio = reservation.getStartDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime fim = reservation.getEndDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

		for (LogActivity l : getLogActivity()) {
			LocalDateTime logInicio = l.getInicio();
			LocalDateTime logFim = l.getFim();
			if ((inicio.isBefore(logFim) || inicio.isEqual(logFim))
					&& (logInicio.isBefore(fim) || logInicio.isEqual(fim)))
				return true;
		}
		return false;
	}

}
