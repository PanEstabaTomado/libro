package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibroService {
    private final LibroRepository libroRepository;
    private final WebClient webClient;

    private LibroResponseDTO mapToDTO(Libro libro){
        return new LibroResponseDTO(
                libro.getIdLibro(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.isPrestado(),
                libro.getPrecio(),
                libro.getIdDona()
        );
    }

    private void validarDonacion(Long idDona){
        try{
            webClient.get()
                    .uri("/api/bibliotecaam/")
        }
    }
}
