package pe.com.pathOrder.restcontroller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import pe.com.pathOrder.model.OrdenDespacho;
import pe.com.pathOrder.model.Proveedor;
import pe.com.pathOrder.service.OrdenDespachoService;
import pe.com.pathOrder.service.ProveedorService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("EndPoints de Proveedor")
@RestController
@RequestMapping("/api/Proveedor")
public class ProveedorRestController {
	
	@Autowired
	private ProveedorService proveedorService;
	
	@Autowired
	private OrdenDespachoService ordenDespachoService;
	
	@ApiOperation(value="Listar Agentes Aduaneros")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Proveedor>>Listar(){
		ResponseEntity<List<Proveedor>>response;
		
		try {
			List<Proveedor> proveedores=proveedorService.findAll();
			response=new ResponseEntity<List<Proveedor>>(proveedores, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de Proveedores: "+e);
		}
		
		return null;
	}
	
	@ApiOperation(value="Permite buscar a un agente aduanero por su ID")
	@GetMapping(path = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Proveedor>GetProveedores(@PathVariable("id") int id){
		
		try {
			Optional<Proveedor>proveedor=proveedorService.findById(id);
			if(proveedor.isPresent()) {
				return new ResponseEntity<Proveedor>(proveedor.get(), HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<Proveedor>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Proveedor>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina un agente aduanero con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Proveedor>proveedor=proveedorService.findById(id);
			if(proveedor.isPresent()) {
				proveedorService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Proveedor> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Proveedor proveedor) {
		try {
			if(id.equals(proveedor.getId())) {
				Optional<Proveedor> provee = proveedorService.findById(id);
				if(provee.isPresent()) {
					Proveedor proveedorUpdate = proveedorService.update(proveedor);
					return new ResponseEntity<Proveedor>(proveedorUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Proveedor>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Proveedor>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Proveedor>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Proveedor proveedor, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Proveedor nuevoproveedor = proveedorService.save(proveedor);
			return new ResponseEntity<Proveedor>(nuevoproveedor, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Proveedor>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Ingresa el proveedor en las ordenes de despacho" )
	@PostMapping(path = "/{id}/OrdenDespacho", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevaOrden(@PathVariable("id") Integer id,
			@Valid @RequestBody OrdenDespacho ordenDespacho, Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Optional<Proveedor> prov = proveedorService.findById(id);
			if(prov.isPresent()) {
				ordenDespacho.setProveedor(prov.get());
				OrdenDespacho nuevo = ordenDespachoService.save(ordenDespacho);
				
				return new ResponseEntity<OrdenDespacho>(nuevo, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<OrdenDespacho>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<OrdenDespacho>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
