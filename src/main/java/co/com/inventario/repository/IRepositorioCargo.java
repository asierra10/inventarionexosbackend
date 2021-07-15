package co.com.inventario.repository;

import co.com.inventario.model.Cargo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IRepositorioCargo extends JpaRepository<Cargo, Long> {

}
