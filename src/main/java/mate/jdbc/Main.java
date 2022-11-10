package mate.jdbc;

import mate.jdbc.db.ManufacturerDao;
import mate.jdbc.db.ManufacturerDaoImpl;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

import java.util.List;
import java.util.NavigableMap;
import java.util.Optional;
import java.util.function.Supplier;


public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.db");
    public static void main(String[] args) {
        ManufacturerDaoImpl manufacturerDao = (ManufacturerDaoImpl) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setName("Mercedes-benz");
        manufacturer.setCountry("China");
        manufacturer = manufacturerDao.create(manufacturer);
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
        manufacturer.setCountry("Germany");
        manufacturerDao.update(manufacturer);
        Optional<Manufacturer> getManufacturer = manufacturerDao.get(manufacturer.getId());
        if (getManufacturer.isPresent()) {
            manufacturer = getManufacturer.get();
            System.out.println("-------------------------");
            System.out.println(manufacturer);
        }
        System.out.println("-------------------------");
        manufacturerDao.delete(manufacturer.getId());
        manufacturerList = manufacturerDao.getAll();
        manufacturerList.forEach(System.out::println);
    }
}
