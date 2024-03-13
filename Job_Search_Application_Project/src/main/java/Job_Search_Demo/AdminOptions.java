package Job_Search_Demo;
import Job_Search_Entity.*;

import java.text.ParseException;
import java.util.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import Job_Search_Entity.Companies;
import Job_Search_Entity.Job;
import Job_Search_Util.Job_Search_Util;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.Scanner;
import Job_Search_Entity.*;
import Job_Search_Entity.Applications;
import Job_Search_Util.*;
import Job_Search_DAO.*;
import java.util.List;

	public class AdminOptions 
	{
		public static void main(String[] args) 
		{
			Scanner scanner = new Scanner(System.in);
			
	        
	        try {
	           adminOptions(scanner);
	        } catch (ParseException e) {
	            e.printStackTrace();
	        } finally {
	            scanner.close(); // Close the scanner to prevent resource leak
	        }
	    }
	    
     public static void adminOptions(Scanner scanner) throws ParseException 
        {
	 
	     boolean running = true;
	        while (running) {
//	            System.out.println("Admin Options:");
	            System.out.println("1. Enter new  Admin Details");
	            System.out.println("2. View Students who applied to jobs");
	            System.out.println("3. View Company details");
	            System.out.println("4. View posted Jobs  ");	            
	            System.out.println("5. View existing admins");
	            System.out.println("6. Update any admin");
	            System.out.println("7. Delete any admin");
	            System.out.println("8. Delete an Student");
	            System.out.println("9. Updated Student List After Deletion");
	            System.out.println("10. view Company Details along with Jobs");
	            System.out.println("11.Exiting Admin Panel");
	            try {
	                int choice = scanner.nextInt();
	                scanner.nextLine(); // Consume newline

	                switch (choice)
	                {
	                    case 1:
	                	   EnterNewAdminDetails(scanner);
                        break; 
	                    case 2:
	                    	viewStudentsWithAppliedJobs();
	                        break;
	                    case 3:
	                    	viewCompanyDetails();
	                        break;
	                    case 4:
	                    	viewPostedJobs();
	                        break;   
	                    case 5:
	                    	viewExistingAdmins();
	                        break;	                 
	                    case 6:
	                    	updateAdmin();
	                        break;
	                    case 7:
	                    	deleteAdmin();
	                        break; 
	                    case 8:
	                    	deleteStudent();
	                        break;	                       
	                    case 9:
	                    	updateStudentList();
	                        break;	                       	             
	                    case 10:
	                    	 viewCompanyDetailsWithJobs() ;
	                        break;
	                    case 11:	                    	
	                        System.out.println("Exiting Admin Panel...");
	               
	                        break;
	                    default:
	                        System.out.println("Invalid option");
	                }
	            } catch (Exception e) {
	                System.out.println("Error: Invalid input. Please enter a valid option.");
	                scanner.nextLine(); // Consume invalid input
	            }
	        }
	    }
		public static void EnterNewAdminDetails(Scanner scanner) {
		    System.out.println("Enter New Admin Details:");
		    
		    System.out.print("First Name: ");
		    String firstName = scanner.nextLine();
		    
		    System.out.print("Last Name: ");
		    String lastName = scanner.nextLine();
		    
		    System.out.print("Email: ");
		    String email = scanner.nextLine();
		    
		    Session session = Job_Search_Util.getSessionFactory().openSession();
		    
		    HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
		    CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
		    criteriaQuery.select(builder.count(criteriaQuery.from(Admin.class))); // Replace YourEntity with your actual entity class

		    Long count = session.createQuery(criteriaQuery).getSingleResult();

		    System.out.println("Total number of records: " + count);

		    
		    count++;
		     System.out.println(count);
		     
	        String admin_Id = "ADM10" +count;
	        
	        
	        
		    // Create a new Admin object with the entered details
		    Admin newAdmin = new Admin(admin_Id,firstName, lastName, email);

		    // Call the DAO method to save the new admin to the database
		    AdminDao.saveAdmin(newAdmin);

		    System.out.println("New admin details saved successfully!");
		    
		    System.out.println("------------------------------------");
		}
	   
		  
		  public static void viewStudentsWithAppliedJobs() {
			    // Retrieve applications with associated students and jobs
			    List<Applications> applications = CompanyDao.getStudentsWithAppliedJobs();
			    if (applications.isEmpty()) {
			        System.out.println("No students found with applied jobs.");
			    } else {
			        System.out.println("Students with applied jobs:");
			        for (Applications application : applications) 
			        {
			        	System.out.println("Application ID: " + application.getApplication_Id()); 
			            System.out.println("Application Date: " + application.getApplicationDate());
			            System.out.println("Student ID: " + application.getStudent().getStudent_Id());
			            System.out.println("First Name: " + application.getStudent().getFirst_Name());
			            System.out.println("Last Name: " + application.getStudent().getLast_Name());
			            System.out.println("Email: " + application.getStudent().getEmail());
			            System.out.println("Applied Job:");
			            System.out.println("Job ID: " + application.getJob().getJob_Id());
			            System.out.println("Title: " + application.getJob().getTitle());
			            System.out.println("Company Name: " + application.getJob().getCompanyName());			         
			            System.out.println("Job Description: " + application.getJob().getJob_description());
			            System.out.println("Location: " + application.getJob().getLocation());
			    
			            System.out.println("******************************************************");
			        }
			        }
			    }

           public static void viewCompanyDetails() {
			        // Retrieve company details
			        List<Companies> companies = AdminDao.getAllCompanies();

			        if (companies.isEmpty()) {
			            System.out.println("No companies found.");
			        } else {
			            System.out.println("Company Details:");
			            for (Companies company : companies) {
			                System.out.println("Company ID: " + company.getCompany_Id());
			                System.out.println("Company Name: " + company.getCompany_Name());	
			                System.out.println("Comapany Email: " + company.getCompany_Email());	
			                System.out.println("Company Address " + company.getCompany_Address());
			                System.out.println("------------------------------------");
			            }
			        }
			    }
           
           public static void viewPostedJobs() {
   	        System.out.println("Posted Jobs:");
   	        
   	        // Retrieve and display posted jobs
   	        List<Job> postedJobs = JobDao.viewPostedJobs();
   	        
   	        for (Job job : postedJobs) 
   	        {
   	            System.out.println(job);
   	            
   	            System.out.println("------------------------------------");
   	        }
   	    }
           
           public static void viewExistingAdmins() {
               // Retrieve existing admins
               List<Admin> admins = AdminDao.getAllAdmins();

               if (admins.isEmpty()) {
                   System.out.println("No admins found.");
               } else {
                   System.out.println("Existing Admins:");
                   for (Admin admin : admins) {
                       System.out.println("Admin ID: " + admin.getAdmin_Id());
                       System.out.println("Fisrt name : " + admin.getFirst_Name());
                       System.out.println("Last name : " + admin.getLast_Name());
                       System.out.println("Email: " + admin.getEmail());
                       System.out.println("------------------------------------");
                   }
               }
           }


  public static void updateAdmin()
    {
        	    Scanner scanner = new Scanner(System.in);

        	    // Prompt the user to enter the ID of the admin to update
        	    System.out.println("Enter the ID of the admin you want to update:");
        	    String admin_Id = scanner.next();
        	    scanner.nextLine(); // Consume newline

        	    // Retrieve the admin by ID
        	    Admin admin = AdminDao.getAdminById(admin_Id);
        	    if (admin == null) {
        	        System.out.println("No admin found with ID " + admin_Id);
        	        return;
        	    }

        	    // Prompt the user to choose which attribute to update
        	    System.out.println("Choose which attribute to update:");
        	    System.out.println("1. First Name");
        	    System.out.println("2. Last Name");
        	    System.out.println("3. Email");
        	    int choice = scanner.nextInt();
        	    scanner.nextLine(); // Consume newline

        	    switch (choice) {
        	        case 1:
        	            System.out.println("Enter the new First Name:");
        	            String newFirstName = scanner.nextLine();
        	            admin.setFirst_Name(newFirstName);
        	            break;
        	        case 2:
        	            System.out.println("Enter the new Last Name:");
        	            String newLastName = scanner.nextLine();
        	            admin.setLast_Name(newLastName);
        	            break;
        	        case 3:
        	            System.out.println("Enter the new Email:");
        	            String newEmail = scanner.nextLine();
        	            admin.setEmail(newEmail);
        	            break;
        	        default:
        	            System.out.println("Invalid choice.");
        	            return;
        	    }

        	    // Save the updated admin to the database
        	    AdminDao.updateAdmin(admin);

        	    System.out.println("Admin updated successfully!");
        	    System.out.println("------------------------------------");
        	}
         
 
  public static void deleteAdmin() {
        Scanner scanner = new Scanner(System.in);

        // Prompt the admin to enter the ID of the admin to delete
        System.out.println("Enter the ID of the admin you want to delete:");
        String adminId = scanner.next();
        scanner.nextLine(); // Consume newline

        // Retrieve the admin by ID
        Admin adminToDelete = AdminDao.getAdminById(adminId);
        if (adminToDelete == null) {
            System.out.println("No admin found with ID " + adminId);
            return;
        }

        // Confirm deletion
        System.out.println("Are you sure you want to delete admin with ID " + adminId + "? (yes/no)");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            // Delete the admin
            AdminDao.deleteAdmin(adminToDelete);
            System.out.println("Admin with ID " + adminId + " has been deleted successfully.");
        } else {
            System.out.println("Deletion cancelled.");
            
        }
        System.out.println("------------------------------------");
    }
  
  public static void deleteStudent() {
	    Scanner scanner = new Scanner(System.in);

	    // Prompt the admin to enter the ID of the student to delete
	    System.out.println("Enter the ID of the student you want to delete:");
	    String student_Id = scanner.next();
	    scanner.nextLine(); // Consume newline

	    // Retrieve the student by ID
	    Student studentToDelete = StudentDao.getStudentById(student_Id);
	    if (studentToDelete == null) {
	        System.out.println("No student found with ID " + student_Id);
	        return;
	    }

	    // Confirm deletion
	    System.out.println("Are you sure you want to delete student with ID " + student_Id + "? (yes/no)");
	    String confirmation = scanner.nextLine();
	    if (confirmation.equalsIgnoreCase("yes")) {
	        // Delete the student
	        StudentDao.deleteStudent(studentToDelete);
	        System.out.println("Student with ID " + student_Id + " has been deleted successfully.");
	    } else {
	        System.out.println("Deletion cancelled.");
	    }
	    System.out.println("------------------------------------");
	}

		 
		 
public static void updateStudentList() 
{
			    // Get the updated list of students from the database
			    List<Student> students = StudentDao.getAllStudents();

			    // Display the updated student list in the UI
			    if (students.isEmpty()) {
			        System.out.println("No students found.");
			    } else {
			        System.out.println("Updated Student List:");
			        for (Student student : students) {
			            System.out.println("Student ID: " + student.getStudent_Id());
			            System.out.println("Name: " + student.getFirst_Name() + " " + student.getLast_Name());
			            System.out.println("Email: " + student.getEmail());
			            // Add more fields as needed
			            System.out.println("------------------------------------"); 
			        }
			    }
			        
	    }

		 public static void viewCompanyDetailsWithJobs() {
		        Transaction transaction = null;
		        try (Session session = Job_Search_Util.getSessionFactory().openSession()) {
		            transaction = session.beginTransaction();

		            // Retrieve all companies from the database
		            List<Companies> companies = session.createQuery("FROM Companies", Companies.class).list();
		            
		            // Iterate over each company
		            for (Companies company : companies) {
		                System.out.println("Company ID: " + company.getCompany_Id());
		                System.out.println("Company Name: " + company.getCompany_Name());		              
		                System.out.println("Location: " + company.getCompany_Email());
		                System.out.println("Jobs posted by " + company.getCompany_Name());
		            
		                // Retrieve jobs posted by this company
		                List<Job> jobs = company.getJobs();
		                if (jobs.isEmpty()) {
		                    System.out.println("No jobs posted by this company.");
		                } else {
		                    // Print details of each job
		                    for (Job job : jobs) {
		                        System.out.println("Job ID: " + job.getJob_Id());
		                        System.out.println("Job Title: " + job.getTitle());
		                        System.out.println("Job Company Name: " + job.getCompanyName());
		                        System.out.println("Job Description: " + job.getJob_description());
		                        System.out.println("Job Skills: " + job.getSkills_required());
		                        System.out.println("Job Location: " + job.getLocation());
		                        System.out.println("Job posted Date: " + job.getPostedDate());
		                        // Print other job details as needed
		                        System.out.println("------------------------------------");  
		                    }
		                    
		                    
		                }
		                   
		            }

		            transaction.commit();
		        } catch (Exception e) {
		            if (transaction != null) {
		                transaction.rollback();
		            }
		            e.printStackTrace();
		        }
		        System.out.println("------------------------------------");    
		    }
		 
		
	}


		
		

