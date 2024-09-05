package br.ufscar.dc.consultas.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.consultas.dao.MedicoDAO;
import br.ufscar.dc.consultas.domain.Medico;
import br.ufscar.dc.consultas.service.spec.IProfissionalService;

@Service
@Transactional(readOnly = false)
public class ProfissionalService implements IProfissionalService {
	@Autowired
	MedicoDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Medico buscarPorId(String id) {
		return dao.findByCrm(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Medico> buscarTodos() {
		return (List<Medico>) dao.findAll();
	}

	@Override
	public void save(Medico medico) {
		dao.save(medico);
	}
	
	@Override
	public void delete(String id) {
		dao.deleteByCrm(id);
	}

	@Override
	public Medico buscarPorEmail(String email) {
		// TODO Auto-generated method stub
		return dao.findByEmail(email);
	}

	@Override
	public List<Medico> buscarPorEspecialidade(String especialidade) {
		// TODO Auto-generated method stub
		return (List<Medico>) dao.buscarPorEspecialidade(especialidade);
	}
}