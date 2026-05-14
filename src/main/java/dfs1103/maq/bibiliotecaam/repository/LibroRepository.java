package dfs1103.maq.bibiliotecaam.repository;

import dfs1103.maq.bibiliotecaam.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
    List<Libro> findByIsbn(String isbn);
    List<Libro> findByTitulo(String titulo);
    List<Libro> findByAutor(String autor);
    List<Libro> findByPrecio(Long precio);
    List<Libro> findByPrestado(boolean prestado);
}
