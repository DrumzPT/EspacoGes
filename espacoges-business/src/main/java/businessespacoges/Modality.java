package businessespacoges;

import businessespacoges.enums.ModalityType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * Objects of this class represent modalities of EspacoGes
 *
 */
@Entity

@NamedQueries({
@NamedQuery(name=Modality.FIND_BY_DESIGNATION, query="SELECT m FROM Modality m WHERE m.designation = :" + 
		Modality.DESIGNATION_MODALITY),
@NamedQuery(name=Modality.FIND_ALL_DESIGNATIONS, query="SELECT m FROM Modality m")
})

public class Modality {
	
	// Named query name constants
	public static final String FIND_BY_DESIGNATION = "Modality.findByDesignation";
	public static final String FIND_ALL_DESIGNATIONS = "Modality.findAllDesignations";
		
	public static final String DESIGNATION_MODALITY = "designation";

	// Modality attributes
	
	@Id @GeneratedValue private int id;

	/**
	 * Modality designation
	 */
	@Column(nullable = false, unique = true)
	private String designation;

	/**
	 * Modality type
	 */
	@Enumerated(EnumType.STRING)
	private ModalityType modalityType;

	/**
	 * Minimum number of participants
	 */
	@Column(nullable = false)
	private int minParticipants;

	/**
	 * Minimum time required
	 */
	@Column(nullable = false)
	private int minTime;

	// 1. constructor

	/**
	 * Creates a new modality
	 * 
	 * @param designation
	 * @param modalityType
	 * @param minParticipants
	 * @param minTime
	 */

	public Modality(String designation, ModalityType modalityType, int minParticipants, int minTime) {
		this.designation = designation;
		this.modalityType = modalityType;
		this.minParticipants = minParticipants;
		this.minTime = minTime;
	}
	
	/**
	 * Needed by JPA
	 */
	public Modality(){
		
	}

	// 2. getters and setters

	/**
	 * @return The designation of the modality
	 */
	public String getDesignation() {
		return designation;
	}

	/**
	 * @return The type of the modality
	 */
	public ModalityType getType() {
		return modalityType;
	}

	/**
	 * @return The min participants of the modality
	 */
	public int getMinParticipants() {
		return minParticipants;
	}

	/**
	 * @return The min time of the modality
	 */
	public int getMinTime() {
		return minTime;
	}

}
