package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.OrdenDespacho;

@Repository
public interface OrdenDespachoRepository extends JpaRepository<OrdenDespacho, Integer>{

}
