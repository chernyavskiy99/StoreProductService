package entity;

import java.util.Objects;

public class StoreProduct {
    private Integer storeId;
    private Integer productId;
    private Integer quantity;
    private Double cost;

    public StoreProduct(Integer storeId, Integer productId, Integer quantity, Double cost) {
        this.storeId = storeId;
        this.productId = productId;
        this.quantity = quantity;
        this.cost = cost;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public Integer getProductId() {
        return productId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Double getCost() {
        return cost;
    }

    public String toCSVformat() {
        return storeId + "," + productId + "," + quantity + "," + cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StoreProduct that = (StoreProduct) o;
        return Objects.equals(storeId, that.storeId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(storeId, productId);
    }

    @Override
    public String toString() {
        return "StoreProduct{" +
                "storeId=" + storeId +
                ", productId=" + productId +
                ", quantity=" + quantity +
                ", cost=" + cost +
                '}';
    }
}
