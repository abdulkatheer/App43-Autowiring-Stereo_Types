package org.katheer.dao;

import org.katheer.dto.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@Repository("studentDao")
public class StudentDaoImpl implements StudentDao {
    static {
        System.out.println("StudentDaoImpl class loading...");
    }

    @Autowired
    private DataSource dataSource;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet rs;

    public StudentDaoImpl() {
        System.out.println("StudentDaoImpl class instantiating...");
    }

    private boolean isExists(Student student) {
        boolean isExists = false;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement
                    ("SELECT * FROM student WHERE id = ?");
            preparedStatement.setInt(1, student.getId());
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                isExists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isExists;
    }

    @Override
    public String add(Student student) {
        String status = "";

        if (!this.isExists(student)) {
            try {
                preparedStatement = connection.prepareStatement("INSERT INTO " +
                        "student VALUES(?, ?, ?, ?, ?, ?)");
                preparedStatement.setInt(1, student.getId());
                preparedStatement.setString(2, student.getName());
                preparedStatement.setString(3, student.getDept());
                preparedStatement.setFloat(4, student.getCgpa());
                preparedStatement.setString(5, student.getEmail());
                preparedStatement.setString(6, student.getMobile());

                int rowCount = preparedStatement.executeUpdate();

                if (rowCount == 1) {
                    status = "success";
                } else {
                    status = "failed";
                }
            } catch (Exception e) {
                System.out.println("***Exception in getting connection while " +
                        "adding student***");
                e.printStackTrace();
                status = "failed";
            }
        } else {
            status = "exists";
        }

        return status;
    }

    private Student getRecordData(ResultSet rs) {
        Student student = new Student();
        try {
            student.setId(rs.getInt("id"));
            student.setName(rs.getString("name"));
            student.setMobile(rs.getString("mobile"));
            student.setEmail(rs.getString("email"));
            student.setCgpa(rs.getFloat("cgpa"));
            student.setDept(rs.getString("dept"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return student;
    }

    private int getRecordCount(ResultSet rs) {
        int rowcount = 0;
        try {
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rowcount;
    }

    @Override
    public Student getById(int id) {
        Student s = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " +
                    "student WHERE id = ?");
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            int rowcount = this.getRecordCount(rs);

            if (rowcount != 0) {
                while (rs.next()) {
                    s = this.getRecordData(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public Student[] getByName(String name) {
        Student[] s = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " +
                    "student WHERE name = ?");
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            int rowcount = this.getRecordCount(rs);

            if (rowcount != 0) {
                s = new Student[rowcount];
                int i = 0;
                while (rs.next()) {
                    s[i] = this.getRecordData(rs);
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public Student[] getByDept(String dept) {
        Student[] s = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM " +
                    "student WHERE dept = ?");
            preparedStatement.setString(1, dept);
            ResultSet rs = preparedStatement.executeQuery();
            int rowcount = this.getRecordCount(rs);

            if (rowcount != 0) {
                s = new Student[rowcount];
                int i = 0;
                while (rs.next()) {
                    s[i] = this.getRecordData(rs);
                    i++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    @Override
    public String update(Student student) {
        String status = "";
        try {
            preparedStatement = connection.prepareStatement("UPDATE " +
                    "student SET name=?, dept=?, cgpa=?, email=?, " +
                    "mobile=? WHERE id=?");

            preparedStatement.setString(1, student.getName());
            preparedStatement.setString(2, student.getDept());
            preparedStatement.setFloat(3, student.getCgpa());
            preparedStatement.setString(4, student.getEmail());
            preparedStatement.setString(5, student.getMobile());
            preparedStatement.setInt(6, student.getId());

            int rowCount = preparedStatement.executeUpdate();

            if (rowCount == 1) {
                status = "success";
            } else {
                status = "failed";
            }
        } catch (Exception e) {
            System.out.println("***Exception while updating student***");
            e.printStackTrace();
            status = "failed";
        }
        return status;
    }

    @Override
    public String remove(int id) {
        String status = "";
        try {
            connection = dataSource.getConnection();
            preparedStatement = connection.prepareStatement("DELETE FROM " +
                    "student WHERE id = ?");
            preparedStatement.setInt(1, id);
            int rowCount = preparedStatement.executeUpdate();

            if (rowCount == 0) {
                status = "notexists";
            } else {
                status = "success";
            }
        } catch (Exception e) {
            e.printStackTrace();
            status = "falied";
        }

        return status;
    }
}
