package dal;

import common.Main;
import entity.Store;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseStoreDAO implements StoreDAO {
    @Override
    public void add(Store store) {
        String sql = "INSERT INTO STORE VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, store.getId());
            preparedStatement.setString(2, store.getAddress());
            preparedStatement.setString(3, store.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Store> getAll() {
        List<Store> storeList = new ArrayList<>();

        String sql = "SELECT * FROM STORE";

        try (Statement statement = Main.connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Integer id = resultSet.getInt("ID");
                String address = resultSet.getString("ADDRESS");
                String name = resultSet.getString("NAME");
                Store store = new Store(id, name, address);

                storeList.add(store);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return storeList;
    }

    @Override
    public Store getById(Integer id) {
        String sql = "SELECT * FROM STORE WHERE ID = ?";

        Store store = null;
        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Integer storeId = resultSet.getInt("ID");
                String address = resultSet.getString("ADDRESS");
                String name = resultSet.getString("NAME");
                store = new Store(id, name, address);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return store;
    }

    @Override
    public void update(Store store) {
        String sql = "UPDATE STORE SET ADDRESS = ?, NAME = ? WHERE ID = ?";

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setString(1, store.getAddress());
            preparedStatement.setString(2, store.getName());
            preparedStatement.setInt(3, store.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void remove(Store store) {
        String sql = "DELETE FROM STORE WHERE ID = ?";

        try (PreparedStatement preparedStatement = Main.connection.prepareStatement(sql)) {
            preparedStatement.setInt(1, store.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
