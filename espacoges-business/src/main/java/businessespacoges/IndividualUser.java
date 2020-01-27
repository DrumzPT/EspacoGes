package businessespacoges;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import facade.exceptions.ApplicationException;

@Entity
public class IndividualUser extends User {

	/**
	 * users birthday dd/MM/yyyy
	 */
	@Temporal(TemporalType.DATE)
	private Date birthday;

	/**
	 * Creates a new individual user
	 * 
	 * @param id       - id of the user
	 * @param name     - name of the user
	 * @param nif      - nif of the user
	 * @param address  - address of the user
	 * @param birthday - date of birth of the user
	 * @throws ApplicationException
	 */
	public IndividualUser(int id, String name, int nif, String address, LocalDate birthday) {
		super(id, name, nif, address);
		this.birthday = toDateUtil(birthday);
	}
	
	/**
	 * Needed by JPA
	 */
	public IndividualUser() {
		
	}
	/**
	 * Converts a LocalDate to a Date, to persist in the database
	 * @param localDate - local date to convert
	 * @return the date
	 */
	private Date toDateUtil(LocalDate localDate) {
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
	
	/**
	 * Converts a Date to a LocalDate 
	 * @param date - the date to convert
	 * @return a local date
	 */ 
	private LocalDate toLocalDate(Date date) {
		LocalDate ld = new java.sql.Date(date.getTime()).toLocalDate();
		System.out.println("DATE " + date);
		System.out.println("LOCAL DATE " + ld);
		return ld;
	}
	

	/**
	 * Gets the users birthday
	 * 
	 * @return Users birthday
	 */
	public LocalDate getBirthday() {
		return toLocalDate(birthday);
	}

	/**
	 * Checks if the user is underage
	 * 
	 * @return true if under 18 years, false c.c
	 */
	public boolean isUnderAged() {
		boolean b = Period.between(toLocalDate(birthday), LocalDate.now()).getYears() < 18;
		System.out.println("IS UNDERANGED: " + b );
		return Period.between(toLocalDate(birthday), LocalDate.now()).getYears() < 18;
	}

}
