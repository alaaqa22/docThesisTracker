package dtt.global.tansport;

public class Faculty {
    private int id ;
    private String name;

    public Faculty(){};

    public Faculty (String name) {
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
