package org.katheer.service;

import org.katheer.dao.StudentDao;
import org.katheer.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("studentService")
public class StudentServiceImpl implements StudentService {
    static {
        System.out.println("StudentServiceImpl class loading...");
    }

    @Autowired
    private StudentDao studentDao;

    public StudentServiceImpl() {
        System.out.println("StudentServiceImpl class instantiating...");
    }

    @Override
    public String addStudent(Student student) {
        return studentDao.add(student);
    }

    @Override
    public Student searchById(int id) {
        return studentDao.getById(id);
    }

    @Override
    public Student[] searchByName(String name) {
        return studentDao.getByName(name);
    }

    @Override
    public Student[] searchByDept(String dept) {
        return studentDao.getByDept(dept);
    }

    @Override
    public String updateStudent(Student student) {
        return studentDao.update(student);
    }

    @Override
    public String removeStudent(int id) {
        return studentDao.remove(id);
    }
}
