package org.katheer.service;

import org.katheer.dto.Student;

public interface StudentService {
    public String addStudent(Student student);

    public Student searchById(int id);

    public Student[] searchByName(String name);

    public Student[] searchByDept(String dept);

    public String updateStudent(Student student);

    public String removeStudent(int id);
}
