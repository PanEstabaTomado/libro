package dfs1103.maq.bibiliotecaam.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

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

     @NotNull(message = "La fecha de publicacion del Libro no puede estar vacia")
     private LocalDate fechaPublicacion;

     //El id de donacion
     private Long idDona;

}
