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
import pe.com.pathOrder.model.Canal;
import pe.com.pathOrder.model.OrdenDespacho;
import pe.com.pathOrder.service.CanalService;
import pe.com.pathOrder.service.OrdenDespachoService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("Endpoints de Canales")
@RestController
@RequestMapping("/api/Canal")
public class CanalRestController {
	
	@Autowired
	private CanalService canalService;
	
	@Autowired
	private OrdenDespachoService ordenDespachoService;
	
	@ApiOperation(value="Listar Canales")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Canal>>Listar(){
		ResponseEntity<List<Canal>>response;
		
		try {
			List<Canal> canales=canalService.findAll();
			response=new ResponseEntity<List<Canal>>(canales, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de bultos: "+e);
		}
		
		return null;
	}
	
	@ApiOperation(value="Permite buscar a un canales por su ID")
	@GetMapping(path = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Canal>GetCanal(@PathVariable("id") int id){
		
		try {
			Optional<Canal>canal=canalService.findById(id);
			if(canal.isPresent()) {
				return new ResponseEntity<Canal>(canal.get(), HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<Canal>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Canal>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina un canal con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Canal>canal=canalService.findById(id);
			if(canal.isPresent()) {
				canalService.deleteById(id);
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
	public ResponseEntity<Canal> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Canal canal) {
		try {
			if(id.equals(canal.getId())) {
				Optional<Canal> can = canalService.findById(id);
				if(can.isPresent()) {
					Canal canalUpdate = canalService.update(canal);
					return new ResponseEntity<Canal>(canalUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Canal>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Canal>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Canal>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Canal canal, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Canal nuevocanal = canalService.save(canal);
			return new ResponseEntity<Canal>(nuevocanal, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Canal>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Ingreso de las ordenes de despacho de un canal" )
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
			Optional<Canal> canal = canalService.findById(id);
			if(canal.isPresent()) {
				ordenDespacho.setCanal(canal.get());
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
