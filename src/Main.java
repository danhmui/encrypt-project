import javax.swing.*;
import java.awt.*;

public class Main extends JFrame {

    public Main() {
        setTitle("Menu");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(10, 10, 10, 10));

        JLabel title = new JLabel("CHỌN KIỂU MÃ HÓA", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));

        JButton btnCeasar = new JButton("Caesar");
        JButton btnSub = new JButton("Bảng Chữ Đơn");
        JButton btnVigenere = new JButton("Vigenere");
        JButton btnPlayFair = new JButton("Play Fair");
        JButton btnCry = new JButton("Chuyển Dịch Dòng");

        JButton btnExit = new JButton("Thoát");

        add(title);
        add(btnCeasar);
        add(btnSub);
        add(btnVigenere);
        add(btnPlayFair);
        add(btnCry);
        add(btnExit);

        // Sự kiện bấm nút
        btnCeasar.addActionListener(e -> {
            new Ceasar().setVisible(true);
            dispose(); // đóng menu chính nếu muốn
        });

        btnCry.addActionListener(e -> {
            new RowPosition().setVisible(true);
            dispose();
        });

        btnVigenere.addActionListener(e -> {
            new VigenereCipherGUI().setVisible(true);
            dispose();
        });
        btnPlayFair.addActionListener(e -> {
            new PlayFairGUI().setVisible(true);
            dispose();
        });
        btnSub.addActionListener(e -> {
            new Substitution();
            dispose();
        });

        btnExit.addActionListener(e -> System.exit(0));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }
}
