/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View;



import Model.SinhVien;
import dao.DAOSearchStudent;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SearchStudentForm extends JFrame {
    
    private DefaultTableModel searchTableModel;
    private JTable searchTable;
    private JTextField searchField;
    private JButton searchButton;
    private DAOSearchStudent daoSearchStudent;
      private JButton cancelButton;

    public SearchStudentForm() {
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
          daoSearchStudent = new DAOSearchStudent();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(15);
        searchButton = new JButton("Tìm kiếm");

        searchPanel.add(new JLabel("Mã SV:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchTableModel = new DefaultTableModel(
                null, new String[]{"Mã SV", "Mã Lớp", "Tên SV", "Giới Tính", "Ngày Sinh", "Quê Quán", "SĐT SV", "Hệ Đào Tạo", "Bậc Đào Tạo"});
        searchTable = new JTable(searchTableModel);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(searchTable), BorderLayout.CENTER);
         cancelButton = new JButton("Hủy");
        searchPanel.add(cancelButton);

        add(mainPanel);

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            performSearch(searchText);
        });
        cancelButton.addActionListener(e -> {
    // Ẩn frame hiện tại
    setVisible(false);

    // Hiển thị frame menu
    MainMenuForm menuForm = new MainMenuForm();  // Thay MenuForm bằng tên lớp của form menu
    menuForm.setVisible(true);
});

    }

    private void performSearch(String maSV) {
        try {
            List<SinhVien> students = daoSearchStudent.searchStudents(maSV);
            populateSearchTable(students);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void populateSearchTable(List<SinhVien> students) {
        searchTableModel.setRowCount(0);

        for (SinhVien student : students) {
            Object[] rowData = {
                    student.getMaSV(),
                    student.getMaLop(),
                    student.getTenSV(),
                    student.getGioiTinh(),
                    student.getNgaySinh(),
                    student.getQueQuan(),
                    student.getsDTSV(),
                    student.getHeDaoTao(),
                    student.getBacDaoTao()
            };
            searchTableModel.addRow(rowData);
        }
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
                SearchStudentForm searchStudentForm = new SearchStudentForm();
            searchStudentForm.daoSearchStudent = new DAOSearchStudent();
            searchStudentForm.setVisible(true);
            }
        });
    }

    
}
