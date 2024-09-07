package br.ufscar.dc.consultas.service.spec;

import br.ufscar.dc.consultas.domain.Consulta;
import java.util.List;
import java.util.Optional;

public interface IConsultaService {

    Optional<Consulta> buscarPorId(Long id);

    List<Consulta> buscarTodos();

    void salvar(Consulta consulta);

    void remover(Long id);

    List<Consulta> buscarPorPacienteCPF(String cpf);
    
    List<Consulta> buscarConsultasPorMedico(String crm);

    List<Consulta> buscarConsultasPorPaciente(String cpf);
}
