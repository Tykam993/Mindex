package com.mindex.challenge.service;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface EmployeeService {
    Employee create(Employee employee);
    Employee read(String id);
    Employee update(Employee employee);
    ReportingStructure createReportingStructure(String employeeId);
    Compensation createCompensation(String employeeId, Integer salary, String effectiveDate);
    Compensation readCompensation(String employeeId);

}
