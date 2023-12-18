package View;

import Model.KhoaHoc;
import dao.DAOCourse;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class CourseForm extends JFrame {
    private DefaultTableModel dfTableModel = new DefaultTableModel(null, new String[]{"Mã Khóa", "Tên Khóa", "Niên Khóa"});
    private JTextField searchField;
    private JTable courseTable;
    private JPanel detailPanel;
    private JTextField maKhoaField;
    private JTextField tenKhoaField;
    private JTextField nienKhoaField;
    private JPanel buttonPanel;
    private DAOCourse daoCourse = new DAOCourse();

    public CourseForm() {
        initComponents();
        loadData();

        courseTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = courseTable.getSelectedRow();
                if (selectedRow != -1) {
                    KhoaHoc selectedCourse = getSelectedCourse(selectedRow);
                    updateDetailPanel(selectedCourse);
                    detailPanel.setVisible(true);
                } else {
                    detailPanel.setVisible(false);
                }
            }
        });

        setTitle("Quản lý Khóa học");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        courseTable = new JTable();
        detailPanel = createDetailPanel();
        buttonPanel = createButtonPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = createSearchPanel();

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(courseTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(detailPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadData() {
        try {
            List<KhoaHoc> courses = daoCourse.getAllCourses();
            for (KhoaHoc khoaHoc : courses) {
                Vector<Object> value = new Vector<>();
                value.add(khoaHoc.getMaKhoa());
                value.add(khoaHoc.getTenKhoa());
                value.add(khoaHoc.getNienKhoa());
                dfTableModel.addRow(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        courseTable.setModel(dfTableModel);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Mã Khóa:"));
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
            String maKhoa = JOptionPane.showInputDialog(CourseForm.this, "Nhập Mã Khóa:");
            String tenKhoa = JOptionPane.showInputDialog(CourseForm.this, "Nhập Tên Khóa:");
            String nienKhoa = JOptionPane.showInputDialog(CourseForm.this, "Nhập Niên Khóa:");

            if (maKhoa != null && tenKhoa != null && nienKhoa != null) {
                KhoaHoc newCourse = new KhoaHoc(maKhoa, tenKhoa, nienKhoa);
                try {
                    int row = daoCourse.insertCourse(newCourse);

                    if (row > 0) {
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        themvaoTable(newCourse);
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
            KhoaHoc updateCourse = new KhoaHoc(
                    maKhoaField.getText(),
                    tenKhoaField.getText(),
                    nienKhoaField.getText()
            );
            try {
                int row = daoCourse.updateCourse(updateCourse);
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
            String maKhoa = maKhoaField.getText();
            try {
                int row = daoCourse.deleteCourse(maKhoa);
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

    private ActionListener createButtonAction(String message) {
        return e -> JOptionPane.showMessageDialog(CourseForm.this, message);
    }

    private JPanel createDetailPanel() {
        maKhoaField = new JTextField(10);
        tenKhoaField = new JTextField(10);
        nienKhoaField = new JTextField(10);

        JPanel detailPanel = new JPanel(new GridLayout(4, 2));
        detailPanel.add(new JLabel("Mã Khóa:"));
        detailPanel.add(maKhoaField);
        detailPanel.add(new JLabel("Tên Khóa:"));
        detailPanel.add(tenKhoaField);
        detailPanel.add(new JLabel("Niên Khóa:"));
        detailPanel.add(nienKhoaField);

        detailPanel.setVisible(false);

        return detailPanel;
    }

    private void updateDetailPanel(KhoaHoc khoaHoc) {
        maKhoaField.setText(khoaHoc.getMaKhoa());
        tenKhoaField.setText(khoaHoc.getTenKhoa());
        nienKhoaField.setText(khoaHoc.getNienKhoa());
    }

    private KhoaHoc getSelectedCourse(int rowIndex) {
        return new KhoaHoc(
                courseTable.getValueAt(rowIndex, 0).toString(),
                courseTable.getValueAt(rowIndex, 1).toString(),
                courseTable.getValueAt(rowIndex, 2).toString()
        );
    }

    private void themvaoTable(KhoaHoc khoaHoc) {
        Vector<Object> value = new Vector<>();
        value.add(khoaHoc.getMaKhoa());
        value.add(khoaHoc.getTenKhoa());
        value.add(khoaHoc.getNienKhoa());
        dfTableModel.addRow(value);
    }

    private void filterTable(String searchText) {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(dfTableModel);
        courseTable.setRowSorter(rowSorter);

        if (searchText.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CourseForm().setVisible(true));
    }
}
