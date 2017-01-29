package co.com.business.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import co.com.business.repository.entities.Producto;
import co.com.business.service.dao.ProductoServicesDao;
import net.bull.javamelody.MonitoredWithSpring;

@RestController
@RequestMapping(value = "/api/v1.0")
@MonitoredWithSpring
public class RestProductoServices {
	
	@Autowired
	private ProductoServicesDao productoServicesDao;
	
	//------------------- Create Producto --------------------------------------------------------
	
	@RequestMapping(value    = "/productos",
					method   = RequestMethod.POST,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Void> crearProducto(@RequestBody(required = true) Producto producto, UriComponentsBuilder ucBuilder){
		
		if (productoServicesDao.isProductExist(producto)){
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		productoServicesDao.createProduct(producto);
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(ucBuilder.path("/productos/{id}").buildAndExpand(producto.getIdProducto()).toUri());
		
		return new ResponseEntity<Void>(header, HttpStatus.OK);
	}
	
	//------------------- List Producto --------------------------------------------------------
	
	@RequestMapping(value    = "/productos",
					method   = RequestMethod.GET,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<List<Producto>> listadoProductos(){
		
		List<Producto> productos = productoServicesDao.getAllProducts();
		
		if (productos.size() == 0){
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
		}
		
		
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);	
	}
	
	//------------------- List Producto Paginacion --------------------------------------------------------
	
	@RequestMapping(value    = "/productos/paginacion",
					method   = RequestMethod.GET,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<List<Producto>> listadoProductos(@RequestParam(value = "start", required = true) int start, 
			                                               @RequestParam(value = "size", required = true)  int size){
		
		if (start == 0 || size == 0){
			return new ResponseEntity<List<Producto>>(HttpStatus.BAD_REQUEST);
		}
		
		List<Producto> productos = productoServicesDao.getAllProducts();
		
		if (productos.size() == 0){
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
		}
		
		if (start + size > productos.size()){
			return new ResponseEntity<List<Producto>>(HttpStatus.PAYLOAD_TOO_LARGE);
		}
		
		return new ResponseEntity<List<Producto>>(productos.subList(start, start + size), HttpStatus.OK);	
	}
	
	//------------------- List Producto --------------------------------------------------------
	
	@RequestMapping(value    = "/productos/{nombre}",
					method   = RequestMethod.GET,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<List<Producto>> listadoProductosPorNombre(@PathVariable("nombre") String nombre){
		
		if (nombre.equalsIgnoreCase("") || nombre == null){
			return new ResponseEntity<List<Producto>>(HttpStatus.NO_CONTENT);
		}
		
		List<Producto> productos = productoServicesDao.getProductsByName(nombre);
		
		if (productos.size() == 0){
			return new ResponseEntity<List<Producto>>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<Producto>>(productos, HttpStatus.OK);		
	}
	
	//------------------- Info Producto --------------------------------------------------------
	
	@RequestMapping(value    = "/productos/{codigo}",
					method   = RequestMethod.GET,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Producto> informacionProducto(@PathVariable("codigo") String codigo){
		
		if (codigo.equalsIgnoreCase("") || codigo == null){
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
		}
		
		Producto producto = (Producto) productoServicesDao.getInfoProduct(codigo);
		
		if (producto == null){
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<Producto>(producto, HttpStatus.OK);	
	}
	
	//------------------- Delete Producto --------------------------------------------------------
	
	@RequestMapping(value    = "/productos/{codigo}",
					method   = RequestMethod.DELETE,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Producto> eliminarProducto(@PathVariable("codigo") String codigo){
		
		if (codigo.equalsIgnoreCase("") || codigo == null){
			return new ResponseEntity<Producto>(HttpStatus.NO_CONTENT);
		}
		
		if (!productoServicesDao.isProductExist(codigo)){
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
		
		boolean exito = productoServicesDao.deleteProductByCode(codigo);
		
		if (!exito){
			return new ResponseEntity<Producto>(HttpStatus.NOT_MODIFIED);	
		}
		
		return new ResponseEntity<Producto>(HttpStatus.OK);
	}

	//------------------- Update Producto --------------------------------------------------------
	
	@RequestMapping(value    = "/productos/{codigo}",
					method   = RequestMethod.PUT,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Producto> actualizarProducto(@PathVariable("codigo") String codigo, @RequestBody(required = true) Producto producto){
		
		if (!productoServicesDao.isProductExist(codigo)){
			return new ResponseEntity<Producto>(HttpStatus.NOT_FOUND);
		}
		
		productoServicesDao.updateProduct(producto);
		
		return new ResponseEntity<Producto>(producto, HttpStatus.NOT_FOUND);
	}
	
}