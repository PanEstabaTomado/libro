package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class LibroService {
    private final LibroRepository libroRepository;
    private final WebClient webClient;

    private LibroResponseDTO mapToDTO(Libro libro){
        String prestado;
        if (libro.getPrestado().equals(true)){
            prestado = "Libro Prestado.";
        } else {
            prestado = "Libro no Prestado.";
        }
        return new LibroResponseDTO(
                libro.getIdLibro(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                prestado,
                libro.getPrecio(),
                libro.getIdDona()
        );


    }

    private void validarDonacion(Long idDona){
        if (idDona == null){
            return;
        }
        try{
            webClient.get()
                    .uri("/api/bibliotecaam/donacion/{id}", idDona)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            log.info(">>> Donacion {} validada correctamente (WebClient)", idDona);
        } catch (WebClientResponseException.NotFound e){
                throw new RuntimeException(
                        "La donacion con id "+ idDona + " no existe en la BD Donacion.");
        } catch (Exception e) {
            throw new RuntimeException(
                    "No se puede conectar con donacion: " + e.getMessage());

        }
    }

    public List<LibroResponseDTO> obtenerTodos(){
        return libroRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public Optional<LibroResponseDTO> obtenerPorId(Long id){
        return libroRepository.findById(id)
                .map(this::mapToDTO);
    }

    public LibroResponseDTO guardar(LibroRequestDTO doto){
        validarDonacion(doto.getIdDona());
        Libro libro = new Libro(
                null,
                doto.getIsbn(),
                doto.getTitulo(),
                doto.getAutor(),
                doto.getPrestado(),
                doto.getPrecio(),
                doto.getIdDona()
        );
        return mapToDTO(libroRepository.save(libro));
    }

    public Optional<LibroResponseDTO> actualizar(Long id, LibroRequestDTO doto){
        return libroRepository.findById(id).map(existente -> {
            validarDonacion(doto.getIdDona());
            existente.setIsbn(doto.getIsbn());
            existente.setTitulo(doto.getTitulo());
            existente.setAutor(doto.getAutor());
            existente.setPrestado(doto.getPrestado());
            existente.setPrecio(doto.getPrecio());
            existente.setIdDona(doto.getIdDona());
            return mapToDTO(libroRepository.save(existente));
        });
    }

    public void eliminar(Long id){

        libroRepository.deleteById(id);
    }

    /*
    ----------------------------------- FUNCIONES EXTRAS
     */


    public List<LibroResponseDTO> obtenerPorIsbn(String isbn){
        return libroRepository.findByIsbn(isbn)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LibroResponseDTO> obtenerPorTitulo(String titulo){
        return libroRepository.findByTitulo(titulo)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LibroResponseDTO> obtenerPorAutor(String autor){
        return libroRepository.findByAutor(autor)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LibroResponseDTO> obtenerPorPrecioMenorQue(Long precio){
        return libroRepository.findByPrecio(precio)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<LibroResponseDTO> obtenerPorPrestado(){
        return libroRepository.findByPrestado()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

}
