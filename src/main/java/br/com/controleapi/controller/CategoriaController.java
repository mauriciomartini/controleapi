package br.com.controleapi.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.controleapi.event.RecursoCriadoEvent;
import br.com.controleapi.model.Categoria;
import br.com.controleapi.repository.CategoriaRepository;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    
    @Autowired
    private CategoriaRepository categoriaRepository;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @GetMapping
    //public ResponseEntity<?> listar(){
      public List<Categoria>  listar(){
        List<Categoria> categorias = categoriaRepository.findAll();
        //return !categorias.isEmpty() ? ResponseEntity.ok(categorias) : ResponseEntity.noContent().build();
        return categorias;
        
    }
    
    @PostMapping
    //@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Categoria> salvar(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
        
        Categoria categoriaSalva = categoriaRepository.save(categoria);
        
        publisher.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
        
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
    }
    
    @GetMapping("/{codigo}")
    public ResponseEntity<?> buscarPeloCodigo(@PathVariable Long codigo) {
        Optional<Categoria> categoria =  categoriaRepository.findById(codigo);
        
        return !categoria.isEmpty() ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
        
    }

}
