package br.com.controleapi.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.controleapi.model.Lancamento;
import br.com.controleapi.model.Pessoa;
import br.com.controleapi.repository.LancamentoRepository;
import br.com.controleapi.repository.PessoaRepository;

@Service
public class LancamentoService {

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private LancamentoRepository lancamentoRepository;
    
    @Autowired
    private PessoaService pessoaService;

    
    
 
    private Lancamento buscarLancamentoPeloCodigo(Long codigo) {

        Lancamento lancamentoSalvo = lancamentoRepository.findBycodigo(codigo);
        if (lancamentoSalvo == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return lancamentoSalvo;
    }
    

}