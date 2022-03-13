package main.employee;

import com.opencsv.bean.*;

import java.time.LocalDate;
import java.util.Objects;

public class Employee {
    private static long countId = 0;

    private final long id = countId++;
    private String firstName;
    private Sex sex;
    @CsvDate(value = "yyyy-MM-dd")
    private LocalDate birthDate;

    public Employee() {}

    public Employee(String firstName, Sex sex, LocalDate birthDate) {
        this.firstName = firstName;
        this.sex = sex;
        this.birthDate = birthDate;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Sex getSex() {
        return sex;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee employee = (Employee) obj;
        return id == employee.id
                && firstName.equals(employee.firstName)
                && sex == employee.sex
                && birthDate.equals(employee.birthDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, sex, birthDate);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", sex=" + sex +
                ", birthDate=" + birthDate +
                '}';
    }

    public static long getCountId() {
        return countId;
    }
}
