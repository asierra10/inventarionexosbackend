package co.com.inventario.controller;

import co.com.inventario.model.Cargo;
import co.com.inventario.repository.IRepositorioCargo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class CargoController {

    @Autowired
    private IRepositorioCargo repo;

    @GetMapping("AllPositions")
    public ResponseEntity<List<Cargo>> getAllProducts(){
        try {
            List<Cargo> cargos = new ArrayList<Cargo>();
            repo.findAll().forEach(cargos::add);
            return new ResponseEntity<>(cargos, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
