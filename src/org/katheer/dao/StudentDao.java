package org.katheer.dao;

import org.katheer.dto.Student;

public interface StudentDao {
    public String add(Student student);

    public Student getById(int id);

    public Student[] getByName(String name);

    public Student[] getByDept(String dept);

    public String update(Student student);

    public String remove(int id);
}
