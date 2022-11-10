package mate.jdbc;

import mate.jdbc.db.ManufacturerDao;
import mate.jdbc.db.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.NavigableMap;
import java.util.Optional;


public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.db");
    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
       /* Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes-benz");
        manufacturer.setCountry("Germany");*/
        //manufacturerDao.create(manufacturer);
        Optional<Manufacturer> manufacturer = manufacturerDao.get(1l);
        System.out.println(manufacturer.get());

    }
}
