package br.com.controleapi.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controleapi.model.Lancamento;

@Transactional
public interface LancamentoRepository extends JpaRepository<Lancamento , Long> {
    
    public Lancamento findBycodigo(Long codigo);

    
    public Lancamento deleteBycodigo(Long codigo);
}
