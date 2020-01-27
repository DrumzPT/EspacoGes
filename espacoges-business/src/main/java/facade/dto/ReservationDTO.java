package facade.dto;
import java.io.Serializable;
import java.util.Date;

import businessespacoges.Reservation;

public class ReservationDTO implements Serializable {

	private static final long serialVersionUID = 7245791084352519920L;
	private final int numeroUtilizador;
	private final String modalidade;
	private final String atividade;
	private final String inicio;
	private final String fim;
	private final int numeroParticipantes;
	private final int id;
	
	public ReservationDTO(int id, int numeroUtilizador, String modalidade, String atividade, String inicio,String fim, int numeroParticipantes) {
		this.id = id;
		this.numeroUtilizador = numeroUtilizador;
		this.modalidade = modalidade;
		this.atividade = atividade;
		this.inicio = inicio;
		this.fim = fim;
		this.numeroParticipantes = numeroParticipantes;
	}
	
	public ReservationDTO(int id, String atividade) {
		this.id = id;
		this.atividade = atividade;
		this.inicio = "";
		this.fim = "";
		this.numeroParticipantes = 0;
		this.modalidade = "";
		this.numeroUtilizador = 0;
	}


	public int getNumeroUtilizador() {
		return numeroUtilizador;
	}

	public String getModalidade() {
		return modalidade;
	}

	public String getAtividade() {
		return atividade;
	}

	public String getInicio() {
		return inicio;
	}

	public String getFim() {
		return fim;
	}

	public int getNumeroParticipantes() {
		return numeroParticipantes;
	}

	public int getId() {
		return id;
	}

}
	