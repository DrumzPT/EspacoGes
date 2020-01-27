package facade.handlers;

import javax.ejb.Remote;

import facade.dto.InstallationDTO;
import facade.dto.ModalityDTO;
import facade.dto.ReservationDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IReservationServiceRemote {

	public ReservationDTO addReservation (int numeroUtilizador, String modalidade, String atividade, 
			String inicio, String fim, int numeroParticipantes) 
			throws ApplicationException;
	
	public Iterable<ModalityDTO> getModalities() throws ApplicationException;

	public Iterable<String> getUserTypes();
	
	public void processReservation(int reservationId,String nomeInst) throws ApplicationException;

	Iterable<ReservationDTO> getReservationsByUserType(String type) throws ApplicationException;
	
	Iterable<InstallationDTO> getInstallationsByModality(String modality)  throws ApplicationException;

}
