package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.Mercaderia;

@Repository
public interface MercaderiaRepository extends JpaRepository<Mercaderia, Integer>{

}
