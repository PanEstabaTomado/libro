package dfs1103.maq.bibiliotecaam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class LibroRequestDTO {

     @NotBlank(message = "El isbn no puede estar vacio.")
    private String isbn;

     @NotBlank(message = "El titulo no puede estar vacio.")
    private String titulo;

     @NotBlank(message = "El autor no puede estar vacio")
    private String autor;

     @PositiveOrZero(message = "El precio (de tenerlo) debe ser positivo, si no tiene precio: Escriba un 0")
    private BigDecimal precio;

     //El id de donacion
     private Long id_dona;

}
