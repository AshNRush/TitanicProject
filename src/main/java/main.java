import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class main {
    public static void main(String[] args) {
        var fillerFlag = false; // Смените флаг на true, чтобы заполнить пустую БД
        try {
            var dbHandler = DBHandler.getInstance();
            var taskHandler = new TaskHandler();
            if (fillerFlag)
                dbHandler.fillDB(Parser.getPassengersFromCSV());
            taskHandler.createBarChart(dbHandler);
            taskHandler.printDelta(dbHandler);
            taskHandler.printAllTickets(dbHandler);
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }
    }
}