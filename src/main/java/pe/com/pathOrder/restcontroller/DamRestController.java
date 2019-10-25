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
import pe.com.pathOrder.model.Dam;
import pe.com.pathOrder.service.DamService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("EndPoints de los Dam")
@RestController
@RequestMapping("/api/Dam")
public class DamRestController {

	@Autowired
	private DamService damService;
	
	@ApiOperation(value="Listar las DAMS")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Dam>>Listar(){
		ResponseEntity<List<Dam>>response;
		try {
			List<Dam>dams=damService.findAll();
			response=new ResponseEntity<List<Dam>>(dams,HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de Authorities: "+e);
		}
		return null;
	}
	
	@ApiOperation("Buscar DAMS por ID")
	@GetMapping(path="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Dam>GetDam(@PathVariable("id")int id){
		try {
			Optional<Dam>dam=damService.findById(id);
			if(dam.isPresent()) {
				return new ResponseEntity<Dam>(dam.get(),HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<Dam>(HttpStatus.NOT_FOUND);
			}
				
		}catch(Exception e) {
			return new ResponseEntity<Dam>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina una DAM con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Dam>dam=damService.findById(id);
			if(dam.isPresent()) {
				damService.deleteById(id);
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
	public ResponseEntity<Dam> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Dam dam) {
		try {
			if(id.equals(dam.getId())) {
				Optional<Dam> da = damService.findById(id);
				if(da.isPresent()) {
					Dam damUpdate = damService.update(dam);
					return new ResponseEntity<Dam>(damUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Dam>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Dam>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Dam>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Dam dam, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Dam nuevodam = damService.save(dam);
			return new ResponseEntity<Dam>(nuevodam, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Dam>(HttpStatus.BAD_REQUEST);
		}
	}
}
