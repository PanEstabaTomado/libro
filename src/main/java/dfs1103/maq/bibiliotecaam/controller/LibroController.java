package dfs1103.maq.bibiliotecaam.controller;

import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/bibliotecaam/libros")
@RequiredArgsConstructor
public class LibroController {
    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(libroService.obtenerTodos());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<LibroResponseDTO> buscarPorId(@PathVariable Long id){
        return libroService.obtenerPorId(id)
                .map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<List<LibroResponseDTO>> buscarPorTitulo(@RequestParam String titulo){
        return ResponseEntity.ok(libroService.buscarPorTitulo(titulo));
    }

    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<List<LibroResponseDTO>> buscarPorISBN(@RequestParam String isbn){
        return ResponseEntity.ok(libroService.buscarPorISBN(isbn));
    }

    @GetMapping("/autor/{autor}")
    public ResponseEntity<List<LibroResponseDTO>> buscarPorAutor(@RequestParam String autor){
        return ResponseEntity.ok(libroService.buscarPorAutor(autor));
    }

    @GetMapping("/precio/{precio}")
    public ResponseEntity<Optional<List<LibroResponseDTO>>> buscarPorPresupuesto(@RequestParam Integer precio){
        return ResponseEntity.ok(libroService.obtenerPorPrecio(precio));
    }

    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<LibroResponseDTO>> buscarPorFecha(@RequestParam LocalDate fecha){
        return ResponseEntity.ok(libroService.buscarPorFecha(fecha));
    }

    @PostMapping
    public ResponseEntity<LibroResponseDTO> guardar(@Valid @RequestBody LibroRequestDTO doto){
        return ResponseEntity.status(201).body(libroService.guardar(doto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequestDTO doto){
        return libroService.actualizar(id, doto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@Valid @RequestBody Long id){
        if (libroService.obtenerPorId(id).isEmpty()){
            return ResponseEntity.notFound().build();
        } else {
            libroService.eliminar(id);
            return ResponseEntity.noContent().build();
        }
    }

}
