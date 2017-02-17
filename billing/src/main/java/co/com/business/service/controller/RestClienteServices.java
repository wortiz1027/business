package co.com.business.service.controller;

import java.math.BigInteger;
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

import co.com.business.repository.entities.Clientes;
import co.com.business.service.dao.ClienteServicesDao;
import co.com.business.service.exceptions.ClienteNotFoundException;
import co.com.business.utils.anotaciones.InfoLogger;
import co.com.business.viewobjects.ClienteFacturaVO;
import net.bull.javamelody.MonitoredWithSpring;

@RestController
@RequestMapping(value = "/api/v1.0")
@MonitoredWithSpring
public class RestClienteServices {
	
	@Autowired
	private ClienteServicesDao clienteServicesDao;
	
	//------------------- Get All Clientes --------------------------------------------------------
	@InfoLogger(origen = "obtenerClientes")
	@RequestMapping(value    = "/clientes",
			        method   = RequestMethod.GET,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<List<Clientes>> obtenerClientes() {
		
		List<Clientes> clientes = clienteServicesDao.getAllClientes();
		
		if (clientes.isEmpty()){
			return new ResponseEntity<List<Clientes>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Clientes>>(clientes, HttpStatus.OK);		
	}
	
	//------------------- Get All Clientes Paginacion --------------------------------------------------------
	
	@RequestMapping(value    = "/clientes/paginacion",
			        method   = RequestMethod.GET,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<List<Clientes>> obtenerClientesPaginacion(@RequestParam(value = "offset", required = true) Integer offset, 
														           @RequestParam(value = "limit", required = true)  Integer limit) {
		
		if (offset == null || limit == null){
			return new ResponseEntity<List<Clientes>>(HttpStatus.BAD_REQUEST);
		}
		
		List<Clientes> clientes = clienteServicesDao.getAllClientes(offset, limit);
		
		if (clientes.isEmpty()){
			return new ResponseEntity<List<Clientes>>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<List<Clientes>>(clientes, HttpStatus.OK);		
	}
	
	
	//------------------- Get Cliente by cedula --------------------------------------------------------
	@InfoLogger(origen = "obtenerInfoCliente")
	@RequestMapping(value    = "/clientes/{cedula}",
			        method   = RequestMethod.GET,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Clientes> obtenerInfoCliente(@PathVariable("cedula") BigInteger cedula) {
		
		Clientes cliente = clienteServicesDao.getInfoCliente(cedula);
		
		if (cliente == null){
			throw new ClienteNotFoundException(cedula);
		}
		
		return new ResponseEntity<Clientes>(cliente, HttpStatus.OK);
	}

	//------------------- Create Cliente --------------------------------------------------------
	
	@RequestMapping(value    = "/clientes",
			        method   = RequestMethod.POST,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Void> crearCliente(@RequestBody(required = true) Clientes cliente, UriComponentsBuilder ucBuilder) {
		
		if (clienteServicesDao.isClientExist(cliente)){
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		
		clienteServicesDao.createCliente(cliente);
		
		HttpHeaders header = new HttpHeaders();
		header.setLocation(ucBuilder.path("/clientes/{id}").buildAndExpand(cliente.getIdCliente()).toUri());
		
		return new ResponseEntity<Void>(header, HttpStatus.CREATED);
	}
	
	//------------------- Delete Cliente --------------------------------------------------------
	
	@RequestMapping(value    = "/clientes/{cedula}",
			        method   = RequestMethod.DELETE,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Clientes> eliminarCliente(@PathVariable("cedula") BigInteger cedula) {
		
		if (!clienteServicesDao.isClientExist(cedula)){
			return new ResponseEntity<Clientes>(HttpStatus.NOT_FOUND);
		}
		
		boolean exito = clienteServicesDao.deleteCliente(cedula);
		
		if (!exito){
			return new ResponseEntity<Clientes>(HttpStatus.NOT_MODIFIED);
		}
		
		return new ResponseEntity<Clientes>(HttpStatus.OK);
	}
	
	@RequestMapping(value    = "/clientes/{cedula}",
			        method   = RequestMethod.PUT,
			        headers  = "Accept=application/json",
			        consumes = MediaType.APPLICATION_JSON_VALUE, 
			        produces = "application/json; charset=UTF-8")
	@ResponseBody
	public ResponseEntity<Clientes> actualizarCliente(@PathVariable("cedula") BigInteger cedula,@RequestBody(required = true) Clientes client) {
		
		if (!clienteServicesDao.isClientExist(cedula)){
			return new ResponseEntity<Clientes>(HttpStatus.NOT_FOUND);
		}
		
		clienteServicesDao.updateCliente(client);
		
		return new ResponseEntity<Clientes>(client, HttpStatus.OK);
	}
	
	//------------------- Get Info Facturas Cliente --------------------------------------------------------
	
		@RequestMapping(value    = "/clientes/{cedula}/facturas",
				        method   = RequestMethod.GET,
				        headers  = "Accept=application/json",
				        consumes = MediaType.APPLICATION_JSON_VALUE, 
				        produces = "application/json; charset=UTF-8")
		@ResponseBody
		public ResponseEntity<ClienteFacturaVO> infoFacturasClientes(@PathVariable("cedula") BigInteger cedula) {
		
			if (!clienteServicesDao.isClientExist(cedula)){
				return new ResponseEntity<ClienteFacturaVO>(HttpStatus.NOT_FOUND);
			}
			
			ClienteFacturaVO clienteFacturas = clienteServicesDao.getInfoFacturasCliente(cedula);
			
			if (clienteFacturas.getCedula() == null){
				return new ResponseEntity<ClienteFacturaVO>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<ClienteFacturaVO>(clienteFacturas, HttpStatus.OK);
		}
		
		//------------------- Get Info Factura Cliente --------------------------------------------------------
		
		@RequestMapping(value    = "/clientes/{cedula}/facturas/{numerofactura}",
				        method   = RequestMethod.GET,
				        headers  = "Accept=application/json",
				        consumes = MediaType.APPLICATION_JSON_VALUE, 
				        produces = "application/json; charset=UTF-8")
		@ResponseBody
		public ResponseEntity<ClienteFacturaVO> infoFacturaClientes(@PathVariable("cedula") BigInteger cedula,
				                                                    @PathVariable("numerofactura") Long numeroFactura) {
			
			if (cedula == null || numeroFactura == null){
				return new ResponseEntity<ClienteFacturaVO>(HttpStatus.BAD_REQUEST);
			}
			
			if (!clienteServicesDao.isClientExist(cedula)){
				return new ResponseEntity<ClienteFacturaVO>(HttpStatus.NOT_FOUND);
			}
			
			ClienteFacturaVO clienteFacturas = clienteServicesDao.getInfoFacturaCliente(cedula,numeroFactura);
			
			if (clienteFacturas.getCedula() == null){
				return new ResponseEntity<ClienteFacturaVO>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<ClienteFacturaVO>(clienteFacturas, HttpStatus.OK);
		}
	
}