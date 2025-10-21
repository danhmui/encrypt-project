
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RowPosition extends Ceasar{
    RowPosition(){
        //Gọi phương thức khởi tạo từ lớp cha
        super();
        // ========== Xử lý sự kiện ==========
        btnEncrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jTextPlain.getText();         // lấy plaintext
                String key = jTextKeyE.getText();           // lấy khóa
                jTextCipher.setText(encrypt(text, key));    // hiển thị kết quả mã hóa
            }
        });
        btnDecrypt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = jTextCipher.getText();        // lấy ciphertext
                String key = jTextkeyD.getText();           // lấy khóa
                jTextPlain.setText(decrypt(text, key));     // hiển thị kết quả giải mã
            }
        });
    }
    // ========== Hàm mã hóa ==========
    private String encrypt(String text, String key) {
        //Lấy mảng nguyên đã kiểm tra thứ tự kí tự của key
        int[] order = getOrder(key);
        //số cột là độ dài của key
        int columns = key.length();
        //thêm 'x' cho đến khi độ dài của chuỗi text chia hết cho số cột
        while (text.length() % columns != 0)
            text += 'x';
        //số hàng
        int rows = text.length() / columns;
        char[][] table = new char[rows][columns];
        // ghi plaintext theo hàng
        int index = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                table[r][c] = text.charAt(index++);
            }
        }
        // đọc theo thứ tự cột
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < columns; i++) {
            // lấy theo thứ tự kí tự của mảng order
            int col = order[i];
            for (int r = 0; r < rows; r++) {
                result.append(table[r][col]);
            }
        }
        return result.toString();
    }
    private String decrypt(String cipher, String key) {
        //Lấy mảng nguyên đã kiểm tra thứ tự kí tự của key
        int[] order = getOrder(key);
        //số cột là độ dài của key
        int columns = key.length();
        //số hàng
        int rows = cipher.length() / columns;
        char[][] table = new char[rows][columns];
        // ghi ciphertext theo thứ tự cột
        int index = 0;
        for (int i = 0; i < columns; i++) {
            // lấy theo thứ tự kí tự của mảng order
            int col = order[i];
            for (int r = 0; r < rows; r++) {
                table[r][col] = cipher.charAt(index++);
            }
        }

        // đọc lại theo hàng
        StringBuilder plain = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                plain.append(table[r][c]);
            }
        }

        return plain.toString();
    }
    private int[] getOrder(String key) {
        //tạo mảng kí tự chứa các kí tự của key
        char[] chars = key.toCharArray();
        //tạo mảng chứa các đã được kiểm tra thứ tự trong key
        int len = chars.length;
        int[] order = new int[len];

        // tạo mảng đã sắp xếp theo alphabet từ key
        char[] sorted = key.toCharArray();
        Arrays.sort(sorted);
        //Hàm kiểm tra thứ tự trong mảng kí tự chars
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (chars[i] == sorted[j]) {
                    order[i] = j;
                    sorted[j] = 0; // tránh trùng ký tự
                    break;
                }
            }
        }
        return order;
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RowPosition().setVisible(true);
        });
    }
}
