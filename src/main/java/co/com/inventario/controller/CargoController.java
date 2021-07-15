package co.com.inventario.controller;

import co.com.inventario.model.Cargo;
import co.com.inventario.model.Usuario;
import co.com.inventario.repository.IRepositorioCargo;
import co.com.inventario.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    @PostMapping("savePosition")
    public ResponseEntity<Cargo> savePosition(@RequestBody Cargo newPosition){
        try{
            Cargo save = repo.save(new Cargo(newPosition.getId_cargo(), newPosition.getCargo()));
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deletePosition")
    public ResponseEntity<Cargo> deletePosition(@RequestParam Long id){
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(new Cargo(0L,"Cargo eliminado"), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
