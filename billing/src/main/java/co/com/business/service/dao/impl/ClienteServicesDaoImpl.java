package co.com.business.service.dao.impl;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.business.repository.daos.ClienteDao;
import co.com.business.repository.entities.Clientes;
import co.com.business.service.dao.ClienteServicesDao;

@Service("clienteServices")
public class ClienteServicesDaoImpl implements ClienteServicesDao {
	
	@Autowired
	private ClienteDao clienteDao;
	
	@Override
	public void createCliente(Clientes client) {
		try{
			if (client != null){
				clienteDao.save(client);
			}			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Clientes> getAllClientes() {
		try{
			return clienteDao.getAllRows();
		}catch(Exception e){
			   System.out.println("Error en el servicio Cliente --> " + e.toString());	
		}
		return null;
	}

	@Override
	public Clientes getInfoCliente(BigInteger cedula) {		
		if (cedula != null){
			System.out.println("2. CEDULA --> " + cedula);
			return clienteDao.getClienteByCedula(cedula);
		}
			
		return null;
	}

	@Override
	public boolean deleteCliente(BigInteger cedula) {
		if (cedula != null){
			clienteDao.deleteClienteByCedula(cedula);
			return true;
		}		
		
		return false;
	}

	@Override
	public void updateCliente(Clientes client) {
		if (client != null){
			clienteDao.update(client);
		}
	}

	@Override
	public boolean isClientExist(Clientes client) {		
		return clienteDao.isExist(client);
	}

	@Override
	public boolean isClientExist(BigInteger cedula) {
		return clienteDao.isExistByCedula(cedula);
	}

	@Override
	public void deleteCliente(Clientes client) {
		if (client != null){
			clienteDao.delete(client.getIdCliente());
		}	
	}

	@Override
	public List<Clientes> getAllClientes(Integer offset, Integer limit) {
		
		if (offset != null && limit != null){
			return clienteDao.getClientes(offset, limit);
		}
		
		return null;
	}
	
}