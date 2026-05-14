package dfs1103.maq.bibiliotecaam.config;

import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class DataIntializer {
    private final LibroRepository libroRepository;

    @Override
    public void run(String... args){
        if (libroRepository.count()>0){
            log.info(">>> Data Initializer: La BD ya tiene datos, se omite inserción inicial.");
            return;
        }

        log.info(">>> Data Initializer: BD vacía detectada, insertando datos de prueba.");

        libroRepository.save(
                new Libro()
        )
    }
}
