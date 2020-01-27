package controller.web.inputController.actions;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import presentation.web.model.NewVisualizeOccupationModel;
import facade.exceptions.ApplicationException;
import facade.handlers.IVisualizeServiceRemote;


/**
 * Handles the visualize occupation event

 */
@Stateless
public class NewVisualizeOccupationAction extends Action {
	
	@EJB private IVisualizeServiceRemote visualizeOccupationHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		NewVisualizeOccupationModel model = new NewVisualizeOccupationModel();
		model.setVisualizeOccupationHandler(visualizeOccupationHandler);
		request.setAttribute("model", model);
		request.getRequestDispatcher("/visualizeOccupation/newVisualizeOccupation.jsp").forward(request, response);
	}

}
