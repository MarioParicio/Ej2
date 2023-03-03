package com.example.Ej2.controler;

import com.example.Ej2.error.NoEncontradoException;
import com.example.Ej2.modelo.Representante;
import com.example.Ej2.repositorio.RepresentanteRepositoria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/representante")
public class RepresentanteControlador {
    @Autowired
    private RepresentanteRepositoria representanteRepositorio;

    @CrossOrigin(origins = {"**"})
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos(){
        return representanteRepositorio.findAll().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(representanteRepositorio.findAll());
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return representanteRepositorio.findById(id).map(ResponseEntity::ok).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @PostMapping
    public ResponseEntity<?> crearRepresentante(@RequestBody Representante nuevo){

        return ResponseEntity.status(HttpStatus.CREATED).body(representanteRepositorio.save(nuevo));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @PutMapping("/{id}")

    public ResponseEntity<?> actualizarRepresentante(@PathVariable Long id, @RequestBody Representante nuevo){

        return representanteRepositorio.findById(id).map(representante -> {
            representante.setNombre(nuevo.getNombre());
            representante.setEmail(nuevo.getEmail());
            return ResponseEntity.ok(representanteRepositorio.save(representante));
        }).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarRepresentante(@PathVariable Long id){
        return representanteRepositorio.findById(id).map(representante -> {
            representanteRepositorio.delete(representante);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/find")
    public ResponseEntity<?> buscarRepresentantes(@RequestParam("nombre") String nombre) {

        List<Representante> lista = representanteRepositorio.findByNombre(nombre);
        return lista.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(lista);


    }
}