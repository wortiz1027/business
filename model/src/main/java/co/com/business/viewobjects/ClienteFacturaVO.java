package co.com.business.viewobjects;

import java.math.BigInteger;
import java.util.List;

public class ClienteFacturaVO {
	
	private BigInteger cedula;
	private String nombre;
	private String apellido;
	private List<FacturasVO> facturas;
	
	public ClienteFacturaVO() {
		
	}

	public ClienteFacturaVO(BigInteger cedula, 
			                String nombre, String apellido,
			                List<FacturasVO> facturas) {
		this.cedula = cedula;
		this.nombre = nombre;
		this.apellido = apellido;
		this.facturas = facturas;
	}

	public BigInteger getCedula() {
		return cedula;
	}

	public void setCedula(BigInteger cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public List<FacturasVO> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturasVO> facturas) {
		this.facturas = facturas;
	}
	
}