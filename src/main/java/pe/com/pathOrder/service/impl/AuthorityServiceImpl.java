package pe.com.pathOrder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import pe.com.pathOrder.model.Authority;
import pe.com.pathOrder.repository.AuthorityRepository;
import pe.com.pathOrder.service.AuthorityService;

@Service
public class AuthorityServiceImpl implements AuthorityService{

	private AuthorityRepository authorityRepository;
	
	@Override
	public List<Authority> findAll() throws Exception {
		return authorityRepository.findAll();
	}

	@Override
	public Optional<Authority> findById(Integer id) throws Exception {
		return authorityRepository.findById(id);
	}

	@Override
	public Authority save(Authority t) throws Exception {
		return authorityRepository.save(t);
	}

	@Override
	public Authority update(Authority t) throws Exception {
		return authorityRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		authorityRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		authorityRepository.deleteAll();
	}

}
