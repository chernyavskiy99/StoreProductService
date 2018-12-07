package dal;

import entity.Product;
import org.h2.store.fs.FileUtils;

import java.io.*;
import java.nio.file.Files;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FileProductDAO implements ProductDAO {
    File file = new File("src/main/resources/product.csv");

    @Override
    public void add(Product product) {
        try (FileWriter fileWriter = new FileWriter(file, true)) {
            fileWriter.write(product.toCSVformat() + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getAll() {
        List<Product> productList = new ArrayList<>();

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(",");
                Integer id = Integer.valueOf(row[0]);
                String name = row[1];
                Product product = new Product(id, name);

                productList.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getById(Integer id) {
        Product product = null;

        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String[] row = scanner.nextLine().split(",");

                if (Integer.valueOf(row[0]).equals(id)) {
                    Integer productId = Integer.valueOf(row[0]);
                    String name = row[1];
                    product = new Product(id, name);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public void update(Product product) {
        File temp = new File("src/main/resources/temp.csv");
        try (FileWriter fileWriter = new FileWriter(temp, true);
             Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (Integer.valueOf(line.split(",")[0]).equals(product.getId())) {
                    line = product.toCSVformat();
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
    public void remove(Product product) {
        File temp = new File("src/main/resources/temp.csv");
        writeToFile((line, fw) -> {
            if (!line.contains(product.toCSVformat())) {
                fw.write(line + '\n');
            }
        }, temp);
//        try (FileWriter fileWriter = new FileWriter(temp, true);
//            Scanner scanner = new Scanner(file)) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                if (!line.contains(product.toCSVformat())) {
//                    fileWriter.write(line + '\n');
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        file.delete();
        temp.renameTo(file);
    }

    @FunctionalInterface
    public interface Callable<TArg1, TArg2> {
        void apply(TArg1 arg1, TArg2 arg2) throws IOException;
    }

    private void writeToFile(Callable<String, FileWriter> action, File file) {
        try (FileWriter fileWriter = new FileWriter(file, true);
             Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                action.apply(line, fileWriter);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
