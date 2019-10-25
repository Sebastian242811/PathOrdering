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
import pe.com.pathOrder.model.Bulto;
import pe.com.pathOrder.service.BultoService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("EndPoints de bultos")
@RestController
@RequestMapping("/api/Bulto")
public class BultoRestController {
	
	@Autowired
	private BultoService bultoService;
	
	@ApiOperation(value="Listar bultos")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Bulto>>Listar(){
		ResponseEntity<List<Bulto>>response;
		
		try {
			List<Bulto> bultos=bultoService.findAll();
			response=new ResponseEntity<List<Bulto>>(bultos, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de bultos: "+e);
		}
		
		return null;
	}
	
	@ApiOperation(value="Permite buscar a un bultos por su ID")
	@GetMapping(path = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Bulto>GetBulto(@PathVariable("id") int id){
		
		try {
			Optional<Bulto>bulto=bultoService.findById(id);
			if(bulto.isPresent()) {
				return new ResponseEntity<Bulto>(bulto.get(), HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<Bulto>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Bulto>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="EndPoint que elimina un bulto con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Bulto>bulto=bultoService.findById(id);
			if(bulto.isPresent()) {
				bultoService.deleteById(id);
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
	public ResponseEntity<Bulto> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Bulto bulto) {
		try {
			if(id.equals(bulto.getId())) {
				Optional<Bulto> bul = bultoService.findById(id);
				if(bul.isPresent()) {
					Bulto bultoUpdate = bultoService.update(bulto);
					return new ResponseEntity<Bulto>(bultoUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Bulto>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Bulto>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Bulto>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Bulto bulto, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Bulto nuevobulto = bultoService.save(bulto);
			return new ResponseEntity<Bulto>(nuevobulto, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Bulto>(HttpStatus.BAD_REQUEST);
		}
	}
}
