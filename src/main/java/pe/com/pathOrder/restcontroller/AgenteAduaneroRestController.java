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
import pe.com.pathOrder.model.AgenteAduanero;
import pe.com.pathOrder.model.OrdenDespacho;
import pe.com.pathOrder.service.AgenteAduaneroService;
import pe.com.pathOrder.service.OrdenDespachoService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("Endpoints de AgenteAduanero")
@RestController
@RequestMapping("api/agenteaduanero")
public class AgenteAduaneroRestController {
	
	@Autowired(required = true)
	private AgenteAduaneroService agenteAduaneroService;
	
	@Autowired
	private OrdenDespachoService ordenDespachoService;
	
	@ApiOperation(value=" listar Agente Aduanero")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AgenteAduanero>>Listar(){
		ResponseEntity<List<AgenteAduanero>>response;
		
		try {
			List<AgenteAduanero> agentesAduaneros=agenteAduaneroService.findAll();
			response=new ResponseEntity<List<AgenteAduanero>>(agentesAduaneros, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de bultos: "+e);
		}
		
		return null;
	}
	
	@ApiOperation(value="Permite buscar a un Agente Aduanero por su ID")
	@GetMapping(path = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgenteAduanero>GetBulto(@PathVariable("id") int id){
		
		try {
			Optional<AgenteAduanero>agente=agenteAduaneroService.findById(id);
			if(agente.isPresent()) {
				return new ResponseEntity<AgenteAduanero>(agente.get(), HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<AgenteAduanero>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<AgenteAduanero>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina un Agente Aduanero con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<AgenteAduanero>agente=agenteAduaneroService.findById(id);
			if(agente.isPresent()) {
				agenteAduaneroService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<String>(HttpStatus.CONFLICT);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<AgenteAduanero> actualizar(@PathVariable("id") Integer id, 
			@RequestBody AgenteAduanero agenteaduanero) {
		try {
			if(id.equals(agenteaduanero.getId())) {
				Optional<AgenteAduanero> agente = agenteAduaneroService.findById(id);
				if(agente.isPresent()) {
					AgenteAduanero agenteUpdate = agenteAduaneroService.update(agenteaduanero);
					return new ResponseEntity<AgenteAduanero>(agenteUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<AgenteAduanero>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<AgenteAduanero>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<AgenteAduanero>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody AgenteAduanero agenteaduanero, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			AgenteAduanero nuevoagente = agenteAduaneroService.save(agenteaduanero);
			return new ResponseEntity<AgenteAduanero>(nuevoagente, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<AgenteAduanero>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Permite ingresar las ordenes de de despacho de un agente aduanero" )
	@PostMapping(path = "/{id}/OrdenDespacho", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevaOrden(@PathVariable("id") Integer id,
			@Valid @RequestBody OrdenDespacho ordenDespacho, Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Optional<AgenteAduanero> agente = agenteAduaneroService.findById(id);
			if(agente.isPresent()) {
				ordenDespacho.setAgenteAduanero(agente.get());
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
