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
import pe.com.pathOrder.model.Factura;
import pe.com.pathOrder.model.Mercaderia;
import pe.com.pathOrder.service.FacturaService;
import pe.com.pathOrder.service.MercaderiaService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("EndPoints de factura")
@RestController
@RequestMapping("/api/Factura")
public class FacturaRestController {
	
	@Autowired
	private FacturaService facturaService;
	
	@Autowired
	private MercaderiaService mercaderiaService;
	
	@ApiOperation(value="Listar las facturas")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Factura>>Listar(){
		ResponseEntity<List<Factura>>response;
		try {
			List<Factura>factura=facturaService.findAll();
			response=new ResponseEntity<List<Factura>>(factura,HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de Authorities: "+e);
		}
		return null;
	}
	
	@ApiOperation("Buscar factura por ID")
	@GetMapping(path="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Factura>GetFactura(@PathVariable("id")int id){
		try {
			Optional<Factura>factura=facturaService.findById(id);
			if(factura.isPresent()) {
				return new ResponseEntity<Factura>(factura.get(),HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<Factura>(HttpStatus.NOT_FOUND);
			}
				
		}catch(Exception e) {
			return new ResponseEntity<Factura>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina una factura con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Factura>factura=facturaService.findById(id);
			if(factura.isPresent()) {
				facturaService.deleteById(id);
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
	public ResponseEntity<Factura> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Factura factura) {
		try {
			if(id.equals(factura.getId())) {
				Optional<Factura> factu = facturaService.findById(id);
				if(factu.isPresent()) {
					Factura facturaUpdate= facturaService.update(factura);
					return new ResponseEntity<Factura>(facturaUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Factura>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Factura>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Factura>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Factura factura, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Factura nuevafactura = facturaService.save(factura);
			return new ResponseEntity<Factura>(nuevafactura, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Factura>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Ingresar las mercaderias de la factura" )
	@PostMapping(path = "/{id}/Mercaderia", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevaMercaderia(@PathVariable("id") Integer id,
			@Valid @RequestBody Mercaderia mercaderia, Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Optional<Factura> fact = facturaService.findById(id);
			if(fact.isPresent()) {
				mercaderia.setFacturas((List<Factura>) fact.get());
				Mercaderia nuevo = mercaderiaService.save(mercaderia);
				
				return new ResponseEntity<Mercaderia>(nuevo, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Mercaderia>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Mercaderia>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
