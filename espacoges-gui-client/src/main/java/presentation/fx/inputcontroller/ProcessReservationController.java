package presentation.fx.inputcontroller;

import java.util.function.UnaryOperator;

import javafx.*;


import facade.exceptions.ApplicationException;
import facade.handlers.IReservationServiceRemote;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import presentation.fx.model.Reservation;
import presentation.fx.model.UserType;
import presentation.fx.model.Installation;
import presentation.fx.model.ProcessReservationModel;
import javafx.scene.control.*;



public class ProcessReservationController extends BaseController {

	@FXML
	private TextField nrUtenteTextField;

	@FXML
	private TextField modalidadeTextField;

	@FXML
	private TextField dataInicioTextField;

	@FXML
	private TextField dataFimTextField;

	@FXML
	private TextField nrParticipantesTextField;

	@FXML
	private TextField installationTextField;

	@FXML 
	private TextField horaAberturaSemana;

	@FXML 
	private TextField horaFechoSemana;

	@FXML 
	private TextField horaAberturaFDS;

	@FXML 
	private TextField horaFechoFDS;

	@FXML
	private ComboBox<UserType> typeSelector;

	@FXML
	private ListView<Reservation> listViewReservation;

	@FXML
	private ListView<Installation> listViewInstallation;

	private ProcessReservationModel model;

	private IReservationServiceRemote reservationService;

	public void setReservationService(IReservationServiceRemote reservationService) {
		this.reservationService = reservationService;
	}

	public void setModel(ProcessReservationModel model) {
		this.model = model;

		typeSelector.setItems(this.model.getUserTypes());   
		typeSelector.setValue(model.getUserType());
		model.getUserTypeProperty().addListener((obs, oldType, newType) -> {
			if(newType == null) {
				listViewReservation.setItems(null);
			}
			else {
				try {
					listViewReservation.setItems(model.getReservationList(newType.getName()));
				}catch(Exception e) {
					System.out.println("Get type= " + newType.getName());
					System.out.println("Size =" + model.getReservationList(newType.getName()).get(0).getDescription());
				}
			}
		});

		listViewReservation.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection)-> model.setSelectedReservation(newSelection));
		model.selectedReservationProperty().addListener((obs, oldReservation, newReservation) -> {
			if(oldReservation != null) {
				nrUtenteTextField.textProperty().unbindBidirectional(oldReservation.userIdProperty());
				modalidadeTextField.textProperty().unbindBidirectional(oldReservation.modalidadeProperty());
				dataInicioTextField.textProperty().unbindBidirectional(oldReservation.inicioProperty());
				dataFimTextField.textProperty().unbindBidirectional(oldReservation.fimProperty());
				nrParticipantesTextField.textProperty().unbindBidirectional(oldReservation.numeroParticipantesProperty());
			}
			if(newReservation == null) {
				nrUtenteTextField.setText("");
				modalidadeTextField.setText("");
				dataInicioTextField.setText("");
				dataFimTextField.setText("");
				nrParticipantesTextField.setText("");
				listViewInstallation.setItems(null);
			}else {
				nrUtenteTextField.textProperty().bindBidirectional(newReservation.userIdProperty(), new NumberStringConverter());
				modalidadeTextField.textProperty().bindBidirectional(newReservation.modalidadeProperty());
				dataInicioTextField.textProperty().bindBidirectional(newReservation.inicioProperty());
				dataFimTextField.textProperty().bindBidirectional(newReservation.fimProperty());
				nrParticipantesTextField.textProperty().bindBidirectional(newReservation.numeroParticipantesProperty(), new NumberStringConverter());

				listViewInstallation.setItems(model.getInstallationlist(newReservation.getModalidade()));
			}
		});
		listViewInstallation.getSelectionModel().selectedItemProperty().addListener((obs,oldSelection, newSelection) -> model.setSelectedInstallation(newSelection));
		model.selectedInstallationProperty().addListener((obs,oldInst,newInst) -> {
			if(oldInst != null) {
				horaAberturaSemana.textProperty().unbindBidirectional(oldInst.aberturaSemanaProperty());
				horaFechoSemana.textProperty().unbindBidirectional(oldInst.fechoSemanaProperty());
				horaAberturaFDS.textProperty().unbindBidirectional(oldInst.aberturaFdsProperty());
				horaFechoFDS.textProperty().unbindBidirectional(oldInst.fechoFdsProperty());
			}
			if(newInst == null) {
				horaAberturaSemana.setText("");
				horaFechoSemana.setText("");
				horaAberturaFDS.setText("");
				horaFechoFDS.setText("");
			}
			else {
				horaAberturaSemana.textProperty().bindBidirectional(newInst.aberturaSemanaProperty());
				horaFechoSemana.textProperty().bindBidirectional(newInst.fechoSemanaProperty());
				horaAberturaFDS.textProperty().bindBidirectional(newInst.aberturaFdsProperty());
				horaFechoFDS.textProperty().bindBidirectional(newInst.fechoFdsProperty());
			}
		});


	}

	@FXML
	void processReservationAction(ActionEvent event) {
		try {
			reservationService.processReservation(model.getSelectedReservation().getId(), model.getSelectedInstalation().getName());
			model.clearProperties();
			showInfo(i18nBundle.getString("new.customer.success"));
		} catch (ApplicationException e) {
			showError(i18nBundle.getString("new.process.error") + ": " + e.getMessage());
		}
	}

	@FXML
	void userTypeSelected(ActionEvent event) {
		model.setUserType(typeSelector.getValue());
	}

}
