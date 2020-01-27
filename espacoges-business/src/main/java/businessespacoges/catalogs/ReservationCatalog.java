package businessespacoges.catalogs;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import java.util.List;

import businessespacoges.ClubUser;
import businessespacoges.IndividualUser;
import businessespacoges.Installation;
import businessespacoges.Modality;
import businessespacoges.Reservation;
import businessespacoges.User;
import facade.exceptions.ApplicationException;

@Stateless
public class ReservationCatalog {


	/**
	 * Entity manager factory for accessing the persistence service
	 */
	@PersistenceContext
	private EntityManager em;
	/**
	 * Gets a reservation by its ID
	 * 
	 * @param id - id of the reservation
	 * @return - a reservation
	 */
	public Reservation getReservationById(int id) throws ApplicationException {
		TypedQuery<Reservation> q = em.createNamedQuery(Reservation.FIND_BY_ID, Reservation.class);
		q.setParameter("id", id);
		try {
			return q.getSingleResult();
		} catch (PersistenceException e) {
			throw new ApplicationException ("Reservation Not found.");
		}
	}

	/**
	 * Adds a reservation to the catalog
	 * 
	 * @param id            - id of the reservation
	 * @param u             - user who made the reservation
	 * @param modality      - modality of the reservation
	 * @param description   - description of the reservation
	 * @param inicio        - start of the reservation
	 * @param fim           - end of the reservation
	 * @param nParticipants - number of participants of this reservation
	 * @throws ApplicationException
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void addReservation(User u, Modality modality, String description, LocalDateTime inicio, LocalDateTime fim,
			int nParticipants) throws ApplicationException {

		// Due to JPA, gotta convert from LocalDateTime to Java.Util.Date
		
		IndividualUser iu;
		if (inicio.isBefore(LocalDateTime.now()) || fim.isBefore(LocalDateTime.now()))
			throw new ApplicationException("Uma das datas estao no passado");

		if (fim.isBefore(inicio))
			throw new ApplicationException("A data de fim é anterior à data de inicio");

		if (u instanceof IndividualUser) {
			iu = (IndividualUser) u;
			if (iu.isUnderAged())
				throw new ApplicationException("Utilizador é menor de idade");
			// Checks if the reservation starts and ends on the same day
			if (!isSameDayReservation(inicio, fim))
				throw new ApplicationException("A reserva tem a duracao superior a um dia");
			// Checks if the club has a reservation that lasts more than 24 hours
		} else if (u instanceof ClubUser && isSameDayReservation(inicio, fim)) {
			throw new ApplicationException("A reserva do clube tem uma duracao inferior a 24 horas");
		}
		if (modality == null) {
			throw new ApplicationException("A modalidade indicada nao existe");
		}

		if (nParticipants < modality.getMinParticipants()) {
			throw new ApplicationException("Nr Participantes = " + nParticipants + "<br> Nr: "
					+ modality.getMinParticipants() + " para a modalidade " + modality.getDesignation());
		}

		if (Reservation.getMaximiumDuration(inicio, fim) <= modality.getMinTime())
			throw new ApplicationException(
					"o periodo definido pelo par de datas/horas nao tem o numero de minutos minimo das "
							+ "atividades da modalidade: " + modality.getDesignation());

		Reservation reservation = new Reservation(u, modality, description,
				Date.from(inicio.atZone(ZoneId.systemDefault()).toInstant()),
				Date.from(fim.atZone(ZoneId.systemDefault()).toInstant()), nParticipants);
		em.persist(reservation);
	}

	/**
	 * Checks if a reservations is only on one day
	 * 
	 * @param inicio - start of the reservation
	 * @param fim    - end of the reservation
	 * @return true if is same day, false c.c
	 */
	private boolean isSameDayReservation(LocalDateTime inicio, LocalDateTime fim) {
		return inicio.getDayOfYear() == fim.getDayOfYear() && inicio.getYear() == fim.getYear();
	}

	/**
	 * Gets reservations made by a specific user type and modality
	 * 
	 * @param modality - the modality of the reservation
	 * @param userType - the userType (individual or club)
	 * @return - a map with the reservations
	 * @throws ApplicationException
	 */
	public List<Reservation> getReservationsByUserTypeAndModality(Modality modality, String userType) {
		TypedQuery<Reservation> q = em.createNamedQuery(Reservation.FIND_BY_USER_TYPE_AND_MODALITY, Reservation.class);
		q.setParameter("utype", userType);
		q.setParameter("modalityname", modality.getDesignation());
		return sortReservationByDate(q.getResultList());

	}

	/**
	 * Sorts the reservations by creation date
	 * 
	 * @param unsortedMap - map to sort
	 * @return a sorted map
	 */
	private List<Reservation> sortReservationByDate(List<Reservation> unsortedList) {
		List<Reservation> list = new LinkedList<>(unsortedList);

		// Sorting the list based on values
		Collections.sort(list, (o1, o2) -> o2.compareTo(o1));

		return list;

	}

	/**
	 * Gets reservations made by a specific user type
	 * 
	 * @param userType - the userType (individual or club)
	 * @return - a list with the reservations
	 * @throws ApplicationException
	 */
	public List<Reservation> getReservationsByUserType(String userType) throws ApplicationException{

		TypedQuery<Reservation> q = em.createNamedQuery(Reservation.FIND_BY_USER_TYPE, Reservation.class);
		q.setParameter("utype", userType);
		try {
		return sortReservationByDate(q.getResultList());
		}catch(PersistenceException e) {
			throw new ApplicationException("Error trying 2 find reservation with user type: " + userType);
		}
	}

	/**
	 * Updates Reservation Status
	 * 
	 * @param reservation
	 * @throws ApplicationException
	 */
	@Transactional(Transactional.TxType.REQUIRES_NEW)
	public void acceptReservation(Reservation reservation) throws ApplicationException {

		try {
			reservation.setStatus("ACCEPTED");
			em.merge(reservation);
		} catch (PersistenceException e) {
			throw new ApplicationException("Erro ao atualizar o estado da reserva");
		}
	}

}