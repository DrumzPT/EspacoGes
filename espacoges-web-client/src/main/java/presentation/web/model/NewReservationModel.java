package presentation.web.model;

import facade.dto.ModalityDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IReservationServiceRemote;

/**
 * Helper class to assist in the response of nova reserva.
 * This class is the response information expert.
 */
public class NewReservationModel extends Model {

	private String nutente;
	private String modalidade;
	private String atividade;
	private String horaInicio;
	private String horaFim;
	private String nparticipants;
	
	private IReservationServiceRemote addReservationHandler;
		
	public void setAddReservationHandler(IReservationServiceRemote createReservationHandler) {
		this.addReservationHandler = createReservationHandler;
	}
	
	public void setnutente(String nutente) {
		this.nutente = nutente;	
	}	
	
	public String getnutente() {
		return nutente;
	}
	
	public String getAtividade() {
		return atividade;
	}
	
	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraInicio() {
		return horaInicio;
	}
	
	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public String getHoraFim() {
		return horaFim;
	}
	
	public void setnparticipants(String nParticipants) {
		this.nparticipants = nParticipants;
	}

	public String getnparticipants() {
		return nparticipants;
	}
	
	public void clearFields() {
		nutente = atividade =  horaInicio = horaFim = nparticipants = "";
	}
	
	public Iterable<ModalityDTO> getModalities () throws ApplicationException {
		return addReservationHandler.getModalities();
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}


}
