package businessespacoges.catalogs;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import businessespacoges.ClubUser;
import businessespacoges.User;
import facade.exceptions.ApplicationException;

@Stateless
public class UserCatalog {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;

	/**
	 * Gets a user by its ID
	 * @param id - the id of the user
	 * @return - a user with the specified id
	 * @throws ApplicationException
	 */
	public User getUser(int id) throws ApplicationException {
		
		TypedQuery<User> query = em.createNamedQuery(User.FIND_BY_ID, User.class);
		query.setParameter(User.FIND_BY_ID_ID, id);
		try {
			return query.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException("User with internal id: " + id + " not found.");

		}
	}
	
	
	/**
	 * Updates a clubs status according to a K constant
	 * 
	 * @param u - the club user
	 * @param k - the constant
	 * @throws ApplicationException
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void updateStatusIfNeeded(User u, int k) throws ApplicationException {
		long hours = ((ClubUser) u).getHoursReservated();
		try {
			if (hours <= k) {
				((ClubUser) u).setEstatuto("BRONZE");
			} else if (hours <= 100 * k) {
				((ClubUser) u).setEstatuto("SILVER");
			} else {
				((ClubUser) u).setEstatuto("GOLD");
			}
			em.merge(u);
		} catch (Exception e) {
			throw new ApplicationException("Erro ao dar update ao status do clube: " + u.getName());
		}
	}

	/**
	 * Updates the number of hours reserved by a club
	 * 
	 * @param u            - user
	 * @param ldtStartDate - the start date
	 * @param ldtEndDate   - the end date
	 * @throws ApplicationException
	 * @requires User to be of ClubUser Instance
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void updateHoursReservated(User u, LocalDateTime ldtStartDate, LocalDateTime ldtEndDate)
			throws ApplicationException {
		long hours = ChronoUnit.HOURS.between(ldtStartDate, ldtEndDate);
		try {
			((ClubUser) u).addHoursReservated(hours);
			em.merge(u);
		} catch (Exception e) {
			throw new ApplicationException(
					"Erro ao dar update no n�mero de horas reservadas pelo Utilizador: " + u.getName());
		}

	}

}
