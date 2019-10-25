package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.Bulto;

@Repository
public interface BultoRepository extends JpaRepository<Bulto, Integer>{

}
