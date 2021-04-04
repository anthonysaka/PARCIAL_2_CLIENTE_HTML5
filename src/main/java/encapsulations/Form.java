package encapsulations;

import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Form implements Serializable {
    @Id
    private Long id;
    private String nombre;
    private String nivel_escolar;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user_creador;


}
