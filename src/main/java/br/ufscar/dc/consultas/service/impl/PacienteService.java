package br.ufscar.dc.consultas.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.ufscar.dc.consultas.dao.PacienteDAO;
import br.ufscar.dc.consultas.domain.Paciente;
import br.ufscar.dc.consultas.service.spec.IPacienteService;

@Service
@Transactional(readOnly = false)
public class PacienteService implements IPacienteService {
	@Autowired
	PacienteDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Paciente buscarPorId(String id) {
		return dao.findByCPF(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Paciente> buscarTodos() {
		return dao.findAll();
	}

	@Override
	public void save(Paciente paciente) {
		dao.save(paciente);
	}
	
	@Override
	public void delete(String id) {
		dao.deleteByCpf(id);
	}

	@Override
	public Paciente buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmail(email);
	}
}