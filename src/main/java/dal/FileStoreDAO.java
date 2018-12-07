package dal;

import entity.Product;
import entity.Store;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStoreDAO implements StoreDAO {
    File file = new File("src/main/resources/store.csv");

    @Override
    public void add(Store store) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(store.toCSVformat() + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Store> getAll() {
        List<Store> storeList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(",");
                Integer id = Integer.valueOf(row[0]);
                String name = row[1];
                String address = row[2];
                Store store = new Store(id, name, address);

                storeList.add(store);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storeList;
    }

    @Override
    public Store getById(Integer id) {
        Store store = null;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(",");

                if (Integer.valueOf(row[0]).equals(id)) {
                    Integer storeId = Integer.valueOf(row[0]);
                    String name = row[1];
                    String address = row[2];
                    store = new Store(id, name, address);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return store;
    }

    @Override
    public void update(Store store) {
        File temp = new File("src/main/resources/temp.csv");
        try (FileWriter fileWriter = new FileWriter(temp, true);
             Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (Integer.valueOf(line.split(",")[0]).equals(store.getId())) {
                    line = store.toCSVformat();
                }
                fileWriter.write(line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
        temp.renameTo(file);
    }

    @Override
    public void remove(Store store) {
        File temp = new File("src/main/resources/temp.csv");
        try (FileWriter fileWriter = new FileWriter(temp, true);
             Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.contains(store.toCSVformat())) {
                    fileWriter.write(line + '\n');
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        file.delete();
        temp.renameTo(file);
    }
}
