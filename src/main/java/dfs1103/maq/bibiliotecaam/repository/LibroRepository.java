package dfs1103.maq.bibiliotecaam.repository;

import dfs1103.maq.bibiliotecaam.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {
}
