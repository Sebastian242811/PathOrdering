package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.Canal;

@Repository
public interface CanalRepository extends JpaRepository<Canal, Integer>{

}
