package co.com.business.repository.daos.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import co.com.business.repository.daos.FacturaDao;
import co.com.business.utils.Util;
import co.com.business.viewobjects.ClienteFacturaVO;
import co.com.business.viewobjects.FacturasVO;

@Repository("facturaDao")
@Transactional(readOnly = true)
public class ClienteFacturaDaoImpl implements FacturaDao {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	@Override
	public ClienteFacturaVO getFacturaCliente(BigInteger cedula,
											  Long numeroFactura) {
		
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT c.cedula, c.nombre, c.apellido, f.numeroFactura, f.fecha ");
		sb.append("FROM Clientes c JOIN c.facturaList f ");
		sb.append("WHERE c.cedula = :ipCedula ");
		sb.append("AND   f.numeroFactura = :ipNumeroFactura");
		
		Query<?> query = getSessionFactory()
				                .getCurrentSession()
                				.createQuery(sb.toString());
		
		List<Object[]> listado = (List<Object[]>) query.setParameter("ipCedula", cedula)
									  .setParameter("ipNumeroFactura", numeroFactura)
				                      .getResultList();
		
		ClienteFacturaVO cliente  = new ClienteFacturaVO();
		List<FacturasVO> facturas = new ArrayList<FacturasVO>();
		
		for (Object[] obj : listado){
			cliente.setCedula((BigInteger)obj[0]);
			cliente.setNombre((String)obj[1]);
			cliente.setApellido((String)obj[2]);
			
			FacturasVO factura = new FacturasVO();
			factura.setNumeroFactura((Long)obj[3]);
			factura.setFecha(Util.formatDate("dd/MM/yyyy hh:mm:ss",(java.util.Date)obj[4]));
			 
			facturas.add(factura);
			
			cliente.setFacturas(facturas);
			
		}
		
		return cliente;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ClienteFacturaVO getFacturasCliente(BigInteger cedula) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT c.cedula, c.nombre, c.apellido, f.numeroFactura, f.fecha ");
		sb.append("FROM Clientes c JOIN c.facturaList f ");
		sb.append("WHERE c.cedula = :ipCedula ");
		
		Query<Object[]> query = getSessionFactory().getCurrentSession()
                                             .createQuery(sb.toString());
		
		List<Object[]> listado = query.setParameter("ipCedula", cedula).getResultList();
		
		ClienteFacturaVO cliente  = new ClienteFacturaVO();
		List<FacturasVO> facturas = new ArrayList<FacturasVO>();
		
		for (Object[] obj : listado){
			cliente.setCedula((BigInteger)obj[0]);
			cliente.setNombre((String)obj[1]);
			cliente.setApellido((String)obj[2]);
			
			FacturasVO factura = new FacturasVO();
			factura.setNumeroFactura((Long)obj[3]);
			factura.setFecha(Util.formatDate("dd/MM/yyyy hh:mm:ss",(java.util.Date)obj[4]));
			 
			facturas.add(factura);
			
			cliente.setFacturas(facturas);
			
		}
		
		return cliente;
	}
	
	@Override
	public ClienteFacturaVO getDetallesFactura(BigInteger cedula) {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT c.cedula, c.nombre, c.apellido, ");
		sb.append("       f.numero_factura, TO_CHAR(f.fecha, 'dd/MM/yyyy hh:mm:ss'), ");
		sb.append("       TO_CHAR(SUM(df.cantidad * df.precio),'999,999,999,999.99') ");
		sb.append("FROM Clientes c INNER JOIN c.facturaList f ON (c.id_cliente = f.id_cliente) ");
		sb.append("                INNER JOIN f.detalleFacturaList df ON (f.numero_factura = df.numero_factura) ");
		sb.append("                INNER JOIN df.idProducto p ON (df.id_producto = p.id_producto)");
		sb.append("WHERE c.cedula = :ipCedula");
		sb.append("GROUP BY c.cedula, c.nombre, c.apellido,f.numero_factura, TO_CHAR(f.fecha, 'dd/MM/yyyy hh:mm:ss');");
		
		return null;
	}

	@Override
	public ClienteFacturaVO getDetalleFactura(BigInteger cedula, Long numeroFactura) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.cedula, c.nombre, c.apellido, ");
		sb.append("       f.numeroFactura, to_char(f.fecha, 'dd/MM/yyyy hh:mm:ss'), ");
		sb.append("       p.codigo, p.nombre, df.cantidad, df.precio, to_char(df.cantidad * df.precio, '999,999,999,999.99')");
		sb.append("FROM Clientes c JOIN c.facturaList f ");
		sb.append("               JOIN f.detalleFacturaList df");
		sb.append("               JOIN df.producto p");
		sb.append("WHERE c.cedula = :ipCedula");
		sb.append("AND   f.numeroFactura = :ipNumeroFactura");
		return null;
	}
	
	@Override
	public ClienteFacturaVO getDetallesFactura2(BigInteger cedula) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT c.cedula, c.nombre, c.apellido, ");
		sb.append("       f.numeroFactura, to_char(f.fecha, 'dd/MM/yyyy hh:mm:ss'), ");
		sb.append("       to_char(SUM(df.cantidad * df.precio), '999,999,999,999.99') ");
		sb.append("FROM Clientes c JOIN c.facturaList f ");
		sb.append("                JOIN f.detalleFacturaList df ");
		sb.append("                JOIN df.producto p ");
		sb.append("WHERE c.cedula = :ipCedula");
		sb.append("GROUP BY c.cedula, c.nombre, c.apellido, ");
		sb.append("         f.numeroFactura, to_char(f.fecha, 'dd/MM/yyyy hh:mm:ss') ");
		sb.append("ORDER BY f.numeroFactura");
		return null;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}