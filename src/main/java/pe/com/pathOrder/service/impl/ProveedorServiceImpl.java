package pe.com.pathOrder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.pathOrder.model.Proveedor;
import pe.com.pathOrder.repository.ProveedorRepository;
import pe.com.pathOrder.service.ProveedorService;

@Service
public class ProveedorServiceImpl implements ProveedorService {
	@Autowired
	private ProveedorRepository proveedorRepository;

	@Override
	@Transactional(readOnly = true)
	public List<Proveedor> findAll() throws Exception {
		return proveedorRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Proveedor> findById(Integer id) throws Exception {
		return proveedorRepository.findById(id);
	}

	@Override
	public Proveedor save(Proveedor t) throws Exception {
		return proveedorRepository.save(t);
	}

	@Override
	public Proveedor update(Proveedor t) throws Exception {
		return proveedorRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		proveedorRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		proveedorRepository.deleteAll();
	}
}
