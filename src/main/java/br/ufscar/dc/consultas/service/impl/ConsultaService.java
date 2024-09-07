package br.ufscar.dc.consultas.service.impl;

import br.ufscar.dc.consultas.dao.ConsultaDAO;
import br.ufscar.dc.consultas.domain.Consulta;
import br.ufscar.dc.consultas.service.spec.IConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = false)
public class ConsultaService implements IConsultaService {

    @Autowired
    private ConsultaDAO dao;

    @Override
    @Transactional(readOnly = true)
    public Optional<Consulta> buscarPorId(Long id) {
        return dao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Consulta> buscarTodos() {
        return (List<Consulta>) dao.findAll();
    }

    @Override
    public void salvar(Consulta consulta) {
        dao.save(consulta);
    }

    @Override
    public void remover(Long id) {
        dao.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Consulta> buscarPorPacienteCPF(String cpf) {
        return dao.findConsultasByPacienteCPF(cpf);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Consulta> buscarConsultasPorPaciente(String cpf) {
        return dao.findConsultasByPacienteCPF(cpf); 
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<Consulta> buscarConsultasPorMedico(String crm) {
        return dao.findConsultasByMedicoCRM(crm); 
    }
}

