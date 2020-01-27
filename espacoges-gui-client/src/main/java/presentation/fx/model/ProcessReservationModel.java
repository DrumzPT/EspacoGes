package presentation.fx.model;


import facade.exceptions.ApplicationException;

import facade.handlers.IReservationServiceRemote;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProcessReservationModel {

	private final StringProperty resdesignation;
	private final StringProperty instname;
	private final ObjectProperty<Reservation> selectedRes;
	private final ObjectProperty<Installation> selectedInst;
	private final ObservableList<Reservation> reservations;
	private final ObservableList<Installation> installations;
	private final ObjectProperty<UserType> userType;
	private final ObservableList<UserType> userTypeList;

	private IReservationServiceRemote rs;
	
	public ProcessReservationModel(IReservationServiceRemote rs) {
		resdesignation = new SimpleStringProperty();
		instname = new SimpleStringProperty();
		this.rs = rs;
		this.userTypeList = FXCollections.observableArrayList();
		this.reservations = FXCollections.observableArrayList();
		this.installations = FXCollections.observableArrayList();
		userTypeList.add(new UserType("IndividualUser"));
		userTypeList.add(new UserType("ClubUser"));		
		this.userType = new SimpleObjectProperty<>(null);
		this.selectedRes = new SimpleObjectProperty<>(null);
		this.selectedInst = new SimpleObjectProperty<>(null);
	}
	
	public String getResdesignation() {
		return resdesignation.get();
	}
	
	public StringProperty resDesignationProperty() {
		return resdesignation;
	}

	public String getInstName() {
		return instname.get();
	}
	
	public StringProperty instNameProperty() {
		return instname;
	}
	
	public ObjectProperty<Reservation> selectedReservationProperty(){
		return selectedRes;
	}
	
	public final Reservation getSelectedReservation() {
		return selectedRes.get();
	}
	
	public final void setSelectedReservation(Reservation r) {
		selectedRes.set(r);
	}
	
	public ObservableList<Reservation> getReservations(){
		return reservations;
	}
	
	public ObjectProperty<Installation> selectedInstallationProperty(){
		return selectedInst;
	}
	
	public final Installation getSelectedInstalation() {
		return selectedInst.get();
	}
	
	public final void setSelectedInstallation(Installation i) {
		selectedInst.set(i);
	}
	
	public ObservableList<Installation> getInstallation(){
		return installations;
	}
	
	public final void setUserType(UserType u) {
		userType.set(u);
	}
	
	public UserType getUserType() {
		return userType.get();
	}
	
	public ObjectProperty<UserType> getUserTypeProperty() {
		return userType;
	}
	
	public ObservableList<UserType> getUserTypes(){
		return userTypeList;
	}
	
	public void clearProperties() {
		instname.set("");
		resdesignation.set("");
		selectedRes.set(null);
		selectedInst.set(null);
		userType.set(null);
	}

	public ObservableList<Reservation> getReservationList(String type) {
		ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
		try {
			rs.getReservationsByUserType(type).forEach(r -> reservationList.add(new Reservation(r.getId(),r.getNumeroUtilizador(),r.getModalidade(),r.getAtividade(),r.getInicio(),r.getFim(), r.getNumeroParticipantes())));
		} catch (ApplicationException e) {
			System.out.println("Nao encontrou nada");
		}
		return reservationList;
	}

	public ObservableList<Installation> getInstallationlist(String modalidade) {
		ObservableList<Installation> installationList = FXCollections.observableArrayList();
		try {
			rs.getInstallationsByModality(modalidade).forEach(i -> installationList.add(new Installation(i.getId(), i.getName(),i.getAberturaSemana(),i.getFechoSemana(),i.getAberturaFds(),i.getFechoFds())));
		} catch (ApplicationException e) {
			System.out.println("Nao encontrou nada");
		}
		return installationList;
	}
}
