package common;

import entity.Product;
import entity.Store;
import service.ProductService;
import service.StoreService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static Connection connection = Util.getConnection();

    public static void main(String[] args) throws SQLException {
        StoreService storeService = new StoreService();
        ProductService productService = new ProductService();

        //products
        Product chocolate = new Product(1, "Alyonka");
        Product juice = new Product(2, "Dobriy");
        Product television = new Product(3, "PHILIPS");

        //stores
        Store pyatorochka = new Store(1, "Pyatorochka", "SPb Stroiteley street 25");
        Store auchan = new Store(2, "Auchan", "Moscow Stroiteley street 25");
        
        //quantityBatches
        Map<Product, Integer> auchanProductQuantity = new HashMap<>();
        auchanProductQuantity.put(chocolate, 50);
        auchanProductQuantity.put(television, 3);

        Map<Product, Integer> pyatorochkaProdcutQuantity = new HashMap<>();
        pyatorochkaProdcutQuantity.put(juice, 5);
        pyatorochkaProdcutQuantity.put(chocolate, 60);

        Map<Product, Integer> auchanBatch = new HashMap<>();
        auchanBatch.put(chocolate, 50);
        auchanBatch.put(television, 8);

        Map<Product, Integer> pyatorochkaBatch = new HashMap<>();
        pyatorochkaBatch.put(chocolate, 55);

        //costBatches
        Map<Product, Double> auchanProductCost = new HashMap<>();
        auchanProductCost.put(chocolate, 45.99);
        auchanProductCost.put(television, 21000.00);

        Map<Product, Double> pyatorochkaProductCost = new HashMap<>();
        pyatorochkaProductCost.put(juice, 80.00);
        pyatorochkaProductCost.put(chocolate, 50.49);

        //adding entities
        productService.addProduct(chocolate);
        productService.addProduct(juice);
        productService.addProduct(television);
        storeService.addStore(pyatorochka);
        storeService.addStore(auchan);
        storeService.addBatch(auchanProductQuantity, auchan);
        storeService.addBatch(pyatorochkaProdcutQuantity, pyatorochka);

        //update
        storeService.updateCost(auchanProductCost, auchan);
        storeService.updateCost(pyatorochkaProductCost, pyatorochka);

        //searching store with cheapest product
        Store cheapestStore = storeService.findStoreWithCheapestProduct(chocolate);
        System.out.println(cheapestStore);
        System.out.println();

        //what we can buy for some money
        Map<Product, Integer> pyatorochkaMap = storeService.whatCanBuy(500, pyatorochka);
        for (Map.Entry entry : pyatorochkaMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        Map<Product, Integer> auchanMap = storeService.whatCanBuy(500, auchan);
        for (Map.Entry entry : auchanMap.entrySet()) {
            System.out.println(entry.getKey() + " - " + entry.getValue());
        }
        System.out.println();

        //buying a batch of goods
        Double auchanOrder = storeService.makeOrder(auchanBatch, auchan);
        Double pyatorochkaOrder = storeService.makeOrder(pyatorochkaProdcutQuantity, pyatorochka);
        System.out.println("auchanOrder = " + auchanOrder);
        System.out.println("pyatorochkaOrder = " + pyatorochkaOrder);
        System.out.println();

        //searching store with cheapest batch
        cheapestStore = storeService.findStoreWithCheapestBatch(pyatorochkaBatch);
        System.out.println(cheapestStore);
        System.out.println();

        //cleaning
        storeService.removeBatch(auchanProductQuantity, auchan);
        storeService.removeBatch(pyatorochkaProdcutQuantity, pyatorochka);
        productService.removeProduct(chocolate);
        productService.removeProduct(juice);
        productService.removeProduct(television);
        storeService.removeStore(pyatorochka);
        storeService.removeStore(auchan);

        connection.close();
    }
}
