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
import pe.com.pathOrder.model.TipoDespacho;
import pe.com.pathOrder.service.OrdenDespachoService;
import pe.com.pathOrder.service.TipoDespachoService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("EndPoints de TipoDespacho")
@RestController
@RequestMapping("/api/TipoDespacho")
public class TipoDespachoRestController {

	@Autowired
	private TipoDespachoService tipodespachoService;
	
	@Autowired
	private OrdenDespachoService ordenDespachoService;
	
	@ApiOperation(value="Listar Agentes Aduaneros")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TipoDespacho>>Listar(){
		ResponseEntity<List<TipoDespacho>>response;
		
		try {
			List<TipoDespacho> tiposdespacho=tipodespachoService.findAll();
			response=new ResponseEntity<List<TipoDespacho>>(tiposdespacho, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de Agentes Aduaneros: "+e);
		}
		
		return null;
	}
	
	@ApiOperation(value="Permite buscar a un agente aduanero por su ID")
	@GetMapping(path = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TipoDespacho>GetTipoDespacho(@PathVariable("id") int id){
		
		try {
			Optional<TipoDespacho>tipodespacho=tipodespachoService.findById(id);
			if(tipodespacho.isPresent()) {
				return new ResponseEntity<TipoDespacho>(tipodespacho.get(), HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<TipoDespacho>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<TipoDespacho>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina un agente aduanero con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<TipoDespacho>tipo=tipodespachoService.findById(id);
			if(tipo.isPresent()) {
				tipodespachoService.deleteById(id);
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
	public ResponseEntity<TipoDespacho> actualizar(@PathVariable("id") Integer id, 
			@RequestBody TipoDespacho tipodespacho) {
		try {
			if(id.equals(tipodespacho.getId())) {
				Optional<TipoDespacho> tipo = tipodespachoService.findById(id);
				if(tipo.isPresent()) {
					TipoDespacho tipoUpdate = tipodespachoService.update(tipodespacho);
					return new ResponseEntity<TipoDespacho>(tipoUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<TipoDespacho>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<TipoDespacho>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<TipoDespacho>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody TipoDespacho tipodespacho, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			TipoDespacho nuevodespacho = tipodespachoService.save(tipodespacho);
			return new ResponseEntity<TipoDespacho>(nuevodespacho, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<TipoDespacho>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Ingresar el tipo de despacho en una orden" )
	@PostMapping(path = "/{id}/OrdeDespacho", 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevaOrden(@PathVariable("id") Integer id,
			@Valid @RequestBody OrdenDespacho ordenDespacho, Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Optional<TipoDespacho> tipo = tipodespachoService.findById(id);
			if(tipo.isPresent()) {
				ordenDespacho.setTipoDespacho(tipo.get());
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
