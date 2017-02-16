package co.com.business.service.exceptions;

import java.math.BigInteger;

public class ClienteNotFoundException extends RuntimeException {
	
	private static final long serialVersionUID = -2859292084648724403L;
	private final BigInteger cedula;
	
	public ClienteNotFoundException(BigInteger cedula){
		this.cedula = cedula;
	}

	public BigInteger getCedula() {
		return cedula;
	}
	
}