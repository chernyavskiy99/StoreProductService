package dal;

import entity.StoreProduct;
import common.Main;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStoreProductDAO implements StoreProductDAO{
    @Override
    public void add(StoreProduct storeProduct) {
        String sql = "INSERT INTO STORE_PRODUCT VALUES(?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, storeProduct.getStoreId());
            preparedStatement.setInt(2, storeProduct.getProductId());
            preparedStatement.setInt(3, storeProduct.getQuantity());
            preparedStatement.setDouble(4, storeProduct.getCost());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<StoreProduct> getAll() {
        List<StoreProduct> storeProductList = new ArrayList<>();

        String sql = "SELECT * FROM STORE_PRODUCT";

        try(Statement statement = Main.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Integer productId = resultSet.getInt("PRODUCT_ID");
                Integer storeId = resultSet.getInt("STORE_ID");
                Integer quantity = resultSet.getInt("QUANTITY");
                Double cost = resultSet.getDouble("COST");
                StoreProduct storeProduct = new StoreProduct(storeId, productId, quantity, cost);

                storeProductList.add(storeProduct);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProductList;
    }

    @Override
    public StoreProduct getByStoreIdAndProductId(Integer storeId, Integer productId) {
        String sql = "SELECT * FROM STORE_PRODUCT WHERE PRODUCT_ID = ? AND STORE_ID = ?";

        StoreProduct storeProduct = null;

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.setInt(2, storeId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Integer newProductId = resultSet.getInt("PRODUCT_ID");
                Integer newStoreId = resultSet.getInt("STORE_ID");
                Integer quantity = resultSet.getInt("QUANTITY");
                Double cost = resultSet.getDouble("COST");
                storeProduct = new StoreProduct(storeId, productId, quantity, cost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeProduct;
    }

    @Override
    public void update(StoreProduct storeProduct) {
        String sql = "UPDATE STORE_PRODUCT SET QUANTITY = ?, COST = ? WHERE PRODUCT_ID = ? AND STORE_ID = ?";

        try(PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, storeProduct.getQuantity());
            preparedStatement.setDouble(2, storeProduct.getCost());
            preparedStatement.setInt(3, storeProduct.getProductId());
            preparedStatement.setInt(4, storeProduct.getStoreId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(StoreProduct storeProduct) {
        String sql = "DELETE FROM STORE_PRODUCT WHERE PRODUCT_ID = ? AND STORE_ID = ?";

        try(PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, storeProduct.getProductId());
            preparedStatement.setInt(2, storeProduct.getStoreId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
