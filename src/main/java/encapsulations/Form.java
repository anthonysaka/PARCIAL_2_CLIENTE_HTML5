package encapsulations;

import com.sun.istack.NotNull;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Form implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String nombre;
    @NotNull
    private String sector;
    @NotNull
    private String nivel_escolar;
    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    private User user_creador;

    public Form( String nombre, String sector, String nivel_escolar, User user_creador) {
        this.nombre = nombre;
        this.sector = sector;
        this.nivel_escolar = nivel_escolar;
        this.user_creador = user_creador;
    }

    public Form() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getNivel_escolar() {
        return nivel_escolar;
    }

    public void setNivel_escolar(String nivel_escolar) {
        this.nivel_escolar = nivel_escolar;
    }

    public User getUser_creador() {
        return user_creador;
    }

    public void setUser_creador(User user_creador) {
        this.user_creador = user_creador;
    }
}
