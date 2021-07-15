package co.com.inventario.model;

import lombok.*;
import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargos", schema = "public")
public class Cargo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_cargo")
    private Long id_cargo;
    @Column(name = "cargo")
    private String cargo;
}
