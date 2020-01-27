package facade.dto;

import java.io.Serializable;

public class ModalityDTO implements Serializable{

	private static final long serialVersionUID = -302985777263576566L;
	private final String designation;
	
	public ModalityDTO(String designation) {
		this.designation = designation;
	}

	public String getDesignation() {
		return designation;
	}
	
	
}
