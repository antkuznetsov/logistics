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
            System.out.println("�������� � ����������� � ��!");
        }
    }

    @Test
    public void debtors() {
        System.out.println("**********************************************");
        System.out.println("��������");

        //code here

        System.out.println("**********************************************\n");
    }

    @Test
    public void bestCustomers() {
        System.out.println("**********************************************");
        System.out.println("������ �������");
        try {
            String query = "SELECT * FROM organization ORDER BY id";

            PreparedStatement statement = connection.prepareStatement(query);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getInt(1));
                result.append(resultSet.getString(2));

                System.out.println(result.toString());
            }
            System.out.println("**********************************************\n");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void popularProduct() {
        System.out.println("**********************************************");
        System.out.println("���������� �������");

        //code here

        System.out.println("**********************************************\n");
    }

    @Test
    public void popularCity() {
        System.out.println("**********************************************");
        System.out.println("���������� �����");

        //code here

        System.out.println("**********************************************\n");
    }

    @Test
    public void longDirections() {
        System.out.println("**********************************************");
        System.out.println("������ �����������");

        //code here

        System.out.println("**********************************************\n");
    }
}