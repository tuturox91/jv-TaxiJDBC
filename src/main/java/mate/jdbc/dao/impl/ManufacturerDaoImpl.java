package mate.jdbc.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;
import mate.jdbc.utils.MySqlConnection;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {

    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers (name, country) VALUES (?,?);";
        try (Connection connection = MySqlConnection.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                Long id = generatedKeys.getLong(1);
                manufacturer.setId(id);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't create manufacturer" + manufacturer, e);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String selectQuery = "SELECT * FROM manufacturers WHERE id = ?;";
        try (Connection connection = MySqlConnection.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(selectQuery, Statement.RETURN_GENERATED_KEYS);
            statement.setLong(1, id);
            ResultSet generatedKeys = statement.executeQuery();
            if (generatedKeys.next()) {
                manufacturer = generateManufacturer(generatedKeys);
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't find Manufacturer with id: " + id, e);
        }
        return Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> manufacturers = new ArrayList<>();
        String selectAllQuery = "SELECT * FROM manufacturers WHERE is_deleted = FALSE";
        try (Connection connection = MySqlConnection.getConnection()) {
            PreparedStatement statement =
                    connection.prepareStatement(selectAllQuery, Statement.RETURN_GENERATED_KEYS);
            ResultSet generatedKeys = statement.executeQuery();
            while (generatedKeys.next()) {
                manufacturers.add(generateManufacturer(generatedKeys));
            }
        } catch (SQLException e) {
            throw new DataProcessingException("Can't get all from Manufactures table", e);
        }
        return manufacturers;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String updateQuery =
                "UPDATE manufacturers SET name = ?,"
                        + " country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = MySqlConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(updateQuery);
            statement.setString(1, manufacturer.getName());
            statement.setString(2, manufacturer.getCountry());
            statement.setLong(3, manufacturer.getId());
            statement.executeUpdate();
            return manufacturer;
        } catch (SQLException e) {
            throw new DataProcessingException("Cant update this Manufacturer: " + manufacturer, e);
        }
    }

    @Override
    public boolean delete(Manufacturer manufacturer) {
        String deleteQuery = "UPDATE manufacturers SET is_deleted = TRUE WHERE id = ?";
        try (Connection connection = MySqlConnection.getConnection()) {
            PreparedStatement statement = connection.prepareStatement(deleteQuery);
            statement.setLong(1, manufacturer.getId());
            statement.executeUpdate();
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Manufacturer generateManufacturer(ResultSet generatedKeys) throws SQLException {
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(generatedKeys.getLong(1));
        manufacturer.setName(generatedKeys.getString(2));
        manufacturer.setCountry(generatedKeys.getString(3));
        return manufacturer;
    }
}
