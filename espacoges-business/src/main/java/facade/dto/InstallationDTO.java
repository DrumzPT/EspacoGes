package facade.dto;

import java.io.Serializable;

public class InstallationDTO implements Serializable {


	private static final long serialVersionUID = -1301513385782651178L;
	private final String name;
	private final String aberturaSemana;
	private final String fechoSemana;
	private final String aberturaFds;
	private final String fechoFds;
	private final int id;
	
	public InstallationDTO(int id,String name, String aberturaSemana, String fechoSemana, String aberturaFds, String fechoFds) {
		this.id=id;
		this.name = name;
		this.aberturaSemana = aberturaSemana;
		this.fechoSemana = fechoSemana;
		this.fechoFds = fechoFds;
		this.aberturaFds = aberturaFds;
	}
	
	public InstallationDTO(String name) {
		this.name = name;
		this.aberturaSemana = "";
		this.fechoSemana = "";
		this.fechoFds = "";
		this.aberturaFds = "";
		this.id = -1;
	}

	public String getName() {
		return name;
	}

	public String getAberturaSemana() {
		return aberturaSemana;
	}

	public String getFechoSemana() {
		return fechoSemana;
	}

	public String getAberturaFds() {
		return aberturaFds;
	}

	public String getFechoFds() {
		return fechoFds;
	}

	public int getId() {
		return id;
	}
	
}
