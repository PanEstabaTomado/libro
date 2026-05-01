package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LibroService {
    private final LibroRepository libroRepository;

    private LibroResponseDTO mapToDTO(Libro libro){
        return new LibroResponseDTO(
                libro.getId_libro(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.isPrestado(),
                libro.getId_dona()
        );
    }

    public List<LibroResponseDTO> listarTodos(){
        return libroRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<LibroRequestDTO> listarPorTitulo(String titulo) { return libroRepository.findByTitulo(titulo)}
}
