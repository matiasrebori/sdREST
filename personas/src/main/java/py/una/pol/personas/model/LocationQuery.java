package py.una.pol.personas.model;

public class LocationQuery {
    Integer posX;
    Integer posY;
    Integer radius;

    public LocationQuery(){}
    public LocationQuery(Integer posX, Integer posY, Integer radius) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
    }

    public Integer getPosX() {
        return posX;
    }

    public void setPosX(Integer posX) {
        this.posX = posX;
    }

    public Integer getPosY() {
        return posY;
    }

    public void setPosY(Integer posY) {
        this.posY = posY;
    }

    public Integer getRadius() {
        return radius;
    }

    public void setRadius(Integer radius) {
        this.radius = radius;
    }


}
