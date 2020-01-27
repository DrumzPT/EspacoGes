package business;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import businessespacoges.ClubUser;
import businessespacoges.Installation;
import businessespacoges.Reservation;
import businessespacoges.User;
import businessespacoges.catalogs.InstallationCatalog;
import businessespacoges.catalogs.ModalityCatalog;
import businessespacoges.catalogs.ReservationCatalog;
import businessespacoges.catalogs.UserCatalog;
import businessespacoges.enums.ReservationStatus;
import facade.dto.InstallationDTO;
import facade.dto.ReservationDTO;
import facade.exceptions.ApplicationException;

@Stateless
public class ProcessReservationHandler {
	
	@EJB
	private UserCatalog userCatalog;
	
	@EJB
	private InstallationCatalog installationCatalog;
	
	@EJB
	private ReservationCatalog reservationCatalog;
	
	/**
	 * The constant used on the clubs status
	 */
	private static final int K = 30;

	
	public void processReservation(int reservationId, String nomeInst) throws ApplicationException {
		
		Reservation reservation;
		Installation installation;
		
		try {
			reservation = reservationCatalog.getReservationById(reservationId);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		try {
			installation = installationCatalog.getInstallationByname(nomeInst);
		} catch (Exception e) {
			throw new ApplicationException(e.getMessage());
		}
		
		if (!reservation.getStatus().equals(ReservationStatus.PENDING))
			throw new ApplicationException("A reserva " + reservationId + " indicada nao esta pendente");
		else if (installation.doReservationsCollide(reservation))
			throw new ApplicationException("Ja existe uma reserva para esse periodo nesta instalação");
		else
			installationCatalog.generateLogs(installation, reservation);
		
		reservationCatalog.acceptReservation(reservation);
		User u = reservation.getUser();
		if (u instanceof ClubUser) {
			userCatalog.updateHoursReservated(u, reservation.getLDTStartDate(), reservation.getLDTEndDate());
			userCatalog.updateStatusIfNeeded(u, K);
		}
	}



	public Iterable<ReservationDTO> getReservationsByUserType(String type) throws ApplicationException {
		DateFormat df = new SimpleDateFormat("dd/MM/yyy HH:mm");
		
		List<ReservationDTO> resultReservation = new LinkedList<>();
		List<Reservation> oldReservations = reservationCatalog.getReservationsByUserType(type);
		for (Reservation r : oldReservations) {
			
			String startDate = df.format(r.getStartDate());
			String endDate = df.format(r.getEndDate());
			
			resultReservation.add(new ReservationDTO(r.getId(), r.getUserId(), r.getModality().getDesignation(), r.getDescription(),
					startDate, endDate, r.getNumberOfParticipants()));
		}
		return resultReservation;
	}
	
	public List<InstallationDTO> getInstallationsByModality(String modality) throws ApplicationException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
		
		List<InstallationDTO> listOfDTO = new LinkedList<>();
		List<Installation> list = installationCatalog.getInstallationsBymodality(modality);
		
		for(Installation i : list) {
			String startDateW = i.getWorkDayStartDateTime().format(dtf);
			String endDateW = i.getWorkDayEndDateTime().format(dtf);
			String  startDateF = i.getNonWorkingdDayStartDateTime().format(dtf);
			String endDateF = i.getNonWorkingdDayEndDateTime().format(dtf);
			
			listOfDTO.add(new InstallationDTO(i.getId(),i.getName(),startDateW,endDateW,startDateF,endDateF));
			
		}
		
		return listOfDTO;

	}
	
	
	

}
