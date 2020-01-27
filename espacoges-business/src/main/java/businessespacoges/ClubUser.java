package businessespacoges;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import businessespacoges.enums.Estatuto;
import facade.exceptions.ApplicationException;

@Entity 
public class ClubUser extends User {

	/**
	 * Club status
	 */
	@Enumerated(EnumType.STRING)
	private Estatuto estatuto;
	
	/**
	 * Number of hours that this club has reserved, since it joined the system
	 */
	private long nHoursReservated;

	/**
	 * Creates a club
	 * 
	 * @param id       - id of the club
	 * @param name     - name of the club
	 * @param nif      - nif of the club
	 * @param address  - address of the club
	 * @param estatuto - status of the club
	 */
	public ClubUser(int id, String name, int nif, String address, Estatuto estatuto) {
		super(id, name, nif, address);
		this.estatuto = estatuto;
		this.nHoursReservated = 0;
	}
	
	/**
	 * Needed by JPA
	 */
	public ClubUser() {		
	}

	/**
	 * @return club status
	 */
	public Estatuto getEstatuto() {
		return estatuto;
	}

	/**
	 * Sets a new status for the club
	 * 
	 * @param estatuto - new status
	 * @throws ApplicationException
	 */
	public void setEstatuto(String estatuto) throws ApplicationException {
		if (estatuto.equals("BRONZE"))
			this.estatuto = Estatuto.BRONZE;
		else if (estatuto.equals("SILVER"))
			this.estatuto = Estatuto.SILVER;
		else if (estatuto.equals("GOLD"))
			this.estatuto = Estatuto.GOLD;
		else
			throw new ApplicationException("Estatuto inexistente");
	}
	
	/**
	 * 
	 * @return club number of reservated hours
	 */
	public long getHoursReservated() {
		return this.nHoursReservated;
	}
	
	/**
	 * @param hours increments the club reservated hours
	 */
	public void addHoursReservated(long hours) {
		this.nHoursReservated += hours;
	}

}