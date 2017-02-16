package co.com.business.service.advice;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import co.com.business.service.exceptions.ClienteNotFoundException;
import co.com.business.service.exceptions.ErrorInfo;
import co.com.business.service.utils.Constantes;

@ControllerAdvice(basePackages = {"co.com.business.service.controller"})
public class GlobalExceptionHandler {
	
	@Autowired
	private MessageSource messageSource;
	
	@ExceptionHandler(value = FileNotFoundException.class)
	public void handlerFileNotFound(FileNotFoundException exception, HttpServletResponse response) throws IOException {
		response.sendError(404, exception.getMessage());
	}
	
	@ExceptionHandler(MaxUploadSizeExceededException.class)
    public void handleError2(MaxUploadSizeExceededException exception, HttpServletResponse response) throws IOException {
		response.sendError(400, exception.getMessage());
    }
	
	@ExceptionHandler(value = IOException.class)
	public void handlerIOException(FileNotFoundException fne, HttpServletResponse response) throws IOException {
		response.sendError(500, fne.getMessage());
	}
	
	@ExceptionHandler(ClienteNotFoundException.class)
	@ResponseStatus(value=HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorInfo smartphoneNotFound(HttpServletRequest request, ClienteNotFoundException exception) {
		
		String errorMessage = localizeErrorMessage(Constantes.ERROR_CLIENT_NOT_FOUND_KEY);
		
		errorMessage += exception.getCedula();
		String errorURL = request.getRequestURL().toString();
		
		return new ErrorInfo(errorURL, errorMessage);
	}
	
	public String localizeErrorMessage(String errorCode) {
		Locale locale = LocaleContextHolder.getLocale();
		String errorMessage = messageSource.getMessage(errorCode, null, locale);
		return errorMessage;
	}
	
}