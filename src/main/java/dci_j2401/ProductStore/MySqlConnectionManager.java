package dci_j2401.ProductStore;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlConnectionManager implements DBConnectionManager{

    @Override
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/product_store",
                    "root",
                    "1q2w3e4r"
            );
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}