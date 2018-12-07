package dal;

import common.Main;
import entity.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProductDAO implements ProductDAO {
    @Override
    public void add(Product product){
        String sql = "INSERT INTO PRODUCT VALUES (?, ?)";

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();

        String sql = "SELECT * FROM PRODUCT";

        try (Statement statement = Main.connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                Product product = new Product(id, name);

                productList.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getById(Integer id) {
        String sql = "SELECT * FROM PRODUCT WHERE ID = ?";

        Product product = null;

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer productId = resultSet.getInt("ID");
                String name = resultSet.getString("NAME");
                product = new Product(id, name);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void update(Product product){
        String sql = "UPDATE Product SET NAME = ? WHERE ID = ?";

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Product product){
        String sql = "DELETE FROM PRODUCT WHERE ID = ?";

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, product.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

