package dfs1103.maq.bibiliotecaam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroRequestDTO {
    @NotBlank(message = "El isbn es obligatorio.")
    private String isbn;
    @NotBlank(message = "El titulo es obligatorio.")
    private String titulo;
    @NotBlank(message = "El autor es obligatorio.")
    private String autor;
    @NotNull(message = "Se debe saber si el libro ha sido prestado o no.")
    private Boolean prestado;
    private Integer precio;
    private Long idDona;
}
