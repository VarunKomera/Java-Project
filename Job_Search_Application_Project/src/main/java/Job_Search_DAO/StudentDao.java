package Job_Search_DAO;

import java.util.List;
import Job_Search_Util.Job_Search_Util;
import Job_Search_Util.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import Job_Search_DAO.CompanyDao;
import Job_Search_Entity.Applications;
import Job_Search_Entity.Job;
import Job_Search_Entity.Student;
import Job_Search_Util.*;
import org.hibernate.query.Query;

public class StudentDao {

		public static void saveStudent(Student student) {
	        Transaction transaction = null;
	        Session session=null;
	        try {
	        	
	         session = Job_Search_Util.getSessionFactory().openSession(); 
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            session.merge(student);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        finally {
	            if (session != null) {
	                session.close();
	            }
	            //Job_Search_Util.getSessionFactory().close();
	        }
	        
	    }

	    public static void updateStudent(Student student) {
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // save the student object
	            session.merge(student);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    public void deleteStudent(long student_Id) {

	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();

	            // Delete a instructor object
	            Student student = session.get(Student.class, student_Id);
	            if (student != null) {
	                session.remove(student);
	                System.out.println("student  is deleted");
	            }

	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    public Student getStudent(int student_Id) {

	        Transaction transaction = null;
	        Student student = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // get an instructor object
	           student = session.get(Student.class, student_Id);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return  student;
	    }	
	
	    
	    public int getStudentCount() 
	    {
	        Transaction transaction = null;
	        long count = 0;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            count = (long) session.createQuery("select count(*) from Student").uniqueResult();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return (int) count;
	    }
	    
	    
	    public static long getcountStudent() 
	    {

	        Transaction transaction = null;
	        long count=0;
	        Student student = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();
	            // get an instructor object
	            Query<Long> query = session.createQuery("select count(*) from Student", Long.class);
	            
	            count = query.getFirstResult();
	            System.out.println("No. of students "+ count);
	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return (int) count;
	    }
	 
	    public static List<Job> getAvailableJobs() {
	        List<Job> availableJobs = null;
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            // Adjust this query based on your entity and database structure
	            String hql = "FROM Job j WHERE j.selected = false";
	            Query<Job> query = session.createQuery(hql, Job.class);
	            availableJobs = query.list();
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return availableJobs;
	    }

	    public static List<Job> getAppliedJobs(long student_Id)
	    {
	        Transaction transaction = null;
	        List<Job> appliedJobs = null;

	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();

	            // Assuming you have a Job entity with a property 'selected'
	            String hql = "SELECT j FROM Job j JOIN FETCH j.students s WHERE s.id = :student_Id";
	            Query<Job> query = session.createQuery(hql, Job.class);
	            query.setParameter("student_Id", student_Id);

	            appliedJobs = query.list();

	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }

	        return appliedJobs;
	    }
	    
	    public static void deleteStudent(Student student) {
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // start a transaction
	            transaction = session.beginTransaction();

	            // Delete the student object
	            session.delete(student);
	            System.out.println("Student is deleted");

	            // commit transaction
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	    }

	    public static List<Student> getAllStudents() {
	        List<Student> students = null;
	        Session session = null;
	        try {
	            session = Job_Search_Util.getSessionFactory().openSession();
	            session.beginTransaction();
	            Query<Student> query = session.createQuery("FROM Student", Student.class);
	            students = query.getResultList();
	            session.getTransaction().commit();
	        } catch (Exception e) {
	            if (session != null) {
	                session.getTransaction().rollback();
	            }
	            e.printStackTrace();
	        } finally {
	            if (session != null) {
	                session.close();
	            }
	        }
	        return students;
	    }
	    
	    public static Student getStudentById(String student_Id) {
	        Student student = null;
	        Transaction transaction = null;
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            transaction = session.beginTransaction();
	            student = session.get(Student.class, student_Id);
	            transaction.commit();
	        } catch (Exception e) {
	            if (transaction != null) {
	                transaction.rollback();
	            }
	            e.printStackTrace();
	        }
	        return student;
	    }
	    public static List<Applications> getAppliedJobsByStudent(String Email) {
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            // Retrieve student information by email
	            Student student = getStudentByEmail(Email);
	            if (student == null) {
	                System.out.println("Student with Email " + Email + " does not exist.");
	                return null;
	            }

	            // Retrieve applied jobs for the student
	            String hql = "FROM Applications WHERE student = :student";
	            Query<Applications> query = session.createQuery(hql, Applications.class);
	            query.setParameter("student", student);
	            return query.list();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	
	    
	    public static Student getStudentByEmail(String Email) 
	    {
	        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
	            String hql = "FROM Student WHERE Email = :Email";
	            Query<Student> query = session.createQuery(hql, Student.class);
	            query.setParameter("Email", Email);
	            return query.uniqueResult();
	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

		
	
	}
