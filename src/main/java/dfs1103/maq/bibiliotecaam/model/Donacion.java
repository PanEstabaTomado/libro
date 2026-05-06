package dfs1103.maq.bibiliotecaam.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "donacion")
public class Donacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDona;

    @Column(nullable = false, precision = 7)
    private Long numrunDona;

    @Column(nullable = false, length = 1)
    private String dvrunDona;

    @Column(nullable = false, length = 20)
    private String pnombreDona;

    @Column(nullable = true, length = 30)
    private String snombreDona;

    @Column(nullable = false, length = 30)
    private String appaternoDona;

    @Column(nullable = false, length = 30)
    private String apmaternoDona;

    @ManyToOne
    @JoinColumn(name = "id_emp",nullable = false)
    private Empleado empleado;
}
