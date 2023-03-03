package com.example.Ej2.controler;

import com.example.Ej2.error.NoEncontradoException;
import com.example.Ej2.modelo.Tenista;
import com.example.Ej2.repositorio.RaquetaRepositorio;
import com.example.Ej2.repositorio.TenistaRepositorio;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/tenista")
public class TenistaControlador {
    @Autowired
    private TenistaRepositorio tenistaRepositorio;

    @Autowired
    private RaquetaRepositorio raquetaRepositorio;

    @CrossOrigin(origins = {"**"})
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos(){
        return tenistaRepositorio.findAll().isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(tenistaRepositorio.findAll());
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id){
        return tenistaRepositorio.findById(id).map(ResponseEntity::ok).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @PostMapping
    public ResponseEntity<?> crearTenista(@RequestBody Tenista nuevo){

        return ResponseEntity.status(HttpStatus.CREATED).body(tenistaRepositorio.save(nuevo));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
    @PutMapping("/{id}")

    public ResponseEntity<?> actualizarTenista(@PathVariable Long id, @RequestBody Tenista nuevo){

        return tenistaRepositorio.findById(id).map(tenista -> {
            tenista.setNombre(nuevo.getNombre());
            tenista.setRanking(nuevo.getRanking());
            tenista.setFechaNacimiento(nuevo.getFechaNacimiento());
            tenista.setAltura(nuevo.getAltura());
            tenista.setPeso(nuevo.getPeso());
            tenista.setManoDominante(nuevo.getManoDominante());
            tenista.setTipoDeReves(nuevo.getTipoDeReves());
            tenista.setPuntos(nuevo.getPuntos());
            tenista.setPais(nuevo.getPais());
            return ResponseEntity.ok(tenistaRepositorio.save(tenista));
        }).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"http://localhost:8888"})
   @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarTenista(@PathVariable Long id){
        return tenistaRepositorio.findById(id).map(tenista -> {
            tenistaRepositorio.delete(tenista);
            return ResponseEntity.noContent().build();
        }).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/find")
    public ResponseEntity<?> buscarTenistas(@RequestParam("nombre") String nombre) {

        return tenistaRepositorio.findByNombre(nombre).isEmpty() ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(tenistaRepositorio.findByNombre(nombre));
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/find/{id}/raqueta")
    public ResponseEntity<?> buscarRaqueta(@PathVariable Long id) {
        return tenistaRepositorio.findById(id).map(tenista -> {
            return ResponseEntity.ok(raquetaRepositorio.findByTenista(tenista));
        }).orElseThrow(() -> new NoEncontradoException(id));
    }

    @CrossOrigin(origins = {"**"})
    @GetMapping("/ranking/{ranking}")
    public ResponseEntity<?> buscarPorRanking(@PathVariable int ranking) {

        return tenistaRepositorio.findByRanking(ranking).map(ResponseEntity::ok).orElseThrow(() -> new NoEncontradoException(ranking));
    }
}
