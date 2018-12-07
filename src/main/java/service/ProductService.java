package service;

import common.Util;
import dal.*;
import entity.Product;

import java.sql.Connection;
import java.util.List;

public class ProductService {
    ProductDAO productDAO;
    StoreDAO storeDAO;
    StoreProductDAO storeProductDAO;

    public ProductService() {
        if (Util.getProperty().equals("file")) {
            productDAO = new FileProductDAO();
            storeDAO = new FileStoreDAO();
            storeProductDAO = new FileStoreProductDAO();
        } else if (Util.getProperty().equals("database")){
            productDAO = new DatabaseProductDAO();
            storeDAO = new DatabaseStoreDAO();
            storeProductDAO = new DatabaseStoreProductDAO();
        } else {
            System.out.println("These storage is not supported");
            System.exit(0);
        }
    }

    public void removeProduct(Product product) {
        productDAO.remove(product);
    }

    public void addProduct(Product product) {
        List<Product> productList = productDAO.getAll();
        if (!productList.contains(product)) {
            productDAO.add(product);
        }
    }
}
