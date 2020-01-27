package businessespacoges.catalogs;

import java.util.ArrayList;
import java.util.List;

import businessespacoges.Modality;
import facade.exceptions.ApplicationException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 * A catalog of modalities
 *
 */
@Stateless
public class ModalityCatalog {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Gets a modality by its designation
	 * 
	 * @param designation - the modality designation
	 * @return - a modality
	 */
	public Modality getModalityByDesignation(String designation) throws ApplicationException {
		TypedQuery<Modality> q = em.createNamedQuery(Modality.FIND_BY_DESIGNATION, Modality.class);
		q.setParameter("designation", designation);
		
		try {
			return q.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Couldn't find modalities");
		}
	}

	/**
	 * @return Returns all modalities names
	 */
	public List<String> getAllModalitiesNames()  throws ApplicationException{
		ArrayList<String> list = new ArrayList<>();
		TypedQuery<Modality> q = em.createNamedQuery(Modality.FIND_ALL_DESIGNATIONS, Modality.class);
		try {
		List<Modality> listOfModalities = q.getResultList();

		for (Modality m : listOfModalities) {
			list.add(m.getDesignation());
		}
		return list;
		}catch(PersistenceException e) {
			throw new ApplicationException ("Customer not found.");
		}
	}
}
