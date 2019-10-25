package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.Dam;

@Repository
public interface DamRepository extends JpaRepository<Dam, Integer> {

}
