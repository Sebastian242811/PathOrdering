package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.TipoDespacho;

@Repository
public interface TipoDespachoRepository extends JpaRepository<TipoDespacho, Integer>{

}
