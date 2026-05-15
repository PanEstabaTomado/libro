package dfs1103.maq.bibiliotecaam.config;

import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Data
public class DataIntializer implements CommandLineRunner {
    private final LibroRepository libroRepository;

    @Override
    public void run(String... args){
        if (libroRepository.count()>0){
            log.info(">>> Data Initializer: La BD ya tiene datos, se omite inserción inicial.");
            return;
        }

        log.info(">>> Data Initializer: BD vacía detectada, insertando datos de prueba.");

        libroRepository.save(
                new Libro(null, "12389412", "Tractatus Logico-Philosophicus", "Wittgenstein", true, 35000L, null )
        );

        libroRepository.save(
                new Libro(null, "987q654", "Teoria de los tres cuerpos", "Cixin Liu", true, 250000L, null )
        );

        libroRepository.save(
                new Libro(null, "asd987as", "El Arbol del conocimiento", "Maturana y Varela", true, 12000L, null )
        );
    }
}
