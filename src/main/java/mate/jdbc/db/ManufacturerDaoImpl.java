package mate.jdbc.db;

import com.mysql.jdbc.Driver;
import mate.jdbc.exception.DataProcessingException;
import mate.jdbc.lib.Dao;
import mate.jdbc.model.Manufacturer;

import java.sql.*;
import java.util.List;
import java.util.Optional;

@Dao
public class ManufacturerDaoImpl implements ManufacturerDao {
    private static final int INDEX_OF_ID_COLUMN = 1;


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
            throw new DataProcessingException("Can't insert to database, Manufacturer: " + manufacturer, ex);
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
            ResultSet resultSet = getManufacturerStatement.executeQuery();
            if(resultSet.next()) {
                Long manufacturerId = resultSet.getObject(1, Long.class);
                String manufacturerName = resultSet.getObject(2, String.class);
                String manufacturerCountry = resultSet.getObject(3, String.class);
                manufacturer = new Manufacturer();
                manufacturer.setId(manufacturerId);
                manufacturer.setName(manufacturerName);
                manufacturer.setCountry(manufacturerCountry);
            }
        }  catch (SQLException ex) {
            throw new DataProcessingException("Can't get from database, Manufacturer by id: " + id, ex);
        }
        return  Optional.ofNullable(manufacturer);
    }

    @Override
    public List<Manufacturer> getAll() {
        return null;
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
