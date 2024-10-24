package dci_j2401.ProductStore;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {

    private DBConnectionManager dbConnectionManager;

    public ProductDAOImpl(DBConnectionManager dbConnectionManager) {
        this.dbConnectionManager = dbConnectionManager;
    }

    @Override
    public void addProduct(Product product) throws DuplicateProductException {
        String sql = "INSERT INTO products " +
                "(name, description, stock, price, created_at, modified_at) " +
                "VALUES (?, ?, ?, ?, NOW(), NOW())";

        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getStock());
            statement.setDouble(4, product.getPrice());

            statement.executeUpdate();

        } catch (SQLIntegrityConstraintViolationException e) {
            // Throw the custom exception when a duplicate product is detected
            throw new DuplicateProductException("A product with the name '" + product.getName() + "' already exists.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Product> getAllProducts() {
        String sql = "SELECT * FROM products";
        List<Product> products = new ArrayList<>();

        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");
                Timestamp modifiedAtTimestamp = resultSet.getTimestamp("modified_at");

                Product product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price"),
                        createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null,
                        modifiedAtTimestamp != null ? modifiedAtTimestamp.toLocalDateTime() : null
                );

                products.add(product);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public Product getProductById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        Product product = null;

        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Timestamp createdAtTimestamp = resultSet.getTimestamp("created_at");
                Timestamp modifiedAtTimestamp = resultSet.getTimestamp("modified_at");

                product = new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getString("description"),
                        resultSet.getInt("stock"),
                        resultSet.getDouble("price"),
                        createdAtTimestamp != null ? createdAtTimestamp.toLocalDateTime() : null,
                        modifiedAtTimestamp != null ? modifiedAtTimestamp.toLocalDateTime() : null
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;  // Return the product or null if not found
    }


    @Override
    public void deleteProduct(Long id) {

        String sql = "DELETE FROM products WHERE id = ?";

        try(Connection connection = dbConnectionManager.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = ?, description = ?, stock = ?, price = ?, modified_at = NOW() WHERE id = ?";

        try (Connection connection = dbConnectionManager.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            // Set the parameters for the update query
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setInt(3, product.getStock());
            statement.setDouble(4, product.getPrice());
            statement.setLong(5, product.getId());

            // Execute the update
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                throw new SQLException("Updating product failed, no rows affected.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
