package mate.jdbc;

import java.util.Optional;
import mate.jdbc.dao.ManufacturerDao;
import mate.jdbc.lib.Injector;
import mate.jdbc.model.Manufacturer;

public class Main {
    private static final Injector injector = Injector.getInstance("mate.jdbc.dao");
    private static final Long getId = 2L;

    public static void main(String[] args) {
        ManufacturerDao manufacturerDao =
                (ManufacturerDao) injector.getInstance(ManufacturerDao.class);
        Manufacturer manufacturer = new Manufacturer("Mazda", "Japan");
        Manufacturer manufacturerTwo = new Manufacturer("Audi", "Germany");
        manufacturerDao.create(manufacturer);
        System.out.println("Создан: " + manufacturer);
        manufacturerDao.create(manufacturerTwo);
        System.out.println("Создан: " + manufacturerTwo);
        Optional<Manufacturer> getManufacturerById = manufacturerDao.get(getId);
        getManufacturerById.ifPresent((string) ->
                System.out.println("Получить по id: " + getId + " " + string));
        System.out.println("Вывести все: ");
        manufacturerDao.getAll().forEach(System.out::println);
        System.out.println("Обновить объект: ");
        Manufacturer updateThisManufacturer = getManufacturerById.get();
        updateThisManufacturer.setName("Mercedes");
        manufacturerDao.update(updateThisManufacturer);
        getManufacturerById = manufacturerDao.get(getId);
        getManufacturerById.ifPresent((string) ->
                System.out.println("Получить по id: " + getId + " " + string));
        System.out.println("Удалим Manufacturer с id: " + getId);
        manufacturerDao.delete(getManufacturerById.get());
        System.out.println("Вывести все: ");
        manufacturerDao.getAll().forEach(System.out::println);
    }
}
