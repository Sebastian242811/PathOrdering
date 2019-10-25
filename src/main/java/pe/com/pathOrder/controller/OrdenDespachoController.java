package pe.com.pathOrder.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import pe.com.pathOrder.model.OrdenDespacho;
import pe.com.pathOrder.service.OrdenDespachoService;
import pe.com.pathOrder.service.impl.OrdenDespachoServiceImpl;

@Controller
@RequestMapping("/OrdenDespacho")
@SessionAttributes("OrdenDespacho")
public class OrdenDespachoController extends OrdenDespachoServiceImpl {
	@Autowired
	private OrdenDespachoService ordenDespachoService;
	
	@GetMapping("inicio")
	public String inicio(Model model) {
		String nombre = "Josealdo :v";
		model.addAttribute("nombre2", nombre);
		return "OrdenDespacho/inicio";
	}
	
	@GetMapping("listado")
	public String listar(Model model) {
		try {
			List<OrdenDespacho> ordenesDespachos = ordenDespachoService.findAll();
			model.addAttribute("carreras", ordenesDespachos);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}	
		return "OrdenDespacho/listado";
	}
	
}
