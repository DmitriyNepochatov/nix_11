package ua.com.alevel.hw2.dao.invoices;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.model.Plane;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.fighter.TypeOfFighter;
import ua.com.alevel.hw2.model.invoice.Invoice;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Singleton
public final class InvoiceDatabase implements InvoiceDao {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static InvoiceDatabase instance;

    @Autowired
    private InvoiceDatabase() {
    }

    public static InvoiceDatabase getInstance() {
        if (instance == null) {
            instance = new InvoiceDatabase();
        }

        return instance;
    }

    @Override
    public void save(Invoice invoice) {
        String sql = "INSERT INTO \"public\".\"Invoices\" (id, sum, created) VALUES (uuid(?), ?, ?);";

        Map<Class<? extends Plane>, String> sqlQueries = new HashMap<>();
        sqlQueries.put(CargoPlane.class, "UPDATE \"public\".\"CargoPlanes\" SET invoice_id = uuid(?) WHERE id = uuid(?) AND invoice_id IS NULL;");
        sqlQueries.put(Fighter.class, "UPDATE \"public\".\"Fighters\" SET invoice_id = uuid(?) WHERE id = uuid(?) AND invoice_id IS NULL;");
        sqlQueries.put(PassengerPlane.class, "UPDATE \"public\".\"PassengerPlanes\" SET invoice_id = uuid(?) WHERE id = uuid(?) AND invoice_id IS NULL;");

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setInt(2, invoice.getSum());
            preparedStatement.setDate(3, new Date(invoice.getCreated().getTime()));
            preparedStatement.execute();

            for (Plane product : invoice.getProducts()) {
                updatePlaneDB(sqlQueries.get(product.getClass()), invoice, product);
            }

            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException e) {
            try {
                CONNECTION.rollback();
            }
            catch (SQLException exception) {
                throw new RuntimeException(exception);
            }

            throw new RuntimeException(e);
        }
    }

    private void updatePlaneDB(String sql, Invoice invoice, Plane plane) throws SQLException {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, invoice.getId());
            preparedStatement.setString(2, plane.getId());
            preparedStatement.execute();
        }
    }

    @Override
    public List<Invoice> getInvoicesExpensiveThanPrice(int price) {
        String sql = "SELECT * FROM \"public\".\"Invoices\" WHERE sum > ?;";

        List<String> sqlQueries = new ArrayList<>();
        sqlQueries.add("SELECT * FROM \"public\".\"CargoPlanes\" WHERE invoice_id = uuid(?);");
        sqlQueries.add("SELECT * FROM \"public\".\"Fighters\" WHERE invoice_id = uuid(?);");
        sqlQueries.add("SELECT * FROM \"public\".\"PassengerPlanes\" WHERE invoice_id = uuid(?);");

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            List<Invoice> invoices = new ArrayList<>();

            preparedStatement.setInt(1, price);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Invoice invoice = createInvoice(resultSet);
                invoice.setProducts(createProducts(sqlQueries, invoice));
                invoices.add(invoice);
            }

            return (invoices.isEmpty()) ? Collections.emptyList() : invoices;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Invoice createInvoice(ResultSet resultSet) throws SQLException {
        Invoice invoice = new Invoice(null, 0, null, null);

        invoice.setId(resultSet.getObject("id").toString());
        invoice.setSum(resultSet.getInt("sum"));
        invoice.setCreated(new java.util.Date(resultSet.getDate("created").getTime()));

        return invoice;
    }

    private List<Plane> createProducts(List<String> sqlQueries, Invoice invoice) throws SQLException {
        List<Plane> planes = new ArrayList<>();

        for (String sqlQuery : sqlQueries) {
            try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sqlQuery)) {
                preparedStatement.setString(1, invoice.getId());
                ResultSet resultSet = preparedStatement.executeQuery();

                while (resultSet.next()) {
                    if (sqlQuery.contains(CargoPlane.class.getSimpleName())) {
                        planes.add(parseCargoPlane(resultSet));
                    }
                    if (sqlQuery.contains(Fighter.class.getSimpleName())) {
                        planes.add(parseFighter(resultSet));
                    }
                    if (sqlQuery.contains(PassengerPlane.class.getSimpleName())) {
                        planes.add(parsePassengerPlane(resultSet));
                    }
                }
            }
        }

        return planes;
    }

    private CargoPlane parseCargoPlane(ResultSet resultSet) throws SQLException {
        CargoPlane cargoPlane = new CargoPlane(null, null, null, 0, 0,
                null, null, null, 0, 0);

        cargoPlane.setId(resultSet.getObject("id").toString());
        cargoPlane.setBrand(PlaneBrand.valueOf(resultSet.getString("brand")));
        cargoPlane.setModel(resultSet.getString("model"));
        cargoPlane.setPrice(resultSet.getInt("price"));
        cargoPlane.setCount(resultSet.getInt("count"));
        cargoPlane.setCurrency(resultSet.getString("currency"));
        cargoPlane.setCreated(new java.util.Date(resultSet.getDate("created").getTime()));
        cargoPlane.setManufacturingMaterial(new ManufacturingMaterial(resultSet.getString("material"),
                resultSet.getString("color")));
        cargoPlane.setLoadCapacity(resultSet.getInt("load_capacity"));
        cargoPlane.setCountOfCrew(resultSet.getInt("count_of_crew"));

        return cargoPlane;
    }

    private Fighter parseFighter(ResultSet resultSet) throws SQLException {
        Fighter fighter = new Fighter.Builder(null, 0).build();

        fighter.setId(resultSet.getObject("id").toString());
        fighter.setBrand(PlaneBrand.valueOf(resultSet.getString("brand")));
        fighter.setModel(resultSet.getString("model"));
        fighter.setPrice(resultSet.getInt("price"));
        fighter.setCount(resultSet.getInt("count"));
        fighter.setCurrency(resultSet.getString("currency"));
        fighter.setCreated(new java.util.Date(resultSet.getDate("created").getTime()));
        fighter.setManufacturingMaterial(new ManufacturingMaterial(resultSet.getString("material"),
                resultSet.getString("color")));
        fighter.setTypeOfFighter(TypeOfFighter.valueOf(resultSet.getString("type_of_fighter")));
        fighter.setBombLoad(resultSet.getInt("bomb_load"));

        return fighter;
    }

    private PassengerPlane parsePassengerPlane(ResultSet resultSet) throws SQLException {
        PassengerPlane passengerPlane = new PassengerPlane(null, null, null, 0, 0,
                null, null, null, 0, 0);

        passengerPlane.setId(resultSet.getObject("id").toString());
        passengerPlane.setBrand(PlaneBrand.valueOf(resultSet.getString("brand")));
        passengerPlane.setModel(resultSet.getString("model"));
        passengerPlane.setPrice(resultSet.getInt("price"));
        passengerPlane.setCount(resultSet.getInt("count"));
        passengerPlane.setCurrency(resultSet.getString("currency"));
        passengerPlane.setCreated(new java.util.Date(resultSet.getDate("created").getTime()));
        passengerPlane.setManufacturingMaterial(new ManufacturingMaterial(resultSet.getString("material"),
                resultSet.getString("color")));
        passengerPlane.setNumberOfPassenger(resultSet.getInt("number_of_passenger"));
        passengerPlane.setRangeOfFlight(resultSet.getInt("range_of_flight"));

        return passengerPlane;
    }

    @Override
    public int getInvoiceCount() {
        String sql = "SELECT count(*) AS count FROM \"public\".\"Invoices\";";

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);

            if (resultSet.next()) {
                return resultSet.getInt("count");
            }

            return 0;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Invoice> findById(String id) {
        String sql = "SELECT * FROM \"public\".\"Invoices\" WHERE id = uuid(?);";

        List<String> sqlQueries = new ArrayList<>();
        sqlQueries.add("SELECT * FROM \"public\".\"CargoPlanes\" WHERE invoice_id = uuid(?);");
        sqlQueries.add("SELECT * FROM \"public\".\"Fighters\" WHERE invoice_id = uuid(?);");
        sqlQueries.add("SELECT * FROM \"public\".\"PassengerPlanes\" WHERE invoice_id = uuid(?);");

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Invoice invoice = createInvoice(resultSet);
                invoice.setProducts(createProducts(sqlQueries, invoice));
                return Optional.of(invoice);
            }

            return Optional.empty();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(String id, java.util.Date date) {
        String sql = "UPDATE \"public\".\"Invoices\" SET created = ? WHERE id = uuid(?);";

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setDate(1, new Date(date.getTime()));
            preparedStatement.setString(2, id);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<Integer, Integer> groupBySum() {
        String sql = "SELECT sum, count(id) AS count FROM \"public\".\"Invoices\" GROUP BY sum;";

        try (Statement statement = CONNECTION.createStatement()) {
            Map<Integer, Integer> group = new HashMap<>();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                group.put(resultSet.getInt("sum"), resultSet.getInt("count"));
            }

            return (group.isEmpty()) ? Collections.emptyMap() : group;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Invoice> findAll() {
        String sql = "SELECT * FROM \"public\".\"Invoices\";";
        List<String> sqlQueries = new ArrayList<>();
        sqlQueries.add("SELECT * FROM \"public\".\"CargoPlanes\" WHERE invoice_id = uuid(?);");
        sqlQueries.add("SELECT * FROM \"public\".\"Fighters\" WHERE invoice_id = uuid(?);");
        sqlQueries.add("SELECT * FROM \"public\".\"PassengerPlanes\" WHERE invoice_id = uuid(?);");

        try (Statement statement = CONNECTION.createStatement()) {
            List<Invoice> invoices = new ArrayList<>();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Invoice invoice = createInvoice(resultSet);
                invoice.setProducts(createProducts(sqlQueries, invoice));
                invoices.add(invoice);
            }

            return (invoices.isEmpty()) ? Collections.emptyList() : invoices;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
