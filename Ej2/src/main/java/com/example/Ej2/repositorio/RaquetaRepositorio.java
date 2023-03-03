package com.example.Ej2.repositorio;

import com.example.Ej2.modelo.Raqueta;
import com.example.Ej2.modelo.Tenista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  RaquetaRepositorio extends JpaRepository<Raqueta, Long> {
    List<Raqueta> findByMarca(String marca);

    List<Raqueta> findByTenista(Tenista tenista);
}
