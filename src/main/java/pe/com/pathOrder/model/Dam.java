package pe.com.pathOrder.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Dam")
public class Dam {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(min=10,max=100,message="La descripcion del DAM debe tener entre 10 y 100 caracteres")
	@Column(name = "descripcion", length = 100, nullable = false)
	private String descripcion;
	@Column(name ="CIF", precision = 2, nullable = false)
	private float Cif;
	
	@OneToOne(mappedBy = "dam", cascade =  CascadeType.ALL)
	OrdenDespacho ordenDespacho;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}	

	public float getCif() {
		return Cif;
	}

	public void setCif(float cif) {
		Cif = cif;
	}

	public OrdenDespacho getOrdenDespacho() {
		return ordenDespacho;
	}

	public void setOrdenDespacho(OrdenDespacho ordenDespacho) {
		this.ordenDespacho = ordenDespacho;
	}
}
