import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Parser {
    public static ArrayList<Passenger> getPassengersFromCSV() throws IOException {
        var passengerArray = new ArrayList<Passenger>();
        var headerFlag = true;
        List<String[]> r = null;
        try (var reader = new CSVReader(new FileReader("titanicPassengers.csv"))){
            r = reader.readAll();
        } catch (CsvException e) {
            e.printStackTrace();
        }
        for (String[] info : r)
        {
            if (headerFlag){
                headerFlag = false;
                continue;
            }
            if (Objects.equals(info[5], ""))
                info[5] = "-1";
            if (Objects.equals(info[11], ""))
                info[11] = "S";
            passengerArray.add(new Passenger(Integer.parseInt(info[0]), Integer.parseInt(info[1]),
                    Integer.parseInt(info[2]), info[3], info[4], Double.parseDouble(info[5]),
                    Integer.parseInt(info[6]),Integer.parseInt(info[7]), info[8], Double.parseDouble(info[9]),
                    info[10], info[11].charAt(0)));
        }
        return passengerArray;
    }
}
