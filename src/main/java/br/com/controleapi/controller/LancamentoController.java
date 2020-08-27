package br.com.controleapi.controller;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.controleapi.event.RecursoCriadoEvent;
import br.com.controleapi.exceptionhandler.ControleApiExceptionHandler.Erro;
import br.com.controleapi.model.Lancamento;
import br.com.controleapi.repository.LancamentoRepository;
import br.com.controleapi.service.LancamentoService;
import br.com.controleapi.service.exection.PessoaInexistenteOuInativaException;

@RestController
@RequestMapping("/lancamentos")
public class LancamentoController {
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private LancamentoRepository lancamentoRepository;

    @Autowired
    private LancamentoService lancamentoService;

    @Autowired
    private ApplicationEventPublisher publisher;



    @GetMapping("/{codigo}")
    public ResponseEntity<Lancamento> burscarLancamentoPorCodigo(@PathVariable Long codigo) {

        Lancamento lancamento = lancamentoRepository.findBycodigo(codigo);
        return lancamento != null ? ResponseEntity.ok(lancamento) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{codigo}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarLancamento(@PathVariable Long codigo) {
        lancamentoRepository.deleteBycodigo(codigo);
        
    }
    
    @PostMapping
    public ResponseEntity<Lancamento> salvarLacamento(@Valid @RequestBody Lancamento lancamento, HttpServletResponse response) {

        Lancamento lancamentoSalva = lancamentoService.salvar(lancamento);
        publisher.publishEvent(new RecursoCriadoEvent(this, response, lancamentoSalva.getCodigo()));
        return ResponseEntity.status(HttpStatus.CREATED).body(lancamentoSalva);
    }
  
    
    @ExceptionHandler({PessoaInexistenteOuInativaException.class})
    public ResponseEntity<Object> handlePessoaInexistenteOuInativaException(PessoaInexistenteOuInativaException ex){
        
            String mensagemUsuario = messageSource.getMessage("pessoa.inexistente-ou-inativa", null, LocaleContextHolder.getLocale());
            String mensagemDesenvolvedor = ex.toString();
            List<Erro> erros = Arrays.asList(new Erro(mensagemUsuario, mensagemDesenvolvedor)); 
            return ResponseEntity.badRequest().body(erros);
    }
    
  

  


}