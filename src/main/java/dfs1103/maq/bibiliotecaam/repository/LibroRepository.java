package dfs1103.maq.bibiliotecaam.repository;

import dfs1103.maq.bibiliotecaam.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l FROM Libro l WHERE UPPER(l.isbn) LIKE UPPER(:isbn)")
    List<Libro> findByIsbn(@Param("isbn") String isbn);

    @Query("SELECT l FROM Libro l WHERE UPPER(l.titulo) LIKE UPPER(:titulo)")
    List<Libro> findByTitulo(@Param("titulo") String titulo);

    @Query("SELECT l FROM Libro l WHERE UPPER(l.autor) LIKE UPPER(:autor)")
    List<Libro> findByAutor(@Param("autor") String autor);

    @Query("SELECT l FROM Libro l WHERE l.precio <= :precio ORDER BY l.precio")
    List<Libro> findByPrecio(@Param("precio") Long precio);

    @Query("SELECT l FROM Libro l WHERE l.prestado = true ORDER BY l.isbn")
    List<Libro> findByPrestado();
}
