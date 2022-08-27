package ua.com.alevel.hw2.dao.jdbc.products.fighterdatabase;

import ua.com.alevel.hw2.annotations.Autowired;
import ua.com.alevel.hw2.annotations.Singleton;
import ua.com.alevel.hw2.config.JDBCConfig;
import ua.com.alevel.hw2.model.PlaneBrand;
import ua.com.alevel.hw2.model.fighter.Fighter;
import ua.com.alevel.hw2.model.fighter.TypeOfFighter;
import ua.com.alevel.hw2.model.manufacturingmaterial.ManufacturingMaterial;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Singleton
public final class FighterDatabase implements FighterDao {
    private static final Connection CONNECTION = JDBCConfig.getConnection();
    private static FighterDatabase instance;

    @Autowired
    private FighterDatabase() {
    }

    public static FighterDatabase getInstance() {
        if (instance == null) {
            instance = new FighterDatabase();
        }

        return instance;
    }

    @Override
    public void save(Fighter plane) {
        String sql = """
                INSERT INTO \"public\".\"Fighters\" (id, brand, model, price, count, currency, created, color, material, 
                type_of_fighter, bomb_load) 
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

    private void setFields(PreparedStatement preparedStatement, Fighter plane) throws SQLException {
        preparedStatement.setString(1, plane.getId());
        preparedStatement.setString(2, plane.getBrand().toString());
        preparedStatement.setString(3, plane.getModel());
        preparedStatement.setInt(4, plane.getPrice());
        preparedStatement.setInt(5, plane.getCount());
        preparedStatement.setString(6, plane.getCurrency());
        preparedStatement.setDate(7, new Date(plane.getCreated().getTime()));
        preparedStatement.setString(8, plane.getManufacturingMaterial().getColor());
        preparedStatement.setString(9, plane.getManufacturingMaterial().getMaterial());
        preparedStatement.setString(10, plane.getTypeOfFighter().toString());
        preparedStatement.setInt(11, plane.getBombLoad());
    }

    @Override
    public void saveAll(Fighter[] planes) {
        String sql = """
                INSERT INTO \"public\".\"Fighters\" (id, brand, model, price, count, currency, created, color, material, 
                type_of_fighter, bomb_load) 
                VALUES (uuid(?), ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);
                """;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(sql)) {
            CONNECTION.setAutoCommit(false);
            for (Fighter plane : planes) {
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
    public void update(Fighter plane) {
        String sql = """
                UPDATE \"public\".\"Fighters\" 
                SET brand = ?, model = ?, price = ?, count = ?, currency = ?, created = ?, color = ?, 
                material = ?, type_of_fighter = ?, bomb_load = ? 
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

    private void setFieldsUpdate(PreparedStatement preparedStatement, Fighter plane) throws SQLException {
        preparedStatement.setString(11, plane.getId());
        preparedStatement.setString(1, plane.getBrand().toString());
        preparedStatement.setString(2, plane.getModel());
        preparedStatement.setInt(3, plane.getPrice());
        preparedStatement.setInt(4, plane.getCount());
        preparedStatement.setString(5, plane.getCurrency());
        preparedStatement.setDate(6, new Date(plane.getCreated().getTime()));
        preparedStatement.setString(7, plane.getManufacturingMaterial().getColor());
        preparedStatement.setString(8, plane.getManufacturingMaterial().getMaterial());
        preparedStatement.setString(9, plane.getTypeOfFighter().toString());
        preparedStatement.setInt(10, plane.getBombLoad());
    }

    @Override
    public void delete(String id) {
        String sql = """
                DELETE FROM \"public\".\"Fighters\" WHERE id = uuid(?);
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
    public Optional<Fighter> findById(String id) {
        String sql = """
                SELECT * FROM  \"public\".\"Fighters\" WHERE id = uuid(?);
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

    private Fighter parsePlane(ResultSet resultSet) throws SQLException {
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

    @Override
    public List<Fighter> findAll() {
        List<Fighter> planes = new ArrayList<>();
        String sql = """
                SELECT * FROM \"public\".\"Fighters\";
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
    public List<Fighter> findAllInStock() {
        List<Fighter> planes = new ArrayList<>();
        String sql = """
                SELECT * FROM \"public\".\"Fighters\" WHERE invoice_id IS NULL;
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
