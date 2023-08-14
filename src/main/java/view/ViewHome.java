package view;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import java.awt.*;



public class ViewHome extends JFrame {
    public ViewHome(){
//        khung ngoài phần mềm------------------------------------------------------------------------------------------
        setTitle("Register");             //        tiêu đề cho form
        // Tạo panel chính, mainPanel là một JPanel chứa toàn bộ giao diện của ứng dụng
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.decode("#293740"));
//        phần tab chuyển-----------------------------------------------------------------------------------------------
        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        JPanel tab1 = new JPanel();
        JPanel tab2 = new JPanel();
        JPanel tab3 = new JPanel();
        JPanel tab4 = new JPanel();
        JPanel tab5 = new JPanel();
        JPanel tab6 = new JPanel();
        tabbedPane.addTab("Employee", tab1);
        tabbedPane.addTab("Transaction", tab2);
        tabbedPane.addTab("Booking", tab3);
        tabbedPane.addTab("Dish", tab4);
        tabbedPane.addTab("Menu", tab5);
        tabbedPane.addTab("Oder", tab6);
        tab1.setLayout(new BorderLayout());
        tab2.setLayout(new BorderLayout());
        tab3.setLayout(new BorderLayout());
        tab4.setLayout(new BorderLayout());
        tab5.setLayout(new BorderLayout());
        tab6.setLayout(new BorderLayout());

//        thêm các view vào tab
        // một đối tượng jpanel chỉ dùng dc ở một nơi, nếu gọi lần hai ở nơi khác thì nơi gọi đầu tiên sẽ bị mất jpanel đó
        tab1.add(MainProgram.getViewPersonMain(),BorderLayout.CENTER);
        tab2.add(MainProgram.getViewTransaction(),BorderLayout.CENTER);
        tab3.add(MainProgram.getViewBooking(),BorderLayout.CENTER);
        tab4.add(MainProgram.getViewDishMain(),BorderLayout.CENTER);
        tab5.add(MainProgram.getViewNewMenuMain(),BorderLayout.CENTER);
        tab6.add(MainProgram.getViewListBooking(),BorderLayout.CENTER);

        tab1.setBackground(Color.green);
        tab2.setBackground(Color.ORANGE);
        tab3.setBackground(Color.CYAN);
        tab4.setBackground(Color.cyan);
        tab5.setBackground(Color.PINK);
        tab6.setBackground(Color.pink);


        tabbedPane.setTabComponentAt(0, new JLabel("Employee"));
        tabbedPane.setTabComponentAt(1, new JLabel("Transaction"));
        tabbedPane.setTabComponentAt(2, new JLabel("Booking"));
        tabbedPane.setTabComponentAt(3, new JLabel("Dish"));
        tabbedPane.setTabComponentAt(4, new JLabel("Menu"));
        tabbedPane.setTabComponentAt(5, new JLabel("Oder"));
        // Tạo mảng chứa kích thước mới cho các tab
        Dimension[] tabSizes = {new Dimension(100, 50),
                new Dimension(150, 50),
                new Dimension(100, 50),
                new Dimension(100, 50),
                new Dimension(100, 50),
                new Dimension(100, 50)};
        // Đặt kích thước mới cho tab
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getTabComponentAt(i);
            if (tabComponent instanceof JLabel) {
                JLabel tabLabel = (JLabel) tabComponent;
                tabLabel.setPreferredSize(tabSizes[i]);
            }
        }
        // Đặt kích thước phông chữ cho tất cả các tab
        Font tabFont = new Font("MyFont", Font.BOLD, 14);
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getTabComponentAt(i);
            if (tabComponent instanceof JLabel) {
                JLabel tabLabel = (JLabel) tabComponent;
                tabLabel.setFont(tabFont);
            }
        }
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getTabComponentAt(i);
            if (tabComponent instanceof JLabel) {
                JLabel tabLabel = (JLabel) tabComponent;
                tabLabel.setHorizontalAlignment(SwingConstants.CENTER); // Căn giữa chữ trong tab
            }
        }
        // thay đổi màu chữ trong tab
        Color tabTextColor = Color.decode("#FFEFD7"); // Thay đổi màu chữ ở đây

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            Component tabComponent = tabbedPane.getTabComponentAt(i);
            if (tabComponent instanceof JLabel) {
                JLabel tabLabel = (JLabel) tabComponent;
                tabLabel.setForeground(tabTextColor); // Đặt màu chữ cho tất cả các tab
            }
        }
        // thay đổi màu nền của tab
        tabbedPane.setBackground(Color.decode("#293740"));

        // thêm các tab vào layout chính
        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        add(mainPanel);
        revalidate();
        repaint();
        setSize(1500, 900);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        // Thực hiện các tác vụ khi chạy ứng dụng từ lớp HomeView
        try {
            //            chuyển giao diện sang giống ios
            UIManager.setLookAndFeel(new FlatLightLaf());
            ViewHome homeView = new ViewHome();
            homeView.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}


