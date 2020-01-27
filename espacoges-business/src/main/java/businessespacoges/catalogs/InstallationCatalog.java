package businessespacoges.catalogs;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import businessespacoges.Installation;
import businessespacoges.Reservation;
import facade.exceptions.ApplicationException;

@Stateless
public class InstallationCatalog {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;


	/**
	 * Gets an installation by its name
	 * 
	 * @param name - name of the installation
	 * @return an installation
	 * @throws ApplicationException
	 */
	public Installation getInstallationByname(String name) throws ApplicationException {
		TypedQuery<Installation> q;
		try {
			q = em.createNamedQuery(Installation.FIND_BY_NAME, Installation.class);
			q.setParameter("name", name);
			return q.getSingleResult();
		} catch (Exception e) {
			em.close();
			throw new ApplicationException("Instalacao inexistente");
		}
	}

	/**
	 * Gets the list of this system Installations names
	 * 
	 * @return List that contains the names of the installations
	 */
	public List<String> getAllInstallationNames() {
		ArrayList<String> list = new ArrayList<>();
		TypedQuery<Installation> q = em.createNamedQuery(Installation.FIND_ALL_INSTALLATIONS, Installation.class);
		List<Installation> listOfInstallation = q.getResultList();

		for (Installation i : listOfInstallation) {
			list.add(i.getName());
		}
		return list;
	}
	
	/**
	 * Gets the list of this system Installations that has a given modality
	 * @return List that contains the installations
	 */
	public List<Installation> getInstallationsBymodality(String modality) {
		
		TypedQuery<Installation> q = em.createNamedQuery(Installation.FIND_INSTALLATIONS_BY_MODALITY, Installation.class);
        q.setParameter("modality",modality);
        
        return q.getResultList();
	}

	/**
	 * Generates the logs of an installation according to a reservation
	 * 
	 * @param installation - the installations
	 * @param r            - the reservation
	 * @throws ApplicationException
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void generateLogs(Installation installation, Reservation r) throws ApplicationException {
		try {
			installation.generateLogs(r);
			em.merge(installation);
		} catch (Exception e) {
			throw new ApplicationException("Erro ao gerar logs");
		}

	}

}
