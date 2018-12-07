package dal;

import entity.Store;
import entity.StoreProduct;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileStoreProductDAO implements StoreProductDAO {
    File file = new File("src/main/resources/storeProduct.csv");

    @Override
    public void add(StoreProduct storeProduct) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(storeProduct.toCSVformat() + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<StoreProduct> getAll() {
        List<StoreProduct> storeProductList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(",");
                Integer storeId = Integer.valueOf(row[0]);
                Integer productId = Integer.valueOf(row[1]);
                Integer quantity = Integer.valueOf(row[2]);
                Double cost = Double.valueOf(row[3]);
                StoreProduct storeProduct = new StoreProduct(storeId, productId, quantity, cost);

                storeProductList.add(storeProduct);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return storeProductList;
    }

    @Override
    public StoreProduct getByStoreIdAndProductId(Integer storeId, Integer productId) {
        StoreProduct storeProduct = null;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(",");

                if (Integer.valueOf(row[1]).equals(productId) && Integer.valueOf(row[0]).equals(storeId)) {
                    Integer newStoreId = Integer.valueOf(row[0]);
                    Integer newProductId = Integer.valueOf(row[1]);
                    Integer quantity = Integer.valueOf(row[2]);
                    Double cost = Double.valueOf(row[3]);
                    storeProduct = new StoreProduct(storeId, productId, quantity, cost);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return storeProduct;
    }

    @Override
    public void update(StoreProduct storeProduct) {
        File temp = new File("src/main/resources/temp.csv");
        try (FileWriter fileWriter = new FileWriter(temp, true);
             Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (Integer.valueOf(line.split(",")[0]).equals(storeProduct.getStoreId()) &&
                        Integer.valueOf(line.split(",")[1]).equals(storeProduct.getProductId())) {
                    line = storeProduct.toCSVformat();
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
    public void remove(StoreProduct storeProduct) {
        File temp = new File("src/main/resources/temp.csv");
        try (FileWriter fileWriter = new FileWriter(temp, true);
             Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.contains(storeProduct.toCSVformat())) {
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
