package lab.country;

import java.util.Scanner;

/**
 * Консольное приложение для работы со странами.
 * Позволяет создавать новые записи, выводить список и завершать программу.
 */

public class Main {
    private static final String COMMAND_EXIT = "exit";
    private static final String COMMAND_CREATE = "create";
    private static final String COMMAND_PRINT = "print";
    private static final String COMMAND_DENSITY = "density";
    private static final String YES = "y";
    private static final String NO = "n";

    private static final Scanner SCANNER = new Scanner(System.in);
    CountryRespository respository = new CountryRespository();
    Country found = respository.findCountryByName(name);


    public static void main(String[] args) {



        try {

            // Цикл обработки команд пользователя
            while (true) {

                System.out.println("Введите команду:");
                String command = readInput().toLowerCase();

                switch (command) {

                    case COMMAND_CREATE:

                        String name;
                        while (true) {
                            System.out.println("Введите название страны:");
                            name = readInput();

                            try {
                                Country.validateName(name);
                                break;
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        double area;
                        while (true) {
                            System.out.println("Введите площадь (км²):");
                            String input = readInput();

                            try {
                                area = Double.parseDouble(input);
                                Country.validateArea(area);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Введите число.");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        long population;
                        while (true) {
                            System.out.println("Введите население:");
                            String input = readInput();

                            try {
                                population = Long.parseLong(input);
                                Country.validatePopulation(population);
                                break;
                            } catch (NumberFormatException e) {
                                System.out.println("Введите число.");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }
                        }

                        String answer;
                        Country newCountry;

                        while (true) {
                            System.out.println("Есть ли столица? (y/n):");
                            answer = readInput().toLowerCase();

                            if (!answer.equals(YES) && !answer.equals(NO)) {
                                System.out.println("Некорректный ввод. Введите 'y' или 'n'.");
                                continue;
                            }

                            if (answer.equals(NO)) {
                                newCountry = new Country(name, area, population);
                                break;
                            }

                            System.out.println("Введите название столицы:");
                            String capitalName = readInput();

                            try {
                                Country.validateCapitalName(capitalName);
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                                System.out.println("Уточните свой выбор.");
                                continue;
                            }

                            long capitalPopulation;
                            while (true) {
                                System.out.println("Введите население столицы:");
                                String input = readInput();

                                try {
                                    capitalPopulation = Long.parseLong(input);
                                    Country.validateCapitalPopulation(capitalPopulation);
                                    break;
                                } catch (NumberFormatException e) {
                                    System.out.println("Введите число.");
                                } catch (IllegalArgumentException e) {
                                    System.out.println(e.getMessage());
                                }
                            }

                            newCountry = new Country(name, area, population, capitalName, capitalPopulation);
                            break;
                        }

                        if (index >= countries.length) {
                            countries = extendArray(countries);
                        }

                        countries[index] = newCountry;

                        System.out.println("Страна добавлена: " + newCountry.getName());
                        index++;
                        break;

                    case COMMAND_PRINT:
                        printAll(countries);
                        break;

                    case COMMAND_DENSITY:
                        String nameDensity;
                        System.out.println("Введите название страны:");
                        nameDensity = readInput();

                        Country found = findCountryByName(nameDensity);

                        if (found == null) {
                            System.out.println("Страна с таким именем не найдена.");
                            break;
                        }

                        try {
                            double density = found.getPopulationDensity();
                            System.out.println("Плотность населения страны " + found.getName() + ": "
                                    + String.format("%.2f", density) + " чел/км².");
                        } catch (IllegalStateException e) {
                            System.out.println(e.getMessage());
                        }
                        break;

                    default:
                        System.out.println("Неизвестная команда. Доступные: create, print, density, exit.");
                }
            }

        } catch (ExitException e) {
            System.out.println("Программа завершена.");
        }
    }

    /**
     * Выводит в консоль информацию о всех непустых элементах массива стран.
     *
     * @param countries массив объектов Country для печати.
     */

    public static void printAll(Country[] countries) {

        if (countries == null || countries.length == 0) {
            return;
        }

        for (Country c : countries) {
            if (c != null) {
                System.out.println(c);
                System.out.println();
            }
        }
    }


    /**
     * Считывает строку, введённую пользователем, и проверяет команду выхода.
     * Если пользователь ввёл "exit", возбуждается исключение ExitException.
     *
     * @return введённая пользователем строка
     * @throws ExitException если введена команда "exit"
     */

    private static String readInput() {
        String input = SCANNER.nextLine().trim();
        if (COMMAND_EXIT.equalsIgnoreCase(input)) {
            throw new ExitException();
        }
        return input;
    }

class ExitException extends RuntimeException {
}
}