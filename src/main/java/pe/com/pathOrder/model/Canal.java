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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
@Entity
@Table(name ="canal")
public class Canal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@NotNull(message="El nombre del canal no puede ser vacio")
	@Size(min=4,max=20,message="El nombre del canal debe tener entre 4 y 20 caracteres")
	@Column(name = "nombre", length = 50, nullable = false)
	private String nombre;
	@Size(min=10,max=200,message="La descripcion del canal debe tener entre 10 y 200 caracteres")
	@Column(name = "descripcion",length = 200,nullable = true)
	private String descripcion;
	@OneToMany(mappedBy = "canal")
	private List<OrdenDespacho> ordenesDespachos;
	
	public Canal() {
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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<OrdenDespacho> getOrdenesDespachos() {
		return ordenesDespachos;
	}

	public void setOrdenesDespachos(List<OrdenDespacho> ordenesDespachos) {
		this.ordenesDespachos = ordenesDespachos;
	}
	
	
}
