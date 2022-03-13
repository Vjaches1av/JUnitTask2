package main;

import main.employee.Employee;
import main.employee.util.EmployeeGeneration;

import java.util.List;

public class Main {

    public static void main(String[] args) {
        try {
            EmployeeGeneration.toCSV(EmployeeGeneration.run(10_001));
            try {
                List<Employee> employees = EmployeeGeneration.readCSV("data.csv");
//                Employees.displayEmployees(employees);
            } catch (Exception e) {
                System.err.println("Ошибка при чтении CSV-файла");
            }
        } catch (Exception e) {
            System.err.println("Ошибка при формировании CSV-файла");
        }
    }
}
