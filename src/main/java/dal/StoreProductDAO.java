package dal;

import entity.StoreProduct;

import java.util.List;

public interface StoreProductDAO {

    //create
    void add(StoreProduct storeProduct);

    //read
    List<StoreProduct> getAll();

    StoreProduct getByStoreIdAndProductId(Integer storeId, Integer productId);

    //update
    void update(StoreProduct storeProduct);

    //delete
    void remove(StoreProduct storeProduct);
}
