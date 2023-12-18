package View;
import Model.ChuyenNganh;
import dao.DAOManagerment;
import dao.FetchDataChuyenNganh;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

public class ManagementForm extends JFrame {
    private DefaultTableModel dfTableModel = new DefaultTableModel(null, new String[]{"Mã Ngành", "Tên Ngành"});
    private JTextField searchField;
    private JTable chuyenNganhTable;
    private JPanel detailPanel;
    private JTextField maNganhField;
    private JTextField tenNganhField;
    private JPanel buttonPanel;
    private DAOManagerment daoManagerment = new DAOManagerment();

    public ManagementForm() {
        initComponents();
        loadData();

        chuyenNganhTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = chuyenNganhTable.getSelectedRow();
                if (selectedRow != -1) {
                    ChuyenNganh selectedChuyenNganh = getSelectedChuyenNganh(selectedRow);
                    updateDetailPanel(selectedChuyenNganh);
                    detailPanel.setVisible(true);
                } else {
                    detailPanel.setVisible(false);
                }
            }
        });

        setTitle("Quản lý Chuyên ngành");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        searchField = new JTextField(15);
    JButton searchButton = new JButton("Tìm kiếm");
    chuyenNganhTable = new JTable();
    detailPanel = createDetailPanel();
    buttonPanel = createButtonPanel();

    JPanel mainPanel = new JPanel(new BorderLayout());
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    JPanel searchPanel = createSearchPanel();

    mainPanel.add(searchPanel, BorderLayout.NORTH);
    mainPanel.add(new JScrollPane(chuyenNganhTable), BorderLayout.CENTER);

    JPanel southPanel = new JPanel(new BorderLayout());
    southPanel.add(detailPanel, BorderLayout.NORTH);
    southPanel.add(buttonPanel, BorderLayout.SOUTH);

    mainPanel.add(southPanel, BorderLayout.SOUTH);

    add(mainPanel);
    }

    private void loadData() {
        java.util.List<ChuyenNganh> lst = new ArrayList<>();
        FetchDataChuyenNganh csdl = new FetchDataChuyenNganh();

        try {
            lst = csdl.getChuyenNganh();
            for (ChuyenNganh hh : lst) {
                Vector<Object> value = new Vector<>();
                value.add(hh.getMaNganh());
                value.add(hh.getTenNganh());
                dfTableModel.addRow(value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        chuyenNganhTable.setModel(dfTableModel);
    }

    private JPanel createSearchPanel() {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton searchButton = new JButton("Tìm kiếm");

        searchPanel.add(new JLabel("Mã ngành:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
        @Override
        public void insertUpdate(javax.swing.event.DocumentEvent e) {
            // Xử lý khi có sự thay đổi trong văn bản (insert)
            String searchText = searchField.getText().trim();
            filterTable(searchText);
        }

        @Override
        public void removeUpdate(javax.swing.event.DocumentEvent e) {
            // Xử lý khi có sự thay đổi trong văn bản (remove)
            String searchText = searchField.getText().trim();
            filterTable(searchText);
        }

        @Override
        public void changedUpdate(javax.swing.event.DocumentEvent e) {
            // Xử lý khi có sự thay đổi trong văn bản (change)
            String searchText = searchField.getText().trim();
            filterTable(searchText);
        }
    });

    // Thêm action listener cho nút tìm kiếm
    searchButton.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String searchText = searchField.getText().trim();
            filterTable(searchText);
        }
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
        String maNganh = JOptionPane.showInputDialog(ManagementForm.this, "Nhập Mã Ngành:");
        String tenNganh = JOptionPane.showInputDialog(ManagementForm.this, "Nhập Tên Ngành:");

        if (maNganh != null && tenNganh != null) {
            ChuyenNganh newChuyenNganh = new ChuyenNganh(Integer.parseInt(maNganh), tenNganh);
            int row = daoManagerment.insertChuyenNganh(newChuyenNganh);

            if (row > 0) {
                JOptionPane.showMessageDialog(null, "Thêm thành công");
                themvaoTable(newChuyenNganh);
            } else {
                JOptionPane.showMessageDialog(null, "Thêm không thành công");
            }
        }
    });
        
     huyButton.addActionListener(e -> {
    // Ẩn frame hiện tại
    setVisible(false);

    // Hiển thị frame menu
    MainMenuForm menuForm = new MainMenuForm();  // Thay MenuForm bằng tên lớp của form menu
    menuForm.setVisible(true);
});

     suaButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        ChuyenNganh updateChuyenNganh = new ChuyenNganh(
            Integer.parseInt(maNganhField.getText()),
            tenNganhField.getText()
        );
        int row = daoManagerment.updateChuyenNganh(updateChuyenNganh);
        if (row > 0) {
            JOptionPane.showMessageDialog(null, "Sửa thành công");
            dfTableModel.setNumRows(0);
            loadData();
        } else {
            JOptionPane.showMessageDialog(null, "Chưa sửa được");
        }
    }
});
xoaButton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {

ChuyenNganh deleteChuyenNganh=new ChuyenNganh(Integer.parseInt(maNganhField.getText()),
            tenNganhField.getText()
);
int row=daoManagerment.deleteChuyenNganh(deleteChuyenNganh);
if(row>0) {
JOptionPane.showMessageDialog(null, "Xóa thành công");
dfTableModel.setNumRows(0);
loadData();
}
else
JOptionPane.showMessageDialog(null, "Chưa xóa được");
}
});



    
        buttonPanel.add(suaButton);
        buttonPanel.add(xoaButton);
        buttonPanel.add(luuButton);
        buttonPanel.add(huyButton);

        return buttonPanel;
    }

    private ActionListener createButtonAction(String message) {
        return e -> JOptionPane.showMessageDialog(ManagementForm.this, message);
    }

    private JPanel createDetailPanel() {
        maNganhField = new JTextField(10);
        tenNganhField = new JTextField(10);

        JPanel detailPanel = new JPanel(new GridLayout(3, 2));
        detailPanel.add(new JLabel("Mã ngành:"));
        detailPanel.add(maNganhField);
        detailPanel.add(new JLabel("Tên ngành:"));
        detailPanel.add(tenNganhField);

        detailPanel.setVisible(false);  // Ẩn detailPanel ban đầu

        return detailPanel;
    }

    private void updateDetailPanel(ChuyenNganh chuyenNganh) {
        maNganhField.setText(String.valueOf(chuyenNganh.getMaNganh()));
        tenNganhField.setText(chuyenNganh.getTenNganh());
    }

    private ChuyenNganh getSelectedChuyenNganh(int rowIndex) {
        return new ChuyenNganh(
                Integer.parseInt(chuyenNganhTable.getValueAt(rowIndex, 0).toString()),
                chuyenNganhTable.getValueAt(rowIndex, 1).toString()
        );
    }
    
   
  

private void themvaoTable(ChuyenNganh cn) {
    Vector<Object> value = new Vector<>();
    value.add(cn.getMaNganh());
    value.add(cn.getTenNganh());
    dfTableModel.addRow(value);
}
private void filterTable(String searchText) {
    TableRowSorter<DefaultTableModel> rowSorter = new TableRowSorter<>(dfTableModel);
    chuyenNganhTable.setRowSorter(rowSorter);

    if (searchText.length() == 0) {
        rowSorter.setRowFilter(null);
    } else {
        rowSorter.setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }
}
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ManagementForm().setVisible(true));
    }
}
