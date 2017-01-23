package co.com.business.repository.daos;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import co.com.business.repository.entities.Clientes;
import co.com.business.repository.generics.GenericDao;

public interface ClienteDao extends GenericDao<Clientes, BigDecimal> {
	
	public Clientes getClienteByCedula(BigInteger cedula);
	
	public boolean isExist(Clientes cliente);
	
	public boolean isExistByCedula(BigInteger cedula);
	
	public boolean deleteClienteByCedula(BigInteger cedula);
	
	public List<Clientes> getClientes(Integer offset, Integer limit);
	
}
