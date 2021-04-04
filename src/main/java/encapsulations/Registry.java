package encapsulations;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity()
public class Registry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @OneToOne(fetch = FetchType.EAGER)
    private Form auxForm;
    @NotNull
    private Float latitude;
    @NotNull
    private Float longitude;
    @NotNull
    private Date created_date;

    public Registry(Form auxForm, Float latitude, Float longitude, Date created_date) {
        this.auxForm = auxForm;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_date = created_date;
    }

    public Registry(){

    }

    /*** Gets and Sets ***/

    public Form getAuxForm() {
        return auxForm;
    }

    public void setAuxForm(Form auxForm) {
        this.auxForm = auxForm;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

}
