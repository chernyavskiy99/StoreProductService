package dal;

import common.Util;
import entity.Product;
import entity.Store;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

public interface ProductDAO {

    //create
    void add(Product product);

    //read
    List<Product> getAll();

    Product getById(Integer id);

    //update
    void update(Product product);

    //delete
    void remove(Product product);
}
