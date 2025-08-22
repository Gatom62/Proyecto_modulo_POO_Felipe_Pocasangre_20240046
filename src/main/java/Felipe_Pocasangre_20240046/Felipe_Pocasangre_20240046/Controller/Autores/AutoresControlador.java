package Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Controller.Autores;

import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Model.DTO.Autores.AutorDTO;
import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Service.Autores.AutoresService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/apiAutores")
@CrossOrigin
public class AutoresControlador {

    @Autowired
    private AutoresService service;

    //Optener todos los registros
    @GetMapping("/getDataAutores")
    public ResponseEntity<?> getDataAutores(){
        try{
            List<AutorDTO> autores = service.getAllAutores();
            if (autores.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("status", "No hay autores registrados"));
            }
            return ResponseEntity.ok(autores);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "Error al optener al optener estudiantes",
                "message", e.getMessage()
            ));
        }
    }

    //Optener autores por su id
    @GetMapping("/getDataAutores/{id}")
    public ResponseEntity<AutorDTO> getAutoresPorId(@PathVariable Long id){

        try {
            AutorDTO autor = service.getAutoresPorId(id);
            return ResponseEntity.ok(autor);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

}