package co.com.inventario.model;

import lombok.*;
import javax.persistence.*;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "producto", schema = "public")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_producto")
    private Long id_producto;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "cantidad")
    private int cantidad;
    @ManyToOne
    @JoinColumn(name = "usuario_creador")
    private Usuario usuario_creador;
    @Column(name = "fecha_creacion")
    private Date fecha_creacion;
    @ManyToOne
    @JoinColumn(name = "usuario_editor")
    private Usuario usuario_editor;
    @Column(name = "fecha_edicion")
    private Date fecha_edicion;
}
