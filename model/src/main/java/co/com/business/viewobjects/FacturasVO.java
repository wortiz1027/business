package co.com.business.viewobjects;

public class FacturasVO {
	
	private Long numeroFactura;
	private String fecha;
	
	public FacturasVO() {
		
	}

	public FacturasVO(Long numeroFactura, String fecha) {
		this.numeroFactura = numeroFactura;
		this.fecha = fecha;
	}

	public Long getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(Long numeroFactura) {
		this.numeroFactura = numeroFactura;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}
	
}