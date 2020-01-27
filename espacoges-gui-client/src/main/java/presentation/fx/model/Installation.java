package presentation.fx.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Installation {

	private final StringProperty name = new SimpleStringProperty();
	private final StringProperty aberturaSemana = new SimpleStringProperty();
	private final StringProperty fechoSemana = new SimpleStringProperty();
	private final StringProperty aberturaFds = new SimpleStringProperty();
	private final StringProperty fechoFds = new SimpleStringProperty();
	public final int id;
	public Installation( int id,String name, String aberturaSemana, String fechoSemana, String aberturaFds, String fechoFds) {
		this.id=id;
		setName(name);
		setAberturaSemana(aberturaSemana);
		setFechoSemana(fechoSemana);
		setAberturaFds(aberturaFds);
		setFechoFds(fechoFds);
	}
	
	public final StringProperty nameProperty() {
		return this.name;
	}
	
	public final void setName(final String description) {
		this.nameProperty().set(description);
	}
	
	public final String getName() {
		return this.name.get();
	}
	
	
	public final StringProperty aberturaSemanaProperty() {
		return this.aberturaSemana;
	}
	
	public final void setAberturaSemana(String ab) {
		this.aberturaSemanaProperty().set(ab);
	}
	
	public final String getAberturaSemana() {
		return this.aberturaSemana.get();
	}

	
	public final StringProperty fechoSemanaProperty() {
		return this.fechoSemana;
	}
	
	public final void setFechoSemana(String ab) {
		this.fechoSemanaProperty().set(ab);
	}
	
	public final String getFechoSemana() {
		return this.fechoSemana.get();
	}
	
	
	public final StringProperty aberturaFdsProperty() {
		return this.aberturaFds;
	}
	
	public final void setAberturaFds(String ab) {
		this.aberturaFdsProperty().set(ab);
	}
	
	public final String getAberturaFds() {
		return this.aberturaFds.get();
	}

	
	public final StringProperty fechoFdsProperty() {
		return this.fechoFds;
	}
	
	public final void setFechoFds(String ab) {
		this.fechoFdsProperty().set(ab);
	}
	
	public final String getFechoFds() {
		return this.fechoFds.get();
	}
    
    @Override
    public String toString(){
    	return getName();
    }

	public Integer getId() {
		return this.id;
	}
    
}
