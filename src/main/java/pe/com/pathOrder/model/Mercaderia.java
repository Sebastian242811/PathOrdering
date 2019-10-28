package pe.com.pathOrder.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
@Entity
@Table(name = "mercaderia")
public class Mercaderia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Size(min=10,max=200,message="La descripcion de la mercaderia debe tener entre 10 y 200 caracteres")
	@Column(name = "descripcion", length = 200, nullable = false)
	private String descripcion;
	@Column(name = "tipo", length = 20, nullable = false)
	private String tipo;
	
	@ManyToMany(mappedBy = "mercaderias")
	private List<Factura> facturas;
	
	public Mercaderia() {
		this.facturas = new ArrayList<>();
	}

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public List<Factura> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<Factura> facturas) {
		this.facturas = facturas;
	}
	
}
