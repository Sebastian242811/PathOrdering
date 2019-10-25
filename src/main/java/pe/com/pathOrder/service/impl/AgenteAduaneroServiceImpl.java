package pe.com.pathOrder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.pathOrder.model.AgenteAduanero;
import pe.com.pathOrder.repository.AgenteAduaneroRepository;
import pe.com.pathOrder.service.AgenteAduaneroService;

@Service
public class AgenteAduaneroServiceImpl implements AgenteAduaneroService{
	@Autowired
	private AgenteAduaneroRepository agenteAduaneroRepository;
	
	@Override
	@Transactional(readOnly = true)
	public List<AgenteAduanero> findAll() throws Exception {
		return agenteAduaneroRepository.findAll();
	}
	@Override
	@Transactional(readOnly = true)
	public Optional<AgenteAduanero> findById(Integer id) throws Exception {
		return agenteAduaneroRepository.findById(id);
	}

	@Override
	public AgenteAduanero save(AgenteAduanero t) throws Exception {
		return agenteAduaneroRepository.save(t);
	}

	@Override
	public AgenteAduanero update(AgenteAduanero t) throws Exception {
		return agenteAduaneroRepository.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		agenteAduaneroRepository.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		agenteAduaneroRepository.deleteAll();
	}

}
