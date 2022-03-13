package main.employee;

import main.employee.util.EmployeeGeneration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class EmployeesTest {
    private static List<Employee> employeeArr;

    @BeforeAll
    static void createEmployees() throws IOException {
        employeeArr = EmployeeGeneration.readCSV("data.csv");
    }

    @Test
    void addEmployees() {
        Employee[] es = {
                new Employee("Роман", Sex.MALE, LocalDate.of(2001, 1, 1)),
                new Employee("Элина", Sex.FEMALE, LocalDate.of(1998, 4, 28)),
                new Employee("Тимур", Sex.MALE, LocalDate.of(1975, 11, 25)),
                new Employee("Олеся", Sex.FEMALE, LocalDate.of(1984, 6, 9)),
                new Employee("Ясмина", Sex.FEMALE, LocalDate.of(1952, 7, 2))};
        Employees employees = new Employees();
        employees.addEmployees(es);
        assertThat(employees.getEmployees(), hasSize(es.length));
    }

    @Test
    void removeEmployee() {
        Employee[] es = {
                new Employee("Роман", Sex.MALE, LocalDate.of(2001, 1, 1)),
                new Employee("Элина", Sex.FEMALE, LocalDate.of(1998, 4, 28)),
                new Employee("Тимур", Sex.MALE, LocalDate.of(1975, 11, 25)),
                new Employee("Олеся", Sex.FEMALE, LocalDate.of(1984, 6, 9)),
                new Employee("Ясмина", Sex.FEMALE, LocalDate.of(1952, 7, 2))};
        Employees employees = new Employees(es);
        Employee employee0 = employees.getEmployee(es[0].getId());
        Employee employee2 = employees.getEmployee(es[2].getId());
        Employee employee3 = employees.getEmployee(es[3].getId());
        Employee employee4 = employees.getEmployee(es[4].getId());
        employees.removeEmployee(es[1].getId());
        assertThat(employees.getEmployees(), contains(employee0, employee2, employee3, employee4));
    }

    @Test
    void count() {
        assertThat(new Employees(employeeArr).count(), equalTo(10001));
    }

    @Test
    void filterEmployeeByGender() throws IOException {
        List<Employee> expected = EmployeeGeneration.readCSV(ClassLoader.getSystemResource("FilterDataByGender.csv").getPath());
        List<Employee> actual = new Employees(employeeArr).filterEmployeeByGender(Sex.MALE);
        assertThat(actual, equalTo(expected));
    }

    @Test
    void filterEmployeeOlder() throws IOException { // требуется ежедневное изменение ожидаемых входных данных
        List<Employee> expected = EmployeeGeneration.readCSV(ClassLoader.getSystemResource("FilterEmployeeOlder18.csv").getPath());
        List<Employee> actual = new Employees(employeeArr).filterEmployeeOlder(18);
        assertThat(actual, equalTo(expected));
    }
}
