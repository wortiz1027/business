package co.com.business.repository.daos.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.business.repository.daos.ClienteDao;
import co.com.business.repository.generics.GenericDaoImpl;
import co.com.business.utils.anotaciones.InfoLogger;
import co.com.business.repository.entities.Clientes;

@Repository("ClientesDao")
@Transactional
public class ClienteDaoImpl extends GenericDaoImpl<Clientes, BigDecimal> implements ClienteDao {

	public ClienteDaoImpl() {
		super(Clientes.class);
	}
	
	@InfoLogger(origen = "getClienteByCedula")
	@Override
	public Clientes getClienteByCedula(BigInteger cedula) {
		try {
			if (cedula != null){
				Query<?> query = getSessionFactory().getCurrentSession().getNamedQuery("Clientes.findByCedula");
				query.setParameter("cedula", cedula);
				
				Clientes Clientes = null;
				
				if (query.getResultList().size() > 0){
					Clientes = (Clientes) query.getResultList().get(0);
				}
				return Clientes;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;		
	}

	@Override
	public boolean isExist(Clientes Clientes) {
		
		try {
			if (Clientes != null){
				Query<?> query = getSessionFactory().getCurrentSession().getNamedQuery("Clientes.findByCedula");
				query.setParameter("cedula", Clientes.getCedula());
				
				int numClientess = query.getResultList().size();
				
				if (numClientess != 0)				
					return true;
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean isExistByCedula(BigInteger cedula) {
		try {
			if (cedula != null){
				Query<?> query = getSessionFactory().getCurrentSession().getNamedQuery("Clientes.findByCedula");
				query.setParameter("cedula", cedula);
				
				int numClientess = query.getResultList().size();
				
				if (numClientess != 0)				
					return true;
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean deleteClienteByCedula(BigInteger cedula) {
		try {
			if (cedula != null){
				Query<?> query = getSessionFactory().getCurrentSession().getNamedQuery("Clientes.findByCedula");
				query.setParameter("cedula", cedula);
				
				if (query.getResultList().size() > 0){
					Clientes Clientes = (Clientes) query.getResultList().get(0);
					
					getSessionFactory().getCurrentSession().delete(Clientes);
					
					return true;
				}
				
			}
		} catch (Exception e) {
				e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public List<Clientes> getClientes(Integer offset, Integer limit) {
		try{
			Query<?> query = getSessionFactory().getCurrentSession().getNamedQuery("Clientes.findAll");
			
			query.setFirstResult(offset);
			query.setMaxResults(limit);
			
			if (query.getResultList().size() > 0) {
				
				List<Clientes> list = new ArrayList<Clientes>();
				
				for (Object Clientes : query.getResultList()){
					if (Clientes instanceof Clientes){
						list.add((Clientes)Clientes);
					}
				}				
				
				return list;
				
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return null;
	}
	
}
