package entity;

import java.util.Objects;

public class Store {
    private Integer id;
    private String address;
    private String name;

    public Store(Integer id, String name, String address) {
        this.id = id;
        this.address = address;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public String toCSVformat() {
        return id + "," + name + "," + address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store Store = (Store) o;
        return Objects.equals(id, Store.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, name);
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
