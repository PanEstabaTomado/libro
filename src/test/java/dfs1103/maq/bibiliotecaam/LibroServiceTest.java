package dfs1103.maq.bibiliotecaam;

import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import dfs1103.maq.bibiliotecaam.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@SpringBootTest(classes = LibroService.class)
@ActiveProfiles("test")
@DisplayName("Tests Unitarios - LibroService")
class LibroServiceTest {

    @Autowired
    private LibroService libroService;

    @MockitoBean
    private LibroRepository libroRepository;

    @MockitoBean
    private WebClient webClient;

    // Mocks para la interfaz fluida de WebClient
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpecMock;
    @SuppressWarnings("rawtypes")
    private WebClient.RequestHeadersSpec requestHeadersSpecMock;
    private WebClient.ResponseSpec responseSpecMock;

    @BeforeEach
    void setUp() {
        requestHeadersUriSpecMock = Mockito.mock(WebClient.RequestHeadersUriSpec.class);
        requestHeadersSpecMock = Mockito.mock(WebClient.RequestHeadersSpec.class);
        responseSpecMock = Mockito.mock(WebClient.ResponseSpec.class);
    }

    @SuppressWarnings("unchecked")
    private void mockWebClientSuccess(WebClient webClientMock, String uri, Object id) {
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri(eq(uri), eq(id))).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(String.class)).thenReturn(Mono.just("OK"));
    }

    @SuppressWarnings("unchecked")
    private void mockWebClientException(WebClient webClientMock, String uri, Object id, Throwable exception) {
        Mockito.when(webClientMock.get()).thenReturn(requestHeadersUriSpecMock);
        Mockito.when(requestHeadersUriSpecMock.uri(eq(uri), eq(id))).thenReturn(requestHeadersSpecMock);
        Mockito.when(requestHeadersSpecMock.retrieve()).thenReturn(responseSpecMock);
        Mockito.when(responseSpecMock.bodyToMono(String.class)).thenReturn(Mono.error(exception));
    }

    @Test
    @DisplayName("GIVEN: Existen libros WHEN: obtenerTodos THEN: Retorna la lista completa mapeada a DTO")
    void shouldReturnAllLibros() {
        List<Libro> mockList = Arrays.asList(
                new Libro(1L, "111-222", "Libro A", "Autor A", true, 10000, 50L),
                new Libro(2L, "333-444", "Libro B", "Autor B", false, 12000, 51L)
        );
        Mockito.when(libroRepository.findAll()).thenReturn(mockList);

        List<LibroResponseDTO> resultado = libroService.obtenerTodos();

        assertEquals(2, resultado.size());
        assertEquals("Libro Prestado.", resultado.get(0).getPrestado());
        assertEquals("Libro no Prestado.", resultado.get(1).getPrestado());
    }

    @Test
    @DisplayName("GIVEN: Existe libro por ID WHEN: obtenerPorId THEN: Retorna el DTO correspondiente")
    void shouldReturnLibroById() {
        Long id = 1L;
        Libro libro = new Libro(id, "111-222", "Libro A", "Autor A", false, 10000, 50L);
        Mockito.when(libroRepository.findById(id)).thenReturn(Optional.of(libro));

        Optional<LibroResponseDTO> resultado = libroService.obtenerPorId(id);

        assertTrue(resultado.isPresent());
        assertEquals(id, resultado.get().getIdLibro());
        assertEquals("Libro no Prestado.", resultado.get().getPrestado());
    }

    @Test
    @DisplayName("GIVEN: Request válido con donación WHEN: guardar THEN: Valida la donación y guarda el libro")
    void shouldSaveLibroSuccessfully() {
        LibroRequestDTO request = new LibroRequestDTO("111-222", "El Aleph", "Borges", false, 15000, 50L);
        Libro libroGuardado = new Libro(100L, "111-222", "El Aleph", "Borges", false, 15000, 50L);

        mockWebClientSuccess(webClient, "/api/bibliotecaam/donacion/{id}", 50L);
        Mockito.when(libroRepository.save(any(Libro.class))).thenReturn(libroGuardado);

        LibroResponseDTO resultado = libroService.guardar(request);

        assertNotNull(resultado);
        assertEquals(100L, resultado.getIdLibro());
        assertEquals("Libro no Prestado.", resultado.getPrestado());
    }


    @Test
    @DisplayName("GIVEN: Donación inexistente WHEN: guardar THEN: Lanza RuntimeException")
    void shouldThrowExceptionWhenDonacionNotFound() {
        LibroRequestDTO request = new LibroRequestDTO("111-222", "El Aleph", "Borges", false, 15000, 99L);

        WebClientResponseException notFoundException = Mockito.mock(WebClientResponseException.NotFound.class);
        mockWebClientException(webClient, "/api/bibliotecaam/donacion/{id}", 99L, notFoundException);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> libroService.guardar(request));
        assertTrue(exception.getMessage().contains("La donacion con id 99 no existe"));
        Mockito.verify(libroRepository, Mockito.never()).save(any(Libro.class));
    }

    @Test
    @DisplayName("GIVEN: ID y Request válido WHEN: actualizar THEN: Modifica el libro existente")
    void shouldUpdateLibroSuccessfully() {
        Long id = 1L;
        Libro existente = new Libro(id, "111-222", "Viejo", "Autor", false, 5000, 40L);
        LibroRequestDTO request = new LibroRequestDTO("111-222", "Nuevo", "Autor", true, 6000, 40L);
        Libro modificado = new Libro(id, "111-222", "Nuevo", "Autor", true, 6000, 40L);

        Mockito.when(libroRepository.findById(id)).thenReturn(Optional.of(existente));
        mockWebClientSuccess(webClient, "/api/bibliotecaam/donacion/{id}", 40L);
        Mockito.when(libroRepository.save(any(Libro.class))).thenReturn(modificado);

        Optional<LibroResponseDTO> resultado = libroService.actualizar(id, request);

        assertTrue(resultado.isPresent());
        assertEquals("Nuevo", resultado.get().getTitulo());
        assertEquals("Libro Prestado.", resultado.get().getPrestado());
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: eliminar THEN: Borra el registro en el repositorio")
    void shouldDeleteLibro() {
        Long id = 1L;
        Mockito.doNothing().when(libroRepository).deleteById(id);

        assertDoesNotThrow(() -> libroService.eliminar(id));
        Mockito.verify(libroRepository, Mockito.times(1)).deleteById(id);
    }

    @Test
    @DisplayName("GIVEN: ISBN válido WHEN: obtenerPorIsbn THEN: Retorna la lista filtrada")
    void shouldReturnLibrosByIsbn() {
        String isbn = "111-222";
        List<Libro> mockList = Arrays.asList(new Libro(1L, isbn, "Libro X", "Autor X", false, 9000, null));
        Mockito.when(libroRepository.findByIsbn(isbn)).thenReturn(mockList);

        List<LibroResponseDTO> resultado = libroService.obtenerPorIsbn(isbn);

        assertEquals(1, resultado.size());
        assertEquals(isbn, resultado.get(0).getIsbn());
    }

    @Test
    @DisplayName("GIVEN: Título existente WHEN: obtenerPorTitulo THEN: Retorna los libros coincidentes")
    void shouldReturnLibrosByTitulo() {
        String titulo = "Don Quijote";
        List<Libro> mockList = Arrays.asList(new Libro(1L, "123", titulo, "Cervantes", false, 15000, null));
        Mockito.when(libroRepository.findByTitulo(titulo)).thenReturn(mockList);

        List<LibroResponseDTO> resultado = libroService.obtenerPorTitulo(titulo);

        assertEquals(1, resultado.size());
        assertEquals(titulo, resultado.get(0).getTitulo());
    }

    @Test
    @DisplayName("GIVEN: Autor existente WHEN: obtenerPorAutor THEN: Retorna sus libros")
    void shouldReturnLibrosByAutor() {
        String autor = "Orwell";
        List<Libro> mockList = Arrays.asList(new Libro(1L, "123", "1984", autor, false, 8000, null));
        Mockito.when(libroRepository.findByAutor(autor)).thenReturn(mockList);

        List<LibroResponseDTO> resultado = libroService.obtenerPorAutor(autor);

        assertEquals(1, resultado.size());
        assertEquals(autor, resultado.get(0).getAutor());
    }

    @Test
    @DisplayName("GIVEN: Precio tope WHEN: obtenerPorPrecioMenorQue THEN: Retorna libros bajo ese precio")
    void shouldReturnLibrosByPrecio() {
        Integer precio = 15000;
        List<Libro> mockList = Arrays.asList(new Libro(1L, "123", "Moby Dick", "Melville", false, 12000, null));
        Mockito.when(libroRepository.findByPrecio(precio)).thenReturn(mockList);

        List<LibroResponseDTO> resultado = libroService.obtenerPorPrecioMenorQue(precio);

        assertEquals(1, resultado.size());
        assertTrue(resultado.get(0).getPrecio() <= precio);
    }

    @Test
    @DisplayName("GIVEN: Libros prestados WHEN: obtenerPorPrestado THEN: Retorna registros correspondientes")
    void shouldReturnLibrosPrestados() {
        List<Libro> mockList = Arrays.asList(new Libro(1L, "123", "Metamorfosis", "Kafka", true, 5000, null));
        Mockito.when(libroRepository.findByPrestado()).thenReturn(mockList);

        List<LibroResponseDTO> resultado = libroService.obtenerPorPrestado();

        assertEquals(1, resultado.size());
        assertEquals("Libro Prestado.", resultado.get(0).getPrestado());
    }
}