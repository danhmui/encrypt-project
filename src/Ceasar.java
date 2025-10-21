import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Ceasar extends JFrame{
    public JTextArea jTextPlain , jTextCipher;
    public JTextField jTextKeyE, jTextkeyD;
    public JButton btnEncrypt, btnDecrypt, btnBack;

    public Ceasar (){
        setTitle(""); // Title
        setDefaultCloseOperation(EXIT_ON_CLOSE); // đống JFrame
        setSize(600,300); // size của JFrame
        setLocationRelativeTo(null); // căn giũa

        // ========== Panel ==========
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout()); // xắp sếp các thành phần thành dạng lưới linh hoạt
        GridBagConstraints gbc = new GridBagConstraints(); // bố trí
        gbc.insets = new Insets(5,5,5,5); // khoảng cách xung quanh
        gbc.fill = GridBagConstraints.HORIZONTAL; // giãn ra theo chiều ngang để lấp ô khi còn trống

        // ========== Ô nhập plainText ==========
        jTextPlain = new JTextArea(5,30); // 5 dòng 1 dòng gồm 30 kí tự
        JScrollPane scrollPlain = new JScrollPane(jTextPlain); // thanh cuộn
        gbc.gridx = 0;// cột đầu tiên (tọa độ x)
        gbc.gridy = 0;// hàng đầu tiên (tọa độ y)
        gbc.gridwidth = 2; // ô này chiếm 2 cột
        panel.add(scrollPlain,gbc); // thêm vùng văn bản có thanh cuộn vào panel theo vị trí gbc

        // ========== Key và nút Encryption ==========
        JLabel lblKey1 = new JLabel("Key");
        gbc.gridwidth = 1; // ô này chiếm 1 cột
        gbc.gridx = 2; // cột thứ 3 (tọa độ x)
        gbc.gridy = 0;// hàng đầu tiên (tọa độ y)
        panel.add(lblKey1, gbc);

        jTextKeyE = new JTextField(5);
        gbc.gridx = 3; // cột thứ 4 (tọa độ x)
        gbc.gridy = 0; // hàng đầu tiên (tọa độ y)
        panel.add(jTextKeyE, gbc);

        btnEncrypt = new JButton("Encryption");
        gbc.gridx = 3; // cột thứ 4 (tọa độ x)
        gbc.gridy = 1; // hàng thứ (tọa độ y)
        panel.add(btnEncrypt, gbc);

        // ========== Ô hiển thị văn bản mã hóa ==========
        jTextCipher = new JTextArea(5, 30);
        JScrollPane scrollCipher = new JScrollPane(jTextCipher);

        gbc.gridx = 0; // cột đàu tiê (tọa độ x)
        gbc.gridy = 2; // hàng thứ 3 (tọa độ y)
        gbc.gridwidth = 2; // ô này chiếm 2 cột
        panel.add(scrollCipher, gbc);

        // ========== Key và nút Decryption ==========
        JLabel lblKey2 = new JLabel("Key");
        gbc.gridwidth = 1; // ô này chiếm 1 cột
        gbc.gridx = 2;  // cột thứ 3 (tọa độ x)
        gbc.gridy = 2; // hàng thứ 3 (tọa độ y)
        panel.add(lblKey2, gbc);

        jTextkeyD = new JTextField(5);
        gbc.gridx = 3;  // cột thứ 4  (tọa độ x)
        gbc.gridy = 2; // hàng thứ 3 (tọa độ y)
        panel.add(jTextkeyD, gbc);

        btnDecrypt = new JButton("Decryption");
        gbc.gridx = 3;  // cột thứ 4 (tọa độ x)
        gbc.gridy = 3; // hàng thứ e (tọa độ y)
        panel.add(btnDecrypt, gbc);

        // ========== Nút Trở về ==========
        btnBack = new JButton("Trở về");
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(btnBack, gbc);

        // ========== Thêm panel vào frame ==========
        add(panel);

        // ========== Xử lý sự kiện ==========
        btnEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jTextPlain.getText();// lấy dữ liệu từu plainText
                int key = Integer.parseInt(jTextKeyE.getText()); //lấy dữ liệu từu keyEncrypt vì đây là string nên chuyển sang int
                jTextCipher.setText(encrypt(text,key)); // set cho cipherText đoạn mã sau mã hóa
            }
        });

        btnDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jTextCipher.getText();// lấy dữ liệu từu cipherText
                int key = Integer.parseInt(jTextkeyD.getText()); //lấy dữ liệu từu keyDecrypt vì đây là string nên chuyển sang int
                jTextPlain.setText(decrypt(text,key)); // set cho plainText đoạn mã sau giải mã
            }
        });
        btnBack.addActionListener(e -> {
            new Main().setVisible(true); // mở lại menu chính
            dispose(); // đóng cửa sổ Caesar
        });
    }

    // ========== Hàm mã hóa Caesar ==========

    private String encrypt(String text, int key ){
        StringBuilder res = new StringBuilder();
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)){ //kiểm tra xem c phải kí tự ko
                char base = Character.isLowerCase(c) ? 'a' : 'A'; // nếu c là chữ in thường thì base = 'a' ko thì base = 'A'
                c = (char) ((c - base + key) % 26 + base); //chuyển kí tự c thành kí tự mã hóa
            }
            res.append(c);
        }
        return res.toString();
    }

    // ========== Hàm giải mã Caesar ==========
    private String decrypt(String text, int key) {
        return encrypt(text, 26 - (key % 26)); //giải mã text
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Ceasar().setVisible(true);
        });
    }
}
