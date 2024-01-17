package net.javaguides.ems.repository;

import net.javaguides.ems.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Sergei Aleshchenko
 */
public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {

}
