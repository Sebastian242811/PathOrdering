package pe.com.pathOrder.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.pathOrder.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Integer>{
	
}
