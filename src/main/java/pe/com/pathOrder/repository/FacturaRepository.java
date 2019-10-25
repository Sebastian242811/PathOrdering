package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Integer>{

}
