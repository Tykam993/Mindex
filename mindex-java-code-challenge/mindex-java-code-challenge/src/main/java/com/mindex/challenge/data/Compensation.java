package com.mindex.challenge.data;

import com.mindex.challenge.data.Employee;
import org.springframework.data.annotation.Transient;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Compensation {
    private String compensationId;
    private Employee employee;
    private Integer salary;
    private String effectiveDate;

    public Compensation(
            Employee employee,
            Integer salary,
            String effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;

    }

    public String getCompensationId() { return compensationId; }

    public void setCompensationId(String compensationId) { this.compensationId = compensationId; }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

}
