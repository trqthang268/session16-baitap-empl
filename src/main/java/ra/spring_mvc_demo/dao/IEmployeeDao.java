package ra.spring_mvc_demo.dao;

import ra.spring_mvc_demo.entity.Employee;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface IEmployeeDao {
    List<Employee> getEmployees();
    Employee getEmployeeById(Integer empId);
    public boolean addEmployee(Employee employee);
    public boolean updateEmployee(Employee employee);
    public boolean deleteEmployee(Integer empId);
    List<Employee> getEmployeeByName(String name);
}
