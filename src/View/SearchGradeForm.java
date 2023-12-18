package View;

import Model.KetQuaHocTap;
import dao.DAOSearchGrade;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SearchGradeForm extends JFrame {
    private DefaultTableModel searchTableModel;
    private JTable searchTable;
    private JTextField searchField;
    private JButton searchButton;
    private DAOSearchGrade daoSearchGrade;
    private JButton cancelButton;

    public SearchGradeForm() {
        initUI();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);
        daoSearchGrade = new DAOSearchGrade();
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
                null, new String[]{"Mã SV", "Mã Học Phần", "Học Kỳ", "Điểm TB"});
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
            List<KetQuaHocTap> grades = daoSearchGrade.searchGrades(maSV);
            populateSearchTable(grades);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void populateSearchTable(List<KetQuaHocTap> grades) {
        searchTableModel.setRowCount(0);

        for (KetQuaHocTap grade : grades) {
            Object[] rowData = {
                    grade.getMaSV(),
                    grade.getMaHP(),
                    grade.getHocKy(),
                    grade.getDiemTB()
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
                SearchGradeForm searchGradeForm = new SearchGradeForm();
            searchGradeForm.daoSearchGrade = new DAOSearchGrade();
            searchGradeForm.setVisible(true);
            }
        });
    }

   
}
