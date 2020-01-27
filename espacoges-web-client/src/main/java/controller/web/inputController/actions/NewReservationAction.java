package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.NewReservationModel;
import facade.handlers.IReservationServiceRemote;


/**
 * Handles the nova reservation event
 */

@Stateless
public class NewReservationAction extends Action {
	
	@EJB private IReservationServiceRemote addReservationHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		NewReservationModel model = new NewReservationModel();
		model.setAddReservationHandler(addReservationHandler);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/addReserva/fazerPedidoReserva.jsp").forward(request, response);
	}

}
