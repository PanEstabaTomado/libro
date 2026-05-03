package dfs1103.maq.bibiliotecaam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponseDTO {
    private Long id_libro;
    private String isbn;
    private String titulo;
    private String autor;
    private BigDecimal precio;

    private Integer numrun_dona;
}
