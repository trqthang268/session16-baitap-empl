package ra.spring_mvc_demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.spring_mvc_demo.dao.IEmployeeDao;
import ra.spring_mvc_demo.dao.impl.EmployeeDaoImpl;
import ra.spring_mvc_demo.entity.Employee;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private IEmployeeDao employeeDao;

    @RequestMapping(value = {"/","/initInsertEmployee"})
    public String initInsertEmployee(Model model){
        Employee e = new Employee();
        model.addAttribute("e", e);
        return "addemployee";
    }

    @RequestMapping("/insertEmployee")
    public String insertEmployee(@ModelAttribute("employee") Employee empl,Model model){
        boolean bl = employeeDao.addEmployee(empl);
        if (bl){
            return "redirect:/loadEmployees";
        }else{
            model.addAttribute("err","Insert failed!");
            model.addAttribute("e",empl);
            return "addemployee";
        }
    }

    @RequestMapping("/loadEmployees")
    public String loadEmployee(Model model){
        List<Employee> list = employeeDao.getEmployees();
        model.addAttribute("list",list);
        return "listemployee";
    }

    @GetMapping("/updateemployee/{id}")
    public String initUpdate(@PathVariable("id") Integer id, Model model){
        Employee employee = employeeDao.getEmployeeById(id);
        model.addAttribute("e", employee);
        return "updateemployee";
    }

    @PostMapping("/updateemployee")
    public String update(@ModelAttribute("e") Employee employee){
        employeeDao.updateEmployee(employee);
        return "redirect:/loadEmployees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        employeeDao.deleteEmployee(id);
        return "redirect:/loadEmployees";
    }

}
