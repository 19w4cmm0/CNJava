package View;

import Model.Lop;
import dao.DAOClass;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ClassForm extends JFrame {
    private DefaultTableModel dfTableModel = new DefaultTableModel(null, new String[]{"Mã Lớp", "Tên Lớp", "Giáo viên chủ nhiệm", "Số điện thoại GV", "Mã Ngành", "Mã Khoa", "Tên Ngành", "Tên Khoa"});
    private JTextField searchField;
    private JTable classTable;
    private JPanel detailPanel;
    private JTextField maLopField;
    private JTextField tenLopField;
    private JTextField gVCNField;
    private JTextField sDTGVField;
    private JTextField maNganhField;
    private JTextField maKhoaField;
    private JTextField tenNganhField;
    private JTextField tenKhoaField;
    private JPanel buttonPanel;
    private DAOClass daoClass = new DAOClass();

    public ClassForm() {
        initComponents();
        loadData();

        classTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = classTable.getSelectedRow();
                if (selectedRow != -1) {
                    Lop selectedClass = getSelectedClass(selectedRow);
                    updateDetailPanel(selectedClass);
                    detailPanel.setVisible(true);
                } else {
                    detailPanel.setVisible(false);
                }
            }
        });

        setTitle("Quản lý Lớp học");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        classTable = new JTable();
        detailPanel = createDetailPanel();
        buttonPanel = createButtonPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = createSearchPanel();

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(classTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(detailPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadData() {
        try {
            List<Lop> classes = daoClass.getAllClasses();
            for (Lop lop : classes) {
                Vector<Object> value = new Vector<>();
                value.add(lop.getMaLop());
                value.add(lop.getTenLop());
                value.add(lop.getGVCN());
                value.add(lop.getSDTGV());
                value.add(lop.getMaNganh());
                value.add(lop.getMaKhoa());
                value.add(lop.getTenNganh());
                value.add(lop.getTenKhoa());
                dfTableModel.addRow(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        classTable.setModel(dfTableModel);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Mã Lớp:"));
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
            String maLop = JOptionPane.showInputDialog(ClassForm.this, "Nhập Mã Lớp:");
            String tenLop = JOptionPane.showInputDialog(ClassForm.this, "Nhập Tên Lớp:");
            String gVCN = JOptionPane.showInputDialog(ClassForm.this, "Nhập Giáo viên chủ nhiệm:");
            String sDTGV = JOptionPane.showInputDialog(ClassForm.this, "Nhập Số điện thoại GV:");
            String maNganh = JOptionPane.showInputDialog(ClassForm.this, "Nhập Mã Ngành:");
            String maKhoa = JOptionPane.showInputDialog(ClassForm.this, "Nhập Mã Khoa:");
            String tenNganh = JOptionPane.showInputDialog(ClassForm.this, "Nhập Tên Ngành:");
            String tenKhoa = JOptionPane.showInputDialog(ClassForm.this, "Nhập Tên Khoa:");

            if (maLop != null && tenLop != null && gVCN != null && sDTGV != null && maNganh != null && maKhoa != null && tenNganh != null && tenKhoa != null) {
                Lop newClass = new Lop(maLop, tenLop, gVCN, sDTGV, maNganh, maKhoa, tenNganh, tenKhoa);
                try {
                    int row = daoClass.insertClass(newClass);

                    if (row > 0) {
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        themvaoTable(newClass);
                    } else {
                        JOptionPane.showMessageDialog(null, "Thêm không thành công");
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        huyButton.addActionListener(e -> {
            setVisible(false);
            MainMenuForm menuForm = new MainMenuForm();
            menuForm.setVisible(true);
        });

        suaButton.addActionListener(e -> {
            Lop updateClass = new Lop(
                    maLopField.getText(),
                    tenLopField.getText(),
                    gVCNField.getText(),
                    sDTGVField.getText(),
                    maNganhField.getText(),
                    maKhoaField.getText(),
                    tenNganhField.getText(),
                    tenKhoaField.getText()
            );
            try {
                int row = daoClass.updateClass(updateClass);
                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "Sửa thành công");
                    dfTableModel.setNumRows(0);
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(null, "Chưa sửa được");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        xoaButton.addActionListener(e -> {
            String maLop = maLopField.getText();
            try {
                int row = daoClass.deleteClass(maLop);
                if (row > 0) {
                    JOptionPane.showMessageDialog(null, "Xóa thành công");
                    dfTableModel.setNumRows(0);
                    loadData();
                } else
                    JOptionPane.showMessageDialog(null, "Chưa xóa được");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        buttonPanel.add(suaButton);
        buttonPanel.add(xoaButton);
        buttonPanel.add(luuButton);
        buttonPanel.add(huyButton);

        return buttonPanel;
    }

    private JPanel createDetailPanel() {
        maLopField = new JTextField(10);
        tenLopField = new JTextField(10);
        gVCNField = new JTextField(10);
        sDTGVField = new JTextField(10);
        maNganhField = new JTextField(10);
        maKhoaField = new JTextField(10);
        tenNganhField = new JTextField(10);
        tenKhoaField = new JTextField(10);

        JPanel detailPanel = new JPanel(new GridLayout(8, 2));
        detailPanel.add(new JLabel("Mã Lớp:"));
        detailPanel.add(maLopField);
        detailPanel.add(new JLabel("Tên Lớp:"));
        detailPanel.add(tenLopField);
        detailPanel.add(new JLabel("Giáo viên chủ nhiệm:"));
        detailPanel.add(gVCNField);
        detailPanel.add(new JLabel("Số điện thoại GV:"));
        detailPanel.add(sDTGVField);
        detailPanel.add(new JLabel("Mã Ngành:"));
        detailPanel.add(maNganhField);
        detailPanel.add(new JLabel("Mã Khoa:"));
        detailPanel.add(maKhoaField);
        detailPanel.add(new JLabel("Tên Ngành:"));
        detailPanel.add(tenNganhField);
        detailPanel.add(new JLabel("Tên Khoa:"));
        detailPanel.add(tenKhoaField);

        detailPanel.setVisible(false);

        return detailPanel;
    }

    private void updateDetailPanel(Lop lop) {
        maLopField.setText(lop.getMaLop());
        tenLopField.setText(lop.getTenLop());
        gVCNField.setText(lop.getGVCN());
        sDTGVField.setText(lop.getSDTGV());
        maNganhField.setText(lop.getMaNganh());
        maKhoaField.setText(lop.getMaKhoa());
        tenNganhField.setText(lop.getTenNganh());
        tenKhoaField.setText(lop.getTenKhoa());
    }

    private Lop getSelectedClass(int rowIndex) {
        return new Lop(
                classTable.getValueAt(rowIndex, 0).toString(),
                classTable.getValueAt(rowIndex, 1).toString(),
                classTable.getValueAt(rowIndex, 2).toString(),
                classTable.getValueAt(rowIndex, 3).toString(),
                classTable.getValueAt(rowIndex, 4).toString(),
                classTable.getValueAt(rowIndex, 5).toString(),
                classTable.getValueAt(rowIndex, 6).toString(),
                classTable.getValueAt(rowIndex, 7).toString()
        );
    }

    private void themvaoTable(Lop lop) {
        Vector<Object> value = new Vector<>();
        value.add(lop.getMaLop());
        value.add(lop.getTenLop());
        value.add(lop.getGVCN());
        value.add(lop.getSDTGV());
        value.add(lop.getMaNganh());
        value.add(lop.getMaKhoa());
        value.add(lop.getTenNganh());
        value.add(lop.getTenKhoa());
        dfTableModel.addRow(value);
    }

    private void filterTable(String searchText) {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(dfTableModel);
        classTable.setRowSorter(rowSorter);

        if (searchText.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClassForm().setVisible(true));
    }
}
