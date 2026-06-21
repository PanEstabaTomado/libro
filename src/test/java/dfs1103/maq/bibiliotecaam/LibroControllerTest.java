package dfs1103.maq.bibiliotecaam;

import dfs1103.maq.bibiliotecaam.assemblers.LibroModelAssembler;
import dfs1103.maq.bibiliotecaam.controller.LibroController;
import dfs1103.maq.bibiliotecaam.dto.LibroRequestDTO;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import dfs1103.maq.bibiliotecaam.service.LibroService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibroController.class)
@ActiveProfiles("test")
@Import(LibroModelAssembler.class)
@DisplayName("Tests Unitarios - LibroController")
class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private LibroService libroService;

    @Test
    @DisplayName("GIVEN: Existen libros WHEN: GET /api/bibliotecaam/libro THEN: Retorna 200 OK y la lista HAL-JSON")
    void shouldReturnTodosLosLibros() throws Exception {
        LibroResponseDTO l1 = new LibroResponseDTO(1L, "978-3-16-148410-0", "Cien años de soledad", "Gabriel García Márquez", "No", 15000, 101L);
        LibroResponseDTO l2 = new LibroResponseDTO(2L, "978-0-452-28423-4", "1984", "George Orwell", "Sí", 12000, 102L);
        List<LibroResponseDTO> lista = Arrays.asList(l1, l2);

        Mockito.when(libroService.obtenerTodos()).thenReturn(lista);

        mockMvc.perform(get("/api/bibliotecaam/libro")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.libroResponseDTOList.length()").value(2))
                .andExpect(jsonPath("$._embedded.libroResponseDTOList[0].idLibro").value(1L))
                .andExpect(jsonPath("$._embedded.libroResponseDTOList[0].titulo").value("Cien años de soledad"));
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: GET /api/bibliotecaam/libro/{id} THEN: Retorna 200 OK y el libro")
    void shouldReturnLibroById() throws Exception {
        Long id = 1L;
        LibroResponseDTO mockResponse = new LibroResponseDTO(id, "978-3-16-148410-0", "Cien años de soledad", "Gabriel García Márquez", "No", 15000, 101L);

        Mockito.when(libroService.obtenerPorId(id)).thenReturn(Optional.of(mockResponse));

        mockMvc.perform(get("/api/bibliotecaam/libro/{id}", id)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idLibro").value(id))
                .andExpect(jsonPath("$.titulo").value("Cien años de soledad"));
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: GET /api/bibliotecaam/libro/{id} THEN: Retorna 404 Not Found")
    void shouldReturnNotFoundWhenLibroDoesNotExist() throws Exception {
        Long id = 99L;
        Mockito.when(libroService.obtenerPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/bibliotecaam/libro/{id}", id)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("GIVEN: ISBN válido WHEN: GET /api/bibliotecaam/libro/isbn/{isbn} THEN: Retorna la lista de libros")
    void shouldReturnLibroByIsbn() throws Exception {
        String isbn = "978-3-16-148410-0";
        LibroResponseDTO l1 = new LibroResponseDTO(1L, isbn, "Cien años de soledad", "Gabriel García Márquez", "No", 15000, 101L);
        Mockito.when(libroService.obtenerPorIsbn(isbn)).thenReturn(Arrays.asList(l1));

        mockMvc.perform(get("/api/bibliotecaam/libro/isbn/{isbn}", isbn)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.libroResponseDTOList[0].isbn").value(isbn));
    }

    @Test
    @DisplayName("GIVEN: Título válido WHEN: GET /api/bibliotecaam/libro/titulo/{titulo} THEN: Retorna la lista de libros")
    void shouldReturnLibroByTitulo() throws Exception {
        String titulo = "1984";
        LibroResponseDTO l1 = new LibroResponseDTO(2L, "978-0-452-28423-4", "1984", "George Orwell", "Sí", 12000, 102L);
        Mockito.when(libroService.obtenerPorTitulo(titulo)).thenReturn(Arrays.asList(l1));

        mockMvc.perform(get("/api/bibliotecaam/libro/titulo/{titulo}", titulo)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.libroResponseDTOList[0].titulo").value(titulo));
    }

    @Test
    @DisplayName("GIVEN: Autor válido WHEN: GET /api/bibliotecaam/libro/autor/{autor} THEN: Retorna la lista de libros")
    void shouldReturnLibroByAutor() throws Exception {
        String autor = "Orwell";
        LibroResponseDTO l1 = new LibroResponseDTO(2L, "978-0-452-28423-4", "1984", "George Orwell", "Sí", 12000, 102L);
        Mockito.when(libroService.obtenerPorAutor(autor)).thenReturn(Arrays.asList(l1));

        mockMvc.perform(get("/api/bibliotecaam/libro/autor/{autor}", autor)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.libroResponseDTOList[0].autor").value("George Orwell"));
    }

    @Test
    @DisplayName("GIVEN: Precio tope WHEN: GET /api/bibliotecaam/libro/precio/{precio} THEN: Retorna libros con precio menor")
    void shouldReturnLibrosByPrecioMenorQue() throws Exception {
        Integer precio = 20000;
        LibroResponseDTO l1 = new LibroResponseDTO(2L, "978-0-452-28423-4", "1984", "George Orwell", "Sí", 12000, 102L);
        Mockito.when(libroService.obtenerPorPrecioMenorQue(precio)).thenReturn(Arrays.asList(l1));

        mockMvc.perform(get("/api/bibliotecaam/libro/precio/{precio}", precio)
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.libroResponseDTOList[0].precio").value(12000));
    }

    @Test
    @DisplayName("GIVEN: Libros prestados WHEN: GET /api/bibliotecaam/libro/prestado THEN: Retorna la lista")
    void shouldReturnLibrosPrestados() throws Exception {
        LibroResponseDTO l1 = new LibroResponseDTO(2L, "978-0-452-28423-4", "1984", "George Orwell", "Sí", 12000, 102L);
        Mockito.when(libroService.obtenerPorPrestado()).thenReturn(Arrays.asList(l1));

        mockMvc.perform(get("/api/bibliotecaam/libro/prestado")
                        .accept(MediaTypes.HAL_JSON_VALUE))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.libroResponseDTOList[0].prestado").value("Sí"));
    }

    @Test
    @DisplayName("GIVEN: Request válido WHEN: POST /api/bibliotecaam/libro THEN: Retorna 201 Created")
    void shouldCreateLibro() throws Exception {
        LibroRequestDTO request = new LibroRequestDTO("111-222", "El Aleph", "Jorge Luis Borges", false, 18000, 200L);
        LibroResponseDTO mockResponse = new LibroResponseDTO(10L, "111-222", "El Aleph", "Jorge Luis Borges", "No", 18000, 200L);

        Mockito.when(libroService.guardar(any(LibroRequestDTO.class))).thenReturn(mockResponse);

        mockMvc.perform(post("/api/bibliotecaam/libro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.idLibro").value(10L))
                .andExpect(jsonPath("$.titulo").value("El Aleph"));
    }

    @Test
    @DisplayName("GIVEN: ID y Request válido WHEN: PUT /api/bibliotecaam/libro/{id} THEN: Retorna 200 OK")
    void shouldUpdateLibro() throws Exception {
        Long id = 1L;
        LibroRequestDTO request = new LibroRequestDTO("978-3-16-148410-0", "Cien años de soledad (Edición Especial)", "Gabriel García Márquez", false, 25000, 101L);
        LibroResponseDTO mockResponse = new LibroResponseDTO(id, "978-3-16-148410-0", "Cien años de soledad (Edición Especial)", "Gabriel García Márquez", "No", 25000, 101L);

        Mockito.when(libroService.actualizar(eq(id), any(LibroRequestDTO.class))).thenReturn(Optional.of(mockResponse));

        mockMvc.perform(put("/api/bibliotecaam/libro/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Cien años de soledad (Edición Especial)"))
                .andExpect(jsonPath("$.precio").value(25000));
    }

    @Test
    @DisplayName("GIVEN: ID válido WHEN: DELETE /api/bibliotecaam/libro/{id} THEN: Retorna 200 OK con mensaje de éxito")
    void shouldDeleteLibroSuccessfully() throws Exception {
        Long id = 1L;
        LibroResponseDTO mockResponse = new LibroResponseDTO(id, "978-3-16-148410-0", "Cien años de soledad", "Gabriel García Márquez", "No", 15000, 101L);

        Mockito.when(libroService.obtenerPorId(id)).thenReturn(Optional.of(mockResponse));
        Mockito.doNothing().when(libroService).eliminar(id);

        mockMvc.perform(delete("/api/bibliotecaam/libro/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.['¡EXITO! ']").value("¡El libro fue eliminado con exito!"));
    }

    @Test
    @DisplayName("GIVEN: ID inexistente WHEN: DELETE /api/bibliotecaam/libro/{id} THEN: Retorna 404 Not Found con mensaje de error")
    void shouldReturnNotFoundWhenDeletingNonExistentLibro() throws Exception {
        Long id = 99L;
        Mockito.when(libroService.obtenerPorId(id)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/bibliotecaam/libro/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.['¡ERROR! ']").value("¡El libro con id 99 no fue encontrado!"));
    }
}