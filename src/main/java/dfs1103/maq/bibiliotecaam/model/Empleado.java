package dfs1103.maq.bibiliotecaam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "empleado")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEmp;

    @Column(nullable = false, length = 8, unique = true)
    private Long numrunEmp;

    @Column(nullable = false, length = 1, unique = false)
    private String dvrunEmp;

    @Column(nullable = false, length = 30, unique = false)
    private String pnombreEmp;

    @Column(nullable = true, length = 30, unique = false)
    private String snombreEmp;

    @Column(nullable = false, length = 30, unique = false)
    private String appaternoEmp;

    @Column(nullable = false, length = 30, unique = false)
    private String apmaternoEmp;

    @Column(nullable = false)
    private LocalDate fecContrato;

    @Column(nullable = false, length = 7, unique = false)
    private Long sueldo;
}
