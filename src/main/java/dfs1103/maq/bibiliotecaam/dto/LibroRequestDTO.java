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

    @NotBlank(message = "El ID del libro no puede estar vacio.")
    private Long id_libro;
    @NotBlank(message = "El isbn no puede estar vacio.")
    private String isbn;
    @NotBlank(message = "El titulo no puede estar vacio.")
    private String titulo;
    @NotBlank(message = "El autor del libro no puede estar vacio")
    private String autor;
    @NotNull(message = "El campo prestado debe ser definido.")
    private boolean prestado;

}
