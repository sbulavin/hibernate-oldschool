package com.examples.hibernate.oldschool.dao.impl;


import com.examples.hibernate.oldschool.dao.EmployeeDAO;
import com.examples.hibernate.oldschool.entities.Employee;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO{

    private SessionFactory factory;

    public EmployeeDAOImpl(SessionFactory factory) {
        this.factory = factory;
    }

    @Override
    public Employee getEmployeeById(int employeeId){
        Session session = factory.openSession();
        Transaction tx = null;
        Employee employee = null;


        try {
            tx = session.beginTransaction();
            employee = session.get(Employee.class, employeeId);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return employee;
    }


    @Override
    public List<Employee> getAllEmployees(){
        Session session = factory.openSession();
        Transaction tx = null;
        List<Employee> employees = new ArrayList();

        try {
            tx = session.beginTransaction();
            employees = session.createQuery("FROM Employee").list();
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }

        return employees;
    }

    @Override
    public int saveEmployee(Employee employee){
        Session session = factory.openSession();
        Transaction tx = null;
        Integer employeeID = null;

        try {
            tx = session.beginTransaction();
            employeeID = (Integer) session.save(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
        return employeeID;
    }

    @Override
    public void updateEmployee(Employee employee){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            session.update(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    @Override
    public void deleteEmployee(int employeeId){
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();
            Employee employee = (Employee)session.get(Employee.class, employeeId);
            session.delete(employee);
            tx.commit();
        } catch (HibernateException e) {
            if (tx!=null) tx.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}
