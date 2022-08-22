package ua.com.alevel.hw2.dao.products.passengerplanedatabase;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import ua.com.alevel.hw2.model.passengerplane.PassengerPlane;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public final class PassengerPlaneDatabase implements PassengerPlaneDao {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static PassengerPlaneDatabase instance;

    @Autowired
    private PassengerPlaneDatabase() {
    }

    public static PassengerPlaneDatabase getInstance() {
        if (instance == null) {
            instance = new PassengerPlaneDatabase();
        }

        return instance;
    }

    @Override
    public void save(PassengerPlane plane) {
        String sql = """
                INSERT INTO \"public\".\"PassengerPlanes\" (id, brand, model, price, count, currency, created, color, material, 
                number_of_passenger, range_of_flight) 
                VALUES (uuid(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            setFields(preparedStatement, plane);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFields(PreparedStatement preparedStatement, PassengerPlane plane) throws SQLException {
        preparedStatement.setString(1, plane.getId());
        preparedStatement.setString(2, plane.getBrand().toString());
        preparedStatement.setString(3, plane.getModel());
        preparedStatement.setInt(4, plane.getPrice());
        preparedStatement.setInt(5, plane.getCount());
        preparedStatement.setString(6, plane.getCurrency());
        preparedStatement.setDate(7, new Date(plane.getCreated().getTime()));
        preparedStatement.setString(8, plane.getManufacturingMaterial().getColor());
        preparedStatement.setString(9, plane.getManufacturingMaterial().getMaterial());
        preparedStatement.setInt(10, plane.getNumberOfPassenger());
        preparedStatement.setInt(11, plane.getRangeOfFlight());
    }

    @Override
    public void saveAll(PassengerPlane[] planes) {
        String sql = """
                INSERT INTO \"public\".\"PassengerPlanes\" (id, brand, model, price, count, currency, created, color, material, 
                number_of_passenger, range_of_flight) 
                VALUES (uuid(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (PassengerPlane plane : planes) {
                setFields(preparedStatement, plane);
                preparedStatement.addBatch();
            }
            preparedStatement.executeBatch();
            CONNECTION.commit();
            CONNECTION.setAutoCommit(true);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(PassengerPlane plane) {
        String sql = """
                UPDATE \"public\".\"PassengerPlanes\" 
                SET brand = ?, model = ?, price = ?, count = ?, currency = ?, created = ?, color = ?, 
                material = ?, number_of_passenger = ?, range_of_flight = ? 
                WHERE id = uuid(?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            setFieldsUpdate(preparedStatement, plane);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldsUpdate(PreparedStatement preparedStatement, PassengerPlane plane) throws SQLException {
        preparedStatement.setString(11, plane.getId());
        preparedStatement.setString(1, plane.getBrand().toString());
        preparedStatement.setString(2, plane.getModel());
        preparedStatement.setInt(3, plane.getPrice());
        preparedStatement.setInt(4, plane.getCount());
        preparedStatement.setString(5, plane.getCurrency());
        preparedStatement.setDate(6, new Date(plane.getCreated().getTime()));
        preparedStatement.setString(7, plane.getManufacturingMaterial().getColor());
        preparedStatement.setString(8, plane.getManufacturingMaterial().getMaterial());
        preparedStatement.setInt(9, plane.getNumberOfPassenger());
        preparedStatement.setInt(10, plane.getRangeOfFlight());
    }

    @Override
    public void delete(String id) {
        String sql = """
                DELETE FROM \"public\".\"PassengerPlanes\" WHERE id = uuid(?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            preparedStatement.execute();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<PassengerPlane> findById(String id) {
        String sql = """
                SELECT * FROM  \"public\".\"PassengerPlanes\" WHERE id = uuid(?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            preparedStatement.setString(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return (resultSet.next()) ? Optional.of(parsePlane(resultSet)) : Optional.empty();
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private PassengerPlane parsePlane(ResultSet resultSet) throws SQLException {
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
    public List<PassengerPlane> findAll() {
        List<PassengerPlane> planes = new ArrayList<>();
        String sql = """
                SELECT * FROM \"public\".\"PassengerPlanes\";
                """;

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                planes.add(parsePlane(resultSet));
            }

            return (planes.isEmpty()) ? Collections.emptyList() : planes;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PassengerPlane> findAllInStock() {
        List<PassengerPlane> planes = new ArrayList<>();
        String sql = """
                SELECT * FROM \"public\".\"PassengerPlanes\" WHERE invoice_id IS NULL;
                """;

        try (Statement statement = CONNECTION.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                planes.add(parsePlane(resultSet));
            }

            return (planes.isEmpty()) ? Collections.emptyList() : planes;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
