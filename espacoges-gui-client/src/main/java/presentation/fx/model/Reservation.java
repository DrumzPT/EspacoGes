package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Reservation {
	
	private final IntegerProperty id = new SimpleIntegerProperty();
	private final IntegerProperty userid = new SimpleIntegerProperty();
	private final StringProperty description = new SimpleStringProperty();
	private final StringProperty modalidade = new SimpleStringProperty();
	private final StringProperty inicio = new SimpleStringProperty();
	private final StringProperty fim = new SimpleStringProperty();
	private final IntegerProperty numeroParticipantes = new SimpleIntegerProperty();
	
	public Reservation(int id,int numeroUtilizador, String modalidade, String description,  String inicio,String fim, int numeroParticipantes) {
		setId(id);
		setDescription(description);
		setModalidade(modalidade);
		setUserId(numeroUtilizador);
		setInicio(inicio);
		setFim(fim);
		setNumeroParticipantes(numeroParticipantes);
	}
	
	public final IntegerProperty idProperty() {
		return this.id;
	}
	
	public void setId(final int id) {
		this.idProperty().set(id);
	}
	
	public int getId() {
		return id.get();
	}
	
	
	public final IntegerProperty userIdProperty() {
		return this.userid;
	}
	
	public int getUserid() {
		return this.userid.get();
	}
	
	public void setUserId(final int userId) {
		this.userIdProperty().set(userId);
	}
	

	public String getModalidade() {
		return this.modalidade.get();
	}
	
	public final StringProperty modalidadeProperty() {
		return this.modalidade;
	}
	
	public void setModalidade(final String mod) {
		this.modalidadeProperty().set(mod);
	}

	
	
	public String getInicio() {
		return this.inicio.get();
	}
	
	public StringProperty inicioProperty() {
		return this.inicio;
	}
	
	public void setInicio(final String inicio) {
		this.inicioProperty().set(inicio);
	}

	
	
	public String getFim() {
		return this.fim.get();
	}
	
	public StringProperty fimProperty() {
		return this.fim;
	}
	
	public void setFim(final String fim) {
		this.fimProperty().set(fim);
	}

	
	public final IntegerProperty numeroParticipantesProperty() {
		return this.numeroParticipantes;
	}
	
	public void setNumeroParticipantes(final int nparts) {
		this.numeroParticipantesProperty().set(nparts);
	}
	
	public int getNumeroPArticipantes() {
		return numeroParticipantes.get();
	}
	
	public final StringProperty descriptionProperty() {
		return this.description;
	}
	
	public final void setDescription(final String description) {
		this.descriptionProperty().set(description);
	}
	
	public final String getDescription() {
		return this.description.get();
	}
	
    
    @Override
    public String toString(){
    	StringBuilder sb = new StringBuilder();
    	sb.append("Descrição: ");
    	sb.append(getDescription());
		return sb.toString();
    }
    
}
