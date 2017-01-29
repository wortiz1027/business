package co.com.business.service.utils;

import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Saludo extends ResourceSupport {
	
	private final String content;
	
	@JsonCreator
	public Saludo(@JsonProperty("content") String content){
		this.content = content;
	}

	public String getContent() {
		return content;
	}
	
}