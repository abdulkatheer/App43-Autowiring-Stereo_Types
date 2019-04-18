package org.katheer.test;

import org.katheer.controller.StudentController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
    private static ApplicationContext context;
    private static BufferedReader br;
    private static StudentController studentController;

    static {
        System.out.println("***Application Debugging Details***");
        System.out.println("Client class loading...");
    }

    public Client() {
        System.out.println("Client class instantiating...");
    }

    public static void main(String[] args) {
        context = new ClassPathXmlApplicationContext("org" +
                "/katheer/resource/applicationContext.xml");
        try {
            br = new BufferedReader(new InputStreamReader(System.in));
        } catch (Exception e) {
            e.printStackTrace();
        }
        studentController = (StudentController) context.getBean(
                "studentController");

        String[] beans = context.getBeanDefinitionNames();
        for (String bean : beans) {
            System.out.println(bean);
        }

        System.out.println("***Application Starts***");

        int choice = 0;
        while (true) {
            try {
                System.out.println("******STUDENT DATABASE******");
                System.out.println("1.ADD STUDENT");
                System.out.println("2.UPDATE STUDENT");
                System.out.println("3.SEARCH STUDENT");
                System.out.println("4.DELETE STUDENT");
                System.out.println("5.EXIT");
                System.out.print("Enter choice from above [Options are 1, 2, " +
                        "3, 4, 5] : ");
                choice = Integer.parseInt(br.readLine());

                switch (choice) {
                    case 1:
                        studentController.addStudent();
                        break;
                    case 2:
                        studentController.updateStudent();
                        break;
                    case 3:
                        studentController.searchStudent();
                        break;
                    case 4:
                        studentController.removeStudent();
                        break;
                    case 5:
                        System.out.println("******THANKS FOR USING OUR " +
                                "SERVICE******");
                        return;
                    default:
                        System.out.println("Wrong choice! Enter again !!");
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
