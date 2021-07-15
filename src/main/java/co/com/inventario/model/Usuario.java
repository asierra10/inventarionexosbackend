package co.com.inventario.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario", schema = "public")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_usuario")
    private Long id_usuario;
    @Column(name = "nombre_usuario")
    private String nombre_usuario;
    @Column(name = "edad")
    private int edad;
    @Column(name = "fecha_ingreso")
    private Date fecha_ingreso;
    @ManyToOne
    @JoinColumn(name = "cargo")
    private Cargo cargo;

}
