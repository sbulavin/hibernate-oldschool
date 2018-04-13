package com.examples.hibernate.oldschool;


import com.examples.hibernate.oldschool.dao.EmployeeDAO;
import com.examples.hibernate.oldschool.dao.impl.EmployeeDAOImpl;
import com.examples.hibernate.oldschool.entities.Employee;
import com.examples.hibernate.oldschool.entities.Tool;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class ManageEmployee {
    private static SessionFactory factory;
    private static EmployeeDAO employeeDAO;
    public static void main(String[] args) {

        try {
            factory = new Configuration().configure().addAnnotatedClass(Employee.class)
                    .addAnnotatedClass(Tool.class).buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }

        employeeDAO = new EmployeeDAOImpl(factory);

        ManageEmployee manageEmployee = new ManageEmployee();

      /* Add few employee records in database */

        Set<Tool> tools;

        tools = new HashSet<>();
        tools.add(new Tool("hummer", "red"));
        tools.add(new Tool("screwdriver", "red"));
        Integer empID1 = manageEmployee.addEmployee("Zara", "Ali", 1000, tools);

        tools = new HashSet<>();
        tools.add(new Tool("hummer", "green"));
        tools.add(new Tool("screwdriver", "green"));

        Integer empID2 = manageEmployee.addEmployee("Daisy", "Das", 5000, tools);

        tools = new HashSet<>();
        tools.add(new Tool("hummer", "yellow"));
        tools.add(new Tool("screwdriver", "yellow"));
        Integer empID3 = manageEmployee.addEmployee("John", "Paul", 10000, tools);

      /* List down all the employees */
        manageEmployee.listEmployees();

      /* Update employee's records */
        manageEmployee.updateEmployeeSalary(empID1, 5000);

        tools = new HashSet<>();
        tools.add(new Tool("hummer", "brown"));
        tools.add(new Tool("screwdriver", "brown"));
        manageEmployee.updateEmployeeTools(empID1, tools);


      /* Delete an employee from the database */
        manageEmployee.deleteEmployee(empID2);

      /* List down new list of the employees */
        manageEmployee.listEmployees();

        factory.close();
    }

    /* Method to CREATE an employee in the database */
    public Integer addEmployee(String fname, String lname, int salary, Set<Tool> tools){
        Employee employee = new Employee(fname, lname, salary);
        tools.forEach(r -> r.setEmployee(employee));
        employee.setTools(tools);
        return employeeDAO.saveEmployee(employee);
    }

    /* Method to  READ all the employees */
    public void listEmployees( ){
        List<Employee> employees = employeeDAO.getAllEmployees();

        for (Object emp: employees){
            Employee employee = (Employee) emp;
            System.out.print("First Name: " + employee.getFirstName());
            System.out.print("  Last Name: " + employee.getLastName());
            System.out.println("  Salary: " + employee.getSalary());
            System.out.println("  Tools: " + employee.getTools());
        }

    }

    /* Method to UPDATE salary for an employee */
    public void updateEmployeeSalary(Integer employeeId, int salary ){
        Employee employee = employeeDAO.getEmployeeById(employeeId);
        employee.setSalary(salary);
        employeeDAO.updateEmployee(employee);
    }

    public void updateEmployeeTools(Integer employeeId, Set<Tool> tools){
        Employee employee = employeeDAO.getEmployeeById(employeeId);
        tools.forEach(r -> r.setEmployee(employee));
        employee.getTools().clear();
        employee.getTools().addAll(tools);
        employeeDAO.updateEmployee(employee);
    }

    /* Method to DELETE an employee from the records */
    public void deleteEmployee(Integer employeeId){
       employeeDAO.deleteEmployee(employeeId);
    }
}
