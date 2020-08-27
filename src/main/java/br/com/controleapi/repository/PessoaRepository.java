package br.com.controleapi.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import br.com.controleapi.model.Pessoa;


public interface PessoaRepository extends JpaRepository<Pessoa, Long> {
    public Pessoa findBycodigo(Long codigo);
    public Pessoa deleteBycodigo(Long codigo);

}
