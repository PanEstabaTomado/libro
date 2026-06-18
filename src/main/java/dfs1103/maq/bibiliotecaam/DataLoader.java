package dfs1103.maq.bibiliotecaam;

import dfs1103.maq.bibiliotecaam.model.Libro;
import dfs1103.maq.bibiliotecaam.repository.LibroRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private LibroRepository libroRepository;

    @Override
    public void run(String... args) throws Exception{
        Faker faker = new Faker();

        for (int i = 0; i < 6; i++) {
            Libro libro = new Libro();
            libro.setIsbn(faker.code().isbn10());
            libro.setTitulo(faker.lorem().sentence());
            libro.setAutor(faker.name().fullName());
            libro.setPrestado(faker.bool().bool());
            libro.setPrecio(faker.number().numberBetween(5000,99999));
            libro.setIdDona((long) faker.number().numberBetween(1,3));

            libroRepository.save(libro);
        }
    }
}
