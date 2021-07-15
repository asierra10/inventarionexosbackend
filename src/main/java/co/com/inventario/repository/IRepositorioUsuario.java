package co.com.inventario.repository;

import co.com.inventario.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IRepositorioUsuario extends JpaRepository<Usuario,Long>{
    List<Usuario> findAll();
}
