package businessespacoges;

import static javax.persistence.InheritanceType.SINGLE_TABLE;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
	@NamedQuery(name=User.FIND_BY_ID, query="SELECT u FROM User u WHERE u.id = :" + User.FIND_BY_ID_ID)
})
@Inheritance(strategy = SINGLE_TABLE)
public abstract class User {
	
	public static final String FIND_BY_ID = "User.findById";
	public static final String FIND_BY_ID_ID = "id";

	/**
	 * ID
	 */
	@Id 
	private int id;

	/**
	 * Name
	 */
	private String name;

	/**
	 * NIF
	 */
	private int nif;

	/**
	 * address
	 */
	private String address;
	
	
	@Column(insertable = false, updatable = false) private String dtype;

	// 1. constructor
	/**
	 * Creates a new User
	 * 
	 * @param id     - the ID of the user (registration number)
	 * @param name
	 * @param nif
	 * @param morada
	 */
	public User(int id, String name, int nif, String address) {
		this.id = id;
		this.name = name;
		this.nif = nif;
		this.address = address;
	}
	
	/**
	 * Needed by JPA
	 */
	public User() {
		
	}

	// 2. getters and setters

	/**
	 * @return users ID
	 */
	public int getID() {
		return id;
	}

	/**
	 * @return users Name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return users Nif
	 */
	public int getNif() {
		return nif;
	}

	/**
	 * @return users Address
	 */
	public String getAddress() {
		return address;
	}

}
