package View;
import Model.KetQuaHocTap;
import Model.SinhVien;
import dao.DAOGrade;
import dao.DAOStudentProfile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.table.TableRowSorter;

public class GradeForm extends JFrame {
    private JTabbedPane tabbedPane = new JTabbedPane();
    private DefaultTableModel studentTableModel = new DefaultTableModel(
            null, new String[]{"Mã SV", "Mã Lớp", "Tên SV", "Giới Tính", "Ngày Sinh", "Quê Quán", "SĐT SV", "Hệ Đào Tạo", "Bậc Đào Tạo"});
    private JTable studentTable = new JTable(studentTableModel);
    private DefaultTableModel gradeTableModel = new DefaultTableModel(
            null, new String[]{"Mã Học Phần", "Học Kỳ", "Điểm QT", "Điểm GK", "Điểm TH", "Điểm CK", "Điểm HP", "Điểm Thi Lại", "Điểm TB"});
    private JTable gradeTable = new JTable(gradeTableModel);
    private JTextField searchField = new JTextField(15);
    private DAOStudentProfile daoStudentProfile = new DAOStudentProfile();
    private DAOGrade daoGrade = new DAOGrade();
    private JButton addGradeButton;
    private JButton editGradeButton;
    private JButton deleteGradeButton;
    private JButton backButton;  // Thêm nút trở về
    private String selectedMaSV;

    private JTextField txtMaHP;
    private JTextField txtHocKy;
    private JTextField txtDiemQT;
    private JTextField txtDiemGK;
    private JTextField txtDiemTH;
    private JTextField txtDiemCK;
    private JTextField txtDiemHP;
    private JTextField txtDiemThiLai;
    private JTextField txtDiemTB;

    public GradeForm() {
        initUI();
        loadData();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        daoGrade = new DAOGrade();
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Mã SV:"));
        JButton huyButton = new JButton("Hủy");
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.add(huyButton);

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
        huyButton.addActionListener(e -> {
            setVisible(false);
            MainMenuForm menuForm = new MainMenuForm();
            menuForm.setVisible(true);
        });

        JPanel studentPanel = createStudentPanel();
        JPanel gradePanel = createGradePanel();

        tabbedPane.addTab("Thông Tin Sinh Viên", studentPanel);
        tabbedPane.addTab("Bảng Điểm", gradePanel);

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String maSV = studentTable.getValueAt(selectedRow, 0).toString();
                    loadGradeData(maSV);

                    // Chuyển qua tab Bảng Điểm
                    tabbedPane.setSelectedIndex(1); // Giả sử tab Bảng Điểm là tab thứ 2
                }
            }
        });
    }

    private JPanel createStudentPanel() {
        JPanel studentPanel = new JPanel(new BorderLayout());
        studentPanel.add(new JScrollPane(studentTable), BorderLayout.CENTER);
        return studentPanel;
    }

    private JPanel createGradePanel() {
        JPanel gradePanel = new JPanel(new BorderLayout());

        // Tạo bảng điểm
        gradeTableModel = new DefaultTableModel(
                null, new String[]{"Mã Học Phần", "Học Kỳ", "Điểm QT", "Điểm GK", "Điểm TH", "Điểm CK", "Điểm HP", "Điểm Thi Lại", "Điểm TB"});
        gradeTable = new JTable(gradeTableModel);

        // Thêm nút chức năng
        addGradeButton = new JButton("Thêm");
        editGradeButton = new JButton("Sửa");
        deleteGradeButton = new JButton("Xóa");
        backButton = new JButton("Trở Về");  // Thêm nút trở về

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addGradeButton);
        buttonPanel.add(editGradeButton);
        buttonPanel.add(deleteGradeButton);
        buttonPanel.add(backButton);  // Thêm nút trở về

       
        
        // Thêm sự kiện cho nút chức năng
         addGradeButton.addActionListener(e -> {
    int selectedRow = studentTable.getSelectedRow();

    if (selectedRow != -1) {
        String maSV = studentTable.getValueAt(selectedRow, 0).toString();
        String maHP = JOptionPane.showInputDialog(GradeForm.this, "Nhập Mã Học Phần:");
        String hocKy = JOptionPane.showInputDialog(GradeForm.this, "Nhập Học Kỳ:");
        String diemQTStr = JOptionPane.showInputDialog(GradeForm.this, "Nhập Điểm Quá Trình:");
        String diemGKStr = JOptionPane.showInputDialog(GradeForm.this, "Nhập Điểm Giữa Kỳ:");
        String diemTHStr = JOptionPane.showInputDialog(GradeForm.this, "Nhập Điểm Thực Hành:");
        String diemCKStr = JOptionPane.showInputDialog(GradeForm.this, "Nhập Điểm Cuối Kỳ:");
        String diemHPStr = JOptionPane.showInputDialog(GradeForm.this, "Nhập Điểm Học Phần:");
        String diemThiLaiStr = JOptionPane.showInputDialog(GradeForm.this, "Nhập Điểm Thi Lại:");

        try {
            float diemQT = Float.parseFloat(diemQTStr);
            float diemGK = Float.parseFloat(diemGKStr);
            float diemTH = Float.parseFloat(diemTHStr);
            float diemCK = Float.parseFloat(diemCKStr);
            float diemHP = Float.parseFloat(diemHPStr);
            float diemThiLai = Float.parseFloat(diemThiLaiStr);

            // Calculate diemTB
            float diemTB = (diemQT + diemGK + diemTH + diemCK + diemHP) / 5;

            KetQuaHocTap newGrade = new KetQuaHocTap(maSV, maHP, hocKy, diemQT, diemGK, diemTH, diemCK, diemHP, diemThiLai, diemTB);
            int row = daoGrade.insertGrade(newGrade);

            if (row > 0) {
                JOptionPane.showMessageDialog(null, "Thêm điểm thành công");
                themvaoTable(newGrade);
            } else {
                JOptionPane.showMessageDialog(null, "Thêm điểm không thành công");
            }
        } catch (NumberFormatException | SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Dữ liệu không hợp lệ");
        }
    } else {
        JOptionPane.showMessageDialog(null, "Vui lòng chọn một sinh viên trước khi thêm điểm.");
    }
});


        // Assume editGradeButton is your edit button
      editGradeButton.addActionListener(e -> {
           
    System.out.println("Edit button clicked");
    KetQuaHocTap updateGrade = new KetQuaHocTap(
            selectedMaSV,
            txtMaHP.getText(),
            txtHocKy.getText(),
            Float.parseFloat(txtDiemQT.getText()),
            Float.parseFloat(txtDiemGK.getText()),
            Float.parseFloat(txtDiemTH.getText()),
            Float.parseFloat(txtDiemCK.getText()),
            Float.parseFloat(txtDiemHP.getText()),
            Float.parseFloat(txtDiemThiLai.getText()),
            Float.parseFloat(txtDiemTB.getText())
    );

    System.out.println("Updating grade: " + updateGrade);

    try {
        int row = daoGrade.updateGrade(updateGrade);
        System.out.println("Rows updated: " + row);

        if (row > 0) {
            JOptionPane.showMessageDialog(null, "Sửa thành công");
            gradeTableModel.setNumRows(0);
            loadGradeData(selectedMaSV);
        } else {
            JOptionPane.showMessageDialog(null, "Chưa sửa được");
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
});



        deleteGradeButton.addActionListener(e -> {
            String maHP = txtMaHP.getText();
            try {
                int row = daoGrade.deleteGrade(maHP);
                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                    gradeTableModel.setNumRows(0);
                    loadData();
                } else
                    JOptionPane.showMessageDialog(null, "Chưa xóa được");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> {
            // Chuyển về tab Thông Tin Sinh Viên
            tabbedPane.setSelectedIndex(0); // Giả sử tab Thông Tin Sinh Viên là tab đầu tiên
        });
        gradeTable.getSelectionModel().addListSelectionListener(e -> {
    if (!e.getValueIsAdjusting()) {
        int selectedRow = gradeTable.getSelectedRow();
        if (selectedRow != -1) {
            String maHP = gradeTable.getValueAt(selectedRow, 0).toString();
            String hocKy = gradeTable.getValueAt(selectedRow, 1).toString();

            // Hiển thị thông tin môn học trên editPanel
            showGradeInfoOnEditPanel(maHP, hocKy);
        }
    }
});

        gradePanel.add(new JScrollPane(gradeTable), BorderLayout.CENTER);
    gradePanel.add(buttonPanel, BorderLayout.NORTH);

    // Thêm panel sửa điểm
    JPanel editPanel = createEditGradePanel();
    gradePanel.add(editPanel, BorderLayout.SOUTH);

    return gradePanel;
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
            studentTableModel.addRow(value);
        }

        studentTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    selectedMaSV = studentTable.getValueAt(selectedRow, 0).toString();
                    loadGradeData(selectedMaSV);

                    // Chuyển qua tab Bảng Điểm
                    tabbedPane.setSelectedIndex(1); // Giả sử tab Bảng Điểm là tab thứ 2
                }
            }
        });
    } catch (SQLException e) {
        e.printStackTrace();
    }
}


    private void filterTable(String searchText) {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(studentTableModel);
        studentTable.setRowSorter(rowSorter);

        if (searchText.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    private void loadGradeData(String maSV) {
        this.selectedMaSV = maSV;
        // Xóa dữ liệu cũ trong bảng điểm
        gradeTableModel.setRowCount(0);

        try {
            List<KetQuaHocTap> grades = daoGrade.getGradesByStudent(maSV);
            for (KetQuaHocTap grade : grades) {
                Vector<Object> value = new Vector<>();
                value.add(grade.getMaHP());
                value.add(grade.getHocKy());
                value.add(grade.getDiemQT());
                value.add(grade.getDiemGK());
                value.add(grade.getDiemTH());
                value.add(grade.getDiemCK());
                value.add(grade.getDiemHP());
                value.add(grade.getDiemThiLai());
                value.add(grade.getDiemTB());
                gradeTableModel.addRow(value);
            }
        } catch (SQLException ex) {
        ex.printStackTrace();
        System.out.println("Load data error: " + ex.getMessage());
        }
    }
    private void themvaoTable(KetQuaHocTap grade) {
    Vector<Object> value = new Vector<>();
    value.add(grade.getMaHP());
    value.add(grade.getHocKy());
    value.add(grade.getDiemQT());
    value.add(grade.getDiemGK());
    value.add(grade.getDiemTH());
    value.add(grade.getDiemCK());
    value.add(grade.getDiemHP());
    value.add(grade.getDiemThiLai());
    value.add(grade.getDiemTB());
    gradeTableModel.addRow(value);
}
    
private JPanel createEditGradePanel() {
    JPanel editPanel = new JPanel(new GridLayout(9, 2));

    // Thêm các thành phần giao diện cho việc sửa điểm
    editPanel.add(new JLabel("Mã Học Phần:"));
    txtMaHP = new JTextField();
    editPanel.add(txtMaHP);

    editPanel.add(new JLabel("Học Kỳ:"));
    txtHocKy = new JTextField();
    editPanel.add(txtHocKy);

    editPanel.add(new JLabel("Điểm QT:"));
    txtDiemQT = new JTextField();
    editPanel.add(txtDiemQT);

    editPanel.add(new JLabel("Điểm GK:"));
    txtDiemGK = new JTextField();
    editPanel.add(txtDiemGK);

    editPanel.add(new JLabel("Điểm TH:"));
    txtDiemTH = new JTextField();
    editPanel.add(txtDiemTH);

    editPanel.add(new JLabel("Điểm CK:"));
    txtDiemCK = new JTextField();
    editPanel.add(txtDiemCK);

    editPanel.add(new JLabel("Điểm HP:"));
    txtDiemHP = new JTextField();
    editPanel.add(txtDiemHP);

    editPanel.add(new JLabel("Điểm Thi Lại:"));
    txtDiemThiLai = new JTextField();
    editPanel.add(txtDiemThiLai);

    editPanel.add(new JLabel("Điểm TB:"));
    txtDiemTB = new JTextField();
    editPanel.add(txtDiemTB);

    return editPanel;
}
private void showGradeInfoOnEditPanel(String maHP, String hocKy) {
    for (int i = 0; i < gradeTableModel.getRowCount(); i++) {
        if (maHP.equals(gradeTableModel.getValueAt(i, 0).toString())
                && hocKy.equals(gradeTableModel.getValueAt(i, 1).toString())) {

            // Cập nhật thông tin từ gradeTableModel lên editPanel
            txtMaHP.setText(gradeTableModel.getValueAt(i, 0).toString());
            txtHocKy.setText(gradeTableModel.getValueAt(i, 1).toString());
            txtDiemQT.setText(gradeTableModel.getValueAt(i, 2).toString());
            txtDiemGK.setText(gradeTableModel.getValueAt(i, 3).toString());
            txtDiemTH.setText(gradeTableModel.getValueAt(i, 4).toString());
            txtDiemCK.setText(gradeTableModel.getValueAt(i, 5).toString());
            txtDiemHP.setText(gradeTableModel.getValueAt(i, 6).toString());
            txtDiemThiLai.setText(gradeTableModel.getValueAt(i, 7).toString());
            txtDiemTB.setText(gradeTableModel.getValueAt(i, 8).toString());

            break;
        }
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GradeForm().setVisible(true));
    }
}
