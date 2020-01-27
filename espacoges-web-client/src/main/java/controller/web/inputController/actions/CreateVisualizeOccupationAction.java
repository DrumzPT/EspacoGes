package controller.web.inputController.actions;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.NewVisualizeOccupationModel;
import facade.dto.LogActivityDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IVisualizeServiceRemote;

/**
 * Handles the visualize occupation event.
 * If the request is valid, it dispatches it to the domain model (the application's business logic)
 * Notice as well the use of an helper class to assist in the jsp response. 
 *
 */
@Stateless
public class CreateVisualizeOccupationAction extends Action {

	@EJB private IVisualizeServiceRemote visualizeOccupationHandler;

	@Override
	public void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		NewVisualizeOccupationModel model = createHelper(request);
		request.setAttribute("model", model);
		model.setVisualizeOccupationHandler(visualizeOccupationHandler);

			try {
				
				List<LogActivityDTO> logs;
				logs = visualizeOccupationHandler.visualizeOccupation(model.getInstallation(), model.getDataInicio(), model.getDataFim());
				System.out.println(" logs.size = " + logs.size());
				model.setLogs(logs);
			} 
            catch (ApplicationException e) {
				model.addMessage("Error Fetching Logs: " + e.getMessage());
			}

		request.getRequestDispatcher("/visualizeOccupation/newVisualizeOccupation.jsp").forward(request, response);
	}



	private NewVisualizeOccupationModel createHelper(HttpServletRequest request) {
		// Create the object model
		NewVisualizeOccupationModel model = new NewVisualizeOccupationModel();
		model.setVisualizeOccupationHandler(visualizeOccupationHandler);
		model.setInstallation(request.getParameter("installation"));
		model.setDataFim(request.getParameter("dataFim"));
		model.setDataInicio(request.getParameter("dataInicio"));

		return model;
	}	
}
