package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.model.Donacion;
import dfs1103.maq.bibiliotecaam.model.Empleado;
import dfs1103.maq.bibiliotecaam.repository.DonacionRepository;
import dfs1103.maq.bibiliotecaam.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    public List<Empleado> obtenerTodos(){
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerPorId(Long id){
        return empleadoRepository.findById(id);
    }

    public Empleado guardar(Empleado empleado){
        return empleadoRepository.save(empleado);
    }

    public void eliminar(Long id){
        empleadoRepository.deleteById(id);
    }
}