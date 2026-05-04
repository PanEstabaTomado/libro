package dfs1103.maq.bibiliotecaam.service;

import dfs1103.maq.bibiliotecaam.repository.DonacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DonacionService {
    private final DonacionRepository donacionRepository;
}
