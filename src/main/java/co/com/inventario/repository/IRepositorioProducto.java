package co.com.inventario.repository;

import co.com.inventario.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IRepositorioProducto extends JpaRepository<Producto, Long> {

    List<Producto> findByCantidad(String cantidad);

    Producto findByNombre(String nombre);

    List<Producto>findByNombreContainingIgnoreCase(String nombre);
}
