package ua.com.alevel.hw2.dao.jdbc.products.cargoplanedatabase;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.cargoplane.CargoPlane;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import java.sql.*;
import java.sql.Date;
import java.util.*;

@Singleton
public final class CargoPlaneDatabase implements CargoPlaneDao {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static CargoPlaneDatabase instance;

    @Autowired
    private CargoPlaneDatabase() {
    }

    public static CargoPlaneDatabase getInstance() {
        if (instance == null) {
            instance = new CargoPlaneDatabase();
        }

        return instance;
    }

    @Override
    public void save(CargoPlane plane) {
        String sql = """
                INSERT INTO \"public\".\"CargoPlanes\" (id, brand, model, price, count, currency, created, color, material, 
                load_capacity, count_of_crew) 
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

    private void setFields(PreparedStatement preparedStatement, CargoPlane plane) throws SQLException {
        preparedStatement.setString(1, plane.getId());
        preparedStatement.setString(2, plane.getBrand().toString());
        preparedStatement.setString(3, plane.getModel());
        preparedStatement.setInt(4, plane.getPrice());
        preparedStatement.setInt(5, plane.getCount());
        preparedStatement.setString(6, plane.getCurrency());
        preparedStatement.setDate(7, new Date(plane.getCreated().getTime()));
        preparedStatement.setString(8, plane.getManufacturingMaterial().getColor());
        preparedStatement.setString(9, plane.getManufacturingMaterial().getMaterial());
        preparedStatement.setInt(10, plane.getLoadCapacity());
        preparedStatement.setInt(11, plane.getCountOfCrew());
    }

    @Override
    public void saveAll(CargoPlane[] planes) {
        String sql = """
                INSERT INTO \"public\".\"CargoPlanes\" (id, brand, model, price, count, currency, created, color, material, 
                load_capacity, count_of_crew) 
                VALUES (uuid(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (CargoPlane plane : planes) {
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
    public void update(CargoPlane plane) {
        String sql = """
                UPDATE \"public\".\"CargoPlanes\" 
                SET brand = ?, model = ?, price = ?, count = ?, currency = ?, created = ?, color = ?, 
                material = ?, load_capacity = ?, count_of_crew = ? 
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

    private void setFieldsUpdate(PreparedStatement preparedStatement, CargoPlane plane) throws SQLException {
        preparedStatement.setString(11, plane.getId());
        preparedStatement.setString(1, plane.getBrand().toString());
        preparedStatement.setString(2, plane.getModel());
        preparedStatement.setInt(3, plane.getPrice());
        preparedStatement.setInt(4, plane.getCount());
        preparedStatement.setString(5, plane.getCurrency());
        preparedStatement.setDate(6, new Date(plane.getCreated().getTime()));
        preparedStatement.setString(7, plane.getManufacturingMaterial().getColor());
        preparedStatement.setString(8, plane.getManufacturingMaterial().getMaterial());
        preparedStatement.setInt(9, plane.getLoadCapacity());
        preparedStatement.setInt(10, plane.getCountOfCrew());
    }

    @Override
    public void delete(String id) {
        String sql = """
                DELETE FROM \"public\".\"CargoPlanes\" WHERE id = uuid(?);
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
    public Optional<CargoPlane> findById(String id) {
        String sql = """
                SELECT * FROM  \"public\".\"CargoPlanes\" WHERE id = uuid(?);
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

    private CargoPlane parsePlane(ResultSet resultSet) throws SQLException {
        CargoPlane cargoPlane = new CargoPlane();

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

    @Override
    public List<CargoPlane> findAll() {
        List<CargoPlane> planes = new ArrayList<>();
        String sql = """
                SELECT * FROM \"public\".\"CargoPlanes\";
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
    public List<CargoPlane> findAllInStock() {
        List<CargoPlane> planes = new ArrayList<>();
        String sql = """
                SELECT * FROM \"public\".\"CargoPlanes\" WHERE invoice_id IS NULL;
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
