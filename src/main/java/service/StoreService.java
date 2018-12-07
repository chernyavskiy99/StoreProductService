package service;

import common.Util;
import dal.*;
import entity.Product;
import entity.Store;
import entity.StoreProduct;

import java.util.*;
import java.util.stream.Collectors;

public class StoreService {
    ProductDAO productDAO;
    StoreDAO storeDAO;
    StoreProductDAO storeProductDAO;

    public StoreService() {
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

    public void addStore(Store store) {
        List<Store> storeList = storeDAO.getAll();
        if (!storeList.contains(store)) {
            storeDAO.add(store);
        }
    }

    public void removeStore(Store store) {
        storeDAO.remove(store);
    }

    public void addBatch(Map<Product, Integer> productQuantityMap, Store store) {
        List<StoreProduct> storeProductList = storeProductDAO.getAll();
        for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
            Integer storeId = store.getId();
            Integer productId = entry.getKey().getId();
            Integer quantity = entry.getValue();
            Double cost = 0.0;
            StoreProduct storeProduct = new StoreProduct(storeId, productId, quantity, cost);

            if (!storeProductList.contains(storeProduct)) {
                storeProductDAO.add(storeProduct);
            }
        }
    }

    public void updateCost(Map<Product, Double> productCostMap, Store store) {
        for (Map.Entry<Product, Double> entry : productCostMap.entrySet()) {
            StoreProduct storeProduct = storeProductDAO.getByStoreIdAndProductId(store.getId(), entry.getKey().getId());
            Integer storeId = store.getId();
            Integer productId = entry.getKey().getId();
            Integer quantity = storeProduct.getQuantity();
            Double cost = entry.getValue();
            storeProduct = new StoreProduct(storeId, productId, quantity, cost);

            storeProductDAO.update(storeProduct);
        }
    }

    public void removeBatch(Map<Product, Integer> productQuantityMap, Store store) {
        for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
            StoreProduct storeProduct = storeProductDAO.getByStoreIdAndProductId(store.getId(), entry.getKey().getId());
            storeProductDAO.remove(storeProduct);
        }
    }

    public Map<Product, Integer> whatCanBuy(Integer money, Store store) {
        List<StoreProduct> storeProductList = storeProductDAO.getAll();
        Map<Product, Integer> productQuantityMap = new HashMap<>();
        storeProductList.stream()
                .filter((storeProduct) -> storeProduct.getStoreId().equals(store.getId())
                        && storeProduct.getCost() <= money)
                .forEach((storeProduct) -> productQuantityMap.put(productDAO.getById(storeProduct.getProductId()),
                        Math.min((int) (money / storeProduct.getCost()), storeProduct.getQuantity())));
        return productQuantityMap;
    }

    public Store findStoreWithCheapestProduct(Product product) {
        List<StoreProduct> storeProductList = storeProductDAO.getAll();
        return storeProductList.stream()
                .filter((storeProduct) -> storeProduct.getProductId().equals(product.getId()))
                .min(Comparator.comparing(StoreProduct::getCost))
                .map(storeProduct -> storeDAO.getById(storeProduct.getStoreId())).orElse(null);
    }

    public Double makeOrder(Map<Product, Integer> productQuantityMap, Store store) {
        List<StoreProduct> storeProductList = storeProductDAO.getAll();
        double sum = 0;
        int productQuantity = 0;
        for (StoreProduct storeProduct : storeProductList) {
            Product product = productDAO.getById(storeProduct.getProductId());
            if (storeProduct.getStoreId().equals(store.getId()) && productQuantityMap.containsKey(product)) {
                if (storeProduct.getQuantity() >= productQuantityMap.get(product)) {
                    sum += storeProduct.getCost() * productQuantityMap.get(product);
                    productQuantity++;
                } else {
                    return null;
                }
            }
        }
        if (sum != 0 && productQuantity == productQuantityMap.size()) {
            return sum;
        }
        return null;
    }

    public Store findStoreWithCheapestBatch(Map<Product, Integer> productQuantityMap) {
        List<Store> storeList = storeDAO.getAll();
        Map<Store, Double> batchCostMap = new HashMap<>();
        for (Store store : storeList) {
            Double sum = makeOrder(productQuantityMap, store);
            if (sum != null) {
                batchCostMap.put(store, sum);
            }
        }

        if (!batchCostMap.values().isEmpty()) {
            double value = Collections.min(batchCostMap.values());
            storeList = batchCostMap.entrySet()
                    .stream()
                    .filter(entry -> Objects.equals(entry.getValue(), value))
                    .map(Map.Entry::getKey)
                    .collect(Collectors.toList());
            if (!storeList.isEmpty()) {
                return storeList.get(0);
            }
        }
        return null;
    }
}
