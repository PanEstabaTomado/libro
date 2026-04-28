package dfs1103.maq.bibiliotecaam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_libro;

    @Column(nullable = false, length = 10)
    private String isbn;

    @Column(nullable = false, length = 30)
    private String titulo;

    @Column(nullable = false, length = 30)
    private String autor;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal precio;

}
