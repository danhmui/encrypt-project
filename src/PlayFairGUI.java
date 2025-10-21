import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PlayFairGUI extends JFrame {
    private JTextArea plainTextArea, cipherTextArea;
    private JTextArea keyMatrix1, keyMatrix2;
    private JTextField keyEncryptField, keyDecryptField;
    private JButton btnGenerateKey, btnEncrypt, btnDecrypt, btnBack;

    private PlayFairCipher encryptCipher;
    private PlayFairCipher decryptCipher;

    // ===== CLASS GIAO DIỆN =====
    public PlayFairGUI() {
        encryptCipher = new PlayFairCipher();
        decryptCipher = new PlayFairCipher();

        setTitle("Thuật toán Play Fair");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        // Panel chính
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        // ===== PlainText =====
        plainTextArea = new JTextArea(5, 30);
        plainTextArea.setLineWrap(true);
        plainTextArea.setWrapStyleWord(true);
        JScrollPane scrollPlain = new JScrollPane(plainTextArea);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        panel.add(scrollPlain, gbc);

        // ===== Key Label (Encryption) =====
        JLabel lblKey1 = new JLabel("Key");
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblKey1, gbc);

        // ===== Key Field (Encryption) =====
        keyEncryptField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        panel.add(keyEncryptField, gbc);

        // ===== Key Matrix 1 =====
        keyMatrix1 = new JTextArea(3, 12);
        keyMatrix1.setEditable(false);
        keyMatrix1.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollMatrix1 = new JScrollPane(keyMatrix1);
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollMatrix1, gbc);

        // ===== Generate Key Button =====
        btnGenerateKey = new JButton("Generate key");
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btnGenerateKey, gbc);

        // ========== Back Button ==========
        btnBack = new JButton("Trở về");
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(btnBack, gbc);

        // ===== Encryption Button =====
        btnEncrypt = new JButton("Encryption");
        gbc.gridx = 3;
        gbc.gridy = 2;
        panel.add(btnEncrypt, gbc);

        // ===== CipherText =====
        cipherTextArea = new JTextArea(5, 30);
        cipherTextArea.setLineWrap(true);
        cipherTextArea.setWrapStyleWord(true);
        JScrollPane scrollCipher = new JScrollPane(cipherTextArea);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollCipher, gbc);

        // ===== Key Label (Decryption) =====
        JLabel lblKey2 = new JLabel("Key");
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(lblKey2, gbc);

        // ===== Key Field (Decryption) =====
        keyDecryptField = new JTextField(10);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.weightx = 0.3;
        panel.add(keyDecryptField, gbc);

        // ===== Key Matrix 2=====
        keyMatrix2 = new JTextArea(3, 12);
        keyMatrix2.setEditable(false);
        keyMatrix2.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollMatrix2 = new JScrollPane(keyMatrix2);
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 0.5;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollMatrix2, gbc);

        // ===== Decryption Button =====
        btnDecrypt = new JButton("Decryption");
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(btnDecrypt, gbc);

        // ===== Add Event Listeners =====
        setupEventListeners();

        // ===== Add Panel =====
        add(panel);
    }

    private void setupEventListeners() {
        // Generate Key Button
        btnGenerateKey.addActionListener(e -> {
            String key = keyEncryptField.getText();
            String matrix = encryptCipher.generateMatrix(key);
            keyMatrix1.setText(matrix);

            // Copy key sang phần decrypt
            keyDecryptField.setText(key);
            keyMatrix2.setText(matrix);
            decryptCipher.generateMatrix(key);
        });

        // Encryption Button
        btnEncrypt.addActionListener(e -> {
            if (keyMatrix1.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng generate key trước!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            String plaintext = plainTextArea.getText();
            String encrypted = encryptCipher.encrypt(plaintext);
            cipherTextArea.setText(encrypted);
        });
        //Back Button
        btnBack.addActionListener(e -> {
            new Main().setVisible(true); // mở lại menu chính
            dispose(); // đóng cửa sổ Caesar
        });
        // Decryption Button
        btnDecrypt.addActionListener(e -> {
            String key = keyDecryptField.getText();
            if (key.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập key!",
                        "Cảnh báo",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Generate matrix nếu chưa có
            if (keyMatrix2.getText().trim().isEmpty()) {
                String matrix = decryptCipher.generateMatrix(key);
                keyMatrix2.setText(matrix);
            }

            String ciphertext = cipherTextArea.getText();
            String decrypted = decryptCipher.decrypt(ciphertext);
            plainTextArea.setText(decrypted);
        });

        // Auto generate key khi nhấn Enter trong key field
        keyEncryptField.addActionListener(e -> btnGenerateKey.doClick());
    }

    // ===== CLASS XỬ LÝ THUẬT TOÁN =====
    public class PlayFairCipher {
        private char[][] matrix = new char[5][5];

        // Tạo ma trận 5x5 từ key
        public String generateMatrix(String key) {
            if (key == null || key.trim().isEmpty()) {
                throw new IllegalArgumentException("Key không được để trống!");
            }

            key = key.toUpperCase().replaceAll("[^A-Z]", "");
            if (key.isEmpty()) {
                throw new IllegalArgumentException("Key phải chứa ít nhất một chữ cái!");
            }

            key = key.replace('J', 'I');

            // Loại bỏ ký tự trùng lặp trong key
            StringBuilder uniqueKey = new StringBuilder();
            boolean[] used = new boolean[26];

            for (char c : key.toCharArray()) {
                if (!used[c - 'A']) {
                    uniqueKey.append(c);
                    used[c - 'A'] = true;
                }
            }

            // Thêm các chữ cái còn lại (trừ J)
            for (char c = 'A'; c <= 'Z'; c++) {
                if (c != 'J' && !used[c - 'A']) {
                    uniqueKey.append(c);
                }
            }

            // Điền vào ma trận 5x5
            int idx = 0;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    matrix[i][j] = uniqueKey.charAt(idx++);
                }
            }

            return getMatrixString();
        }

        // Chuyển ma trận thành chuỗi để hiển thị
        public String getMatrixString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    sb.append(matrix[i][j]).append(" ");
                }
                sb.append("\n");
            }
            return sb.toString();
        }

        // Tìm vị trí của ký tự trong ma trận
        private int[] findPosition(char c) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (matrix[i][j] == c) {
                        return new int[]{i, j};
                    }
                }
            }
            return null;
        }

        // Chuẩn bị plaintext
        private String preparePlaintext(String text) {
            text = text.toUpperCase().replaceAll("[^A-Z]", "");
            text = text.replace('J', 'I');

            StringBuilder prepared = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                prepared.append(text.charAt(i));

                // Nếu 2 ký tự giống nhau, thêm 'X' ở giữa
                if (i < text.length() - 1 && text.charAt(i) == text.charAt(i + 1)) {
                    prepared.append('X');
                }
            }

            // Nếu độ dài lẻ, thêm 'X' vào cuối
            if (prepared.length() % 2 != 0) {
                prepared.append('X');
            }

            return prepared.toString();
        }

        // Mã hóa
        public String encrypt(String plaintext) {
            if (plaintext == null || plaintext.trim().isEmpty()) {
                throw new IllegalArgumentException("Plaintext không được để trống!");
            }

            plaintext = preparePlaintext(plaintext);
            StringBuilder ciphertext = new StringBuilder();

            for (int i = 0; i < plaintext.length(); i += 2) {
                char a = plaintext.charAt(i);
                char b = plaintext.charAt(i + 1);

                int[] posA = findPosition(a);
                int[] posB = findPosition(b);

                if (posA == null || posB == null) continue;

                int rowA = posA[0], colA = posA[1];
                int rowB = posB[0], colB = posB[1];

                if (rowA == rowB) {
                    // Cùng hàng: dịch sang phải
                    ciphertext.append(matrix[rowA][(colA + 1) % 5]);
                    ciphertext.append(matrix[rowB][(colB + 1) % 5]);
                } else if (colA == colB) {
                    // Cùng cột: dịch xuống dưới
                    ciphertext.append(matrix[(rowA + 1) % 5][colA]);
                    ciphertext.append(matrix[(rowB + 1) % 5][colB]);
                } else {
                    // Tạo hình chữ nhật: đổi cột
                    ciphertext.append(matrix[rowA][colB]);
                    ciphertext.append(matrix[rowB][colA]);
                }
            }

            return ciphertext.toString();
        }

        // Giải mã
        public String decrypt(String ciphertext) {
            if (ciphertext == null || ciphertext.trim().isEmpty()) {
                throw new IllegalArgumentException("Ciphertext không được để trống!");
            }

            ciphertext = ciphertext.toUpperCase().replaceAll("[^A-Z]", "");
            StringBuilder plaintext = new StringBuilder();

            for (int i = 0; i < ciphertext.length(); i += 2) {
                if (i + 1 >= ciphertext.length()) break;

                char a = ciphertext.charAt(i);
                char b = ciphertext.charAt(i + 1);

                int[] posA = findPosition(a);
                int[] posB = findPosition(b);

                if (posA == null || posB == null) continue;

                int rowA = posA[0], colA = posA[1];
                int rowB = posB[0], colB = posB[1];

                if (rowA == rowB) {
                    // Cùng hàng: dịch sang trái
                    plaintext.append(matrix[rowA][(colA + 4) % 5]);
                    plaintext.append(matrix[rowB][(colB + 4) % 5]);
                } else if (colA == colB) {
                    // Cùng cột: dịch lên trên
                    plaintext.append(matrix[(rowA + 4) % 5][colA]);
                    plaintext.append(matrix[(rowB + 4) % 5][colB]);
                } else {
                    // Tạo hình chữ nhật: đổi cột
                    plaintext.append(matrix[rowA][colB]);
                    plaintext.append(matrix[rowB][colA]);
                }
            }

            return plaintext.toString();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PlayFairGUI().setVisible(true);
        });
    }
}
