package co.com.inventario.controller;

import co.com.inventario.model.Cargo;
import co.com.inventario.model.Usuario;
import co.com.inventario.repository.IRepositorioCargo;
import co.com.inventario.repository.IRepositorioUsuario;
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
public class UserController {

    @Autowired
    private IRepositorioUsuario repo;

    @Autowired
    private IRepositorioCargo repoCargo;

    @GetMapping("AllUsers")
    public ResponseEntity<List<Usuario>> getAllUsers(){
        try {
            List<Usuario> users = new ArrayList<Usuario>();
            repo.findAll().forEach(users::add);
            return new ResponseEntity<>(users, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("saveUser")
    public ResponseEntity<Usuario> saveUser(@RequestBody Usuario newUser, @RequestParam Long cargo){
        try{
            Usuario save = null;
            Optional<Cargo> validar = repoCargo.findById(cargo);
            if(validar.isPresent()){
                save = repo.save(new Usuario(newUser.getId_usuario(), newUser.getNombre_usuario(), newUser.getEdad(), Util.getFechaActual(),validar.get()));
                return new ResponseEntity<>(save, HttpStatus.CREATED);
            }else{
                return new ResponseEntity<>(new Usuario(0L,"Cargo no existe",0,null,null), HttpStatus.NOT_FOUND);
            }

        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("deleteUser")
    public ResponseEntity<Usuario> deleteUser(@RequestParam Long id){
        try {
            repo.deleteById(id);
            return new ResponseEntity<>(new Usuario(0L,"Usuario eliminado",0,null,null), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
