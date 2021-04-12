package encapsulations;

import com.sun.istack.NotNull;

import java.sql.Date;

public class FormDataTemplate {

    private String name;
    private String sector;
    private String grade;
    private Float latitude;
    private Float longitude;
    private String created_date;
    private String user_creador;
    private Long user_id;

    public FormDataTemplate(String name, String sector, String grade, Float latitude, Float longitude, String created_date, String user_creador, Long user_id) {
        this.name = name;
        this.sector = sector;
        this.grade = grade;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_date = created_date;
        this.user_creador = user_creador;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
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

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public String getUser_creador() {
        return user_creador;
    }

    public void setUser_creador(String user_creador) {
        this.user_creador = user_creador;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
