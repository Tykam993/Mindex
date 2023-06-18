package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.dao.CompensationRepository;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CompensationRepository compensationRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    /**
     * Creates a ReportingStructure for the given employeeId
     *
     * @param  employeeId  an employee id
     * @return Compensation the Compensation for the given employee
     */
    @Override
    public ReportingStructure createReportingStructure(String employeeId) {
        LOG.debug("Creating ReportingStructure for employee with id [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        Queue<Employee> directReports = new ArrayDeque<Employee>();
        directReports.add(employee);
        List<Employee> allReports = new ArrayList<>();

        while (!directReports.isEmpty()) {
            Employee emp = directReports.remove();

            List<Employee> empReports = emp.getDirectReports();
            for(Employee empReport: empReports) {
                // Get the full object to access the direct reports
                Employee temp = employeeRepository.findByEmployeeId(empReport.getEmployeeId());
                if (temp != null) {
                    directReports.add(temp);
                    allReports.add(temp);
                }
            }
        }
        Integer totalReports = allReports.size();
        ReportingStructure repStruc = new ReportingStructure(employee.getEmployeeId(), totalReports);

        return repStruc;
    }

     /**
     * Creates a Compensation for the given employee ID, salary, and date
     *
     *
     * @param  employeeId  an employee id
     * @param  salary an integer representing the salary
     * @param  effectiveDate a string date in dd/mm/yyyy format
     * @return Compensation the Compensation for the given employee
     */
    @Override
    public Compensation createCompensation(String employeeId, Integer salary, String effectiveDate) {
        LOG.debug("Creating Compensation for employee [{}]", employeeId);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        Compensation comp = new Compensation(employee, salary, effectiveDate);
        comp.setCompensationId(UUID.randomUUID().toString());
        compensationRepository.insert(comp);
        return comp;
    }

    /**
     * Retrieves a Compensation for the given employee ID
     *
     *
     * @param  employeeId  an employee id
     * @return Compensation the Compensation for the given employee
     */
    @Override
    public Compensation readCompensation(String employeeId) {
        LOG.debug("Reading Compensation for employee with id [{}]", employeeId);

        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + employeeId);
        }
        Compensation comp = compensationRepository.findByEmployee(employee);
        if (comp == null) {
            throw new RuntimeException("No compensation object exists for given employee");
        }

        return comp;
    }
}
