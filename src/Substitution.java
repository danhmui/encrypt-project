import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Substitution implements ActionListener {
    JFrame jFrame;
    private JLabel jLabKeyE, jLabKeyD;
    private JTextField jTextKeyE, jTextKeyD, jTextAlphabet;
    private JButton btnEncrypt, btnDecrypt, btnRandom, btnBack;
    private JTextArea jTextPlain, jTextCipher;
    private JScrollPane jScrollP, jScrollC;
    Substitution() {
        //title
        jFrame = new JFrame("Bang chu don demo");
        // Dừng chương trình khi đóng cửa sổ
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // xắp sếp các thành phần thành dạng lưới linh hoạt
        jFrame.setLayout(new GridBagLayout());
        // Kích thước của cửa sổ
        jFrame.setSize(800,500);

        // bố trí
        GridBagConstraints gbc = new GridBagConstraints();
        //khoảng cách xung quanh
        gbc.insets = new Insets(5,5,5,5);
        // giãn ra theo chiều ngang để lấp ô khi còn trống
        gbc.fill = GridBagConstraints.HORIZONTAL;
        // ========== Ô nhập plainText ==========
        // 5 dòng 1 dòng gồm 30 kí tự
        jTextPlain = new JTextArea(5,30);
        //thanh cuộn
        jScrollP = new JScrollPane(jTextPlain);
        // cột đầu tiên (tọa độ x)
        gbc.gridx = 0;
        // hàng đầu tiên (tọa độ y)
        gbc.gridy = 0;
        // ô này chiếm 2 cột
        gbc.gridwidth = 3;
        // ô này chiếm 3 hàng
        gbc.gridheight = 3;
        // thêm thanh cuộn vào jFrame
        jFrame.add(jScrollP, gbc);
        // nhãn cho khóa mã hóa
        jLabKeyE = new JLabel("key");
        gbc.gridx = 4;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        jFrame.add(jLabKeyE, gbc);
        // ô nhập khóa mã hóa
        jTextKeyE = new JTextField(10);
        jTextKeyE.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 6;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        jFrame.add(jTextKeyE, gbc);

        // ô nhập bảng chữ cái
        //mặc định 26 kí tự
        jTextAlphabet = new JTextField("abcdefghijklmnopqrstuvwxyz", 26);
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.gridheight = 1;
        jFrame.add(jTextAlphabet, gbc);
        //Nút random(tạo khóa ngẫu nhiên)
        btnRandom = new JButton("Random");
        gbc.gridx = 6;
        gbc.gridy = 2;
        gbc.gridheight = 1;
        jFrame.add(btnRandom, gbc);
        // nút mã hóa
        btnEncrypt = new JButton("Encryption");
        gbc.gridx = 6;
        gbc.gridy = 3;
        gbc.gridheight = 1;
        jFrame.add(btnEncrypt, gbc);

        // vùng văn bản 5 dòng
        jTextCipher = new JTextArea(5, 30);
        // thêm thanh cuộn
        jScrollC = new JScrollPane(jTextCipher);
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 3;
        jFrame.add(jScrollC, gbc);
        // nhãn cho khóa giải mã
        jLabKeyD = new JLabel("key");
        gbc.gridx = 4;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        jFrame.add(jLabKeyD, gbc);
        // ô nhập khóa giải mã
        jTextKeyD = new JTextField(10);
        jTextKeyD.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 6;
        gbc.gridy = 4;
        gbc.gridheight = 1;
        jFrame.add(jTextKeyD, gbc);

        //Nút Decryption
        btnDecrypt = new JButton("Decryption");
        gbc.gridx = 6;
        gbc.gridy = 5;
        jFrame.add(btnDecrypt, gbc);
        //Nút Trở về
        btnBack = new JButton("Trở về");
        gbc.gridx = 0;
        gbc.gridy = 7;
        jFrame.add(btnBack, gbc);

        //định danh sự kiện
        jTextKeyD.setActionCommand("KeyD");
        jTextKeyE.setActionCommand("KeyE");
        jTextAlphabet.setActionCommand("KeyAlphabet");

        //thêm các xử lí sự kiện cho các thành phần
        btnDecrypt.addActionListener(this);
        btnBack.addActionListener(this);
        btnEncrypt.addActionListener(this);
        btnRandom.addActionListener(this);
        jTextKeyE.addActionListener(this);
        jTextKeyD.addActionListener(this);
        jTextAlphabet.addActionListener(this);

        jFrame.setVisible(true);
    }
    //Xử lí sự kiện
    @Override
    public void actionPerformed(ActionEvent e) {
        //Xác định nút nào được bấm
        Object src = e.getSource();

        if (src == btnRandom) {
            String key = randomKey();
            jTextKeyE.setText(key);
            jTextKeyD.setText(key);
        } else if (src == btnEncrypt) {
            String text = jTextPlain.getText();
            String key = jTextKeyE.getText().toLowerCase();
            jTextCipher.setText(encrypt(text, key));
        } else if (src == btnDecrypt) {
            String text = jTextCipher.getText();
            String key = jTextKeyD.getText().toLowerCase();
            jTextPlain.setText(decrypt(text, key));
        } else if(src ==btnBack){
            new Main().setVisible(true);
            jFrame.dispose();
        }
    }
    private String randomKey() {
        // Chuỗi alphabet ban đầu:
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        //Tạo mảng kí tự ngẫu nhiên từ alphabet
        char[] a = alphabet.toCharArray();
        Random random = new Random();
        //Hàm lấy các kí tự ngẫu nhiên từ alphabet
        for(int i = alphabet.length() - 1; i >= 0; i--){
            // Lấy ngẫu nhiên các số nguyên từ 0 đến i +1
            int r = random.nextInt(i + 1);
            char ch = a[i];
            a[i] = a[r];
            a[r] = ch;
        }
        return new String(a);
    }
    private String encrypt(String text, String key) {
        //Lấy chuỗi đã sắp theo theo alphabet
        String alphabet = jTextAlphabet.getText();
        StringBuilder sb = new StringBuilder();
        //chuyển các kí tự trong mảng text thành kí tự thường và duyệt
        for (char ch : text.toLowerCase().toCharArray()) {
            int idx = alphabet.indexOf(ch);
            //lấy kí tự của key có thứ tự trùng với thú tự của mảng alphabet
            if (idx != -1)
                sb.append(key.charAt(idx));
            else
                sb.append(ch);
        }
        return sb.toString();
    }

    private String decrypt(String text, String key) {
        //Lấy chuỗi đã sắp theo theo alphabet
        String alphabet = jTextAlphabet.getText();
        StringBuilder sb = new StringBuilder();
        //===========Hàm giải mã===============
        //chuyển các kí tự trong mảng text thành kí tự thường và duyệt
        for (char ch : text.toLowerCase().toCharArray()) {
            int idx = key.indexOf(ch);
            //Lấy kí tự của mảng alphabet có vị trí trùng với vị trí của kí tự trong key
            if (idx != -1) {
                sb.append(alphabet.charAt(idx));
            } else {
                sb.append(ch);
            }
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Substitution();
            }
        });
    }
}
