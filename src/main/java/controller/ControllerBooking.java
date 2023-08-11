package controller;

import dao.PersonDAO;
import model.Person;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControllerBooking {
//    public static JPanel viewTable = new ViewTable().viewTableSelectTable();
    public static ViewTable viewTable = new ViewTable().viewTableSelectTable();
    public static JPanel viewMenu = new ViewMenu().ViewChoseMenu();
    public static JPanel viewNewMenu = new ViewNewMenu();
    public static ViewPerson viewPerson = new ViewPerson();
    public static int idPerson;

    public static int getIdPerson() {
        return idPerson;
    }

    public ControllerBooking(ViewBooking viewBooking) {



        // sự kiện chọn bàn
        JButton buttonTableFromList = viewBooking.getButtonSelectTableFromList();
        buttonTableFromList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu


                viewBooking.getCenterViewBooking().add(viewTable, BorderLayout.CENTER);
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });

        // sự kiện chọn menu
        JButton buttonSelectMenuFromList = viewBooking.getButtonSelectMenuFromList();
        buttonSelectMenuFromList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu
                viewBooking.getCenterViewBooking().add(viewMenu, BorderLayout.CENTER);
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });

        // sự kiện tạo mới menu
        JButton buttonAddNewMenu = viewBooking.getButtonAddNewMenu();
        buttonAddNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu
                viewBooking.getCenterViewBooking().add(viewNewMenu, BorderLayout.CENTER);
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });

        // sự kiện mở bảng person
        JButton buttonSelectClientFromList = viewBooking.getButtonSelectClientFromList();
        buttonSelectClientFromList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu
                viewBooking.getCenterViewBooking().add(new ViewPerson(), BorderLayout.CENTER);
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });





        // sự kiện chọn người từ bảng person
//        JButton buttonAddNewMenu = viewBooking.getButtonAddNewMenu();
        buttonAddNewMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu
                viewBooking.getCenterViewBooking().add(viewNewMenu, BorderLayout.CENTER);
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });







    }
    public static void reloadBlockInfoPerson(ViewBooking viewBooking){
        int id = ControllerBooking.getIdPerson();
        Person person = PersonDAO.getInstance().getById(id);
        viewBooking.getLabelFirstName().setText(person.getName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelLastName().setText(person.getLastName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelFirstName().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
        viewBooking.getLabelLastName().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
    }
}
