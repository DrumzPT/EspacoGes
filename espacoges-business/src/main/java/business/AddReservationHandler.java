package business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;


import businessespacoges.Modality;
import businessespacoges.User;
import businessespacoges.catalogs.ModalityCatalog;
import businessespacoges.catalogs.ReservationCatalog;
import businessespacoges.catalogs.UserCatalog;
import facade.dto.ReservationDTO;
import facade.exceptions.ApplicationException;

/**
 * The handler of the add reservation use case
 */
@Stateless
public class AddReservationHandler {
	
	@EJB
	private UserCatalog userCatalog;
	
	@EJB
	private ModalityCatalog modalityCatalog;
	
	@EJB
	private ReservationCatalog reservationCatalog;


	/**
	 * Adds a new reservation to the catalog
	 * 
	 * @param userId        - the user who made the reservation
	 * @param modalidade    - the modality of the reservation
	 * @param description   - the description of the reservation
	 * @param inicio        - the start of the reservation
	 * @param fim           - the end of the reservation
	 * @param nParticipants - the number of participants of the reservation
	 * @throws ApplicationException
	 */
	public ReservationDTO addReservation(int userId, String modalidade, String description, String inicio, String fim,
			int nParticipants) throws ApplicationException {

		// the transaction
		User user = userCatalog.getUser(userId);
		Modality modality = modalityCatalog.getModalityByDesignation(modalidade);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
		LocalDateTime inicioConverted = LocalDateTime.parse(inicio, formatter);
		LocalDateTime fimConverted = LocalDateTime.parse(fim, formatter);
		
		try {
			reservationCatalog.addReservation(user, modality, description, inicioConverted, fimConverted, nParticipants);
			return new ReservationDTO(0,userId, modalidade, description, inicio, fim, nParticipants);
		}catch(Exception e) {
			throw new ApplicationException(e.getMessage());
		}

	}

	/**
	 * @return a list with all modalities names
	 * @throws ApplicationException 
	 */
	public List<String> getAllModalitiesNames() throws ApplicationException {
		List<String> x = new ArrayList<>();
		
		try {
			x = modalityCatalog.getAllModalitiesNames();
		} catch (Exception e) {
			throw new ApplicationException("Error getting modalities", e);
		}
		return x;
	}

}
