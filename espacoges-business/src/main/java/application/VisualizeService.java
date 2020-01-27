package application;

import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import business.VisualizeOccupationHandler;
import businessespacoges.Installation;
import businessespacoges.LogActivity;
import facade.dto.InstallationDTO;
import facade.dto.LogActivityDTO;
import facade.exceptions.ApplicationException;
import facade.handlers.IVisualizeServiceRemote;

@Stateless
public class VisualizeService implements IVisualizeServiceRemote {

	@EJB
	private VisualizeOccupationHandler visualizeOccupationHandler;
	
	public List<LogActivityDTO> visualizeOccupation(String installationName, String inicioPeriodo, String fimPeriodo)
			throws ApplicationException {
		List<LogActivityDTO> result = new LinkedList<>();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		for (LogActivity log : visualizeOccupationHandler.visualizeOccupation(installationName, inicioPeriodo, fimPeriodo))
			result.add(new LogActivityDTO(log.getReservation().getId(), log.getInicio().format(dtf), log.getFim().format(dtf)));
		return result;
	}
	
	public List<InstallationDTO> getInstallations() {
		List<InstallationDTO> result = new LinkedList<>();
		
		for (String inst : visualizeOccupationHandler.getInstallationsNames())
			result.add(new InstallationDTO(inst));
		return result;
	}
	
}
