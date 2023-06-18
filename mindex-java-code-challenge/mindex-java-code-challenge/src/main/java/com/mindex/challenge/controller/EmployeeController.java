package com.mindex.challenge.controller;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;


@RestController
public class EmployeeController {
    private static final Logger LOG = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/employee")
    public Employee create(@RequestBody Employee employee) {
        LOG.debug("Received employee create request for [{}]", employee);

        return employeeService.create(employee);
    }

    @GetMapping("/employee/{id}")
    public Employee read(@PathVariable String id) {
        LOG.debug("Received employee update request for id [{}]", id);

        return employeeService.read(id);
    }

    @PutMapping("/employee/{id}")
    public Employee update(@PathVariable String id, @RequestBody Employee employee) {
        LOG.debug("Received employee create request for id [{}] and employee [{}]", id, employee);

        employee.setEmployeeId(id);
        return employeeService.update(employee);
    }

    /**
     * Creates a ReportingStructure for the given employee ID
     *
     *
     * @param  id  an employee id in the URL
     * @return ReportingStructure the reporting structure for the given employee
     */
    @GetMapping("/employee/report/{id}")
    public ReportingStructure createReportStructure(@PathVariable String id) {
        LOG.debug("Received ReportStructure create request for employee id [{}]", id);
        return employeeService.createReportingStructure(id);
    }

    /**
     * Creates a Compensation object with the request body information
     *
     * @return Compensation the Compensation for the employeeId in the payload
     */
    @PostMapping("/employee/create-compensation")
    public Compensation createCompensation(
            @RequestBody HashMap<String, Object> payload) {
        String employeeId = payload.get("employeeId").toString();
        Integer salary = (Integer) payload.get("salary");
        String effectiveDate = payload.get("date").toString();
        LOG.debug("Received Compensation create request for employee: [{}]", employeeId);

        return employeeService.createCompensation(employeeId, salary, effectiveDate);
    }

    /**
     * Retrieves a Compensation for the given employee ID
     *
     *
     * @param  id  an employee id
     * @return Compensation the Compensation for the given employee
     */
    @GetMapping("/employee/compensation/{id}")
    public Compensation readCompensation(@PathVariable String id) {
        LOG.debug("Received Compensation read request for employee id [{}]", id);

        return employeeService.readCompensation(id);
    }

}
