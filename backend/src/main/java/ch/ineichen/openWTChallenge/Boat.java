package ch.ineichen.openWTChallenge;


import java.util.concurrent.atomic.AtomicInteger;

public class Boat {
    protected static AtomicInteger nextId = new AtomicInteger(0);
    public final int id;
    public String name;
    public String description;

    public Boat(String name, String description) {
        this.name = name;
        this.description = description;
        this.id = nextId.getAndIncrement();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boat boat = (Boat) o;

        return id == boat.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
