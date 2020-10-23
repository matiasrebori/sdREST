package py.una.pol.personas.model;
import java.io.Serializable;

public class Transport implements Serializable {
     Integer id;
     String type;
    public Transport(){}
    public Transport( Integer id)
    {
        this.id = id;
        this.type = "null";
    }
    public Transport( String type)
    {
        this.type= type;
    }
    public Transport( Integer id , String type)
    {
        this.id = id;
        this.type = type;
    }


    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

/*
    @Override
    public String toString() {
        return "Transport{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }

 */
}
