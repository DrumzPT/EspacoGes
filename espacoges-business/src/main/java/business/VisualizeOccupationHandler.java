package business;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import businessespacoges.Installation;
import businessespacoges.LogActivity;
import businessespacoges.catalogs.InstallationCatalog;
import facade.exceptions.ApplicationException;

/**
 * The handler of the visualize occupation use case
 */
@Stateless
public class VisualizeOccupationHandler {

	/**
	 * Entity manager factory for accessing the persistence service
	 */
	@EJB
	private InstallationCatalog installationCatalog;
	/**
	 * Visualize occupation of a installation
	 * 
	 * @param installationName - the name of the installation
	 * @param inicioPeriodo    - the start of the period do visualize
	 * @param fimPeriodo       - the end of the period do visualize
	 * @return a list of LogActivity representing the occupation
	 * @throws ApplicationException
	 */
	public List<LogActivity> visualizeOccupation(String installationName, String inicioPeriodo, String fimPeriodo)
			throws ApplicationException {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm");
		LocalDateTime inicio = LocalDateTime.parse(inicioPeriodo, formatter);
		LocalDateTime fim = LocalDateTime.parse(fimPeriodo, formatter);
		Installation installation = null;
		try {
			installation = installationCatalog.getInstallationByname(installationName);
		} catch (ApplicationException e) {
			throw new ApplicationException("Erro ao visualizar logs para a instalcao: " + installationName);
		}

		List<LogActivity> list = installation.getLogActivity();
		return getLogActivityWithinPeriod(inicio, fim, list);

	}

	/**
	 * Returns a list of all Activities within a period
	 * 
	 * @param inicio - period start
	 * @param fim    - period end
	 * @param list   - the list 2 filter
	 * @return sorted list
	 */
	private List<LogActivity> getLogActivityWithinPeriod(LocalDateTime inicio, LocalDateTime fim,
			List<LogActivity> list) {
		List<LogActivity> logsFiltrados = new ArrayList<>();

		for (LogActivity l : list) {
			if ((isSameday(inicio, l.getInicio()) && inicio.isBefore(l.getInicio()))
					|| (isSameday(fim, l.getFim()) && fim.isAfter(l.getFim()))) {
				logsFiltrados.add(l);
			} else if ((inicio.isBefore(l.getFim()) || inicio.isEqual(l.getFim()))
					&& (l.getInicio().isBefore(fim) || l.getInicio().isEqual(fim)))

						logsFiltrados.add(l);
		}

		return sortedLogs(logsFiltrados);

	}

	/**
	 * Check if the 2 dates are on the same day
	 * 
	 * @param data1
	 * @param data2
	 * @return
	 */
	private boolean isSameday(LocalDateTime data1, LocalDateTime data2) {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		return data1.format(fmt).equals(data2.format(fmt));
	}

	/**
	 * Sorts logActivities
	 * 
	 * @param logsFiltrados - lista de logs a filtrar
	 * @return logsFiltrados
	 */
	private List<LogActivity> sortedLogs(List<LogActivity> logsFiltrados) {
		logsFiltrados.sort((o1, o2) -> o2.compareTo(o1));
		return logsFiltrados;
	}

	public List<String> getInstallationsNames() {
		return installationCatalog.getAllInstallationNames();
	}

}
