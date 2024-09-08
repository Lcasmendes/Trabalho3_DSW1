package br.ufscar.dc.consultas.service.spec;

import java.util.List;

import br.ufscar.dc.consultas.domain.Medico;

public interface IProfissionalService {

	Medico buscarPorId(String id);

	List<Medico> buscarTodos();

	void save(Medico estado);

	void delete(String id);

	Medico buscarPorEmail(String email);
	
	List<Medico> buscarPorEspecialidade(String especialidade);
}