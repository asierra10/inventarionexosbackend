package co.com.inventario.controller;

import co.com.inventario.model.Producto;
import co.com.inventario.model.Usuario;
import co.com.inventario.repository.IRepositorioProducto;
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
public class ProductController {

    @Autowired
    private IRepositorioProducto repo;

    @Autowired
    private IRepositorioUsuario users;

    @GetMapping("AllProducts")
    public ResponseEntity<List<Producto>> getAllProducts(){
        try {
            List<Producto> products = new ArrayList<Producto>();
            repo.findAll().forEach(products::add);
            return new ResponseEntity<>(products, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getOneProduct")
    public ResponseEntity<Producto> getOneProduct(@RequestParam Long id){
        try{
            Producto response = null;
            Optional<Producto> fromRepo = repo.findById(id);
            if(fromRepo.isPresent()){
               response = fromRepo.get();
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("getAllProductsByName")
    public ResponseEntity<List<Producto>> getAllProductsByName(@RequestParam String name){
        try{
            List<Producto> fromRepo = repo.findByNombreContaining(name);
            return new ResponseEntity<>(fromRepo, HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("getProductByName")
    public ResponseEntity<Producto> getProductByName(@RequestParam String name){
        try{
            Producto response = repo.findByNombre(name);
            if(response != null){
                return new ResponseEntity<>(response, HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new Producto(0L,"Producto no existente",0,null,null,null,null), HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("saveProduct")
    public ResponseEntity<Producto> saveProduct(@RequestParam Long creador, @RequestBody Producto newProduct) {
        try {
            Producto save = null;
            Optional<Usuario> creator = users.findById(creador);

            Producto validar = repo.findByNombre(newProduct.getNombre());
            if(validar != null && validar.getNombre().equals(newProduct.getNombre())){
                return new ResponseEntity<>(new Producto(0L,"Producto ya existente",0,null,null,null,null), HttpStatus.NOT_FOUND);
            }else if(validar == null){
                save = repo.save(new Producto(newProduct.getId_producto(), newProduct.getNombre(),
                        newProduct.getCantidad(), creator.get(),Util.getFechaActual(),
                        null,null));
            }
            return new ResponseEntity<>(save, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("updateProduct")
    public ResponseEntity<Producto> updateProduct(@RequestParam Long id, @RequestParam Long editor, @RequestBody Producto updateProduct){
        try{
            Optional<Usuario> edit = users.findById(editor);
            Optional<Producto> getProduct = repo.findById(id);
            if (getProduct.isPresent() && edit.isPresent()) {
                Producto response = getProduct.get();
                response.setNombre(updateProduct.getNombre());
                response.setCantidad(updateProduct.getCantidad());
                response.setUsuario_editor(edit.get());
                response.setFecha_edicion(Util.getFechaActual());
                return new ResponseEntity<>(repo.save(response), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("deleteProduct")
    public ResponseEntity<Producto> deleteProduct(@RequestParam Long id,@RequestParam int creator){
        try{
            Optional<Producto> validar = repo.findById(id);
            if(validar.isPresent()){
                if(validar.get().getUsuario_creador().getId_usuario() == creator){
                    repo.delete(validar.get());
                }else{
                    return new ResponseEntity<>(new Producto(0L,"Usuario no creador",0,null,null,null,null), HttpStatus.INTERNAL_SERVER_ERROR);
                }
                return new ResponseEntity<>(new Producto(0L,"Producto eliminado",0,null,null,null,null), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
