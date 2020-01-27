package facade.handlers;

import java.util.List;

import javax.ejb.Remote;

import facade.dto.InstallationDTO;
import facade.dto.LogActivityDTO;
import facade.exceptions.ApplicationException;

@Remote
public interface IVisualizeServiceRemote {

	public List<LogActivityDTO> visualizeOccupation(String installationName, String inicioPeriodo, String fimPeriodo)
			throws ApplicationException;
	
	public List<InstallationDTO> getInstallations()
			throws ApplicationException;
}
