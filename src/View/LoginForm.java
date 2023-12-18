package View;
import View.DatabaseManager;
import View.MainMenuForm;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JButton loginButton;

    public LoginForm() {
        setTitle("Đăng nhập");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        GroupLayout layout = new GroupLayout(panel);
        panel.setLayout(layout);

        JLabel usernameLabel = new JLabel("Tên người dùng:");
        JLabel passwordLabel = new JLabel("Mật khẩu:");

        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        loginButton = new JButton("Đăng nhập");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Gọi phương thức kiểm tra đăng nhập từ DatabaseManager
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (DatabaseManager.isValidLogin(username, password)) {
                    // Đăng nhập thành công, mở form chính
                    openMainMenu();
                } else {
                    // Đăng nhập thất bại, hiển thị thông báo lỗi
                    JOptionPane.showMessageDialog(LoginForm.this, "Đăng nhập không thành công. Vui lòng thử lại.");
                }
            }
        });

        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(usernameLabel)
                .addComponent(passwordLabel));
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(usernameField)
                .addComponent(passwordField)
                .addComponent(loginButton));
        layout.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(usernameLabel)
                .addComponent(usernameField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(passwordLabel)
                .addComponent(passwordField));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                .addComponent(loginButton));
        layout.setVerticalGroup(vGroup);

        add(panel);
    }

    private void openMainMenu() {
        MainMenuForm mainMenuForm = new MainMenuForm();
        mainMenuForm.setVisible(true);
        dispose(); // Đóng form đăng nhập sau khi đăng nhập thành công
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
