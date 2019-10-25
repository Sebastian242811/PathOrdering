package pe.com.pathOrder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.pathOrder.model.OrdenDespacho;
import pe.com.pathOrder.repository.OrdenDespachoRepository;
import pe.com.pathOrder.service.OrdenDespachoService;

@Service
@Primary
public class OrdenDespachoServiceImpl implements OrdenDespachoService{
	@Autowired
	private OrdenDespachoRepository ordenDespachoRepository;

	@Override
	@Transactional(readOnly = true)
	public List<OrdenDespacho> findAll() throws Exception {
		return ordenDespachoRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<OrdenDespacho> findById(Integer id) throws Exception {
		return ordenDespachoRepository.findById(id);
	}

	@Override
	public OrdenDespacho save(OrdenDespacho t) throws Exception {
		return ordenDespachoRepository.save(t);
	}

	@Override
	public OrdenDespacho update(OrdenDespacho t) throws Exception {
		return ordenDespachoRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		ordenDespachoRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		ordenDespachoRepository.deleteAll();
	}
	
	
}
