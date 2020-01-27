package businessespacoges;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


import businessespacoges.enums.DayType;

@Embeddable
public class Schedule {
	
	private static final int A_DAY = 1;

	private static final Month A_MONTH = Month.JANUARY;

	private static final int A_YEAR = 1975;

	/**
	 * Opening Time
	 */
	@Temporal(TemporalType.TIME)
	private Date startTime;

	/**
	 * Closing Time
	 */
	@Temporal(TemporalType.TIME)
	private Date endTime;

	/**
	 * Day of the week
	 */
	@Enumerated(EnumType.STRING)
	private DayType dayType;

	// 1. constructor

	/**
	 * Creates a new Schedule
	 * 
	 * @param openingTime
	 * @param closingTime
	 * @param weekday
	 */
	public Schedule(LocalTime openingTime, LocalTime closingTime, DayType dayType) {
		this.startTime = toDate(openingTime);
		this.endTime = toDate(closingTime);
		this.dayType = dayType;
	}
	
	/**
	 * Needed by JPA
	 */
	public Schedule() {
		
	}

	// 2. getters and setters
	/**
	 * @return the opening time
	 */
	public LocalTime getOpeningTime() {
		return toLocalTime(this.startTime);
	}

	/**
	 * @return the closing time
	 */
	public LocalTime getClosingTime() {
		return toLocalTime(this.endTime);
	}

	/**
	 * @return the weekday
	 */
	public DayType getDayType() {
		return this.dayType;
	}
	
	/**
	 * Converts a LocalTime to a Date to persist in the database
	 * It uses a default Day, Month and Year, because it doesnt matter
	 * to the calculation of the time
	 * @param localDate - the local date to convert
	 * @return a Date
	 */
	private Date toDate(LocalTime localTime) {
		Instant instant = localTime.atDate(LocalDate.of(A_YEAR, A_MONTH, A_DAY)).
		        atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}
	
	/**
	 * Converts a date to a LocalTime
	 * @param date - the date to convert
	 * @return a LocalTime
	 */
	private LocalTime toLocalTime(Date date) {
		Instant instant = Instant.ofEpochMilli(date.getTime());
		return LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalTime();
	}

}
