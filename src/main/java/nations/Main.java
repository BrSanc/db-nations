package nations;

import java.sql.*;
import java.util.Scanner;

public class Main {

    private final static String DB_URl ="jdbc:mysql://localhost:3306/dump_nations";
    private final static String DB_USER = "root";
    private final static String DB_PASSWORD = "root";
    private final static String SQL_NATIONS = "select \n" +
            "c.name as \"NomeNazioni\", \n" +
            "c.country_id as \"IdNazioni\",\n" +
            "r.name as \"NomeRegione\",\n" +
            "c2.name as \"NomeContinente\"\n" +
            "from continents c2 \n" +
            "join regions r on r.continent_id = c2.continent_id\n" +
            "join countries c on c.region_id = r.region_id\n" +
            "order by c.name;";
    private final static String SQL_NATION_SEARCH = "select \n" +
            "c.name as \"NomeNazioni\", \n" +
            "c.country_id as \"IdNazioni\",\n" +
            "r.name as \"NomeRegione\",\n" +
            "c2.name as \"NomeContinente\"\n" +
            "from continents c2 \n" +
            "join regions r on r.continent_id = c2.continent_id\n" +
            "join countries c on c.region_id = r.region_id\n" +
            "where c.name like ? \n" +
            "order by c.name;";
    private final static String SQL_TEST = "select * from countries c where name like ?;";
    public static void main(String[] args) {



        try(Connection conn = DriverManager.getConnection(DB_URl,DB_USER,DB_PASSWORD)){

            /*
            try(PreparedStatement ps = conn.prepareStatement(SQL_NATIONS)){
                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()){
                        String nationName = rs.getString("NomeNazioni");
                        int nationId = rs.getInt("IdNazioni");
                        String regionsName = rs.getString("NomeRegione");
                        String continentsName = rs.getString("NomeContinente");

                        System.out.println(nationName + " | " + nationId + " | " + regionsName + " | " + continentsName) ;
                    }
                }
            }
            */

            Scanner scan = new Scanner(System.in);
            System.out.println("Search: ");
            String search = scan.nextLine();

            try(PreparedStatement ps = conn.prepareStatement(SQL_NATION_SEARCH)){
                ps.setString( 1,"%"+search+"%");
                try(ResultSet rs = ps.executeQuery()){
                    while (rs.next()) {
                        String nationName = rs.getString("NomeNazioni");
                        int nationId = rs.getInt("IdNazioni");
                        String regionsName = rs.getString("NomeRegione");
                        String continentsName = rs.getString("NomeContinente");

                        System.out.println(nationName + " | " + nationId + " | " + regionsName + " | " + continentsName);
                    }
                }
            }



        }catch(SQLException exception){
            System.out.println("An error occurred");
        }


    }
}
