import javax.swing.*;
import java.awt.*;

public class VigenereCipherGUI extends JFrame {
    // Các thành phần giao diện
    private JTextArea plaintextArea, ciphertextArea;  // Vùng nhập plaintext và ciphertext
    private JTextField keyField1, keyField2;  // Trường nhập key
    private JTextField keyGenField1, keyGenField2;  // Hiển thị key generation
    private JButton btnEncrypt, btnDecrypt, btnBack;  // Nút mã hóa và giải mã
    private VigenereCipher cipher;  // Đối tượng xử lý mã hóa

    //Constructor khởi tạo giao diện
    public VigenereCipherGUI() {
        cipher = new VigenereCipher();
        setTitle("Vigenère Cipher");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(750, 400);
        setLocationRelativeTo(null);  // Hiển thị ở giữa màn hình

        // Tạo panel chính với GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(230, 232, 235));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.BOTH;

        // === PHẦN TRÊN: Plaintext và Encrypt ===

        // Vùng nhập plaintext
        plaintextArea = new JTextArea(6, 30);
        plaintextArea.setLineWrap(true);
        plaintextArea.setWrapStyleWord(true);
        JScrollPane scrollPlain = new JScrollPane(plaintextArea);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        panel.add(scrollPlain, gbc);

        // Label "Key"
        JLabel lblKey1 = new JLabel("Key");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblKey1, gbc);

        // Trường nhập key cho mã hóa
        keyField1 = new JTextField(15);
        keyField1.setBackground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        panel.add(keyField1, gbc);

        // Label "Key generation"
        JLabel lblKeyGen1 = new JLabel("Key generation");
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(lblKeyGen1, gbc);

        // Trường hiển thị key generation (không cho chỉnh sửa)
        keyGenField1 = new JTextField(15);
        keyGenField1.setEditable(false);
        keyGenField1.setBackground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 1;
        panel.add(keyGenField1, gbc);

        // Nút Encrypt
        btnEncrypt = new JButton("Encrypt");
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(btnEncrypt, gbc);

        // === PHẦN DƯỚI: Ciphertext và Decrypt ===

        // Vùng hiển thị ciphertext
        ciphertextArea = new JTextArea(6, 30);
        ciphertextArea.setLineWrap(true);
        ciphertextArea.setWrapStyleWord(true);
        JScrollPane scrollCipher = new JScrollPane(ciphertextArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollCipher, gbc);

        // Label "Key"
        JLabel lblKey2 = new JLabel("Key");
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblKey2, gbc);

        // Trường nhập key cho giải mã
        keyField2 = new JTextField(15);
        keyField2.setBackground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.weightx = 0.5;
        panel.add(keyField2, gbc);

        // Label "Key generation"
        JLabel lblKeyGen2 = new JLabel("Key generation");
        gbc.gridx = 1;
        gbc.gridy = 4;
        panel.add(lblKeyGen2, gbc);

        // Trường hiển thị key generation cho giải mã
        keyGenField2 = new JTextField(15);
        keyGenField2.setEditable(false);
        keyGenField2.setBackground(Color.WHITE);
        gbc.gridx = 2;
        gbc.gridy = 4;
        panel.add(keyGenField2, gbc);

        // Nút Decrypt
        btnDecrypt = new JButton("Decrypt");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        panel.add(btnDecrypt, gbc);

        //Nút trở về
        btnBack = new JButton("Trở về");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 1;

        panel.add(btnBack, gbc);

        setupEventListeners();
        add(panel);
    }

    /**
     * Thiết lập các sự kiện cho các nút
     */
    private void setupEventListeners() {
        // Xử lý khi click nút Encrypt
        btnEncrypt.addActionListener(e -> {
            try {
                String plaintext = plaintextArea.getText();
                String key = keyField1.getText();

                // Tạo key string lặp lại theo độ dài plaintext
                String keyGen = cipher.generateKeyString(plaintext, key);
                keyGenField1.setText(keyGen);

                // Mã hóa và hiển thị kết quả
                String encrypted = cipher.encrypt(plaintext, key);
                ciphertextArea.setText(encrypted);

                // Copy key sang phần decrypt để tiện sử dụng
                keyField2.setText(key);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        //Back Button
        btnBack.addActionListener(e -> {
            new Main().setVisible(true); // mở lại menu chính
            dispose(); // đóng cửa sổ Caesar
        });
        // Xử lý khi click nút Decrypt
        btnDecrypt.addActionListener(e -> {
            try {
                String ciphertext = ciphertextArea.getText();
                String key = keyField2.getText();

                // Tạo key string
                String keyGen = cipher.generateKeyString(ciphertext, key);
                keyGenField2.setText(keyGen);

                // Giải mã và hiển thị kết quả
                String decrypted = cipher.decrypt(ciphertext, key);
                plaintextArea.setText(decrypted);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    //  Thuật Toán
    public class VigenereCipher {

        //        Tạo chuỗi key lặp lại theo độ dài của text
        public String generateKeyString(String text, String key) {
            // Kiểm tra key không được rỗng
            if (key == null || key.isEmpty()) {
                throw new IllegalArgumentException("Key không được để trống!");
            }

            // Chỉ giữ lại các chữ cái và chuyển về chữ thường
            String cleanKey = key.replaceAll("[^a-zA-Z]", "").toLowerCase();
            if (cleanKey.isEmpty()) {
                throw new IllegalArgumentException("Key phải chứa ít nhất một chữ cái!");
            }

            StringBuilder keyString = new StringBuilder();
            int keyIndex = 0;
            int keyLen = cleanKey.length();

            // Lặp qua từng ký tự trong text
            for (char c : text.toCharArray()) {
                // Chỉ tạo key cho chữ cái và khoảng trắng
                if (Character.isLetter(c) || c == ' ') {
                    keyString.append(cleanKey.charAt(keyIndex % keyLen));
                    keyIndex++;
                }
            }
            return keyString.toString();
        }

        //         Mã hóa plaintext sử dụng Vigenère Cipher
        public String encrypt(String plaintext, String key) {
            // Kiểm tra plaintext không được rỗng
            if (plaintext == null || plaintext.trim().isEmpty()) {
                throw new IllegalArgumentException("Plaintext không được để trống!");
            }

            String keyString = generateKeyString(plaintext, key);
            StringBuilder ciphertext = new StringBuilder();
            int keyIndex = 0;

            for (char c : plaintext.toCharArray()) {
                if (Character.isLetter(c)) {
                    // Mã hóa chữ cái: C = (P + K) mod 26
                    boolean isUpper = Character.isUpperCase(c);
                    char base = isUpper ? 'A' : 'a';
                    int plainValue = Character.toLowerCase(c) - 'a';  // 0-25
                    int keyValue = keyString.charAt(keyIndex) - 'a';  // 0-25
                    int cipherValue = (plainValue + keyValue) % 26;
                    char encryptedChar = (char) (cipherValue + base);
                    ciphertext.append(encryptedChar);
                    keyIndex++;
                } else if (c == ' ') {
                    // Mã hóa khoảng trắng: value = 26, sử dụng mod 27
                    int plainValue = 26;
                    int keyValue = keyString.charAt(keyIndex) - 'a';
                    int cipherValue = (plainValue + keyValue) % 27;
                    if (cipherValue == 26) {
                        ciphertext.append(' ');  // Kết quả vẫn là khoảng trắng
                    } else {
                        ciphertext.append((char) ('a' + cipherValue));  // Thành chữ cái
                    }
                    keyIndex++;
                } else {
                    // Giữ nguyên các ký tự đặc biệt khác (số, dấu câu,...)
                    ciphertext.append(c);
                }
            }
            return ciphertext.toString();
        }

        //        Giải mã ciphertext sử dụng Vigenère Cipher
        public String decrypt(String ciphertext, String key) {
            // Kiểm tra ciphertext không được rỗng
            if (ciphertext == null || ciphertext.trim().isEmpty()) {
                throw new IllegalArgumentException("Ciphertext không được để trống!");
            }

            String keyString = generateKeyString(ciphertext, key);
            StringBuilder plaintext = new StringBuilder();
            int keyIndex = 0;

            for (char c : ciphertext.toCharArray()) {
                // Giải mã: P = (C - K + 26) mod 26
                boolean isUpper = Character.isUpperCase(c);
                char base = isUpper ? 'A' : 'a';
                int cipherValue = Character.toLowerCase(c) - 'a';  // 0-25
                int keyValue = keyString.charAt(keyIndex) - 'a';  // 0-25
                int plainValue = (cipherValue - keyValue + 26) % 26;  // +26 để tránh số âm
                char decryptedChar = (char) (plainValue + base);
                plaintext.append(decryptedChar);
                keyIndex++;
            }
            return plaintext.toString();
        }
    }

    /**
     * Hàm main - khởi chạy ứng dụng
     */
    public static void main(String[] args) {
        try {
            // Sử dụng giao diện hệ thống
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Khởi chạy GUI trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            new VigenereCipherGUI().setVisible(true);
        });
    }
}