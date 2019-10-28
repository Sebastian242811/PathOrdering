package pe.com.pathOrder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
@Entity
@Table(name="tipoDespacho")
public class TipoDespacho {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(min=10,max=50,message="El nombre de tipo de despacho debe tener entre 10 y 50 caracteres")
	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;
	@OneToMany(mappedBy = "tipoDespacho")
	private List<OrdenDespacho> ordenesDespachos;
	
	public TipoDespacho() {
		this.ordenesDespachos = new ArrayList<>();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<OrdenDespacho> getOrdenesDespacho() {
		return ordenesDespachos;
	}

	public void setOrdenesDespacho(List<OrdenDespacho> ordenesDespacho) {
		this.ordenesDespachos = ordenesDespacho;
	}
	
	
}
