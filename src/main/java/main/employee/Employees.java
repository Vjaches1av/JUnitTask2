package main.employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

public final class Employees {
    private final Map<Long, Employee> list;

    public Employees() {
        list = new HashMap<>();
    }

    public Employees(Employee[] employees) {
        this();
        addEmployees(employees);
    }

    public Employees(List<Employee> employees) {
        this();
        addEmployees(employees);
    }

    public Employee addEmployee(Employee employee) {
        return list.put(employee.getId(), employee);
    }

    public void addEmployees(Employee[] employees) {
        for (Employee employee : employees)
            addEmployee(employee);
    }

    public void addEmployees(List<Employee> employees) {
        for (Employee employee : employees)
            addEmployee(employee);
    }

    public Employee getEmployee(long id) {
        return list.get(id);
    }

    public ArrayList<Employee> getEmployees() {
        return new ArrayList<>(list.values());
    }

    public Employee removeEmployee(long id) {
        return list.remove(id);
    }

    public void displayEmployees() {
        display(getEmployees());
    }

    public int count() {
        return count(new ArrayList<>(list.values()));
    }

    public List<Employee> filterEmployeeByGender(Sex sex) {
        return list.values().stream()
                .filter(s -> s.getSex() == sex)
                .collect(Collectors.toList());
    }

    public List<Employee> filterEmployeeOlder(int ageInclusive) {
        return list.values().stream()
                .filter(s -> Period.between(s.getBirthDate(), LocalDate.now()).getYears() >= ageInclusive)
                .collect(Collectors.toList());
    }

    public static <T> void display(List<T> list) {
        list.forEach(System.out::println);
    }

    public static <T> int count(List<T> list) {
        return list.size();
    }
}
