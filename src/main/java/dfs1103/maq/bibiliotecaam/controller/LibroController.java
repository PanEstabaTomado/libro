package dfs1103.maq.bibiliotecaam.controller;

import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.service.LibroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bibliotecaam/libro")
@RequiredArgsConstructor
public class LibroController {
    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(libroService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerPorId(@PathVariable Long id){
        return libroService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/isbn/{isbn}")
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
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        if (libroService.obtenerPorId(id).isEmpty()){
            Map<String, String> noEncontrado = new HashMap<>();
            noEncontrado.put("ERROR: ", "¡El libro con el id "+ id +" no fue encontrado!");
            return ResponseEntity.status(404).body(noEncontrado);
        }

        libroService.eliminar(id);
        Map<String, String> eliminado = new HashMap<>();
        eliminado.put("¡Exito! ", " ¡El libro fue eliminado con exito!");
        return ResponseEntity.ok(eliminado);
    }
}
