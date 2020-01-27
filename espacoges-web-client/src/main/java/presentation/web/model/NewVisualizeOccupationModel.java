package presentation.web.model;

import java.util.LinkedList;
import java.util.List;

import facade.dto.InstallationDTO;
import facade.dto.LogActivityDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IVisualizeServiceRemote;

/**
 * Helper class to assist in the response of visualize occupations.
 * This class is the response information expert.
 */
public class NewVisualizeOccupationModel extends Model {
	
	private String installation;
	private String dataInicio;
	private String dataFim;
	private List<LogActivityDTO> logs;

	
	private IVisualizeServiceRemote visualizeOccupationHandler;

	public void setVisualizeOccupationHandler(IVisualizeServiceRemote visualizeOccupationHandler) {
		this.visualizeOccupationHandler = visualizeOccupationHandler;
		logs = new LinkedList<LogActivityDTO>();
	}
	
	public String getInstallation() {
		return installation;
	}

	public void setInstallation(String installation) {
		this.installation = installation;
	}
	
	public String getDataInicio() {
		return dataInicio;
	}
	
	public void setDataInicio(String dataInicio) {
		this.dataInicio = dataInicio;
	}
	
	public String getDataFim() {
		return dataFim;
	}
	
	public void setDataFim(String dataFim) {
		this.dataFim = dataFim;
	}

	public void clearFields() {
		dataFim = dataInicio = "";
	}
	
	public Iterable<InstallationDTO> getInstallations () throws ApplicationException {
			return visualizeOccupationHandler.getInstallations();
	}
	
	public List<LogActivityDTO> getLogs(){
		return logs;
	}
	
	public void setLogs(List<LogActivityDTO> logs) {
		this.logs = logs;
	}
	
	public boolean isHasLogs() {
		return logs.size() != 0;
	}


}

