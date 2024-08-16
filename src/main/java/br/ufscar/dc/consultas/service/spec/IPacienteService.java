package br.ufscar.dc.consultas.service.spec;

import java.util.List;

import br.ufscar.dc.consultas.domain.Paciente;

public interface IPacienteService {

	Paciente buscarPorId(Long id);

	List<Paciente> buscarTodos();

	void salvar(Paciente editora);

	void excluir(Long id);	
}