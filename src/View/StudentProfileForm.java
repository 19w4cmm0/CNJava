package View;
import Model.SinhVien;
import View.MainMenuForm;
import dao.DAOStudentProfile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class StudentProfileForm extends JFrame {
    private DefaultTableModel dfTableModel = new DefaultTableModel(null,
            new String[]{"Mã SV", "Mã Lớp", "Tên SV", "Giới Tính", "Ngày Sinh", "Quê Quán", "SĐT SV", "Hệ Đào Tạo", "Bậc Đào Tạo"});
    private JTextField searchField;
    private JTable studentTable;
    private JPanel detailPanel;
    private JTextField maSVField;
    private JTextField maLopField;
    private JTextField tenSVField;
    private JTextField gioiTinhField;
    private JTextField ngaySinhField;
    private JTextField queQuanField;
    private JTextField sDTSVField;
    private JTextField heDaoTaoField;
    private JTextField bacDaoTaoField;
    private JPanel buttonPanel;
    private DAOStudentProfile daoStudentProfile = new DAOStudentProfile();

    public StudentProfileForm() {
        initComponents();
        loadData();

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    SinhVien selectedStudent = getSelectedStudent(selectedRow);
                    updateDetailPanel(selectedStudent);
                    detailPanel.setVisible(true);
                } else {
                    detailPanel.setVisible(false);
                }
            }
        });

        setTitle("Quản lý Sinh viên");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        studentTable = new JTable();
        detailPanel = createDetailPanel();
        buttonPanel = createButtonPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = createSearchPanel();

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(detailPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadData() {
        try {
            List<SinhVien> students = daoStudentProfile.getAllStudents();
            for (SinhVien sinhVien : students) {
                Vector<Object> value = new Vector<>();
                value.add(sinhVien.getMaSV());
                value.add(sinhVien.getMaLop());
                value.add(sinhVien.getTenSV());
                value.add(sinhVien.getGioiTinh());
                value.add(sinhVien.getNgaySinh());
                value.add(sinhVien.getQueQuan());
                value.add(sinhVien.getsDTSV());
                value.add(sinhVien.getHeDaoTao());
                value.add(sinhVien.getBacDaoTao());
                dfTableModel.addRow(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        studentTable.setModel(dfTableModel);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Mã SV:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                String searchText = searchField.getText().trim();
                filterTable(searchText);
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                String searchText = searchField.getText().trim();
                filterTable(searchText);
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                String searchText = searchField.getText().trim();
                filterTable(searchText);
            }
        });

        searchButton.addActionListener(e -> {
            String searchText = searchField.getText().trim();
            filterTable(searchText);
        });

        return searchPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton themButton = new JButton("Thêm");
        JButton suaButton = new JButton("Sửa");
        JButton xoaButton = new JButton("Xóa");
        JButton luuButton = new JButton("Lưu");
        JButton huyButton = new JButton("Hủy");

        buttonPanel.add(themButton);
        themButton.addActionListener(e -> {
            // Code để thêm sinh viên mới
            String maSV = JOptionPane.showInputDialog(null, "Nhập Mã SV:");
            String maLop = JOptionPane.showInputDialog(null, "Nhập Mã Lớp:");
            String tenSV = JOptionPane.showInputDialog(null, "Nhập Tên SV:");
            String gioiTinh = JOptionPane.showInputDialog(null, "Nhập Giới Tính:");
            String ngaySinh = JOptionPane.showInputDialog(null, "Nhập Ngày Sinh:");
            String queQuan = JOptionPane.showInputDialog(null, "Nhập Quê Quán:");
            String sDTSV = JOptionPane.showInputDialog(null, "Nhập SĐT SV:");
            String heDaoTao = JOptionPane.showInputDialog(null, "Nhập Hệ Đào Tạo:");
            String bacDaoTao = JOptionPane.showInputDialog(null, "Nhập Bậc Đào Tạo:");

            SinhVien newStudent = new SinhVien(maSV, maLop, tenSV, gioiTinh, ngaySinh, queQuan, sDTSV, heDaoTao, bacDaoTao);

            int rowsInserted = 0;
            try {
                rowsInserted = daoStudentProfile.insertStudent(newStudent);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(null, "Thêm sinh viên thành công!");
                refreshTableData();
            } else {
                JOptionPane.showMessageDialog(null, "Thêm sinh viên thất bại!");
            }
        });

        huyButton.addActionListener(e -> {
            setVisible(false);
            MainMenuForm menuForm = new MainMenuForm();
            menuForm.setVisible(true);
        });

        suaButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên để cập nhật.");
                return;
            }

            String maSV = maSVField.getText();
            String maLop = JOptionPane.showInputDialog(null, "Nhập Mã Lớp:", maLopField.getText());
            String tenSV = JOptionPane.showInputDialog(null, "Nhập Tên SV:", tenSVField.getText());
            String gioiTinh = JOptionPane.showInputDialog(null, "Nhập Giới Tính:", gioiTinhField.getText());
            String ngaySinh = JOptionPane.showInputDialog(null, "Nhập Ngày Sinh:", ngaySinhField.getText());
            String queQuan = JOptionPane.showInputDialog(null, "Nhập Quê Quán:", queQuanField.getText());
            String sDTSV = JOptionPane.showInputDialog(null, "Nhập SĐT SV:", sDTSVField.getText());
            String heDaoTao = JOptionPane.showInputDialog(null, "Nhập Hệ Đào Tạo:", heDaoTaoField.getText());
            String bacDaoTao = JOptionPane.showInputDialog(null, "Nhập Bậc Đào Tạo:", bacDaoTaoField.getText());

            SinhVien updatedStudent = new SinhVien(maSV, maLop, tenSV, gioiTinh, ngaySinh, queQuan, sDTSV, heDaoTao, bacDaoTao);

            int rowsUpdated = 0;
            try {
                rowsUpdated = daoStudentProfile.updateStudent(updatedStudent);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(null, "Cập nhật sinh viên thành công!");
                refreshTableData();
            } else {
                JOptionPane.showMessageDialog(null, "Cập nhật sinh viên thất bại!");
            }
        });

        xoaButton.addActionListener(e -> {
            int selectedRow = studentTable.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn sinh viên để xóa.");
                return;
            }

            int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa sinh viên này không?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return;
            }

            String maSVToDelete = maSVField.getText();
            int rowsDeleted = 0;
            try {
                rowsDeleted = daoStudentProfile.deleteStudent(maSVToDelete);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(null, "Xóa sinh viên thành công!");
                refreshTableData();
                detailPanel.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Xóa sinh viên thất bại!");
            }
        });

        buttonPanel.add(suaButton);
        buttonPanel.add(xoaButton);
        buttonPanel.add(luuButton);
        buttonPanel.add(huyButton);

        return buttonPanel;
    }

    private JPanel createDetailPanel() {
        maSVField = new JTextField(10);
        maLopField = new JTextField(10);
        tenSVField = new JTextField(10);
        gioiTinhField = new JTextField(10);
        ngaySinhField = new JTextField(10);
        queQuanField = new JTextField(10);
        sDTSVField = new JTextField(10);
        heDaoTaoField = new JTextField(10);
        bacDaoTaoField = new JTextField(10);

        JPanel detailPanel = new JPanel(new GridLayout(10, 2));
        detailPanel.add(new JLabel("Mã SV:"));
        detailPanel.add(maSVField);
        detailPanel.add(new JLabel("Mã Lớp:"));
        detailPanel.add(maLopField);
        detailPanel.add(new JLabel("Tên SV:"));
        detailPanel.add(tenSVField);
        detailPanel.add(new JLabel("Giới Tính:"));
        detailPanel.add(gioiTinhField);
        detailPanel.add(new JLabel("Ngày Sinh:"));
        detailPanel.add(ngaySinhField);
        detailPanel.add(new JLabel("Quê Quán:"));
        detailPanel.add(queQuanField);
        detailPanel.add(new JLabel("SĐT SV:"));
        detailPanel.add(sDTSVField);
        detailPanel.add(new JLabel("Hệ Đào Tạo:"));
        detailPanel.add(heDaoTaoField);
        detailPanel.add(new JLabel("Bậc Đào Tạo:"));
        detailPanel.add(bacDaoTaoField);

        detailPanel.setVisible(false);

        return detailPanel;
    }

    private void updateDetailPanel(SinhVien sinhVien) {
        maSVField.setText(sinhVien.getMaSV());
        maLopField.setText(sinhVien.getMaLop());
        tenSVField.setText(sinhVien.getTenSV());
        gioiTinhField.setText(sinhVien.getGioiTinh());
        ngaySinhField.setText(sinhVien.getNgaySinh());
        queQuanField.setText(sinhVien.getQueQuan());
        sDTSVField.setText(sinhVien.getsDTSV());
        heDaoTaoField.setText(sinhVien.getHeDaoTao());
        bacDaoTaoField.setText(sinhVien.getBacDaoTao());
    }

    private SinhVien getSelectedStudent(int rowIndex) {
        return new SinhVien(
                studentTable.getValueAt(rowIndex, 0).toString(),
                studentTable.getValueAt(rowIndex, 1).toString(),
                studentTable.getValueAt(rowIndex, 2).toString(),
                studentTable.getValueAt(rowIndex, 3).toString(),
                studentTable.getValueAt(rowIndex, 4).toString(),
                studentTable.getValueAt(rowIndex, 5).toString(),
                studentTable.getValueAt(rowIndex, 6).toString(),
                studentTable.getValueAt(rowIndex, 7).toString(),
                studentTable.getValueAt(rowIndex, 8).toString()
        );
    }

    private void filterTable(String searchText) {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(dfTableModel);
        studentTable.setRowSorter(rowSorter);

        if (searchText.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    private void refreshTableData() {
        dfTableModel.setRowCount(0); // Clear existing data
        loadData(); // Reload data from the database
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new StudentProfileForm().setVisible(true));
    }
}
