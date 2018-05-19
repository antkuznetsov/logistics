import org.junit.*;
import java.sql.*;

public class MainTest {

    private static final String DBNAME = "logistics";
    private static final String DBLOGIN = "postgres";
    private static final String DBPASSWORD = "656450";

    private static Connection connection;

    @BeforeClass
    public static void initial() {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost/" + DBNAME, DBLOGIN, DBPASSWORD);
        } catch (SQLException e) {
            System.out.println("Проблема с соединением с БД!");
        }
    }

    @Test
    public void debtors() {
        System.out.println("**********************************************");
        System.out.println("ДОЛЖНИКИ");

        //code here

        System.out.println("**********************************************\n");
    }

    @Test
    public void bestCustomers() {
        System.out.println("**********************************************");
        System.out.println("ЛУЧШИЕ КЛИЕНТЫ");
        try {
            System.out.println("по заключенным договорам");

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT org.id, name, SUM(amount) AS amount " +
                            "FROM contract " +
                            "INNER JOIN organization org " +
                            "   ON contract.organization_id = org.id " +
                            "GROUP BY org.id " +
                            "ORDER BY amount DESC " +
                            "LIMIT 2"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getInt(1))
                        .append(" | ")
                        .append(resultSet.getString(2)).append(" | ")
                        .append(resultSet.getInt(3));

                System.out.println(result.toString());
            }

            System.out.println("по фактическим платежам");

            statement = connection.prepareStatement(
                    "SELECT org.id, org.name, SUM(payment.amount) as amount " +
                            "FROM payment " +
                            "INNER JOIN contract cont " +
                            "    ON payment.contract_id = cont.id " +
                            "INNER JOIN  organization org " +
                            "    ON cont.organization_id = org.id " +
                            "GROUP BY org.id " +
                            "ORDER BY amount DESC " +
                            "LIMIT 2"
            );

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getInt(1))
                        .append(" | ")
                        .append(resultSet.getString(2)).append(" | ")
                        .append(resultSet.getInt(3));

                System.out.println(result.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("**********************************************\n");
    }

    @Test
    public void popularProduct() {
        System.out.println("**********************************************");
        System.out.println("ПОПУЛЯРНЫЙ ПРОДУКТ");

        //code here

        System.out.println("**********************************************\n");
    }

    @Test
    public void popularCity() {
        System.out.println("**********************************************");
        System.out.println("ПОПУЛЯРНЫЙ ГОРОД");

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT city, COUNT(city) AS count " +
                            "FROM shipment " +
                            "INNER JOIN location loc " +
                            "    ON shipment.location_id = loc.id " +
                            "GROUP BY city " +
                            "ORDER BY count DESC " +
                            "LIMIT 1"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getString(1))
                        .append(" (доставок: ").append(resultSet.getInt(2)).append(")");

                System.out.println(result.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("**********************************************\n");
    }

    @Test
    public void longDirections() {
        System.out.println("**********************************************");
        System.out.println("ДОЛГИЕ НАПРАВЛЕНИЯ");

        //code here

        System.out.println("**********************************************\n");
    }
}