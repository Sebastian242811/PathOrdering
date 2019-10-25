package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.Fecha;

@Repository
public interface FechaRepository extends JpaRepository<Fecha, Integer>{

}
