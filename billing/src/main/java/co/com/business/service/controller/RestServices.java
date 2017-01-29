package co.com.business.service.controller;

import java.util.Date;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import co.com.business.service.utils.Saludo;
import net.bull.javamelody.MonitoredWithSpring;

import org.springframework.http.HttpEntity;

@RestController
@RequestMapping(value = "/api/v1.0")
@MonitoredWithSpring
public class RestServices {
	
	private static final String TEMPLATE = "Hola, %s!";
	
	@RequestMapping(value    = "/mensajes/{nombre}", 
			        method   = RequestMethod.GET, 
			        headers  = "Accept=application/json", 
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Saludo> getSaludo(@PathVariable("nombre") String nombre) {
		
		if (nombre.equalsIgnoreCase("") || nombre == null){
			return new ResponseEntity<Saludo>(HttpStatus.NO_CONTENT);
		}
		
		Saludo saludo = new Saludo(String.format(TEMPLATE, nombre));
		
		return new ResponseEntity<Saludo>(saludo, HttpStatus.OK);
	}

	@RequestMapping(value    = "/secure/mensajes/{nombre}", 
			        method   = RequestMethod.GET, 
			        headers  = "Accept=application/json", 
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> getSaludoAdmin(@PathVariable("nombre") String nombre) {
		if (nombre.equalsIgnoreCase("") || nombre == null){
			return new ResponseEntity<String>(HttpStatus.OK);
		}
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("Hola. ");
		sb.append(nombre);
		sb.append(", mucho gusto \n ");
		sb.append(new Date());
		
		return new ResponseEntity<String>(sb.toString(), HttpStatus.OK);
	}
	
	@RequestMapping(value    = "/saludos/{nombre}", 
			        method   = RequestMethod.GET, 
			        headers  = "Accept=application/json", 
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public HttpEntity<Saludo> greeting(@PathVariable("nombre") String nombre) {
		
		if (nombre.equalsIgnoreCase("") || nombre == null){
			return new ResponseEntity<Saludo>(HttpStatus.NO_CONTENT);
		}
		
		Saludo saludo = new Saludo(String.format(TEMPLATE, nombre));
		
		saludo.add(linkTo(methodOn(RestServices.class).greeting(nombre)).withSelfRel());
		
		saludo.add(linkTo(methodOn(RestServices.class).getSaludo(nombre)).withRel("saludo"));
		
		return new ResponseEntity<Saludo>(saludo, HttpStatus.OK);
	}
	
	@RequestMapping(value    = "/saludos/headers/{nombre}", 
			        method   = RequestMethod.GET, 
			        headers  = "Accept=application/json", 
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public HttpEntity<Saludo> greetingHeader(@PathVariable("nombre") String nombre) {
	
		if (nombre.equalsIgnoreCase("") || nombre == null){
			return new ResponseEntity<Saludo>(HttpStatus.NO_CONTENT);
		}
		
		Saludo saludo = new Saludo(String.format(TEMPLATE, nombre));
		
		saludo.add(linkTo(methodOn(RestServices.class).greeting(nombre)).withRel("saludo"));
		
		saludo.add(linkTo(methodOn(RestServices.class).greetingHeader(nombre)).withSelfRel());
		
		saludo.add(linkTo(methodOn(RestServices.class).getSaludo(nombre)).withRel("saludo"));
		
		final HttpHeaders headers = createHeadersWithResourceLocation(nombre);
		
		return new ResponseEntity<Saludo>(saludo, headers, HttpStatus.OK);
	}
	
	private HttpHeaders createHeadersWithResourceLocation(String param) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(linkTo(methodOn(RestServices.class).greeting(param)).toUri());
        headers.setLocation(linkTo(methodOn(RestServices.class).greetingHeader(param)).toUri());
        headers.setLocation(linkTo(methodOn(RestServices.class).getSaludo(param)).toUri());
        return headers;
    }

}