package demo.hiber;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class App {
    public static void main(String[] args) {
        // Step 1: Configure Hibernate
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        configuration.addAnnotatedClass(Department.class);
        SessionFactory sessionFactory = configuration.buildSessionFactory();

        // Step 2: Insert Records
        insertDepartment(sessionFactory);

        // Step 3: Delete Records
        deleteDepartmentById(sessionFactory, 1);

        sessionFactory.close();
    }

    public static void insertDepartment(SessionFactory sessionFactory) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Department department = new Department();
        department.setName("Computer Science");
        department.setLocation("Building A");
        department.setHodName("Dr. Smith");

        session.save(department);

        transaction.commit();
        session.close();
        System.out.println("Department inserted successfully!");
    }

    public static void deleteDepartmentById(SessionFactory sessionFactory, int departmentId) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "DELETE FROM Department WHERE departmentId = :deptId";
        int result = session.createQuery(hql)
                            .setParameter("deptId", departmentId)
                            .executeUpdate();

        transaction.commit();
        session.close();

        if (result > 0) {
            System.out.println("Department with ID " + departmentId + " deleted successfully!");
        } else {
            System.out.println("No Department found with ID " + departmentId);
        }
    }
}
