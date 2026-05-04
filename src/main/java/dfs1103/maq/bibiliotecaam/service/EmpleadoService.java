package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.repository.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
}
