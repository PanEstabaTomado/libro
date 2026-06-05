package dfs1103.maq.bibiliotecaam.controller;

import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bibliotecaam/libro")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Stock de Libros fisicos de la bibliotecaAM")
public class LibroController {
    private final LibroService libroService;

    @GetMapping
    @Operation(summary = "Obtener los datos de todos los libros.", description = "Esta opcion retornara los datos de todos los libros en la Base de Datos.")
    public ResponseEntity<List<LibroResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(libroService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener los datos por el id del Libro", description = "Se retornara el libro que coincida con el id ingresado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro encontrado con exito!"),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El id del libro ingresado no existe!")
    })
    public ResponseEntity<LibroResponseDTO> obtenerPorId(@PathVariable Long id){
        return libroService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/isbn/{isbn}")
    @Operation(summary = "Obtener los datos por el ISBN del Libro", description = "Se retornara el libro que coincida con el ISBN ingresado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro encontrado con exito!"),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El id del libro ingresado no existe!")
    })
    public ResponseEntity<List<LibroResponseDTO>> obtenerPorISBN(@PathVariable String isbn){
        return ResponseEntity.ok(libroService.obtenerPorIsbn(isbn));
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<LibroResponseDTO>> obtenerPorTitulo(@PathVariable String titulo){
        return ResponseEntity.ok(libroService.obtenerPorTitulo(titulo));
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<LibroResponseDTO>> obtenerPorAutor(@PathVariable String autor) {
        return ResponseEntity.ok(libroService.obtenerPorAutor(autor));
    }

    @GetMapping("/precio/{precio}")
    public ResponseEntity<List<LibroResponseDTO>> obtenerPorPrecio(@PathVariable Integer precio){
        return ResponseEntity.ok(libroService.obtenerPorPrecioMenorQue(precio));
    }

    @GetMapping("/prestado")
    public ResponseEntity<List<LibroResponseDTO>> obtenerPrestados(){
        return ResponseEntity.ok(libroService.obtenerPorPrestado());
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo libro en la Base de Datos", description = "Se creara y guardaran los datos de un nuevo libro creado en la Base de Datos.")
    public ResponseEntity<LibroResponseDTO> guardar(@Valid @RequestBody LibroRequestDTO doto){
        return ResponseEntity.status(201).body(libroService.guardar(doto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un libro de la Base de Datos", description = "Se actualizaran los datos de un libro ingresando su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro actualizado con exito!",
                  content = @Content(mediaType = "application/json",
                         schema = @Schema(implementation = Libro.class))),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El id del libro ingresado no existe!"),
            @ApiResponse(responseCode = "400",description = "Puede que no se este comunicando con la tabla Donacion/Faltan parametros.")
    })
    public ResponseEntity<LibroResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequestDTO doto){
        return libroService.actualizar(id, doto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un libro de la Base de Datos", description = "Se eliminara un libro ingresando su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro eliminado con exito!"),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El id del libro ingresado no existe!")
    })
    public ResponseEntity<Map<String,String>> eliminar(@PathVariable Long id){
        if (libroService.obtenerPorId(id).isEmpty()){
            Map<String, String> borrado = new LinkedHashMap<>();
            borrado.put("¡ERROR! ", "¡El libro con id "+id+" no fue encontrado!");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(borrado);
        }else {
            libroService.eliminar(id);
            Map<String, String> borrado = new LinkedHashMap<>();
            borrado.put("¡EXITO! ", "¡El libro fue eliminado con exito!");
            return ResponseEntity.status(HttpStatus.OK).body(borrado);
        }
    }
}
