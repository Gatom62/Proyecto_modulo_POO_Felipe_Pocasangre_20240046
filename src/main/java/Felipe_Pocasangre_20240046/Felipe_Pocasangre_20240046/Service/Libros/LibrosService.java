package Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Service.Libros;


import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Entities.Libros.LibrosEntity;
import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Exception.Libros.ExceptionNotFound;
import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Model.DTO.Libros.LibrosDTO;
import Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Repository.Libros.LibrosRepository;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibrosService {

    @Autowired
    private LibrosRepository repo;

    public List<LibrosDTO> getAllLibros(){
        List<LibrosEntity> libros = repo.findAll();
        return libros.stream().map(this::ConvertirADTO).collect(Collectors.toList());
    }

    public LibrosDTO getLibrosPorId(Long id){
        LibrosEntity libros = repo.findById(id).orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return ConvertirADTO(libros);
    }

    public LibrosDTO insert (@Valid LibrosDTO jsonData) {
        if (jsonData == null) {
            throw new IllegalArgumentException("El estudiante no puede estar en blanco");
        }
        try {
            LibrosEntity librosEntity = ConvertirEntity(jsonData);
            LibrosEntity librosGuardados = repo.save(librosEntity);
            return ConvertirADTO(librosGuardados);
        } catch (Exception e) {
            log.error("Error al registrar al libro" + e.getMessage());
            throw new ExceptionNotFound("Error al registrar el libro" + e.getMessage());
        }
    }

    public LibrosDTO update(Long id, LibrosDTO librosDTO){
        LibrosEntity libroExistente = repo.findById(id).orElseThrow(() -> new
                ExceptionNotFound("Libro no encontrado"));

        libroExistente.setTitulo(librosDTO.getTitulo());
        libroExistente.setIsbn(librosDTO.getIsbn());
        libroExistente.setAnio_publicacion(librosDTO.getAnio_publicacion());
        libroExistente.setGenero(librosDTO.getGenero());
        libroExistente.setAutor_id(librosDTO.getAutor_id());

        if (librosDTO.getAutor_id() != null){
            LibrosEntity libro = repo.findById(librosDTO.getId())
                    .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con id propornionado"));
        }

        LibrosEntity libroActualizado = repo.save(libroExistente);

        return ConvertirADTO(libroActualizado);
    }

    public boolean delete(Long id){
        try {
            LibrosEntity entity = repo.findById(id).orElse(null);

            if (entity != null){
                repo.deleteById(id);
                return true;
            }else{
                System.out.println("Usuario no encontrado");
                return false;
            }
        }catch (EmptyResultDataAccessException e){
            throw new EmptyResultDataAccessException("No se econtro usuario con id: " + id + "para eliminar.", 1);
        }
    }

    private LibrosDTO ConvertirADTO(LibrosEntity entity){
        LibrosDTO dto = new LibrosDTO();
        dto.setId(entity.getId());
        dto.setTitulo(entity.getTitulo());
        dto.setIsbn(entity.getIsbn());
        dto.setAnio_publicacion(entity.getAnio_publicacion());
        dto.setGenero(entity.getGenero());
        dto.setAutor_id(entity.getAutor_id());
        return dto;
    }

    private LibrosEntity ConvertirEntity(@Valid LibrosDTO json){
        LibrosEntity entity = new LibrosEntity();
        entity.setTitulo(json.getTitulo());
        entity.setIsbn(json.getIsbn());
        entity.setAnio_publicacion(json.getAnio_publicacion());
        entity.setGenero(json.getGenero());
        entity.setAutor_id(json.getAutor_id());
        return entity;
    }

}