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
import pe.com.pathOrder.model.Authority;
import pe.com.pathOrder.service.AuthorityService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api(value="Endpoints de Autoridad")
@RestController
@RequestMapping("/api/Authority")
public class AuthorityRestController {
	
	@Autowired
	private AuthorityService authorityService;
	
	@ApiOperation(value="Listar las autoridades disponibles para un usuario")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Authority>>Listar(){
		ResponseEntity<List<Authority>>response;
		try {
			List<Authority>authorities=authorityService.findAll();
			response=new ResponseEntity<List<Authority>>(authorities,HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de Authorities: "+e);
		}
		return null;
	}
	
	@ApiOperation("Busca AUTHORITY por ID")
	@GetMapping(path="/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Authority>GetAuthority(@PathVariable("id")int id){
		try {
			Optional<Authority>authority=authorityService.findById(id);
			if(authority.isPresent()) {
				return new ResponseEntity<Authority>(authority.get(),HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<Authority>(HttpStatus.NOT_FOUND);
			}
				
		}catch(Exception e) {
			return new ResponseEntity<Authority>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina un Authority con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Authority>authority=authorityService.findById(id);
			if(authority.isPresent()) {
				authorityService.deleteById(id);
				return new ResponseEntity<String>("Eliminado", HttpStatus.ACCEPTED);
			}else {
				return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@SuppressWarnings("unlikely-arg-type")
	@PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Authority> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Authority authority) {
		try {
			if(id.equals(authority.getId())) {
				Optional<Authority> autho = authorityService.findById(id);
				if(autho.isPresent()) {
					Authority authorityUpdate = authorityService.update(authority);
					return new ResponseEntity<Authority>(authorityUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Authority>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Authority>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Authority>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Authority authority, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Authority nuevoauthority = authorityService.save(authority);
			return new ResponseEntity<Authority>(nuevoauthority, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Authority>(HttpStatus.BAD_REQUEST);
		}
	}
}
