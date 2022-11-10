package mate.jdbc.db;

import com.mysql.jdbc.Driver;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int INDEX_OF_ID_COLUMN = 1;
    private static final int ID_INDEX = 1;
    private static final int NAME_INDEX = 2;
    private static final int COUNTRY_INDEX = 3;


    @Override
    public Manufacturer create(Manufacturer manufacturer) {
        String createQuery = "INSERT INTO manufacturers (name, country) VALUES (?,?);";
        try (Connection connection = MYSQL_ConnectionUtils.getConnection()) {
            PreparedStatement createManufacturerStatement = connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS);
            createManufacturerStatement.setString(1, manufacturer.getName());
            createManufacturerStatement.setString(2, manufacturer.getCountry());
            createManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = createManufacturerStatement.getGeneratedKeys();
            if(generatedKeys.next()) {
                Long id = generatedKeys.getObject(INDEX_OF_ID_COLUMN, Long.class);
                manufacturer.setId(id);
            }
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't insert to manufactures table, Manufacturer: " + manufacturer, ex);
        }
        return manufacturer;
    }

    @Override
    public Optional<Manufacturer> get(Long id) {
        Manufacturer manufacturer = null;
        String query = "SELECT * FROM manufacturers WHERE id =?;";
        try (Connection connection = MYSQL_ConnectionUtils.getConnection()) {
            PreparedStatement getManufacturerStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getManufacturerStatement.setLong(1,id);
            ResultSet generatedKeys = getManufacturerStatement.executeQuery();
            if(generatedKeys.next()) {
                manufacturer = createManufacturer(generatedKeys);
            }
        }  catch (SQLException ex) {
            throw new DataProcessingException("Can't get manufacturer by id: " + id, ex);
        }
        return  Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        List<Manufacturer> result = new ArrayList<>();
        String query = "SELECT * FROM manufacturers WHERE is_deleted = FALSE;";
        try (Connection connection = MYSQL_ConnectionUtils.getConnection()) {
            PreparedStatement getAllManufacturerStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            getAllManufacturerStatement.executeUpdate();
            ResultSet generatedKeys = getAllManufacturerStatement.getGeneratedKeys();
            while (generatedKeys.next()) {
                Manufacturer manufacturer = createManufacturer(generatedKeys);
                result.add(manufacturer);
            }
        }  catch (SQLException ex) {
            throw new DataProcessingException("Can't get all from Manufactures table", ex);
        }
        return result;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        String query = "UPDATE manufacturers SET name = ?, country = ? WHERE id = ? AND is_deleted = FALSE";
        try (Connection connection = MYSQL_ConnectionUtils.getConnection()) {
            PreparedStatement updateManufacturerStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            updateManufacturerStatement.setString(1, manufacturer.getName());
            updateManufacturerStatement.setString(2, manufacturer.getCountry());
            updateManufacturerStatement.setLong(3, manufacturer.getId());
            updateManufacturerStatement.executeUpdate();
        } catch (SQLException ex) {
            throw new DataProcessingException("Can't update Manufacturer: " + manufacturer, ex);
        }
        return  manufacturer;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    private Manufacturer createManufacturer(ResultSet generatedKeys) throws SQLException {
        Long manufacturerId = generatedKeys.getObject(ID_INDEX, Long.class);
        String manufacturerName = generatedKeys.getObject(NAME_INDEX, String.class);
        String manufacturerCountry = generatedKeys.getObject(COUNTRY_INDEX, String.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(manufacturerId);
        manufacturer.setName(manufacturerName);
        manufacturer.setCountry(manufacturerCountry);
        return manufacturer;
    }
}
