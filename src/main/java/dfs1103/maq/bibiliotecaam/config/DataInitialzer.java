package dfs1103.maq.bibiliotecaam.config;

import dfs1103.maq.bibiliotecaam.repository.DonacionRepository;
import dfs1103.maq.bibiliotecaam.repository.EmpleadoRepository;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitialzer implements CommandLineRunner {

    private final DonacionRepository donacionRepository;
    private final LibroRepository libroRepository;
    private final EmpleadoRepository empleadoRepository;

    @Override
    public void run(String... args){
        if (donacionRepository.count() > 0){
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }
        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");    }
        /*
        * * TO DO
        * COLOCAR LOS DATOS DE EMPLEADO, LIBRO Y DONACION
         */

}
