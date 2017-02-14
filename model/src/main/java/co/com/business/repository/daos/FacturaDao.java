package co.com.business.repository.daos;

import java.math.BigInteger;
import co.com.business.viewobjects.ClienteFacturaVO;

public interface FacturaDao {
	
	public ClienteFacturaVO getFacturasCliente(BigInteger cedula);
	public ClienteFacturaVO getDetallesFactura(BigInteger cedula);
	public ClienteFacturaVO getDetallesFactura2(BigInteger cedula);
	public ClienteFacturaVO getFacturaCliente(BigInteger cedula, Long numeroFactura);	
	public ClienteFacturaVO getDetalleFactura(BigInteger cedula, Long numeroFactura);
	
}