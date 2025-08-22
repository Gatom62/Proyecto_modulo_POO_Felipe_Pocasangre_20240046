package Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Controller.Libros;

import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Exception.Libros.ExceptionNotFound;
import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Model.DTO.Libros.LibrosDTO;
import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Service.Libros.LibrosService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/apiLibros")
@CrossOrigin
public class LibrosControlador {

    @Autowired
    private LibrosService service;

    //Optener todos los registros
    @GetMapping("/getDataLibros")
    public ResponseEntity<?> getDataLibros(){
        try{
            List<LibrosDTO> libros = service.getAllLibros();
            if (libros.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "No hay libros registrados"));
            }
            return ResponseEntity.ok(libros);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Error al optener al libros",
                "message", e.getMessage()
            ));
        }
    }

    //Optener autores por su id
    @GetMapping("/getDataLibros/{id}")
    public ResponseEntity<LibrosDTO> getLibrosPorId(@PathVariable Long id){
        try {
            LibrosDTO libro = service.getLibrosPorId(id);
            return ResponseEntity.ok(libro);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/newLibro")
    public ResponseEntity<Map<String, Object>> InsertLibro(@Valid @RequestBody LibrosDTO json){
        try{
            LibrosDTO response = service.insert(json);
            if (response == null){
                return ResponseEntity.badRequest().body(Map.of(
                        "error", "Insercion incorrecta",
                        "estatus", "Error",
                        "Descripcion", "Verifica si los valores"
                ));
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(Map.of(
                    "estado", "Completado",
                    "data", response
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "error",
                    "message", "Error al registrar el libro",
                    "datail", e.getMessage()
            ));
        }
    }

    @PutMapping("/updateLibro/{id}")
    public ResponseEntity<?> modificarLibro(
            @PathVariable Long id,
            @Valid @RequestBody LibrosDTO librosDTO,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errores.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }
        try {
            LibrosDTO libroActualizado = service.update(id);
            return ResponseEntity.ok(libroActualizado);
        }catch (ExceptionNotFound e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    Map.of("error", "Datos duplicados",
                            "campo", e.getMessage())
            );
        }
    }

    @DeleteMapping("/deleteLibro/{id}")
    public ResponseEntity<Map<String, Object>> eliminarLibro(@Valid Long id){
        try {
            if (!service.delete(id)){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).header(
                        "Message error", "Estudiante no encontrado"
                ).body(Map.of(
                   "error", "Not found",
                   "mensaje", "El estudiante no fue encontrado",
                   "timestamp", Instant.now().toString()
                ));
            }
            return ResponseEntity.ok().body(Map.of(
               "status", "Proceso completado",
               "message", "Estudiante eliminado"
            ));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "status", "Error",
                    "message", "Error al eliminar el libro",
                    "detail", e.getMessage()
            ));
        }
    }

}