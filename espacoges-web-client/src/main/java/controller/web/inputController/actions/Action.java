package controller.web.inputController.actions;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import presentation.web.model.Model;

/**
 * An abstract HTTP action request handler. 
 * Think of it as an operation in the SSD diagram.
 * It has an init method, because objects are
 * create from the prototype (vide UseCaseFrontController)
 * and its easier to use a no parameters construct.
 * 
 * It allows subclasses to define how to handle individual 
 * actions.
 * 
 * We need to store the http request context, since
 * actions are not http servlets and do not have access to
 * the request data.
 */
public abstract class Action {

	/**
	 * Strategy method for processing each request
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract void process(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException;
	
	// the following methods might need to be refactored in order to avoid
	// duplicated code
	protected boolean isInt(Model help, String num, String message) {
		try {
			Integer.parseInt(num);
			return true;
		} catch (NumberFormatException e) {
			help.addMessage(message);
			return false;
		}
	}

	protected int intValue(String num) {
		try {
			return Integer.parseInt(num);
		} catch (NumberFormatException e) {
			return 0;
		}
	}


	protected boolean isFilled (Model helper, String value, String message) {
		if (value == null || (value != null && value.equals(""))) {
			helper.addMessage(message);
			return false;
		}
		return true;
	}
	
	protected boolean isPositive (Model helper, int value, String message) {
		if (value <= 0) {
			helper.addMessage(message);
			return false;
		}
		return true;
	}
	
	protected boolean isValidFormat(Model helper,String date,String message) {
		LocalDateTime ldt = null;
	    DateTimeFormatter fomatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

	    try {
	        ldt = LocalDateTime.parse(date, fomatter);
	        String result = ldt.format(fomatter);
	        return result.equals(date);
	    } catch (DateTimeParseException e) {
	        try {
	            LocalDate ld = LocalDate.parse(date, fomatter);
	            String result = ld.format(fomatter);
	            return result.equals(date);
	        } catch (DateTimeParseException exp) {
	            try {
	                LocalTime lt = LocalTime.parse(date, fomatter);
	                String result = lt.format(fomatter);
	                return result.equals(date);
	            } catch (DateTimeParseException e2) {
	               helper.addMessage(message);
	               return false;
	            }
	        }
	    }
	}

}
