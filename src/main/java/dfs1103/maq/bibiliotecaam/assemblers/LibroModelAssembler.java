package dfs1103.maq.bibiliotecaam.assemblers;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import dfs1103.maq.bibiliotecaam.controller.LibroController;
import dfs1103.maq.bibiliotecaam.dto.LibroResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class LibroModelAssembler implements RepresentationModelAssembler<LibroResponseDTO, EntityModel<LibroResponseDTO>> {
    @Override
    public EntityModel<LibroResponseDTO> toModel(LibroResponseDTO libroDto){
        return EntityModel.of(libroDto,
                linkTo(methodOn(LibroController.class).obtenerPorId(libroDto.getIdLibro())).withSelfRel(),
                linkTo(methodOn(LibroController.class).obtenerTodos()).withRel("libros"));
    }
}
