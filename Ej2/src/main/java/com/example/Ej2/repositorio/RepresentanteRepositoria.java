package com.example.Ej2.repositorio;

import com.example.Ej2.modelo.Raqueta;
import com.example.Ej2.modelo.Representante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  RepresentanteRepositoria  extends JpaRepository<Representante, Long> {
    List<Representante> findByNombre(String nombre);
}
