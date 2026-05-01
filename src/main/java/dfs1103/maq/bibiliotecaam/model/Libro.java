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

    @Column(nullable = false, length = 20, unique = true)
    private String isbn;

    @Column(nullable = false, length = 20)
    private String titulo;

    @Column(nullable = false, length = 50)
    private String autor;

    @Column(nullable = false)
    private boolean prestado;

    @Column(nullable = true, precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(nullable = true, precision = 10)
    private Integer id_dona;

}
