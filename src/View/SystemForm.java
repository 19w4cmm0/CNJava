/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SystemForm extends JFrame {
    public SystemForm() {
        setTitle("Hệ thống");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo các nút cho các chức năng hệ thống
        JButton majorButton = new JButton("Chuyên ngành");
        JButton courseButton = new JButton("Khóa học");
        JButton classButton = new JButton("Lớp");
        JButton subjectButton = new JButton("Môn học");

        // Thiết lập layout
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(majorButton);
        panel.add(courseButton);
        panel.add(classButton);
        panel.add(subjectButton);

        // Xử lý sự kiện khi nhấn các nút
        majorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openMajorForm();
            }
        });

        courseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openCourseForm();
            }
        });

        classButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openClassForm();
            }
        });

        subjectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openSubjectForm();
            }
        });

        // Thêm panel vào frame
        add(panel, BorderLayout.CENTER);
    }

    private void openMajorForm() {
        // Mở form chuyên ngành (MajorForm)
        ManagementForm majorForm = new ManagementForm();
        majorForm.setVisible(true);
    }

    private void openCourseForm() {
        // Mở form khóa học (CourseForm)
        CourseForm courseForm = new CourseForm();
        courseForm.setVisible(true);
    }

    private void openClassForm() {
        // Mở form lớp (ClassForm)
        ClassForm classForm = new ClassForm();
        classForm.setVisible(true);
    }

    private void openSubjectForm() {
        // Mở form môn học (SubjectForm)
        SubjectForm subjectForm = new SubjectForm();
        subjectForm.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SystemForm().setVisible(true);
            }
        });
    }
}

