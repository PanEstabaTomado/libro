package dfs1103.maq.bibiliotecaam.controller;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


import dfs1103.maq.bibiliotecaam.assemblers.LibroModelAssembler;
import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/bibliotecaam/libro")
@RequiredArgsConstructor
@Tag(name = "Libros", description = "Stock de Libros fisicos de la bibliotecaAM")
public class LibroController {
    private final LibroService libroService;
    @Autowired
    private LibroModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @ArraySchema(schema = @Schema(implementation = Libro.class))
    @Operation(summary = "Obtener los datos de todos los libros.", description = "Esta opcion retornara los datos de todos los libros en la Base de Datos.")
    public ResponseEntity<CollectionModel<EntityModel<LibroResponseDTO>>> obtenerTodos(){
        List<EntityModel<LibroResponseDTO>> libros = libroService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(libros,
                linkTo(methodOn(LibroController.class).obtenerTodos()).withSelfRel()));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener los datos por el id del Libro", description = "Se retornara el libro que coincida con el id ingresado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro encontrado con exito!"),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El id del libro ingresado no existe!")
    })
    public ResponseEntity<EntityModel<LibroResponseDTO>> obtenerPorId(@PathVariable Long id){
        return libroService.obtenerPorId(id)
                .map(assembler::toModel)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/isbn/{isbn}")
    @ArraySchema(schema = @Schema(implementation = Libro.class))
    @Operation(summary = "Obtener los datos por el ISBN del Libro", description = "Se retornara el libro que coincida con el ISBN ingresado.")
    public ResponseEntity<CollectionModel<EntityModel<LibroResponseDTO>>> obtenerPorISBN(@PathVariable String isbn){
        List<EntityModel<LibroResponseDTO>> libros = libroService.obtenerPorIsbn(isbn).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(libros,
                linkTo(methodOn(LibroController.class).obtenerPorISBN(isbn)).withSelfRel()));
    }

    @GetMapping("/titulo/{titulo}")
    @ArraySchema(schema = @Schema(implementation = Libro.class))
    @Operation(summary = "Obtener los datos por el titulo del Libro", description = "Se retornara el libro que coincida con el titulo ingresado.")
    public ResponseEntity<CollectionModel<EntityModel<LibroResponseDTO>>> obtenerPorTitulo(@PathVariable String titulo){
        List<EntityModel<LibroResponseDTO>> libros = libroService.obtenerPorTitulo(titulo).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(libros,
                linkTo(methodOn(LibroController.class).obtenerPorTitulo(titulo)).withSelfRel()));
    }

    @GetMapping("/autor/{autor}")
    @ArraySchema(schema = @Schema(implementation = Libro.class))
    @Operation(summary = "Obtener los datos por el autor del Libro", description = "Se retornara el libro que coincida con el autor ingresado.")
    public ResponseEntity<CollectionModel<EntityModel<LibroResponseDTO>>> obtenerPorAutor(@PathVariable String autor) {
        List<EntityModel<LibroResponseDTO>> libros = libroService.obtenerPorAutor(autor).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(libros,
                linkTo(methodOn(LibroController.class).obtenerPorAutor(autor)).withSelfRel()));
    }

    @GetMapping("/precio/{precio}")
    @ArraySchema(schema = @Schema(implementation = Libro.class))
    @Operation(summary = "Obtener los datos por los precios de los Libros", description = "Se retornaran los libros que coincidan con el precio ingresado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro encontrado con exito!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Libro.class))),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El autor ingresado no existe! (Revise si lo ha escrito bien <3)")
    })
    public ResponseEntity<CollectionModel<EntityModel<LibroResponseDTO>>> obtenerPorPrecio(@PathVariable Integer precio){
        List<EntityModel<LibroResponseDTO>> libros = libroService.obtenerPorPrecioMenorQue(precio).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(libros,
                linkTo(methodOn(LibroController.class).obtenerPorPrecio(precio)).withSelfRel()));

    }

    @GetMapping("/prestado")
    @ArraySchema(schema = @Schema(implementation = Libro.class))
    @Operation(summary = "Obtener los datos por los Libros que estan prestados", description = "Se retornaran los libros que hayan sido prestados actualmente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro encontrado con exito!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Libro.class))),
            @ApiResponse(responseCode = "404",description = "ERROR: ¡El autor ingresado no existe! (Revise si lo ha escrito bien <3)")
    })
    public ResponseEntity<CollectionModel<EntityModel<LibroResponseDTO>>> obtenerPrestados(){
        List<EntityModel<LibroResponseDTO>> libros = libroService.obtenerPorPrestado().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(CollectionModel.of(libros,
                linkTo(methodOn(LibroController.class).obtenerPrestados()).withSelfRel()));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo libro en la Base de Datos", description = "Se creara y guardaran los datos de un nuevo libro creado en la Base de Datos.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description = "¡Libro actualizado con exito!",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Libro.class))),
            @ApiResponse(responseCode = "400",description = "Puede que no se este comunicando con la tabla Donacion/Faltan parametros.")
    })
    public  ResponseEntity<EntityModel<LibroResponseDTO>> guardar(@Valid @RequestBody LibroRequestDTO doto){
        LibroResponseDTO nuevoLibro = libroService.guardar(doto);
        return ResponseEntity.status(201).body(assembler.toModel(nuevoLibro));
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
