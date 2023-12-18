package View;

import Model.HocPhan;
import dao.DAOSubject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

public class SubjectForm extends JFrame {
    private DefaultTableModel dfTableModel = new DefaultTableModel(null, new String[]{"Mã HP", "Tên HP", "Số Tín Chỉ", "Số Tiết"});
    private JTextField searchField;
    private JTable subjectTable;
    private JPanel detailPanel;
    private JTextField maHPField;
    private JTextField tenHPField;
    private JTextField soTinChiField;
    private JTextField soTietField;
    private JPanel buttonPanel;
    private DAOSubject daoSubject = new DAOSubject();

    public SubjectForm() {
        initComponents();
        loadData();

        subjectTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = subjectTable.getSelectedRow();
                if (selectedRow != -1) {
                    HocPhan selectedSubject = getSelectedSubject(selectedRow);
                    updateDetailPanel(selectedSubject);
                    detailPanel.setVisible(true);
                } else {
                    detailPanel.setVisible(false);
                }
            }
        });

        setTitle("Quản lý Học phần");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        searchField = new JTextField(15);
        JButton searchButton = new JButton("Tìm kiếm");
        subjectTable = new JTable();
        detailPanel = createDetailPanel();
        buttonPanel = createButtonPanel();

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel searchPanel = createSearchPanel();

        mainPanel.add(searchPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(subjectTable), BorderLayout.CENTER);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(detailPanel, BorderLayout.NORTH);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        mainPanel.add(southPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void loadData() {
        try {
            List<HocPhan> subjects = daoSubject.getAllSubjects();
            for (HocPhan hocPhan : subjects) {
                Vector<Object> value = new Vector<>();
                value.add(hocPhan.getMaHP());
                value.add(hocPhan.getTenHP());
                value.add(hocPhan.getSoTinChi());
                value.add(hocPhan.getSoTiet());
                dfTableModel.addRow(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        subjectTable.setModel(dfTableModel);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Tìm kiếm");
        searchPanel.add(new JLabel("Mã HP:"));
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
            String maHP = JOptionPane.showInputDialog(SubjectForm.this, "Nhập Mã Học Phần:");
            String tenHP = JOptionPane.showInputDialog(SubjectForm.this, "Nhập Tên Học Phần:");
            String soTinChi = JOptionPane.showInputDialog(SubjectForm.this, "Nhập Số Tín Chỉ:");
            String soTiet = JOptionPane.showInputDialog(SubjectForm.this, "Nhập Số Tiết:");

            if (maHP != null && tenHP != null && soTinChi != null && soTiet != null) {
                HocPhan newSubject = new HocPhan(maHP, tenHP, Integer.parseInt(soTinChi), Integer.parseInt(soTiet));
                try {
                    int row = daoSubject.insertSubject(newSubject);

                    if (row > 0) {
                        JOptionPane.showMessageDialog(null, "Thêm thành công");
                        themvaoTable(newSubject);
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
            HocPhan updateSubject = new HocPhan(
                    maHPField.getText(),
                    tenHPField.getText(),
                    Integer.parseInt(soTinChiField.getText()),
                    Integer.parseInt(soTietField.getText())
            );
            try {
                int row = daoSubject.updateSubject(updateSubject);
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
            String maHP = maHPField.getText();
            try {
                int row = daoSubject.deleteSubject(maHP);
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
        return e -> JOptionPane.showMessageDialog(SubjectForm.this, message);
    }

    private JPanel createDetailPanel() {
        maHPField = new JTextField(10);
        tenHPField = new JTextField(10);
        soTinChiField = new JTextField(10);
        soTietField = new JTextField(10);

        JPanel detailPanel = new JPanel(new GridLayout(4, 2));
        detailPanel.add(new JLabel("Mã HP:"));
        detailPanel.add(maHPField);
        detailPanel.add(new JLabel("Tên HP:"));
        detailPanel.add(tenHPField);
        detailPanel.add(new JLabel("Số Tín Chỉ:"));
        detailPanel.add(soTinChiField);
        detailPanel.add(new JLabel("Số Tiết:"));
        detailPanel.add(soTietField);

        detailPanel.setVisible(false);

        return detailPanel;
    }

    private void updateDetailPanel(HocPhan hocPhan) {
        maHPField.setText(hocPhan.getMaHP());
        tenHPField.setText(hocPhan.getTenHP());
        soTinChiField.setText(String.valueOf(hocPhan.getSoTinChi()));
        soTietField.setText(String.valueOf(hocPhan.getSoTiet()));
    }

    private HocPhan getSelectedSubject(int rowIndex) {
        return new HocPhan(
                subjectTable.getValueAt(rowIndex, 0).toString(),
                subjectTable.getValueAt(rowIndex, 1).toString(),
                Integer.parseInt(subjectTable.getValueAt(rowIndex, 2).toString()),
                Integer.parseInt(subjectTable.getValueAt(rowIndex, 3).toString())
        );
    }

    private void themvaoTable(HocPhan hocPhan) {
        Vector<Object> value = new Vector<>();
        value.add(hocPhan.getMaHP());
        value.add(hocPhan.getTenHP());
        value.add(hocPhan.getSoTinChi());
        value.add(hocPhan.getSoTiet());
        dfTableModel.addRow(value);
    }

    private void filterTable(String searchText) {
        TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(dfTableModel);
        subjectTable.setRowSorter(rowSorter);

        if (searchText.length() == 0) {
            rowSorter.setRowFilter(null);
        } else {
            rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SubjectForm().setVisible(true));
    }
}
