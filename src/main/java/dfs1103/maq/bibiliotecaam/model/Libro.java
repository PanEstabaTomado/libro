package dfs1103.maq.bibiliotecaam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "libro")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLibro;
    @Column(nullable = false, length = 8)
    private String isbn;
    @Column(nullable = false, length = 50)
    private String titulo;
    @Column(nullable = false, length = 40)
    private String autor;
    @Column(nullable = false)
    private Boolean prestado;
    @Column(nullable = true)
    private Integer precio;
    @Column(nullable = true)
    private Long idDona;
}
