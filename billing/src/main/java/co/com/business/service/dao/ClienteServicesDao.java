package co.com.business.service.dao;

import java.math.BigInteger;
import java.util.List;

import co.com.business.repository.entities.Clientes;
import co.com.business.viewobjects.ClienteFacturaVO;

public interface ClienteServicesDao {
	
	public void createCliente(Clientes client);
	
	public List<Clientes> getAllClientes();
	
	public List<Clientes> getAllClientes(Integer offset, Integer limit);
	
	public Clientes getInfoCliente(BigInteger cedula);
	
	public void deleteCliente(Clientes client);
	
	public boolean deleteCliente(BigInteger cedula);
	
	public void updateCliente(Clientes client);
	
	public boolean isClientExist(Clientes client);
	
	public boolean isClientExist(BigInteger cedula);
	
	public ClienteFacturaVO getInfoFacturasCliente(BigInteger cedula);
	
	public ClienteFacturaVO getInfoFacturaCliente(BigInteger cedula, Long numeroFactura);
	
}