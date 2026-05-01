package dfs1103.maq.bibiliotecaam.repository;

import dfs1103.maq.bibiliotecaam.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findAll();
    List<Libro> findByTitulo(String titulo);
    List<Libro> findByAutor(String autor);
    List<Libro> findByPrestadoTrue();
    List<Libro> findByPrestadoFalse();
    List<Libro> findById_dona(Long idDona);


}
