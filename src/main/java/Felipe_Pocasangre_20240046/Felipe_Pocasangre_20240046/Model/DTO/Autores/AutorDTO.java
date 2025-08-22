package Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Model.DTO.Autores;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AutorDTO {

    private Long id;

    @NotBlank(message = "El titulo del libro no pude estar en blanco")
    private String titulo;

    private String isbn;
    private Integer anio_publicacion;

    @NotBlank(message = "El genero del libro no puede estar en blanco")
    private String genero;

    private Long autor_id;
}