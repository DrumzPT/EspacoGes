package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.NewReservationModel;
import facade.exceptions.ApplicationException;
import facade.handlers.IReservationServiceRemote;

/**
 * Handles the fazer pedido reserva event.
 * If the request is valid, it dispatches it to the domain model (the application's business logic)
 * Notice as well the use of an helper class to assist in the jsp response. 
 *
 */
@Stateless
public class CreateReservationAction extends Action {

	@EJB private IReservationServiceRemote createReservationHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		NewReservationModel model = createHelper(request);
		request.setAttribute("model", model);


		if (validInput(model)) {

			try {
				createReservationHandler.addReservation(intValue(model.getnutente()), 
						model.getModalidade(), model.getAtividade(), model.getHoraInicio(), model.getHoraFim(), intValue(model.getnparticipants()));
				addPresentationMessage(model);
				model.clearFields();

			}
			catch (ApplicationException e) {
				model.addMessage("Error adding Reservation: " + e.getMessage());
			}

		} else {
			model.addMessage("Error validating reservation data");
		}

		request.getRequestDispatcher("/addReserva/fazerPedidoReserva.jsp").forward(request, response);
	}


	private boolean validInput(NewReservationModel model) {

		boolean result = isFilled(model, model.getAtividade(), "Descrição da atividade deve estar preenchida");

		result &= isFilled(model, model.getHoraFim(), "Hora fim deve estar preenchida");

		result &= isFilled(model, model.getHoraInicio(), "Hora inicio deve estar preenchida");

		result &= isFilled(model, model.getModalidade(), "Modalidade deve estar preenchida");

		result &= isValidFormat(model,model.getHoraInicio(), "Formato invalido na hora inicio, deve ser dd/mm/yyyy HH:mm");
		result &= isValidFormat(model,model.getHoraFim(), "Formato invalido na hora inicio, deve ser dd/mm/yyyy HH:mm");

		result &= isFilled(model, model.getnparticipants(), "Numero de participantes deve estar preenchido")
				&& isInt(model,model.getnparticipants(),"Numero de participantes contem caracteres invalidos")
				&& isPositive(model,intValue(model.getnparticipants()),"Number of participants deve ser superior a 0");

		result &= isFilled(model, model.getnutente(), "Numero de utilizador deve estar preenchido");


		return result;
	}

	private void addPresentationMessage(NewReservationModel model) {
		model.addMessage("Reserva criada com sucesso");
		model.addMessage("Utente nr: " + model.getnutente());
		model.addMessage("Atividade: " +  model.getAtividade());
		model.addMessage("Modalidade: " + model.getModalidade());
		model.addMessage("Data de Inicio: " +  model.getHoraInicio());
		model.addMessage("Date de Fim: " +  model.getHoraFim());
		model.addMessage("Número de Participantes: " +  model.getnparticipants());
		model.addMessage("Obrigado por escolher EspacoGES :D ");
	}


	private NewReservationModel createHelper(HttpServletRequest request) {
		// Create the object model
		NewReservationModel model = new NewReservationModel();
		model.setAddReservationHandler(createReservationHandler);

		model.setAtividade(request.getParameter("atividade"));
		model.setHoraFim(request.getParameter("horaFim"));
		model.setHoraInicio(request.getParameter("horaInicio"));
		model.setnutente(request.getParameter("nutente"));
		model.setnparticipants(request.getParameter("nparticipants"));
		model.setModalidade(request.getParameter("modalidade"));
		return model;
	}	
}
