package dfs1103.maq.bibiliotecaam.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponseDTO {

    private Long id_libro;
    private String isbn;
    private String titulo;
    private String autor;
    private boolean prestado;
    private Long id_dona;

}
