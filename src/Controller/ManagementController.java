package Controller;

import Model.ChuyenNganh;
import dao.FetchDataChuyenNganh;
import View.ManagementForm;

import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;

public class ManagementController {
    private ManagementForm view;
    private FetchDataChuyenNganh fetchDataChuyenNganh;

    public ManagementController(ManagementForm view, FetchDataChuyenNganh fetchDataChuyenNganh) {
        this.view = view;
        this.fetchDataChuyenNganh = fetchDataChuyenNganh;

        // Gắn các sự kiện và xử lý logic ở đây
       /* view.getThemButton().addActionListener(e -> themChuyenNganh());
        view.getSuaButton().addActionListener(e -> suaChuyenNganh());
        view.getXoaButton().addActionListener(e -> xoaChuyenNganh());
        view.getLuuButton().addActionListener(e -> luuChuyenNganh());
        view.getHuyButton().addActionListener(e -> huyChuyenNganh());*/

        // Hiển thị dữ liệu ban đầu
        hienThiDanhSachChuyenNganh();
    }

    private void hienThiDanhSachChuyenNganh() {
        try {
            List<ChuyenNganh> lst = fetchDataChuyenNganh.getChuyenNganh();
            //view.hienThiDanhSachChuyenNganh(lst);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(view, "Không thể kết nối đến cơ sở dữ liệu.");
        }
    }

    private void themChuyenNganh() {
        // Xử lý thêm chuyên ngành
        JOptionPane.showMessageDialog(view, "Chức năng Thêm");
    }

    private void suaChuyenNganh() {
        // Xử lý sửa chuyên ngành
        JOptionPane.showMessageDialog(view, "Chức năng Sửa");
    }

    private void xoaChuyenNganh() {
        // Xử lý xóa chuyên ngành
        JOptionPane.showMessageDialog(view, "Chức năng Xóa");
    }

    private void luuChuyenNganh() {
        // Xử lý lưu chuyên ngành
        JOptionPane.showMessageDialog(view, "Chức năng Lưu");
    }

    private void huyChuyenNganh() {
        // Xử lý hủy
        JOptionPane.showMessageDialog(view, "Chức năng Hủy");
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            ManagementForm view = new ManagementForm();
            FetchDataChuyenNganh fetchDataChuyenNganh = new FetchDataChuyenNganh();
            new ManagementController(view, fetchDataChuyenNganh);
            view.setVisible(true);
        });
    }
}

