package dal;

import entity.Product;
import entity.Store;

import java.util.List;

public interface StoreDAO {

    //create
    void add(Store store);

    //read
    List<Store> getAll();

    Store getById(Integer id);

    //update
    void update(Store store);

    //delete
    void remove(Store store);
}
