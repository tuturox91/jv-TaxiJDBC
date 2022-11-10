package mate.jdbc;

import mate.jdbc.db.ManufacturerDao;
import mate.jdbc.db.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;


public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.db");
    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Toyota",  "Japan");
        // initialize field values using setters or constructor
        manufacturerDao.create(manufacturer);
        // test other methods from ManufacturerDao
    }
}
