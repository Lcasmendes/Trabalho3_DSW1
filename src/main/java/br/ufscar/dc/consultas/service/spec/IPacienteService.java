package br.ufscar.dc.consultas.service.spec;

import java.util.List;

import br.ufscar.dc.consultas.domain.Paciente;

public interface IPacienteService {

	Paciente buscarPorId(String id);

	List<Paciente> buscarTodos();

	void save(Paciente estado);

	void delete(String id);

	Paciente buscarPorEmail(String email);
}