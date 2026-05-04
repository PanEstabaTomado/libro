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
    private Long id_dona;

    @Column(nullable = false, precision = 7)
    private Integer numrun_dona;

    @Column(nullable = false, length = 1)
    private String dvrun_dona;

    @Column(nullable = false, length = 20)
    private String pnombre_dona;

    @Column(nullable = true, length = 30)
    private String snombre_dona;

    @Column(nullable = false, length = 30)
    private String appaterno_dona;

    @Column(nullable = false, length = 30)
    private String apmaterno_dona;

    @ManyToOne
    @JoinColumn(name = "id_emp",nullable = false)
    private Empleado empleado;
}
