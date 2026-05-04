package dfs1103.maq.bibiliotecaam.repository;

import dfs1103.maq.bibiliotecaam.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT l from Libro l WHERE l.titulo = :titulo_lib")
    List<Libro> findByTitulo(@Param("titulo_lib") String titulo_lib);

    @Query("SELECT l from Libro l WHERE l.autor = :autor_lib")
    List<Libro> findByAutor(@Param("autor_lib") String autor_lib);

    @Query("SELECT l from Libro l WHERE l.isbn = :isbn_lib")
    List<Libro> findByISBN(@Param("isbn_lib") String isbn_lib);

    @Query("SELECT l FROM Libro l WHERE l.donacion.id_dona = :id_dona")
    List<Libro> findByDonacionId(@Param("id_dona") Long id_dona);

    @Query("SELECT l FROM Libro l WHERE l.precio <= :presupuesto ORDER BY l.precio DESC")
    List<Libro> findLibrosPorPrecio(@Param("presupuesto") Integer presupuesto);

    @Query("SELECT l FROM Libro l WHERE l.fechaPublicacion= :fecha ORDER BY l.fechaPublicacion DESC")
    List<Libro> findLibroPorFecha(@Param("fecha")LocalDate fecha);
}
