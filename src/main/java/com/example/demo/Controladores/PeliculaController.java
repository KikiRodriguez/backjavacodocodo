package com.example.demo.Controladores;

import com.example.demo.Modelos.Pelicula;
import com.example.demo.Repositorios.PeliculaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//reciben las peticiones desde el front
public class PeliculaController {
    //la siguiente linea es para guardar los datos
    PeliculaRepository repositorio;

    public PeliculaController(PeliculaRepository repositorio) {
        this.repositorio = repositorio;
    }

    //metodo a los que va a responder al poner 127.0.0.1:8080/api/crearPeliculas
    @GetMapping("/api/crearPeliculas")
    public void crearPeliculas(){
        Pelicula pelicula1 = new Pelicula("Titanic","Jams Cameron","drama");
        Pelicula pelicula2 = new Pelicula("Forrest Gump","Robert Zemekis","drama");
        Pelicula pelicula3 = new Pelicula("Star Wars","George Lucas","ciencia ficción");

        repositorio.save(pelicula1);
        repositorio.save(pelicula2);
        repositorio.save(pelicula3);
    }

    //metodo a los que va a responder al poner 127.0.0.1:8080/api/peliculas
    @GetMapping("/api/peliculas")
    public List<Pelicula> obtenerPeliculas() {
        return repositorio.findAll();
    }

    @GetMapping("/api/pelicula/{id}")
    public ResponseEntity<Pelicula> obtenerPelicula(@PathVariable Long id) {
        Optional<Pelicula> opt = repositorio.findById(id);
        if (opt.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        else {
            return ResponseEntity.ok(opt.get());
        }
    }
    //post agrega
    @PostMapping("/api/peliculas")
    public ResponseEntity<Pelicula> guardarPelicula(@RequestBody Pelicula pelicula) {
        if (pelicula.getId()==null) {
            return ResponseEntity.badRequest().build();
        }
        repositorio.save(pelicula);
        return ResponseEntity.ok(pelicula);
    }
    //put actualiza
    @PutMapping("/api/peliculas")
    public ResponseEntity<Pelicula> actualizarPelicula(@RequestBody Pelicula pelicula) {
        if (pelicula.getId()==null || !repositorio.existsById(pelicula.getId())) {
            return ResponseEntity.badRequest().build();
        }
        repositorio.save(pelicula);
        return ResponseEntity.ok(pelicula);
    }

    @DeleteMapping("/api/peliculas/{id}")
    public ResponseEntity<Pelicula> borrarPelicula(@PathVariable Long id) {
        if (id==null || !repositorio.existsById(id)) {
            return ResponseEntity.badRequest().build();
        }
        repositorio.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
