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
@RequestMapping("/api/bibliotecaam")
@RequiredArgsConstructor
public class LibroController {
    private final LibroService libroService;

    @GetMapping
    public ResponseEntity<List<LibroResponseDTO>> obtenerTodos(){
        return ResponseEntity.ok(libroService.obtenerTodos());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<LibroResponseDTO> obtenerPorId(@PathVariable Long id){
        return libroService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
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
