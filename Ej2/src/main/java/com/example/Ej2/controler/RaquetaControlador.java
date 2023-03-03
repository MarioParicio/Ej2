package com.example.Ej2.controler;

import com.example.Ej2.error.NoEncontradoException;
import com.example.Ej2.modelo.Raqueta;
import com.example.Ej2.modelo.Representante;
import com.example.Ej2.modelo.Tenista;
import com.example.Ej2.repositorio.RaquetaRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.List;

@RestController
@RequestMapping("/api/raquetas")
public class RaquetaControlador {
    @Autowired
    private RaquetaRepositorio raquetaRepositorio;

    @CrossOrigin(origins = {"**"})
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos(){
        return raquetaRepositorio.findAll().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(raquetaRepositorio.findAll());
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return raquetaRepositorio.findById(id).map(ResponseEntity::ok).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @PostMapping
    public ResponseEntity<?> crearRaqueta(@RequestBody Raqueta nuevo){
        if (nuevo.getRepresentante() == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.status(HttpStatus.CREATED).body(raquetaRepositorio.save(nuevo));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @PutMapping("/{id}")

    public ResponseEntity<?> actualizarRaqueta(@PathVariable Long id, @RequestBody Raqueta nuevo){
        return raquetaRepositorio.findById(id).map(raqueta -> {
            raqueta.setMarca(nuevo.getMarca());
            raqueta.setPrecio(nuevo.getPrecio());
            raqueta.setRepresentante(nuevo.getRepresentante());
            raqueta.setTenistas(nuevo.getTenistas());
            return ResponseEntity.ok(raquetaRepositorio.save(raqueta));
        }).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarRaqueta(@PathVariable Long id){
        return raquetaRepositorio.findById(id).map(raqueta -> {
            raquetaRepositorio.delete(raqueta);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/find{marca}")
    public ResponseEntity<?> buscarRaquetas(@RequestParam("marca") String marca) {
        return raquetaRepositorio.findByMarca(marca).isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(raquetaRepositorio.findByMarca(marca));
    }
    @CrossOrigin(origins = {"**"})
    @GetMapping("/{id}/representante")
    public ResponseEntity<?> getRepresentanteByRaquetaId(@PathVariable("id") Long id) {

        return raquetaRepositorio.findById(id).map(raqueta -> ResponseEntity.ok(raqueta.getRepresentante())).orElseThrow(() -> new NoEncontradoException(id));
    }
}
