package org.katheer.controller;

import org.katheer.dto.Student;
import org.katheer.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Controller("studentController")
public class StudentControllerImpl implements StudentController {

    static {
        System.out.println("StudentControllerImpl class loading...");
    }

    @Autowired
    private StudentService studentService;
    private BufferedReader br;

    public StudentControllerImpl() {
        System.out.println("StudentControllerImpl class instantiating...");
        br = new BufferedReader(new InputStreamReader(System.in));
    }

    private static void printData(Student... students) {
        System.out.println("***********************************************STUDENT " +
                "DETAILS**********************************************");
        System.out.println(String.format("%-5s%-32s%-12s%-5s%-42s%-12s", "ID", "Student Name",
                "Dept", "CGPA", "Email", "Mobile"));
        if(students == null || students[0] == null ) {
            System.out.println("                                         " +
                    " *****NO DATA FOUND*****                             " +
                    "             ");
        } else {
            for (Student s : students) {
                System.out.println(String.format("%-5s%-32s%-12s%-5s%-42s%-12s",
                        String.valueOf(s.getId()), s.getName(), s.getDept(),
                        String.valueOf(s.getCgpa()), s.getEmail(), s.getMobile()));
            }
        }
        System.out.println("***************************************************" +
                "THE END**************************************************");
        System.out.println();
    }

    private Student getStudentDetails() throws Exception {
        System.out.println("Enter Student Details:");
        System.out.print("Student Name    : ");
        String name = br.readLine().trim();
        System.out.print("Student ID      : ");
        int id = Integer.parseInt(br.readLine());
        System.out.print("Student Dept    : ");
        String dept = br.readLine().trim();
        System.out.print("Student CGPA    : ");
        float cgpa = Float.parseFloat(br.readLine());
        System.out.print("Student Email   : ");
        String email = br.readLine().trim();
        System.out.print("Student Mobile  : ");
        String mobile = br.readLine().trim();

        Student student = new Student();
        student.setName(name);
        student.setCgpa(cgpa);
        student.setDept(dept);
        student.setEmail(email);
        student.setId(id);
        student.setMobile(mobile);
        return student;
    }

    @Override
    public void addStudent() {
        try {
            System.out.println("***ADD STUDENT***");
            String status = studentService.addStudent(this.getStudentDetails());

            if (status.equals("success")) {
                System.out.println("Student Inserted Successfully...!");
            } else if (status.equals("exists")) {
                System.out.println("Student already exists...!");
            } else if (status.equals("failed")) {
                System.out.println("Student Insertion failed...!");
            }
        } catch (Exception e) {
            System.out.println("***Exception in getting student details***");
            e.printStackTrace();
        }
    }

    @Override
    public void searchStudent() {
        while (true) {
            try {
                System.out.println("***SEARCH STUDENT***");
                System.out.println("1.Search by ID");
                System.out.println("2.Search by Name");
                System.out.println("3.Search by Department");
                System.out.println("4.Exit");
                System.out.print("Enter choice from above [Options are 1,2," +
                        "3,4] : ");
                int choice = Integer.parseInt(br.readLine());
                switch (choice) {
                    case 1:
                        System.out.print("Enter Student ID : ");
                        int id = Integer.parseInt(br.readLine());
                        printData(this.studentService.searchById(id));
                        break;
                    case 2:
                        System.out.print("Enter Student Name : ");
                        String name = br.readLine().trim();
                        printData(this.studentService.searchByName(name));
                        break;
                    case 3:
                        System.out.print("Enter Student Department : ");
                        String dept = br.readLine().trim();
                        printData(this.studentService.searchByDept(dept));
                        break;
                    case 4:
                        System.out.println("***THANKS FOR USING SEARCH " +
                                "MENU***");
                        return;
                    default:
                        System.out.println("Wrong Choice! Enter Again!!\n");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateStudent() {
        try {
            int id;
            String status = "";
            System.out.println("***UPDATE STUDENT***");
            System.out.print("Enter Student ID : ");
            id = Integer.parseInt(br.readLine());
            Student student = studentService.searchById(id);

            if(student == null) {
                status = "notexists";
            } else {
                System.out.print(String.format("%-50s : ",
                        "Student Name [ " + student.getName() + " ]") );
                String name = br.readLine().trim();
                System.out.print(String.format("%-50s : ",
                        "Student Dept [ " + student.getDept() + " ]"));
                String dept = br.readLine().trim();
                System.out.print(String.format("%-50s : ",
                        "Student CGPA [ " + student.getCgpa() + " ]"));
                float cgpa = Float.parseFloat(br.readLine().trim());
                System.out.print(String.format("%-50s : ",
                        "Student Email [ " + student.getEmail() + " ]"));
                String email = br.readLine().trim();
                System.out.print(String.format("%-50s : ",
                        "Student Mobile [ " + student.getMobile() + " ]"));
                String mobile = br.readLine().trim();

                student.setName(name);
                student.setDept(dept);
                student.setCgpa(cgpa);
                student.setEmail(email);
                student.setMobile(mobile);

                status = studentService.updateStudent(student);
            }


            if (status.equals("success")) {
                System.out.println("Student Updated Successfully...!\n");
            } else if (status.equals("notexists")) {
                System.out.println("Student not exists...!\n");
            } else if (status.equals("failed")) {
                System.out.println("Student update failed...!\n");
            }
        } catch (Exception e) {
            System.out.println("***Exception in getting student details***");
            e.printStackTrace();
        }
    }

    @Override
    public void removeStudent() {
        try {
            System.out.println("***REMOVE STUDENT***");
            System.out.print("Enter Student ID : ");
            int id = Integer.parseInt(br.readLine());
            String status = studentService.removeStudent(id);

            if (status.equals("success")) {
                System.out.println("Student Removed successfully..!");
            } else if (status.equals("notexists")) {
                System.out.println("No record found...!");
            } else {
                System.out.println("Student Removal Failed..!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
