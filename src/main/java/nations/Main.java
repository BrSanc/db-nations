package nations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Main {

    private final static String DB_URl ="jdbc:mysql://localhost:3306/dump_nations";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "root";
    private final static String SQL_TABLE = "select \n" +
            "c.name as \"Nome Nazioni\", \n" +
            "c.country_id as \"Id Nazioni\",\n" +
            "r.name as \"Nome Regione\",\n" +
            "c2.name as \"Nome Continente\"\n" +
            "from continents c2 \n" +
            "join regions r on r.continent_id = c2.continent_id\n" +
            "join countries c on c.region_id = r.region_id\n" +
            "order by c.name;";
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        try(Connection conn = DriverManager.getConnection(DB_URl,DB_USER,DB_PASSWORD)){

            try(PreparedStatement ps = conn.prepareStatement(SQL_TABLE)){
                try(ResultSet rs = ps.executeQuery()){

                }
            }

        }


    }
}
