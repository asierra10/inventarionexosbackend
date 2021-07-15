package co.com.inventario.controller;

import co.com.inventario.model.Usuario;
import co.com.inventario.repository.IRepositorioUsuario;
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
public class UserController {

    @Autowired
    private IRepositorioUsuario repo;

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


}
