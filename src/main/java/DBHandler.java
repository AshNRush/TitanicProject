import org.sqlite.JDBC;

import java.sql.*;
import java.util.*;

public class DBHandler {

    private static final String CON_STR = "jdbc:sqlite:titanic.s3db";
    private static DBHandler instance = null;

    public static synchronized DBHandler getInstance() throws SQLException {
        if (instance == null)
            instance = new DBHandler();
        return instance;
    }

    private Connection connection;

    private DBHandler() throws SQLException {
        DriverManager.registerDriver(new JDBC());
        this.connection = DriverManager.getConnection(CON_STR);
    }

    public void addPassenger(Passenger passenger) {
        try (var statement = this.connection.prepareStatement(
                "INSERT INTO Passengers(id, survived, class, name, sex, age, sibSp, parch, ticket, fare, cabin, embarked) " +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            statement.setObject(1, passenger.id);
            statement.setObject(2, passenger.survived);
            statement.setObject(3, passenger.pClass);
            statement.setObject(4, passenger.name);
            statement.setObject(5, passenger.sex);
            statement.setObject(6, passenger.age == -1 ? null : passenger.age);
            statement.setObject(7, passenger.sibSp);
            statement.setObject(8, passenger.parch);
            statement.setObject(9, passenger.ticket);
            statement.setObject(10, passenger.fare);
            statement.setObject(11, passenger.cabin);
            statement.setObject(12, passenger.embarked);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void fillDB(List<Passenger> passengersList) {
        try {
            DBHandler dbHandler = DBHandler.getInstance();
            for (var passenger : passengersList) {
                dbHandler.addPassenger(passenger);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public double findDelta(){
        try {
            var queryForMax = "SELECT fare " +
                    "FROM Passengers " +
                    "WHERE age BETWEEN 15 AND 30 AND sex LIKE '%female%' " +
                    "ORDER BY fare DESC " +
                    "LIMIT 1 ";
            var queryForMin = "SELECT fare " +
                    "FROM Passengers " +
                    "WHERE age BETWEEN 15 AND 30 AND sex LIKE '%female%' " +
                    "ORDER BY fare ASC " +
                    "LIMIT 1";
            var statement = connection.createStatement();
            var max = statement.executeQuery(queryForMax).getDouble("fare");
            var min = statement.executeQuery(queryForMin).getDouble("fare");
            return max - min;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public List<String> findTicketsList() throws SQLException {
        var ticketsList = new ArrayList<String>();
        var query = "SELECT ticket " +
                "FROM Passengers " +
                "WHERE age BETWEEN 45 AND 60 AND sex = 'male' " +
                "OR age BETWEEN 20 AND 25 AND sex = 'female'";
        var statement = connection.createStatement();
        var resultSet = statement.executeQuery(query);
        while (resultSet.next())
            ticketsList.add(resultSet.getString("ticket"));
        return ticketsList;
    }

    public double getAverageFare(String sex, String embarked) throws SQLException {
        var sum = 0d;
        var count = 0;
        var query = "SELECT fare " +
                "FROM Passengers " +
                "WHERE sex = ? AND embarked = ? " +
                "ORDER BY fare ASC ";
        var statement = connection.prepareStatement(query);
        statement.setString(1, sex);
        statement.setString(2, embarked);
        var resultSet = statement.executeQuery();
        while (resultSet.next()){
            sum += resultSet.getDouble("fare");
            count += 1;
        }
        return sum / count;
    }

}