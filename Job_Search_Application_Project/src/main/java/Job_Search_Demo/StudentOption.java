package Job_Search_Demo;

import Job_Search_Entity.*;
import Job_Search_Util.Job_Search_Util;
import jakarta.persistence.criteria.CriteriaQuery;

import java.util.Date;
import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;

import Job_Search_DAO.*;

public class StudentOption 
{

	public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);

        //System.out.println("Welcome to the Job Search Application!");
        

        try {
            studentOptions(scanner);
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // Close the scanner to prevent resource leak
        }
    }
   
		

public static void studentOptions(Scanner scanner) throws ParseException 
{
  
 
        System.out.println("1. Enter Student details");
        System.out.println("2. View posted jobs");
        System.out.println("3. Apply for a job");
        System.out.println("4. View applied jobs");
        System.out.println("5. If you want update any student Details ");
        System.out.println("6. Exit");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                enterStudentDetails(scanner);
                studentOptions(scanner);
                break;
            case 2:
                //viewAvailableJobs();
            	viewPostedJobs();
                studentOptions(scanner);
                break;
            case 3:
                applyForJob();
                studentOptions(scanner);
                break;
            case 4: 
            	

                System.out.print("Enter your Email: ");
                String Email = scanner.next();

                System.out.println("You entered: " + Email);

            	viewAppliedJobsByStudent(Email);
                studentOptions(scanner);
                break;
            case 5:
            	updateStudentDetails(); 
                studentOptions(scanner);
                break;                                  
                              
            case 6:
                System.out.println(" *******Thank you 👋👋👋🙋‍♀️🙋‍♀️🙋‍♀️🙋‍♀️🙋‍♂️🙋‍♂️*********");
                break;
            default:
                System.out.println("Invalid option");
                studentOptions(scanner);
        }
    }

public static void enterStudentDetails(Scanner scanner) 
{
    System.out.println("Enter Student Details:");

    System.out.print("Enter your First Name: ");
    String first_Name = scanner.nextLine();
    
    System.out.print("Enter your Last Name: ");
    String last_Name = scanner.nextLine();

    String email;
    do {
        System.out.print("Enter your Email: ");
        email = scanner.nextLine();
        // Check if the email ends with "@gmail.com"
        if (!email.toLowerCase().endsWith("@gmail.com")) {
            System.out.println("Invalid email format! Please enter a Gmail address.");
        }
    } while (!email.toLowerCase().endsWith("@gmail.com")); 
    
    System.out.print("Enter your Highest Education: ");
    String education = scanner.nextLine();

    String phone_number;
    while (true) {
        System.out.print("Enter your Phone Number: ");
         phone_number = scanner.nextLine();

        // Validate phone number format
        if (!isValidPhoneNumber(phone_number)) {
            System.out.println("Invalid phone number format! Please enter a 10-digit phone number.");
        } else {
            System.out.println("Valid phone number entered: " + phone_number);
            break; // Exit the loop if a valid phone number is entered
        }
    }
   
    System.out.print("Enter your Known Skills: ");
    String skills = scanner.nextLine();
    
    System.out.print("Enter your Experience: ");
    String experience = scanner.nextLine();

    System.out.print("Upload your Resume: ");
    String resume = scanner.nextLine();
    
    Session session = Job_Search_Util.getSessionFactory().openSession();
    
    HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
    CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
    criteriaQuery.select(builder.count(criteriaQuery.from(Student.class))); // Replace YourEntity with your actual entity class

    Long count = session.createQuery(criteriaQuery).getSingleResult();

    System.out.println("Total number of records: " + count);

    count++;
    System.out.println(count);
     
    String student_Id = "STD10" + count;
   	    
    // Create a new Student object with the entered details
//    Student newstudent = new Student(student_Id, first_Name, last_Name, email, education, phone_number, skills, experience, resume);
      Student newstudent = new Student();
      newstudent.setStudent_Id(student_Id);
      newstudent.setFirst_Name(first_Name);
      newstudent.setLast_Name(last_Name);
      newstudent.setEmail(email);
      newstudent.setEducation(education);
      newstudent.setPhone_number(phone_number);
      newstudent.setExperience(experience);
      newstudent.setSkills(skills);
      newstudent.setResume(resume);
    // Save the student details to the database
    StudentDao.saveStudent(newstudent);
    
    System.out.println("Student details saved successfully!");
    System.out.println("------------------------------------");
}

private static boolean isValidPhoneNumber(String phoneNumber) {
    // Remove any non-numeric characters
    String numericPhone = phoneNumber.replaceAll("[^0-9]", "");

    // Check if the length is exactly 10 digits
    return numericPhone.length() == 10;
}



    
    public static void viewPostedJobs() 
    {
        List<Job> postedJobs = JobDao.getAllPostedJobs();

        if (postedJobs.isEmpty()) {
            System.out.println("No jobs are currently posted.");
        } else {
            System.out.println("Posted Jobs are :");
            for (Job job : postedJobs) {
                System.out.println(job);
                System.out.println("------------------------------------");
            }
        }
        
        System.out.println("------------------------------------");
    }
    
//    
//    public static void viewPostedJobs() {
//        System.out.println("Posted Jobs:");
//        
//        // Retrieve and display posted jobs for the student
//        List<Job> postedJobs = JobDao.getAllPostedJobs();
//        
//        if (postedJobs != null && !postedJobs.isEmpty()) {
//            for (Job job : postedJobs) {
//                // Print job details
 //                  System.out.println("Job Id: " + job.);
//                System.out.println("Job Title: " + job.getTitle());
//                System.out.println("Job Description: " + job.getCompany_description());
//                System.out.println("Skills Required: " + job.getSkills_required());
//                System.out.println("Location: " + job.getLocation());
//                System.out.println("Posted Date: " + job.getPostedDate());
//
//                // Print related company details
//                System.out.println("Company Name: " + job.getCompany().getCompany_Name());
//                System.out.println("Company Email: " + job.getCompany().getCompany_Email());
//                System.out.println("Company Address: " + job.getCompany().getCompany_Address());
//                
//                System.out.println("------------------------------------");
//            }
//        } else {
//            System.out.println("No posted jobs available for the student.");
//        }
//    }
//    
    public static void applyForJob() 
    {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the ID of the job you want to apply for:");
        String Job_Id = scanner.next();
        scanner.nextLine(); // Consume newline

        // Retrieve the job details using the entered job ID
        Job job = JobDao.getJobById(Job_Id);    
        if (job == null) {
            System.out.println("No job found with ID " + Job_Id);
            return;
        }

        System.out.print("Enter your first name:");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name:");
        String lastName = scanner.nextLine();

        System.out.print("Enter your email:");
        String Email = scanner.nextLine();

        // Retrieve the student details using the entered email
        Student student = StudentDao.getStudentByEmail(Email);    
        if (student == null) {
            System.out.println("No student found with email " + Email);
            return;
        }

        // Set the first name and last name of the student
        student.setFirst_Name(firstName);
        student.setLast_Name(lastName);

        Session session = Job_Search_Util.getSessionFactory().openSession();
        
        HibernateCriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Long> criteriaQuery = builder.createQuery(Long.class);
        criteriaQuery.select(builder.count(criteriaQuery.from(Applications.class))); // Replace YourEntity with your actual entity class

        Long count = session.createQuery(criteriaQuery).getSingleResult();

        System.out.println("Total number of records: " + count);

        count++;
        System.out.println(count);
         
        String app_Id = "APP10" + count;
        
        // Create an application object with job details, student information, and current date
//        Applications application = new Applications(job, student, new Date());
        Applications application = new Applications();
        application.setApplication_Id(app_Id);
        application.setStudent(student);
        application.setJob(job);
        application.setApplicationDate(new Date());
        // Save the application to the database
        ApplicationDao.saveApplication(application);

        System.out.println("Application submitted successfully!");
        System.out.println("------------------------------------");
       
    }
     

    
    public static void viewAppliedJobsByStudent(String Email) 
    {
        List<Applications> appliedJobs = StudentDao.getAppliedJobsByStudent(Email);

        if (appliedJobs == null || appliedJobs.isEmpty()) {
            System.out.println("No applied jobs found for the student.");
        } else {
            System.out.println("Applied Jobs:");
            for (Applications application : appliedJobs) {
                System.out.println("Application ID: " + application.getApplication_Id());
                System.out.println("Application Date: " + application.getApplicationDate());
                System.out.println("Job ID: " + application.getJob().getJob_Id());
                System.out.println("Company Name: " + application.getJob().getCompanyName());
                System.out.println("Title: " + application.getJob().getTitle());
                System.out.println("Location : " + application.getJob().getLocation());
       
                System.out.println("------------------------------------");
            }
        }
    }

    
	public static void updateStudentDetails() 
    {
    	Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the email of the student you want to update:");
        String Email = scanner.nextLine();

        // Retrieve the student by email
        Student studentToUpdate = StudentDao.getStudentByEmail(Email);
        if (studentToUpdate == null) {
            System.out.println("No student found with email " + Email);
            return;
        }

        // Display a menu for updating different attributes
        System.out.println("Choose which attribute to update:");
        System.out.println("1. First Name");
        System.out.println("2. Last Name");
        System.out.println("3. Education");
        System.out.println("4. Phone Number");
        System.out.println("5. Skills");
        System.out.println("6. Experience");
        System.out.println("7. Resume");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.print("Enter the new First Name:");
                String newFirstName = scanner.nextLine();
                studentToUpdate.setFirst_Name(newFirstName);
                break;
            case 2:
                System.out.print("Enter the new Last Name:");
                String newLastName = scanner.nextLine();
                studentToUpdate.setLast_Name(newLastName);
                break;
            case 3:
                System.out.print("Enter the new Education:");
                String newEducation = scanner.nextLine();
                studentToUpdate.setEducation(newEducation);
                break;
            case 4:
                System.out.print("Enter the new Phone Number:");
                String newPhoneNumber = scanner.nextLine();
                studentToUpdate.setPhone_number(newPhoneNumber);
                break;
            case 5:
                System.out.print("Enter the new Skills:");
                String newSkills = scanner.nextLine();
                studentToUpdate.setSkills(newSkills);
                break;
            case 6:
                System.out.print("Enter the new Experience:");
                String newExperience = scanner.nextLine();
                studentToUpdate.setExperience(newExperience);
                break;
            case 7:
                System.out.print("Enter the new Resume:");
                String newResume = scanner.nextLine();
                studentToUpdate.setResume(newResume);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        // Update the student details in the database
        StudentDao.updateStudent(studentToUpdate);

        System.out.println("Student details updated successfully!");
        
        System.out.println("------------------------------------");
    }

}
