package lab.country;

public class CountryRepository {


    private static final int INITIAL_CAPACITY = 10;
    private static final int GROW_FACTOR = 2;
    private Country[] countries;
    private int size;

    public CountryRepository() {

        countries = new Country[INITIAL_CAPACITY];
        this.size = 5;

        // Инициализация тестовых данных
        countries[0] = new Country("Russia", 17100000, 146700000, "Moscow", 12600000);
        countries[1] = new Country("Finland", 338000, 5500000, "Helsinki", 655000);
        countries[2] = new Country("France", 643800, 67800000, "Paris", 2100000);
        countries[3] = new Country("Andorra", 467, 85400, "Andorra la Vella", 22600);
        countries[4] = new Country("Singapore", 2, 5700000);
    }

    /**
     * Увеличивает размер массива стран, копирует все элементы
     * в новый массив, размер которого в два раза больше.
     *
     */

    public void extendArray() {
        Country[] newArray = new Country[countries.length * GROW_FACTOR];
        System.arraycopy(countries, 0, newArray, 0, countries.length);
        countries = newArray;
    }

    /**
     * Ищет страну в массиве по её названию (без учёта регистра).
     * Возвращает найденный объект Country или null, если соответствующая страна
     * отсутствует в массиве.
     *
     * @param nameDensity название страны для поиска
     * @return объект Country или null, если страна не найдена
     */

    public Country findCountryByName(String nameDensity) {
        for (Country c : countries) {
            if (c != null && nameDensity.equalsIgnoreCase(c.getName())) {
                return c;
            }
        }
        return null;
    }


    public void add(Country country) {
        if (size == countries.length) {
            extendArray();
        }
        countries [size] = country;
        size++;
    }

    public void printAll() {
        for (int i = 0; i < size; i++)  {
            System.out.println(countries[i]);
            System.out.println();
        }
    }

}

