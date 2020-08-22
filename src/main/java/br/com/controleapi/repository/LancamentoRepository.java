package br.com.controleapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controleapi.model.Lancamento;


public interface LancamentoRepository extends JpaRepository<Lancamento , Long> {
    
    public Lancamento findBycodigo(Long codigo);

}
