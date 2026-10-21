
package model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import model.ModelException;

public class MySQLConnectionFactory {

    private static final String JDBC_DRIVER_NAME =
            "com.mysql.cj.jdbc.Driver";

    private static final String DATABASE_URL =
            "jdbc:mysql://meu-mysql-alunos-7c85.f.aivencloud.com:10001/facebook?sslMode=REQUIRED";

    static final String USER = System.getenv("DB_USER");
    static final String PASSWORD = System.getenv("DB_PASSWORD");

    public static Connection getConnection() throws ModelException {
        try {
            Class.forName(JDBC_DRIVER_NAME);

            if (USER == null || PASSWORD == null) {
                throw new ModelException(
                    "Configure as variáveis de ambiente DB_USER e DB_PASSWORD"
                );
            }

            return DriverManager.getConnection(
                    DATABASE_URL, USER, PASSWORD);

        } catch (ClassNotFoundException e) {
            throw new ModelException("Driver não encontrado", e);
        } catch (SQLException e) {
            throw new ModelException("Falha na conexão com o BD", e);
        }
    }
}
