package net.javaguides.ems.service;

import net.javaguides.ems.dto.EmployeeDTO;

import java.util.List;

/**
 * @author Sergei Aleshchenko
 */
public interface EmployeeService {
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    EmployeeDTO getEmployeeById(Long employeeId);

    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO updateEmployee(Long employeeId, EmployeeDTO updatedEmployee);

    void deleteEmployee(Long employeeId);
}
