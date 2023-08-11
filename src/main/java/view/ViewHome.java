package view;
import com.formdev.flatlaf.FlatLightLaf;
import controller.ControllerDish;
import controller.ControllerPerson;
import controller.ControllerTable;


import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;


public class ViewHome extends JFrame {
    // view chính-------------------------------------------------------------------------------------------------------
    private static ViewMenu viewMenu ;
    private static ViewDish viewDish;
    private static ViewNewMenu viewNewMenu ;
    private static ViewTable viewTable ;
    private static ViewPerson viewPerson;
    private static ViewBooking viewBooking;

    // get + set--------------------------------------------------------------------------------------------------------
    public static ViewMenu getViewMenu() {
        return viewMenu;
    }

    public static ViewDish getViewDish() {
        return viewDish;
    }
    public static ViewNewMenu getViewNewMenu() {
        return viewNewMenu;
    }
    public static ViewTable getViewTable() {
        return viewTable;
    }
    public static ViewPerson getViewPerson() {
        return viewPerson;
    }

    public ViewHome(){

        try {
            // chuyển giao diện sang giống ios
            UIManager.setLookAndFeel(new FlatLightLaf());

            // Khởi tạo các thành phần tĩnh và áp dụng giao diện
            viewMenu = new ViewMenu();
            viewDish = new ViewDish();
            viewNewMenu = new ViewNewMenu();
            viewTable = new ViewTable();
            viewPerson = new ViewPerson();
            viewBooking = new ViewBooking();

            // ... (các phần mã khác)
        } catch (Exception e){
            e.printStackTrace();
        }
        // set controller-----------------------------------------------------------------------------------------------


//        khung ngoài phần mềm------------------------------------------------------------------------------------------
        setTitle("Register");             //        tiêu đề cho form
        // Tạo panel chính, mainPanel là một JPanel chứa toàn bộ giao diện của ứng dụng
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.decode("#293740"));

//        phần tab chuyển-----------------------------------------------------------------------------------------------
        //        tạo đối tượng tab
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
        tab1.add(viewPerson,BorderLayout.CENTER);
        tab2.add(viewTable.viewTableMain(),BorderLayout.CENTER);
        tab3.add(viewBooking,BorderLayout.CENTER);
        tab4.add(viewDish.ViewMainDish(),BorderLayout.CENTER);
        tab5.add(viewNewMenu,BorderLayout.CENTER);
//        tab6.add(viewNewMenu,BorderLayout.CENTER);




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



        // Thêm một ChangeListener vào tabbedPane
        tabbedPane.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // Lấy chỉ số của tab đang được chọn
                int selectedIndex = tabbedPane.getSelectedIndex();

                // Thực hiện hành động dựa trên chỉ số tab đang chọn
                switch (selectedIndex) {
                    case 0:
                        // Cập nhật nội dung cho tab 1 (Nhân viên)
                        tab1.removeAll(); // Xóa các thành phần hiện tại
                        tab1.add(viewPerson, BorderLayout.CENTER); // Thêm các thành phần mới
                        break;
                    case 1:
                        // Cập nhật nội dung cho tab 2 (Giao dịch)
                        tab2.removeAll(); // Xóa các thành phần hiện tại
                        tab2.add(viewTable, BorderLayout.CENTER); // Thêm các thành phần mới

                        break;
                    case 2:
                        // Load lại nội dung cho tab 3 (Đặt chỗ)
                        tab3.removeAll(); // Xóa các thành phần hiện tại
                        tab3.add(viewBooking, BorderLayout.CENTER); // Thêm các thành phần mới
                        break;
                    case 3:
                        // Load lại nội dung cho tab 4 (Quản lý)
                        tab4.removeAll(); // Xóa các thành phần hiện tại

//                        dishView.tabDish(dishView);

                        tab4.add(viewDish, BorderLayout.CENTER); // Thêm các thành phần mới

                        break;
                    case 4:
                        // Load lại nội dung cho tab 5 (Thực đơn)
                        tab5.removeAll(); // Xóa các thành phần hiện tại
                        tab5.add(viewNewMenu, BorderLayout.CENTER); // Thêm các thành phần mới
                        break;
//                    case 5:
//                        // Load lại nội dung cho tab 5 (Thực đơn)
//                        tab6.removeAll(); // Xóa các thành phần hiện tại
//                        tab6.add(oderView, BorderLayout.CENTER); // Thêm các thành phần mới
//                        break;
                }

                // Thực hiện việc validate lại và vẽ lại tab để phản ánh các thay đổi
                tabbedPane.revalidate();
                tabbedPane.repaint();
            }
        });

//        Đảm bảo các thành phần giao diện được hiển thị----------------------------------------------------------------

        add(mainPanel);
        revalidate();
        repaint();
        setSize(1500, 900);
        setLocationRelativeTo(null);
        //        setBackground(Color.BLUE);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public static void main(String[] args) {
        // Thực hiện các tác vụ khi chạy ứng dụng từ lớp HomeView
        // Ví dụ: Tạo đối tượng HomeView và hiển thị giao diện
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


