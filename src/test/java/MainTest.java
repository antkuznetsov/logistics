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
        /*
        System.out.println("Заполняем таблицы тестовыми данными...");

        try {

            //Таблица «Организация-заказчик»
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO public.organization (id, name) VALUES (1, 'ООО Бестранк'); " +
                            "INSERT INTO public.organization (id, name) VALUES (2, 'ООО Ай-Теко');"
            );

            statement.executeQuery();

            //Таблица «Товар»
            statement = connection.prepareStatement(
                    "INSERT INTO public.product (id, name) VALUES (1, 'iPhone'); " +
                            "INSERT INTO public.product (id, name) VALUES (2, 'Samsung'); " +
                            "INSERT INTO public.product (id, name) VALUES (3, 'Nokia'); " +
                            "INSERT INTO public.product (id, name) VALUES (4, 'Xiaomi'); " +
                            "INSERT INTO public.product (id, name) VALUES (5, 'Motorola');"
            );

            statement.executeQuery();

            //Таблица «Место доставки»
            statement = connection.prepareStatement(
                    "INSERT INTO public.location (id, city, address) VALUES (3, 'Санкт-Петербург', 'Лиговский 54'); " +
                            "INSERT INTO public.location (id, city, address) VALUES (1, 'Санкт-Петербург', 'Невский 151'); " +
                            "INSERT INTO public.location (id, city, address) VALUES (2, 'Иннополис', 'Спортивная 116');"
            );

            statement.executeQuery();

            //Таблица «Договор»
            statement = connection.prepareStatement(
                    "INSERT INTO public.contract (id, amount, details, organization_id) VALUES (1, 50000, 'Тест', 1); " +
                            "INSERT INTO public.contract (id, amount, details, organization_id) VALUES (2, 10000, 'Тест 2', 2); " +
                            "INSERT INTO public.contract (id, amount, details, organization_id) VALUES (3, 500, 'Тест 3', 1);"
            );

            statement.executeQuery();

            //Таблица «Платеж по договору»
            statement = connection.prepareStatement(
                    "INSERT INTO public.payment (id, type, date, amount, contract_id) VALUES (1, '', '', 25000, 1); " +
                            "INSERT INTO public.payment (id, type, date, amount, contract_id) VALUES (3, '', '', 1000, 2); " +
                            "INSERT INTO public.payment (id, type, date, amount, contract_id) VALUES (4, '', '', 2700, 1); " +
                            "INSERT INTO public.payment (id, type, date, amount, contract_id) VALUES (5, '', '', 2300, 2); " +
                            "INSERT INTO public.payment (id, type, date, amount, contract_id) VALUES (2, '', '', 500, 3);"
            );

            statement.executeQuery();

            //Таблица «Доставка»
            statement = connection.prepareStatement(
                    "INSERT INTO public.shipment (id, s_date_expected, s_date_actual, location_id, r_date_expected, r_date_actual, contract_id) VALUES (4, null, '2018-05-01', 3, null, '2018-05-17', 2); " +
                            "INSERT INTO public.shipment (id, s_date_expected, s_date_actual, location_id, r_date_expected, r_date_actual, contract_id) VALUES (5, null, '2018-05-01', 2, null, '2018-05-02', 2); " +
                            "INSERT INTO public.shipment (id, s_date_expected, s_date_actual, location_id, r_date_expected, r_date_actual, contract_id) VALUES (1, null, '2018-05-14', 1, null, '2018-05-17', 1); " +
                            "INSERT INTO public.shipment (id, s_date_expected, s_date_actual, location_id, r_date_expected, r_date_actual, contract_id) VALUES (2, null, '2018-04-25', 2, null, '2018-04-30', 1); " +
                            "INSERT INTO public.shipment (id, s_date_expected, s_date_actual, location_id, r_date_expected, r_date_actual, contract_id) VALUES (3, null, '2018-05-01', 1, null, '2018-05-03', 1); " +
                            "INSERT INTO public.shipment (id, s_date_expected, s_date_actual, location_id, r_date_expected, r_date_actual, contract_id) VALUES (6, null, null, 1, null, null, 1);"
            );

            statement.executeQuery();

            //Таблица «Доставка-Товар»
            statement = connection.prepareStatement(
                    "INSERT INTO public.shipment__product (shipment_id, product_id, count) VALUES (1, 1, 2); " +
                            "INSERT INTO public.shipment__product (shipment_id, product_id, count) VALUES (1, 2, 1); " +
                            "INSERT INTO public.shipment__product (shipment_id, product_id, count) VALUES (1, 3, 4); " +
                            "INSERT INTO public.shipment__product (shipment_id, product_id, count) VALUES (2, 1, 4); " +
                            "INSERT INTO public.shipment__product (shipment_id, product_id, count) VALUES (3, 1, 1);"
            );

            statement.executeQuery();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("Заполнение окончено!");*/
    }

    @Test
    public void debtors() {
        System.out.println("**********************************************");
        System.out.println("ДОЛЖНИКИ");

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT org.id, org.name, SUM(amount - (SELECT SUM(amount) as amount FROM payment WHERE contract_id = contract.id GROUP BY contract_id)) AS debt " +
                            "FROM contract " +
                            "INNER JOIN organization org " +
                            "    ON contract.organization_id = org.id " +
                            "WHERE (amount - (SELECT SUM(amount) as amount FROM payment WHERE contract_id = contract.id GROUP BY contract_id) > 0) AND " +
                            "      ((SELECT COUNT(*) FROM shipment WHERE contract_id = contract.id AND r_date_actual IS NULL) = 0) " +
                            "GROUP BY org.id " +
                            "ORDER BY debt DESC " +
                            "LIMIT 2"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getString(2))
                        .append(" (долг: ").append(resultSet.getInt(3)).append(")");

                System.out.println(result.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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

                result.append(resultSet.getString(2))
                        .append(" (договоров на сумму: ").append(resultSet.getInt(3)).append(")");

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

                result.append(resultSet.getString(2))
                        .append(" (платежей на сумму: ").append(resultSet.getInt(3)).append(")");

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

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT name, SUM(count) AS count " +
                            "FROM shipment__product " +
                            "INNER JOIN shipment ship on shipment__product.shipment_id = ship.id " +
                            "INNER JOIN product prod on shipment__product.product_id = prod.id " +
                            "WHERE DATE_PART('MONTH', r_date_actual) = DATE_PART('MONTH', now()) " +
                            "GROUP BY name " +
                            "ORDER BY count DESC " +
                            "LIMIT 2"
            );

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getString(1))
                        .append(" (продано штук: ").append(resultSet.getInt(2)).append(")");

                System.out.println(result.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            ); //todo: перенести города в отдельную таблицу

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

        try {
            System.out.println("по среднему ожиданию");

            PreparedStatement statement = connection.prepareStatement(
                    "SELECT city, AVG((r_date_actual - s_date_actual)) AS delay " +
                            "FROM shipment " +
                            "INNER JOIN location loc " +
                            "  ON shipment.location_id = loc.id " +
                            "GROUP BY city " +
                            "LIMIT 2"
            ); //todo: перенести города в отдельную таблицу

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getString(1))
                        .append(" (дней ожидания: ").append(resultSet.getInt(2)).append(")");

                System.out.println(result.toString());
            }

            System.out.println("по суммарному ожиданию");

            statement = connection.prepareStatement(
                    "SELECT city, SUM ((r_date_actual - s_date_actual)) AS delay " +
                            "FROM shipment " +
                            "INNER JOIN location loc " +
                            "  ON shipment.location_id = loc.id " +
                            "GROUP BY city " +
                            "LIMIT 2"
            ); //todo: перенести города в отдельную таблицу

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StringBuilder result = new StringBuilder();

                result.append(resultSet.getString(1))
                        .append(" (дней ожидания: ").append(resultSet.getInt(2)).append(")");

                System.out.println(result.toString());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("**********************************************\n");
    }
}