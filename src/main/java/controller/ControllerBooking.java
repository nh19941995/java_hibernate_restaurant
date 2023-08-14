package controller;

import dao.PersonDAO;
import model.Booking;
import model.Person;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ControllerBooking {
    private static ArrayList<Booking> bookings = new ArrayList<>();
    public static ViewTable viewTable = new ViewTable().viewTableSelectTable();
    public static ViewMenu viewMenu = new ViewMenu().ViewChoseMenu();
    public static ViewBooking viewBooking  = new ViewBooking();
    public static ViewNewMenu viewNewMenu = new ViewNewMenu();
    public static ViewPerson viewPerson = new ViewPerson().ViewPersonSelect();
    public static int idPerson;

    public static int getIdPerson() {
        return idPerson;
    }

    // get + set--------------------------------------------------------------------------------------------------------


    public static ViewBooking getViewBooking() {
        return viewBooking;
    }

    public static void setViewBooking(ViewBooking viewBooking) {
        ControllerBooking.viewBooking = viewBooking;
    }

    public static ViewTable getViewTable() {
        return viewTable;
    }

    public static void setViewTable(ViewTable viewTable) {
        ControllerBooking.viewTable = viewTable;
    }

    public static JPanel getViewMenu() {
        return viewMenu;
    }

    public static void setViewMenu(ViewMenu viewMenu) {
        ControllerBooking.viewMenu = viewMenu;
    }

    public static JPanel getViewNewMenu() {
        return viewNewMenu;
    }

    public static void setViewNewMenu(ViewNewMenu viewNewMenu) {
        ControllerBooking.viewNewMenu = viewNewMenu;
    }

    public static ViewPerson getViewPerson() {
        return viewPerson;
    }

    public static void setViewPerson(ViewPerson viewPerson) {
        ControllerBooking.viewPerson = viewPerson;
    }

    public static void setIdPerson(int idPerson) {
        ControllerBooking.idPerson = idPerson;
    }

    public static ArrayList<Booking> getBookings() {
        return bookings;
    }

    public static void setBookings(ArrayList<Booking> bookings) {
        ControllerBooking.bookings = bookings;
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
        JButton buttonSelectPersonFromList = viewBooking.getButtonSelectPersonFromList();
        buttonSelectPersonFromList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu
                viewBooking.getCenterViewBooking().add(MainProgram.getViewPersonInBooking(), BorderLayout.CENTER);
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });






        // sự kiện chọn người từ bảng person
//
//        JButton buttonSelectPerson = ControllerBooking.getViewPerson().ViewPersonSelect().getButtonSelectPerson();
////        JButton buttonSelectPerson = viewPerson.getButtonSelectPerson();
//        buttonSelectPerson.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                System.out.println("haha");
//
//            }
//        });







    }
//    public static void reloadBlockInfoPerson(ViewBooking viewBooking){
//        int id = ControllerBooking.getIdPerson();
//        Person person = PersonDAO.getInstance().getById(id);
//        viewBooking.getLabelFirstName().setText(person.getName()); // Đặt giá trị cho JLabel
//        viewBooking.getLabelLastName().setText(person.getLastName()); // Đặt giá trị cho JLabel
//        viewBooking.getLabelFirstName().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
//        viewBooking.getLabelLastName().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
//    }
}
