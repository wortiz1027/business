package co.com.business.service.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.com.business.repository.daos.ProductoDao;
import co.com.business.repository.entities.Producto;
import co.com.business.service.dao.ProductoServicesDao;

@Service("productoServices")
public class ProductoServicesDaoImpl implements ProductoServicesDao {
	
	@Autowired
	private ProductoDao productoDao;

	@Override
	public void createProduct(Producto producto) {
		try {
			if (producto != null){
				productoDao.save(producto);
			}
		} catch (Exception e) {
				e.printStackTrace();
		}		
	}

	@Override
	public List<Producto> getAllProducts() {
		try {
			return productoDao.getAllRows();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Producto getInfoProduct(String codigo) {
		try {
			Producto producto = productoDao.getByCodigo(codigo);
			return producto;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteProduct(Producto producto) {
		try {
			if (producto != null){
				productoDao.delete(producto.getIdProducto());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateProduct(Producto producto) {
		try {
			if (producto != null){
				productoDao.update(producto);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public List<Producto> getProductsByName(String nombre) {
		try {
			if (nombre != null){
				System.out.println("1. NOMBRE --> " + nombre);
				List<Producto> productos = (List<Producto>) productoDao.getByNombre(nombre);
				return productos;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean isProductExist(Producto producto) {
		return productoDao.isProductExist(producto);
	}

	@Override
	public boolean isProductExist(String codigo) {
		return productoDao.isProductExist(codigo);
	}

	@Override
	public boolean deleteProductByCode(String codigo) {
		if (codigo != null){
			productoDao.deleteByCode(codigo);
			return true;
		}	
		return false;
	}
		
}