package br.com.controleapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import br.com.controleapi.model.Lancamento;
import br.com.controleapi.model.Pessoa;
import br.com.controleapi.repository.LancamentoRepository;
import br.com.controleapi.repository.PessoaRepository;
import br.com.controleapi.service.exection.PessoaInexistenteOuInativaException;

@Service
public class LancamentoService {

  

    @Autowired
    private LancamentoRepository lancamentoRepository;
    
    @Autowired
    private PessoaRepository pessoaRepository;
    

    public Lancamento salvar(Lancamento lancamento) {
        Pessoa pessoa = pessoaRepository.findBycodigo(lancamento.getPessoa().getCodigo());
        if ( pessoa == null || pessoa.isInativo()) {
            throw new PessoaInexistenteOuInativaException();
        }
        return lancamentoRepository.save(lancamento);
    }
    
 
    private Lancamento buscarLancamentoPeloCodigo(Long codigo) {

        Lancamento lancamentoSalvo = lancamentoRepository.findBycodigo(codigo);
        if (lancamentoSalvo == null) {
            throw new EmptyResultDataAccessException(1);
        }
        return lancamentoSalvo;
    }
    

}