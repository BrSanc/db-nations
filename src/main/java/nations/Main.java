package nations;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
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
    private final static String SQL_NATION_LANGUAGUE_STATS = "select \n" +
            "c.country_id as \"IdNation\",\n" +
            "c.name as  \"NameNation\",\n" +
            "l.`language` as \"LanguageNation\",\n" +
            "CS.`year` as \"Year\",\n" +
            "cs.population as \"PopulationNation\",\n" +
            "cs.gdp as \"GDP_Nation\"\n" +
            "from countries c \n" +
            "join country_languages cl on c.country_id = cl.country_id \n" +
            "join languages l on cl.language_id = l.language_id \n" +
            "join country_stats cs on c.country_id = cs.country_id \n" +
            "where c.country_id  = ? and cs.`year` = 2018";
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

            System.out.print("Choose a country id: ");
            int idChoised = Integer.parseInt(scan.nextLine());

            try(PreparedStatement ps = conn.prepareStatement(SQL_NATION_LANGUAGUE_STATS)){
                ps.setInt( 1,idChoised);
                try(ResultSet rs = ps.executeQuery()) {
                    String nationName = null;
                    int nationId = 0;
                    String languages = null;
                    int year = 0;
                    int population = 0;
                    BigDecimal gdp = null;
                    ArrayList<String> languagesList = new ArrayList<>();

                    while (rs.next()) {
                        nationId = rs.getInt("IdNation");
                        nationName = rs.getString("NameNation");
                        languages = rs.getString("LanguageNation");
                        year = rs.getInt("Year");
                        population = rs.getInt("PopulationNation");
                        gdp = rs.getBigDecimal("GDP_Nation");

                        languagesList.add(languages);
                    }
                    System.out.println();
                    System.out.println("Details for country: " + nationName);

                    System.out.print("Languages: ");
                    for (int i = 0; i < languagesList.size(); i++) {
                        System.out.print(languagesList.get(i) + ", ");
                    }
                    System.out.println();
                    System.out.println("Most recent stats");
                    System.out.println("Year: " + year);
                    System.out.println("Population: "+ population);
                    System.out.println("GPD: "+ gdp);

                    //System.out.println(nationName + " | " + nationId + " | " + languages + " | " + year + " | " + population + " | " + gdp);

                }
            }




        }catch(SQLException exception){
            System.out.println("An error occurred");
        }


    }
}
