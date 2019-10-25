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

@Api("EndPoints de Mercaderia")
@RestController
@RequestMapping("/api/Mercaderia")
public class MercaderiaRestController {
	
	@Autowired
	private MercaderiaService mercaderiaService;
	
	@Autowired
	private FacturaService facturaService;
	
	@ApiOperation(value="Listar mercaderia")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Mercaderia>>Listar(){
		ResponseEntity<List<Mercaderia>>response;
		
		try {
			List<Mercaderia> mercaderias=mercaderiaService.findAll();
			response=new ResponseEntity<List<Mercaderia>>(mercaderias, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de Agentes Aduaneros: "+e);
		}
		
		return null;
	}
	
	@ApiOperation(value="Buscar a una mercaderia por su ID")
	@GetMapping(path = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Mercaderia>GetMercaderia(@PathVariable("id") int id){
		
		try {
			Optional<Mercaderia>mercaderia=mercaderiaService.findById(id);
			if(mercaderia.isPresent()) {
				return new ResponseEntity<Mercaderia>(mercaderia.get(), HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<Mercaderia>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Mercaderia>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina una Mercaderia con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Mercaderia>mercaderia=mercaderiaService.findById(id);
			if(mercaderia.isPresent()) {
				mercaderiaService.deleteById(id);
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
	public ResponseEntity<Mercaderia> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Mercaderia mercaderia) {
		try {
			if(id.equals(mercaderia.getId())) {
				Optional<Mercaderia> merca = mercaderiaService.findById(id);
				if(merca.isPresent()) {
					Mercaderia mercaderiaUpdate = mercaderiaService.update(mercaderia);
					return new ResponseEntity<Mercaderia>(mercaderiaUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Mercaderia>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Mercaderia>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Mercaderia>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Mercaderia mercaderia, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Mercaderia nuevamercaderia = mercaderiaService.save(mercaderia);
			return new ResponseEntity<Mercaderia>(nuevamercaderia, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Mercaderia>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("unchecked")
	@ApiOperation(value = "Ingresar las facturas donde aparece la mercaderia" )
	@PostMapping(path = "/{id}/facturas", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevoEstudiante(@PathVariable("id") Integer id,
			@Valid @RequestBody Factura factura, Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Optional<Mercaderia> merca = mercaderiaService.findById(id);
			if(merca.isPresent()) {
				factura.setMercaderias((List<Mercaderia>) merca.get());
				Factura nuevo = facturaService.save(factura);
				
				return new ResponseEntity<Factura>(nuevo, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<Factura>(HttpStatus.NOT_FOUND);
			}
			
		} catch (Exception e) {
			return new ResponseEntity<Factura>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
