package View;
import Model.ChuyenNganh;
import dao.DAOSearchStudent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuForm extends JFrame {
    public MainMenuForm() {
        setTitle("Trang chủ");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo Menu Bar
        JMenuBar menuBar = new JMenuBar();

        // Tạo các Menu
        JMenu systemMenu = new JMenu("Hệ thống");
        JMenu categoryMenu = new JMenu("Danh mục");
        JMenu managementMenu = new JMenu("Quản lý");
        JMenu searchMenu = new JMenu("Tra cứu");

        // Thêm Menu vào Menu Bar
        menuBar.add(systemMenu);
        menuBar.add(categoryMenu);
        menuBar.add(managementMenu);
        menuBar.add(searchMenu);

        // Tạo các mục menu
        JMenuItem systemItem1 = new JMenuItem("Đăng Xuất");
        JMenuItem categoryItem1 = new JMenuItem("Chuyên ngành");
        JMenuItem categoryItem2 = new JMenuItem("Khóa học");
        JMenuItem categoryItem3 = new JMenuItem("Lớp ");
        JMenuItem categoryItem4 = new JMenuItem("Môn học");
        JMenuItem managementItem1 = new JMenuItem("Hồ sơ sinh viên");
        JMenuItem managementItem2 = new JMenuItem("Kết quả học tập");
        JMenuItem searchItem1 = new JMenuItem("Tra cứu danh sách SV");
        JMenuItem searchItem2 = new JMenuItem("Tra cứu điểm SV");

        // Thêm các mục vào các Menu tương ứng
        systemMenu.add(systemItem1);
      
        categoryMenu.add(categoryItem1);
        categoryMenu.add(categoryItem2);
        categoryMenu.add(categoryItem3);
        categoryMenu.add(categoryItem4);
        managementMenu.add(managementItem1);
        managementMenu.add(managementItem2);
        searchMenu.add(searchItem1);
        searchMenu.add(searchItem2);

        // Xử lý sự kiện khi chọn mục menu
        systemItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(MainMenuForm.this, "Mục Hệ thống 1");
            }
        });

        categoryItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ManagementForm frHH=new ManagementForm();
                  frHH.setVisible(true);
            }
        });

        categoryItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CourseForm frHH=new CourseForm();
                  frHH.setVisible(true);
            }
        });
        categoryItem4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SubjectForm frHH=new SubjectForm();
                  frHH.setVisible(true);
            }
        });
        categoryItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClassForm frHH=new ClassForm();
                  frHH.setVisible(true);
            }
        });
        managementItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StudentProfileForm frHH=new StudentProfileForm();
                  frHH.setVisible(true);
            }
        });
        managementItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GradeForm frHH=new GradeForm();
                  frHH.setVisible(true);
            }
        });
                searchItem1.addActionListener(new ActionListener() {
                   @Override
            public void actionPerformed(ActionEvent e) {
                SearchStudentForm frHH=new SearchStudentForm();
                  frHH.setVisible(true);
                }
});


        searchItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SearchGradeForm frHH=new SearchGradeForm();
                  frHH.setVisible(true);
            }
        });
        systemItem1.addActionListener(new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
        // Display a confirmation dialog
        int choice = JOptionPane.showConfirmDialog(MainMenuForm.this, "Bạn có chắc chắn muốn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            // If user confirms, close the current MainMenuForm and open the LoginForm
            dispose(); // Close the current form
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        }
    }
});

        // Thiết lập Menu Bar cho frame
        setJMenuBar(menuBar);
    }

public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }
}
