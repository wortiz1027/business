package co.com.business.repository.daos;

import java.math.BigDecimal;
import java.util.List;

import co.com.business.repository.entities.Producto;
import co.com.business.repository.generics.GenericDao;

public interface ProductoDao extends GenericDao<Producto, BigDecimal>{
	
	public List<Producto> getProductos();
	
	public List<Producto> getByNombre(String nombreProducto);
	
	public Producto getByCodigo(String codigo);
	
	public boolean isProductExist(Producto producto);
	
	public boolean isProductExist(String codigo);
	
	public boolean deleteByCode(String codigo);
	
}