package dfs1103.maq.bibiliotecaam.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LibroResponseDTO {
    private Long idLibro;
    private String isbn;
    private String titulo;
    private String autor;
    private boolean prestado;
    private Long precio;
    private String idDona;

}
