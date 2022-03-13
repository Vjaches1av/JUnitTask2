package main.employee.util;

import com.opencsv.*;
import com.opencsv.bean.*;
import main.employee.Employee;
import main.employee.Sex;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public final class EmployeeGeneration {
    private static final String[][] FIRST_NAMES = {
            {"Александр", "Дмитрий", "Максим", "Сергей", "Андрей", "Алексей", "Артём", "Илья", "Кирилл", "Михаил",
                    "Никита", "Матвей", "Роман", "Егор", "Арсений", "Иван", "Денис", "Евгений", "Даниил", "Тимофей",
                    "Владислав", "Игорь", "Владимир", "Павел", "Руслан", "Марк", "Константин", "Тимур", "Олег", "Ярослав",
                    "Антон", "Николай", "Глеб", "Данил", "Савелий", "Вадим", "Степан", "Юрий", "Богдан", "Артур",
                    "Семен", "Макар", "Лев", "Виктор", "Елисей", "Виталий", "Вячеслав", "Захар", "Мирон", "Дамир",
                    "Георгий", "Давид", "Платон", "Анатолий", "Григорий", "Демид", "Данила", "Станислав", "Василий", "Федор",
                    "Родион", "Леонид", "Одиссей", "Валерий", "Святослав", "Борис", "Эдуард", "Марат", "Герман", "Даниэль",
                    "Петр", "Амир", "Всеволод", "Мирослав", "Гордей", "Артемий", "Эмиль", "Назар", "Савва", "Ян",
                    "Рустам", "Игнат", "Влад", "Альберт", "Тамерлан", "Айдар", "Роберт", "Адель", "Марсель", "Ильдар",
                    "Самир", "Тихон", "Рамиль", "Ринат", "Радмир", "Филипп", "Арсен", "Ростислав", "Святогор", "Яромир"},
            {"Анастасия", "Анна", "Мария", "Елена", "Дарья", "Алина", "Ирина", "Екатерина", "Арина", "Полина",
                    "Ольга", "Юлия", "Татьяна", "Наталья", "Виктория", "Елизавета", "Ксения", "Милана", "Вероника", "Алиса",
                    "Валерия", "Александра", "Ульяна", "Кристина", "София", "Марина", "Светлана", "Варвара", "Софья", "Диана",
                    "Яна", "Кира", "Ангелина", "Маргарита", "Ева", "Алёна", "Дарина", "Карина", "Василиса", "Олеся",
                    "Аделина", "Оксана", "Таисия", "Надежда", "Евгения", "Элина", "Злата", "Есения", "Милена", "Вера",
                    "Мирослава", "Галина", "Людмила", "Валентина", "Нина", "Эмилия", "Камилла", "Альбина", "Лилия", "Любовь",
                    "Лариса", "Эвелина", "Инна", "Агата", "Амелия", "Амина", "Эльвира", "Ярослава", "Стефания", "Регина",
                    "Алла", "Виолетта", "Лидия", "Амалия", "Наталия", "Марьяна", "Анжелика", "Нелли", "Влада", "Виталина",
                    "Майя", "Тамара", "Мелания", "Лиана", "Василина", "Зарина", "Алия", "Владислава", "Самира", "Антонина",
                    "Ника", "Мадина", "Наташа", "Каролина", "Снежана", "Юлиана", "Ариана", "Эльмира", "Ясмина", "Жанна"}
    };

    public static Employee run() {
        Random random = new Random(Employee.getCountId());
        LocalDate birthDate = LocalDate.of(
                random.nextInt(100) + LocalDate.now().getYear() - 100,
                random.nextInt(12) + 1,
                random.nextInt(28) + 1);
        Sex sex = Sex.values()[random.nextInt(2)];
        String firstName;
        if (sex == Sex.FEMALE) {
            firstName = FIRST_NAMES[1][random.nextInt(FIRST_NAMES[1].length)];
        } else {
            firstName = FIRST_NAMES[0][random.nextInt(FIRST_NAMES[1].length)];
        }
        return new Employee(firstName, sex, birthDate);
    }

    public static Employee[] run(int count) {
        Employee[] employees = new Employee[count];
        for (int i = 0; i < count; i++)
            employees[i] = run();
        return employees;
    }

    public static void toCSV(Employee[] employees) throws Exception {
        File file = new File("data.csv");
        try (CSVWriter writer = new CSVWriter(
                new OutputStreamWriter(
                        new FileOutputStream(file, false), StandardCharsets.UTF_8),
                ';',
                ICSVParser.DEFAULT_QUOTE_CHARACTER,
                ICSVParser.DEFAULT_ESCAPE_CHARACTER,
                System.lineSeparator())) {
            HeaderColumnNameMappingStrategy<Employee> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Employee.class);
            StatefulBeanToCsv<Employee> beanToCsv = new StatefulBeanToCsvBuilder<Employee>(writer)
                    .withApplyQuotesToAll(false)
                    .withIgnoreField(Employee.class, Employee.class.getDeclaredField("countId"))
                    .withMappingStrategy(strategy)
                    .withOrderedResults(true)
                    .build();
            beanToCsv.write(new ArrayList<>(Arrays.asList(employees)));
        } catch (Exception e) {
            file.delete();
            throw e;
        }
    }

    public static List<Employee> readCSV(File pathname) throws IOException {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .build();
        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(
                        new FileInputStream(pathname), StandardCharsets.UTF_8))
                .withCSVParser(parser)
                .build()) {
            HeaderColumnNameMappingStrategy<Employee> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(Employee.class);
            CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(reader)
                    .withMappingStrategy(strategy)
                    .build();
            return csvToBean.parse();
        }
    }

    public static List<Employee> readCSV(String pathname) throws IOException {
        return readCSV(new File(pathname));
    }
}
