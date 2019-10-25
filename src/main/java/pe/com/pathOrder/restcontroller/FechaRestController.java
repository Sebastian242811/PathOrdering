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
import pe.com.pathOrder.model.Fecha;
import pe.com.pathOrder.service.FechaService;
import pe.com.pathOrder.validator.ErrorMessageBuilder;

@Api("EndPoints de Fechas")
@RestController
@RequestMapping("/api/fecha")
public class FechaRestController {

	@Autowired
	private FechaService fechaService;
	
	@ApiOperation(value="Listar Fehas")
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Fecha>>Listar(){
		ResponseEntity<List<Fecha>>response;
		
		try {
			List<Fecha> fechas=fechaService.findAll();
			response=new ResponseEntity<List<Fecha>>(fechas, HttpStatus.OK);
			return response;
		} catch (Exception e) {
			System.err.println("Error en el listado de Agentes Aduaneros: "+e);
		}
		
		return null;
	}
	
	@ApiOperation(value="Permite buscar fechas por su ID")
	@GetMapping(path = "/{id}",produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Fecha>GetFecha(@PathVariable("id") int id){
		
		try {
			Optional<Fecha>fecha=fechaService.findById(id);
			if(fecha.isPresent()) {
				return new ResponseEntity<Fecha>(fecha.get(), HttpStatus.ACCEPTED);
			}
			else {
				return new ResponseEntity<Fecha>(HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<Fecha>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Elimina una fecha con respecto a su ID")
	@DeleteMapping(path="/{id}",produces=MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String>eliminar(@PathVariable("id")int id){
		try {
			Optional<Fecha>agenteAduanero=fechaService.findById(id);
			if(agenteAduanero.isPresent()) {
				fechaService.deleteById(id);
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
	public ResponseEntity<Fecha> actualizar(@PathVariable("id") Integer id, 
			@RequestBody Fecha fecha) {
		try {
			if(id.equals(fecha.getId())) {
				Optional<Fecha> fech = fechaService.findById(id);
				if(fech.isPresent()) {
					Fecha fechaUpdate = fechaService.update(fecha);
					return new ResponseEntity<Fecha>(fechaUpdate, HttpStatus.OK);
				} 
				else {
					return new ResponseEntity<Fecha>(HttpStatus.NOT_FOUND);
				}	
			} else {
				return new ResponseEntity<Fecha>(HttpStatus.BAD_REQUEST);
			}
					
		} catch (Exception e) {
			return new ResponseEntity<Fecha>(HttpStatus.INTERNAL_SERVER_ERROR);
		}	
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> nuevo(@Valid @RequestBody Fecha fecha, 
			Errors errors) {
		if(errors.hasErrors()) {
			return ResponseEntity.badRequest()
					.body( ErrorMessageBuilder.build(errors) );
		}
		try {
			Fecha nuevafecha = fechaService.save(fecha);
			return new ResponseEntity<Fecha>(nuevafecha, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<Fecha>(HttpStatus.BAD_REQUEST);
		}
	}
}
