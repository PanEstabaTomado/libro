package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.model.Donacion;
import dfs1103.maq.bibiliotecaam.repository.DonacionRepository;
import dfs1103.maq.bibiliotecaam.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DonacionService {
    private final DonacionRepository donacionRepository;

    private final EmpleadoRepository empleadoRepository;

    public List<Donacion> obtenerTodos(){
        return donacionRepository.findAll();
    }

    public Optional<Donacion> obtenerPorId(Long id){
        return donacionRepository.findById(id);
    }

    public Donacion guardar(Donacion donacion){
        return donacionRepository.save(donacion);
    }

    public void eliminar(Long id){
        donacionRepository.deleteById(id);
    }


}
