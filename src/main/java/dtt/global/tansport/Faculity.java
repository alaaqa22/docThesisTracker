package dtt.global.tansport;

public class Faculity {
    private int id ;
    private String name;

    public Faculity(){};

    public Faculity (String name) {
        this.name = name;
    }

    public void setId (int id) {
        this.id = id;
    }

    public void setName (String name) {
        this.name = name;
    }

    public int getId () {
        return id;
    }

    public String getName () {
        return name;
    }
}
