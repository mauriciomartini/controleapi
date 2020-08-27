package br.com.controleapi.repository;



import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controleapi.model.Pessoa;

@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    public Pessoa findBycodigo(Long codigo);
    
   

}
