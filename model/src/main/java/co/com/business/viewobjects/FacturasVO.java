package co.com.business.viewobjects;

import java.math.BigDecimal;

public class FacturasVO {
	
	private BigDecimal numeroFactura;
	private String fecha;
	
	public FacturasVO() {
		
	}

	public FacturasVO(BigDecimal numeroFactura, String fecha) {
		this.numeroFactura = numeroFactura;
		this.fecha = fecha;
	}

	public BigDecimal getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(BigDecimal numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}