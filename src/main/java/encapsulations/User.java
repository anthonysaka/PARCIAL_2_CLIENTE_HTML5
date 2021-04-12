package encapsulations;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String rol;
    @NotNull
    private String nombre;

    @OneToMany(mappedBy = "user_creador", fetch = FetchType.EAGER)
    private List<Form> lista_forms;  ///AGREGAR SET & GET

    public User(String username, String password, String rol, String nombre) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.rol = rol;
        this.nombre = nombre;
    }

    public User(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    /*** Gets and Sets ***/


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Form> getLista_forms() {
        return lista_forms;
    }

    public void setLista_forms(List<Form> lista_forms) {
        this.lista_forms = lista_forms;
    }
}
