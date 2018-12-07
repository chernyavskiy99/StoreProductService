import dal.*;
import entity.Product;
import entity.Store;
import entity.StoreProduct;

import java.util.List;

public class FileDAO {
    public static void main(String[] args) {
        FileProductDAO productDAO = new FileProductDAO();
        FileStoreDAO storeDAO = new FileStoreDAO();
        FileStoreProductDAO storeProductDAO = new FileStoreProductDAO();

        //products
        Product chocolate = new Product(1, "Alyonka");
        Product juice = new Product(2, "Dobriy");
        Product television = new Product(3, "PHILIPS");

        //stores
        Store pyatorochka = new Store(1, "Pyatorochka", "SPb Stroiteley street 25");
        Store auchan = new Store(2, "Auchan", "Moscow Stroiteley street 25");

        //id
        Integer chocolateId = chocolate.getId();
        Integer pyatorochkaId = pyatorochka.getId();
        Integer televisionId = television.getId();
        Integer juiceId = juice.getId();
        Integer auchanId = auchan.getId();

        //storeProducts
        StoreProduct chocolateInPyatorochka = new StoreProduct(chocolateId, pyatorochkaId, 60, 45.99);
        StoreProduct televisionInAuchan = new StoreProduct(televisionId, auchanId, 3, 21000.00);
        StoreProduct juiceInPyatorochka = new StoreProduct(juiceId, pyatorochkaId, 5, 80.00);
        StoreProduct chocolateInAuchan = new StoreProduct(chocolateId, auchanId, 50, 50.49);

        //start
        productDAO.add(chocolate);
        productDAO.add(juice);
        productDAO.add(television);
        storeDAO.add(pyatorochka);
        storeDAO.add(auchan);
        storeProductDAO.add(chocolateInAuchan);
        storeProductDAO.add(chocolateInPyatorochka);
        storeProductDAO.add(juiceInPyatorochka);
        storeProductDAO.add(televisionInAuchan);

        //read some rows
        System.out.println(productDAO.getById(4));
        System.out.println(storeDAO.getById(1));
        System.out.println(storeProductDAO.getByStoreIdAndProductId(1, 1));
        System.out.println();

        //read all rows
        List<Store> storeList = storeDAO.getAll();
        List<Product> productList = productDAO.getAll();
        List<StoreProduct> storeProductList = storeProductDAO.getAll();

        for (Store s : storeList) {
            System.out.println(s);
        }
        System.out.println();

        for (Product s : productList) {
            System.out.println(s);
        }
        System.out.println();

        for (StoreProduct s : storeProductList) {
            System.out.println(s);
        }
        System.out.println();

        //update
        chocolateInPyatorochka = new StoreProduct(chocolateId, pyatorochkaId, 60, 60.99);
        storeProductDAO.update(chocolateInPyatorochka);

        auchan = new Store(2, "Auchan", "Spb Stroiteley street 25");
        storeDAO.update(auchan);

        pyatorochka = new Store(1, "Pyatorochka", "Moscow Stroiteley street 25");
        storeDAO.update(pyatorochka);

        juice = new Product(2, "Lyubimiy");
        productDAO.update(juice);

        //cleaning
        storeProductDAO.remove(juiceInPyatorochka);
        storeProductDAO.remove(chocolateInAuchan);
        storeProductDAO.remove(chocolateInPyatorochka);
        storeProductDAO.remove(televisionInAuchan);
        productDAO.remove(chocolate);
        productDAO.remove(juice);
        productDAO.remove(television);
        storeDAO.remove(auchan);
        storeDAO.remove(pyatorochka);
    }
}

