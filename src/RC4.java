import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class RC4 implements ActionListener {
    // Các thành phần của giao diện
    private JFrame jFrame;                   // Cửa sổ chính của chương trình
    private JTextField keyField;             // Ô nhập khóa bí mật (key)
    private JTextField pathField;            // Ô hiển thị đường dẫn file/thư mục
    private JButton chooseButton, encryptButton, decryptButton;  // Các nút chức năng
    private JFileChooser fileChooser;        // Hộp thoại chọn file/thư mục

    // Hàm khởi tạo giao diện
    public RC4() {
        jFrame = new JFrame("RC4 File/Folder Encryption");   // Tạo cửa sổ có tiêu đề
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); // Thoát chương trình khi đóng cửa sổ
        jFrame.setSize(700, 400);          // Đặt kích thước cửa sổ 700x400 pixel
        jFrame.setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình
        jFrame.setLayout(new GridBagLayout()); // Dùng layout dạng lưới linh hoạt

        GridBagConstraints gbc = new GridBagConstraints(); // Tạo bộ quản lý vị trí các thành phần
        gbc.insets = new Insets(10, 10, 10, 10);            // Căn lề cho mỗi thành phần (10px mỗi hướng)
        gbc.fill = GridBagConstraints.HORIZONTAL;           // Cho phép các ô giãn ngang

        JLabel keyLabel = new JLabel("Secret Key:"); // Nhãn hiển thị "Secret Key"
        gbc.gridx = 0;  // Cột 0
        gbc.gridy = 0;  // Hàng 0
        jFrame.add(keyLabel, gbc); // Thêm nhãn vào cửa sổ

        keyField = new JTextField();   // Ô nhập khóa
        gbc.gridx = 1;  // Cột 1
        gbc.gridy = 0;  // Hàng 0
        gbc.gridwidth = 3; // Chiếm 3 cột
        jFrame.add(keyField, gbc);     // Thêm ô nhập khóa vào giao diện

        JLabel pathLabel = new JLabel("File/Folder Path:"); // Nhãn hiển thị "File/Folder Path"
        gbc.gridx = 0;  // Cột 0
        gbc.gridy = 1;  // Hàng 1
        gbc.gridwidth = 1; // Chiếm 1 cột
        jFrame.add(pathLabel, gbc);    // Thêm nhãn vào cửa sổ

        pathField = new JTextField();  // Ô hiển thị đường dẫn
        pathField.setEditable(false);  // Không cho sửa bằng tay
        gbc.gridx = 1;  // Cột 1
        gbc.gridy = 1;  // Hàng 1
        gbc.gridwidth = 2; // Chiếm 2 cột
        jFrame.add(pathField, gbc);    // Thêm vào cửa sổ

        chooseButton = new JButton("Browse..."); // Nút chọn file
        gbc.gridx = 3;  // Cột 3
        gbc.gridy = 1;  // Hàng 1
        gbc.gridwidth = 1; // Chiếm 1 cột
        jFrame.add(chooseButton, gbc); // Thêm nút chọn file

        encryptButton = new JButton("Encrypt"); // Nút mã hóa
        gbc.gridx = 1;  // Cột 1
        gbc.gridy = 2;  // Hàng 2
        jFrame.add(encryptButton, gbc); // Thêm nút mã hóa

        decryptButton = new JButton("Decrypt"); // Nút giải mã
        gbc.gridx = 3;  // Cột 3
        gbc.gridy = 2;  // Hàng 2
        jFrame.add(decryptButton, gbc); // Thêm nút giải mã

        fileChooser = new JFileChooser(); // Tạo hộp thoại chọn file
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES); // Cho phép chọn file hoặc thư mục

        // Gán hành động cho 3 nút
        chooseButton.addActionListener(this);
        encryptButton.addActionListener(this);
        decryptButton.addActionListener(this);

        jFrame.setVisible(true); // Hiển thị cửa sổ
    }

    // Xử lý sự kiện khi người dùng bấm nút
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource(); // Lấy đối tượng nguồn gây ra sự kiện

        if (src == chooseButton) { // Nếu người dùng bấm nút "Browse"
            int result = fileChooser.showOpenDialog(jFrame); // Mở hộp thoại chọn file
            if (result == JFileChooser.APPROVE_OPTION) { // Nếu người dùng chọn OK
                pathField.setText(fileChooser.getSelectedFile().getAbsolutePath()); // Hiển thị đường dẫn file đã chọn
            }

        } else if (src == encryptButton) { // Nếu bấm "Encrypt"
            String key = keyField.getText().trim();  // Lấy khóa bí mật người dùng nhập
            String path = pathField.getText().trim(); // Lấy đường dẫn file
            if (key.isEmpty() || path.isEmpty()) { // Kiểm tra nếu chưa nhập khóa hoặc chọn file
                JOptionPane.showMessageDialog(jFrame, "Please enter key and choose File/Folder!", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Thoát khỏi hàm
            }
            encryptFileOrFolder(new File(path), key); // Gọi hàm mã hóa
            JOptionPane.showMessageDialog(jFrame, "Encryption completed!"); // Thông báo hoàn tất

        } else if (src == decryptButton) { // Nếu bấm "Decrypt"
            String key = keyField.getText().trim();
            String path = pathField.getText().trim();
            if (key.isEmpty() || path.isEmpty()) {
                JOptionPane.showMessageDialog(jFrame, "Please enter key and choose File/Folder!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            encryptFileOrFolder(new File(path), key); // Giải mã RC4 = mã hóa lại 1 lần nữa
            JOptionPane.showMessageDialog(jFrame, "Decryption completed!");
        }
    }

    // Đọc nội dung file thành mảng byte
    private byte[] readFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) { // Mở file để đọc
            return fis.readAllBytes(); // Đọc toàn bộ dữ liệu
        }
    }

    // Ghi mảng byte vào file
    private void writeFile(File file, byte[] data) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) { // Mở file để ghi
            fos.write(data); // Ghi dữ liệu vào file
        }
    }

    // Hàm thực hiện thuật toán RC4
    private byte[] rc4(byte[] data, String key) {
        byte[] S = new byte[256]; // Mảng S (state)
        byte[] T = new byte[256]; // Mảng T (key stream)
        int keyLen = key.length(); // Độ dài khóa

        //Hàm KSA
        for (int i = 0; i < 256; i++) {
            S[i] = (byte) i;                         // Khởi tạo S = 0..255
            T[i] = (byte) key.charAt(i % keyLen);    // Tạo mảng T từ khóa lặp lại
        }

        int j = 0;
        for (int i = 0; i < 256; i++) {
            j = (j + (S[i] & 0xFF) + (T[i] & 0xFF)) % 256; // Trộn S bằng T và chính S
            byte temp = S[i]; // Hoán đổi S[i] và S[j]
            S[i] = S[j];
            S[j] = temp;
        }

        // Thuật toán sinh chuỗi PRGA
        int i = 0;
        j = 0;
        byte[] result = new byte[data.length]; // Mảng chứa kết quả mã hóa
        for (int n = 0; n < data.length; n++) {
            i = (i + 1) % 256;
            j = (j + (S[i] & 0xFF)) % 256;
            byte temp = S[i]; // Hoán đổi S[i], S[j]
            S[i] = S[j];
            S[j] = temp;
            int k = (S[i] + S[j]) & 0xFF; // Sinh byte khóa
            byte t = S[k];
            result[n] = (byte) (data[n] ^ t); // XOR byte dữ liệu với byte khóa
        }

        return result; // Trả về dữ liệu đã mã hóa/giải mã
    }

    // Hàm mã hóa/giải mã file hoặc thư mục
    private void encryptFileOrFolder(File file, String key) {
        if (file.isDirectory()) { // Nếu là thư mục
            File[] files = file.listFiles(); // Lấy tất cả file con
            if (files != null) {
                for (File f : files) { // Lặp qua từng file con
                    encryptFileOrFolder(f, key); // Gọi lại đệ quy để xử lý từng file
                }
            }
        } else { // Nếu là file
            try {
                byte[] data = readFile(file);         // Đọc dữ liệu file
                byte[] encrypted = rc4(data, key);    // Mã hóa dữ liệu bằng RC4
                writeFile(file, encrypted);           // Ghi dữ liệu mã hóa lại vào file
            } catch (IOException ex) { // Nếu lỗi khi đọc/ghi
                JOptionPane.showMessageDialog(jFrame, "Error processing file: " + file.getName(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Hàm main - điểm bắt đầu chương trình
    public static void main(String[] args) {
        SwingUtilities.invokeLater(RC4::new); // Tạo giao diện RC4 trong luồng GUI an toàn
    }
}
