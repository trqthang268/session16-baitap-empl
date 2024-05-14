package ra.spring_mvc_demo.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ra.spring_mvc_demo.dao.IEmployeeDao;
import ra.spring_mvc_demo.entity.Employee;

import java.util.List;
@Repository
public class EmployeeDaoImpl implements IEmployeeDao {
    @Autowired
    private SessionFactory sessionFactory;
    @Override
    public List<Employee> getEmployees() {
        Session session = sessionFactory.openSession();
        try {
            List list = session.createQuery("from Employee").list();
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public Employee getEmployeeById(Integer empId) {
        Session session = sessionFactory.openSession();
        try {
            Employee employee = session.get(Employee.class, empId);
            return employee;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }

    @Override
    public boolean addEmployee(Employee employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.save(employee);
            session.getTransaction().commit(); // lưu dữ liệu vào ô đĩa vl
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback(); // hồi phụ dữ liệu trc khi bị lỗi
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.update(employee);
            session.getTransaction().commit(); // lưu dữ liệu vào ô đĩa vl
            return true;
        }catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback(); // hồi phụ dữ liệu trc khi bị lỗi
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public boolean deleteEmployee(Integer empId) {
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            session.delete(getEmployeeById(empId)); // xoá theo object thông qua id
            session.getTransaction().commit(); // lưu dữ liệu vào ô đĩa vl
        }catch (Exception ex){
            ex.printStackTrace();
            session.getTransaction().rollback(); // hồi phụ dữ liệu trc khi bị lỗi
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public List<Employee> getEmployeeByName(String name) {
        Session session = sessionFactory.openSession();
        try {
            if (name == null || name.length() == 0){
                name= "%";
            }else {
                name = "%"+name+"%";
            }
            List list = session.createQuery("from Employee where employeeName like :employeeName ")
                    .setParameter("employeeName",name).list();
            return list;
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            session.close();
        }
        return null;
    }
}
