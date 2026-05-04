package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Donacion;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.DonacionRepository;
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

    private final DonacionRepository donacionRepository;

    /*
     * * EL MapToDTO
     * La razon por la que dice "Doto" es por mero capricho, se puede cambiar a futuro
     * si es necesario.
     */
    private LibroResponseDTO mapToDOTO(Libro libro){
        return new LibroResponseDTO(
                libro.getId_libro(),
                libro.getIsbn(),
                libro.getTitulo(),
                libro.getAutor(),
                libro.getPrecio(),
                libro.getDonacion().getNumrun_dona()
        );
    }

    /*
     * * ESTO ES PARA OBTENER UNA LISTA CON TODOS
     */
    public List<LibroResponseDTO> obtenerTodos(){
        return libroRepository.findAll()
                .stream()
                .map(this::mapToDOTO)
                .collect(Collectors.toList());
    }

    /*
     * * ESTO ES PARA OBTENER UN LIBRO POR SU ID
     */
    public Optional<LibroResponseDTO> obtenerPorId(Long id){
        return libroRepository.findById(id)
                .map(this::mapToDOTO);
    }
    /*
     * * ESTO ES PARA OBTENER POR UN INPUT DE PRECIO, MOSTRARA TODOS LOS PRECIOS MENOR AL INPUT
     */
    public Optional<List<LibroResponseDTO>> obtenerPorPrecio(Integer presupuesto){
        return Optional
                .of(libroRepository.findLibrosPorPrecio(presupuesto)
                        .stream()
                        .map(this::mapToDOTO)
                        .collect(Collectors.toList()));
    }

    /*
    * * ESTO ES PARA GUARDAR
     */
    public LibroResponseDTO guardar(LibroRequestDTO doto){
        Donacion donacion = donacionRepository.findById(doto.getId_dona())
                .orElseThrow(() -> new RuntimeException
                        ("Donacion no encontrada - El id no existe en la base de datos:´" + doto.getId_dona()));

        Libro libro = new Libro(
                null,
                doto.getIsbn(),
                doto.getTitulo(),
                doto.getAutor(),
                doto.getPrecio(),
                donacion
        );
        return mapToDOTO(libroRepository.save(libro));
    }

    /*
     * * ESTO ES PARA ACTUALIZAR
     */
    public Optional<LibroResponseDTO> actualizar(Long id, LibroRequestDTO doto){
        return libroRepository.findById(id).map(existente ->
        {
            Donacion donacion = donacionRepository.findById(doto.getId_dona()).orElseThrow(
                    ()->
                    new RuntimeException("Donacion no encontrada - El id no existe en la base de datos: " + doto.getId_dona()));
            existente.setIsbn(doto.getIsbn());
            existente.setTitulo(doto.getTitulo());
            existente.setAutor(doto.getAutor());
            existente.setPrecio(doto.getPrecio());
            existente.setDonacion(donacion);
            return mapToDOTO(libroRepository.save(existente));
        });
    }

    /*
     * * ESTO ES PARA ELIMINAR
     */
    public void eliminar(Long id){
        libroRepository.deleteById(id);
    }


    /*
     * * ESTO ES PARA BUSCAR POR TITULO
     */
    public List<LibroResponseDTO> buscarPorTitulo(String titulo){
        return libroRepository.findByTitulo(titulo)
                .stream()
                .map(this::mapToDOTO)
                .collect(Collectors.toList());
    }

    /*
     * * ESTO ES PARA BUSCAR POR ISBN
     */
    public List<LibroResponseDTO> buscarPorISBN(String isbn){
        return libroRepository.findByISBN(isbn)
                .stream()
                .map(this::mapToDOTO)
                .collect(Collectors.toList());
    }

    /*
     * * ESTO ES PARA BUSCAR POR AUTOR
     */
    public List<LibroResponseDTO> buscarPorAutor(String autor){
        return libroRepository.findByAutor(autor)
                .stream()
                .map(this::mapToDOTO)
                .collect(Collectors.toList());
    }
}
