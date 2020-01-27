package application;


import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.AddReservationHandler;
import business.ProcessReservationHandler;
import businessespacoges.Reservation;
import facade.dto.InstallationDTO;
import facade.dto.ModalityDTO;
import facade.dto.ReservationDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IReservationServiceRemote;

@Stateless
public class ReservationService implements IReservationServiceRemote {
	
	@EJB
	private AddReservationHandler addReservationHandler;
	
	@EJB
	private ProcessReservationHandler processReservationHandler;

	@Override
	public ReservationDTO addReservation(int numeroUtilizador, String modalidade, String atividade, String inicio,
			String fim, int numeroParticipantes) throws ApplicationException {
		return addReservationHandler.addReservation(numeroUtilizador, modalidade, atividade, inicio, fim, numeroParticipantes);
	}

	@Override
	public Iterable<ModalityDTO> getModalities() throws ApplicationException {
		List<ModalityDTO> result = new LinkedList<>();
		for(String modality: addReservationHandler.getAllModalitiesNames()) {
			result.add(new ModalityDTO(modality));
		}
		return result;
	}

	@Override
	public Iterable<String> getUserTypes() {
		List<String> utypes = new LinkedList<String>();
		utypes.add("Individual");
		utypes.add("Clube");
		return utypes;
	}

	@Override
	public void processReservation(int reservationId, String nomeInst) throws ApplicationException {
		processReservationHandler.processReservation(reservationId, nomeInst);
	}

	@Override
	public Iterable<ReservationDTO> getReservationsByUserType(String type) throws ApplicationException {
		return processReservationHandler.getReservationsByUserType(type);
	}

	@Override
	public Iterable<InstallationDTO> getInstallationsByModality(String modality) throws ApplicationException {
		return processReservationHandler.getInstallationsByModality(modality);
	}
	
}
