package presentation.fx;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;


import facade.handlers.IReservationServiceRemote;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import presentation.fx.inputcontroller.ProcessReservationController;
import presentation.fx.model.ProcessReservationModel;

public class Startup extends Application {

	private static IReservationServiceRemote customerService;

	@Override 
	public void start(Stage stage) throws IOException {

		// This line to resolve keys against Bundle.properties

		ResourceBundle i18nBundle = ResourceBundle.getBundle("i18n.Bundle", new Locale("pt", "PT"));

		FXMLLoader createCustomerLoader = new FXMLLoader(getClass().getResource("/fxml/newReservation.fxml"), i18nBundle);
		Parent root = createCustomerLoader.load();
		ProcessReservationController processReservationController = createCustomerLoader.getController();    	

		ProcessReservationModel processReservationModel = new ProcessReservationModel(customerService);
		try {
		processReservationController.setModel(processReservationModel);
		}catch(Exception e) {
			System.out.println("controller == null" + processReservationController == null);
		}
		processReservationController.setReservationService(customerService);
		processReservationController.setI18NBundle(i18nBundle);

		Scene scene = new Scene(root, 450, 275);

		stage.setTitle(i18nBundle.getString("application.title"));
		stage.setScene(scene);
		stage.show();
	}

	public static void startGUI(IReservationServiceRemote processReservationHandler) {
		Startup.customerService = processReservationHandler;
		launch();
	}
}
