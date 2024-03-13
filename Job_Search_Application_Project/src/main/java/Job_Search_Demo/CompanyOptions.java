package Job_Search_Demo;
import Job_Search_Entity.*;
import Job_Search_Util.Job_Search_Util;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import Job_Search_DAO.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class CompanyOptions 
{

    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        

        try {
            companyOptions(scanner);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // Close the scanner to prevent resource leak
        }
    }
   

    public static void companyOptions(Scanner scanner) throws ParseException {
    
        System.out.println("1. Enter all company details");
        System.out.println("2. Post a new job");
        System.out.println("3. view available Companies Detail");
        System.out.println("4. view posted jobs");
        System.out.println("5. View applied students for a job");
        System.out.println("6. Update any Companies Detail");
        System.out.println("7. Update any posted Jobs ");
        System.out.println("8. Exit");
        System.out.print("Enter your choice (1/2/3/4/5/6/7/8): ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) 
        {
       
              case 1:
        
        	  enterCompanyDetails(scanner);
        	  companyOptions(scanner);
             break;
        
            case 2: 
            	           	    		
//			Companies company = CompanyDao.getCompanyFromDatabase();
            	
            	CompanyOptions.postNewJob(scanner);            	
                    companyOptions(scanner);
            break;                
            case 3:
            	viewCompanies();
                companyOptions(scanner);
                break;
                
            case 4:
            	 viewPostedJobs(); 
                companyOptions(scanner);
                break;  
                                               
            case 5:
            	
            	viewStudentsWithAppliedJobs();
                companyOptions(scanner);             

                break;
             case 6:
            	
            	 updateCompanyDetails();
                companyOptions(scanner);             

                break; 
             case 7:
             	
            	 updatePostedJobs();
                companyOptions(scanner);             

                break;  
                
            case 8:      	
                System.out.println("Exiting...");
                break;
            default:
                System.out.println("Invalid option");
                companyOptions(scanner);
        }
    }

    public static void enterCompanyDetails(Scanner scanner)
    {
        System.out.println("Enter company details:");
        System.out.print("Company Name: ");
        String company_Name = scanner.nextLine();
        System.out.print("Company Email: ");
        String company_Email = scanner.nextLine();
        System.out.print("Company Address: ");
        String company_Address = scanner.nextLine();

//        Companies company = new Companies();
//        company.setCompany_Name(companyName);
//        company.setCompany_Email(companyEmail);
//        company.setCompany_Address(companyAddress);
        
        Session session = Job_Search_Util.getSessionFactory().openSession();
	    
	    HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
	    CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
	    criteriaQuery.select(builder.count(criteriaQuery.from(Companies.class))); // Replace YourEntity with your actual entity class

	    Long count = session.createQuery(criteriaQuery).getSingleResult();

	    System.out.println("Total number of records: " + count);

	    
	    count++;
	     System.out.println(count);
	     
        String company_Id = "COM10" +count;
       	    
		// Create a new Admin object with the entered details
	    Companies newcompany = new Companies(company_Id,company_Name,company_Email,company_Address);

        // Save the company details to the database
        CompanyDao.saveCompany(newcompany);
        
        System.out.println("New Company details saved successfully!");
        
        System.out.println("------------------------------------");
        
      
    }
   
    
    public static void postNewJob(Scanner scanner) throws ParseException 
	{
		

		System.out.println("Login through Email to post jobs:");
		System.out.print("Company Email: ");
		String email = scanner.nextLine();

		// Check if the company exists
		Companies company1 = CompanyDao.getCompanyByEmail(email);
		if (company1 == null) {
		    System.out.println("Company Email does not exist. Please enter company details first.");
		    enterCompanyDetails(scanner);
		    // After entering company details, call the method recursively to post the job
		   postNewJob(scanner);
		    return;
		}else 
		{	

        System.out.println("Enter job details:");
        System.out.print("Company Name: ");
        String companyname = scanner.nextLine();
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Job Description: ");       
        String jobDescription = scanner.nextLine();
        System.out.print("Skills Required: ");
        String skillsRequired = scanner.nextLine();
        System.out.print("Location: ");
        String location = scanner.nextLine();
        
        System.out.print("Posted Date (YYYY-MM-DD): ");
        String postedDateString = scanner.nextLine();

        try 
        {
        
        	 Session session = Job_Search_Util.getSessionFactory().openSession();
        	Query <Companies> query = session.createQuery("FROM Companies WHERE company_Name= :companyname", Companies.class);
        	
        	query.setParameter("companyname", companyname);
        	Companies company = query.uniqueResult(); // Assuming company name is unique

        	// Check if the company exists
        	if (company != null) {
        	    String companyId = company.getCompany_Id();
        	    
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date postedDate = dateFormat.parse(postedDateString);

            // Create a new job entity
            Job newJob = new Job();
            newJob.setCompanyName(companyname);
            
            newJob.setTitle(title);
            newJob.setCompany(company);
            newJob.setJob_description(jobDescription);
            newJob.setSkills_required(skillsRequired);          
            newJob.setLocation(location);
            newJob.setPostedDate(postedDate);
            
            HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
            criteriaQuery.select(builder.count(criteriaQuery.from(Job.class))); // Replace YourEntity with your actual entity class

            Long count = session.createQuery(criteriaQuery).getSingleResult();

            System.out.println("Total number of records: " + count);

            count++;
            System.out.println(count);
             
            String Job_Id = "JOB10" + count;
            
            Job job = new Job();
            job.setJob_Id(Job_Id);
            job.setCompanyName(companyname);
            job.setCompany(company);
            job.setJob_description(jobDescription);
            job.setLocation(location);
            job.setPostedDate(postedDate);
            job.setSkills_required(skillsRequired);
            job.setTitle(title);
            // Save the job entity to the database
            JobDao.saveJob(job);

            System.out.println("Job posted successfully!");
        } 
        }
    	catch (ParseException e) {
        System.out.println("Error: Invalid date format. Please enter the date in YYYY-MM-DD format.");
        
        System.out.println("------------------------------------");
    }
		}
}
        
        
     
	  public static void viewCompanies() {
	        System.out.println(" view available Companies Details :");
	        // Retrieve and display posted jobs
	        List<Companies> postedJobs = CompanyDao.viewPostedJobs();
	        for (Companies job : postedJobs) {
	            System.out.println(job);
	        }
	    }
	  
	  public static void viewPostedJobs() {
		    try {
		        System.out.println("Posted Jobs:");
		        
		        // Retrieve and display posted jobs
		        List<Job> postedJobs = JobDao.viewPostedJobs();
		        
		        for (Job job : postedJobs) {
		            System.out.println(job);
		            System.out.println("------------------------------------");
		        }
		    } catch (Exception e) {
		        e.printStackTrace();
		        // Handle or log the exception appropriately
		    }
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
		            System.out.println("***************************");
		            System.out.println("Applied Job:");
		            System.out.println("- Job ID: " + application.getJob().getJob_Id());
		            System.out.println("- Title: " + application.getJob().getTitle());
		            System.out.println("- Company Name: " + application.getJob().getCompanyName());
		            System.out.println("- Location: " + application.getJob().getLocation());
		            // Include other job details as needed
		            System.out.println("------------------------------------");
		        }
		        }
		    }
	  
	  public static void updateCompanyDetails() 
	  {
		    Scanner scanner = new Scanner(System.in);

		    // Prompt the user to enter the ID of the company to update
		    System.out.println("Enter the ID of the company you want to update:");
		    String company_Id = scanner.next();
		    scanner.nextLine(); // Consume newline

		    // Retrieve the company by ID
		    Companies company = CompanyDao.getCompanyById(company_Id);
		    if (company == null) {
		        System.out.println("No company found with ID " + company_Id);
		        return;
		    }

		    // Prompt the user to choose which attribute to update
		    System.out.println("Choose which attribute to update:");
		    System.out.println("1. Update Company Name");
		    System.out.println("2. Update Company Address");
		    System.out.println("3. Update Company Email");
		    int choice = scanner.nextInt();
		    scanner.nextLine(); // Consume newline

		    switch (choice) {
		        case 1:
		            System.out.println("Enter the new Company Name:");
		            String newCompanyName = scanner.nextLine();
		            company.setCompany_Name(newCompanyName);
		            break;
		        case 2:
		            System.out.println("Enter the new Address:");
		            String newAddress = scanner.nextLine();
		            company.setCompany_Address(newAddress);
		            break;
		        case 3:
		            System.out.println("Enter the new Email:");
		            String newEmail = scanner.nextLine();
		            company.setCompany_Email(newEmail);
		            break;
		        default:
		            System.out.println("Invalid choice.");
		            return;
		    }

		    // Save the updated company details to the database
		    CompanyDao.updateCompany(company);

		    System.out.println("Company details updated successfully!");
		    
		    System.out.println("------------------------------------");
		}
	  
	  
	  public static void updatePostedJobs() {
		    Scanner scanner = new Scanner(System.in);

		    // Prompt the user to enter the ID of the job to update
		    System.out.println("Enter the ID of the job you want to update:");
		    int Job_Id = scanner.nextInt();
		    scanner.nextLine(); // Consume newline

		    // Retrieve the job by ID
		    Job jobToUpdate = CompanyDao.getPostedJobById(Job_Id);
		    if (jobToUpdate == null) {
		        System.out.println("No job found with ID " + Job_Id);
		        return;
		    }

		    // Display choices for updating job details
		    System.out.println("Choose which attribute to update:");
		    System.out.println("1. Title");
		    System.out.println("2. Company Name");
		    System.out.println("3. Company Description");
		    System.out.println("4. Location");
		    System.out.println("5. Posted Date");

		    int choice = scanner.nextInt();
		    scanner.nextLine(); // Consume newline

		    // Prompt the user to enter the new value for the selected attribute
		    String newValue;
		    switch (choice) {
		        case 1:
		            System.out.println("Enter the new title of the job:");
		            newValue = scanner.nextLine();
		            jobToUpdate.getTitle();
		            break;
		        case 2:
		            System.out.println("Enter the new company name:");
		            newValue = scanner.nextLine();
		            jobToUpdate.setCompanyName(newValue);
		            break;
		        case 3:
		            System.out.println("Enter the new company description:");
		            newValue = scanner.nextLine();
		            jobToUpdate.setJob_description(newValue);
		            break;
		        case 4:
		            System.out.println("Enter the new location:");
		            newValue = scanner.nextLine();
		            jobToUpdate.setLocation(newValue);
		            break;
		        case 5:
		        	System.out.println("Enter the new posted date (yyyy-MM-dd):");
		            newValue = scanner.nextLine();
		            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		            try {
		                Date newPostedDate = dateFormat.parse(newValue);
		                jobToUpdate.setPostedDate(newPostedDate);
		            } catch (ParseException e) {
		                System.out.println("Invalid date format. Please enter the date in yyyy-MM-dd format.");
		                return;
		            }
		            break;
		        default:
		            System.out.println("Invalid choice.");
		            return;
		    }

		    CompanyDao.updatePostedJob(jobToUpdate);

		    System.out.println("Job details updated successfully!");
		    
		    System.out.println("------------------------------------");
		}

	 
  }




