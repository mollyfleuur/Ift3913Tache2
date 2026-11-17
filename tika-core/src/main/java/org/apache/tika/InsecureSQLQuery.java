
package org.apache.tika;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InsecureSQLQuery {

    public static ResultSet searchUser(
            Connection connection, String userInput)
            throws SQLException {

        String query =
                "SELECT * FROM users WHERE name = '" + userInput + "'";

        Statement statement = connection.createStatement();
        return statement.executeQuery(query);
    }
}
