package Felipe_Pocasangre_20240046.Felipe_Pocasangre_20240046.Entities.Libros;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table(name = "LIBROS")
public class LibrosEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_libro")
    @SequenceGenerator(name = "seq_libro", sequenceName = "seq_libro", allocationSize = 1)
    private Long id;

    @Column(name = "TITULO")
    private String titulo;

    @Column(name = "ISBN")
    private String isbn;

    @Column(name = "ANIO_PUBLICACION")
    private Integer anio_publicacion;

    @Column(name = "GENERO")
    private String genero;

    @Column(name = "AUTOR_ID")
    private Long autor_id;
}
