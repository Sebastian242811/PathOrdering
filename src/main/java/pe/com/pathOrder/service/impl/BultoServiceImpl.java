package pe.com.pathOrder.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pe.com.pathOrder.model.Bulto;
import pe.com.pathOrder.repository.BultoRepository;
import pe.com.pathOrder.service.BultoService;

@Service
public class BultoServiceImpl implements BultoService {
	@Autowired
	private BultoRepository bultoRepositoy;

	@Override
	@Transactional(readOnly = true)
	public List<Bulto> findAll() throws Exception {
		return bultoRepositoy.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<Bulto> findById(Integer id) throws Exception {
		return bultoRepositoy.findById(id);
	}

	@Override
	public Bulto save(Bulto t) throws Exception {
		return bultoRepositoy.save(t);
	}

	@Override
	public Bulto update(Bulto t) throws Exception {
		return bultoRepositoy.save(t);
	}

	@Override
	public void deleteById(Integer id) throws Exception {
		bultoRepositoy.deleteById(id);
	}

	@Override
	public void deleteAll() throws Exception {
		bultoRepositoy.deleteAll();
	}
}
