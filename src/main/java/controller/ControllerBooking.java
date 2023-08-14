package controller;

import dao.MenuNameDAO;
import dao.PersonDAO;
import dao.TableListDAO;
import model.Booking;
import model.MenuName;
import model.Person;
import model.TableList;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ControllerBooking {
    // data-------------------------------------------------------------------------------------------------------------
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static Set<Integer> setTableId = new HashSet<>(); // tạo set chứa id bàn
    private static int idPerson;
    private static int idMenu;
    private static int idInTempBooking;

    // view ------------------------------------------------------------------------------------------------------------
    private static ViewTable viewTable = MainProgram.getViewTableInBooking();
    private static ViewMenu viewMenuSelect = MainProgram.getViewMenuSelectInBooking();
    private static ViewMenu viewMenuCreat = MainProgram.getViewMenuCreatInBooking();
    private static ViewNewMenu viewNewMenu = MainProgram.getViewNewMenuInBooking();
    private static ViewPerson viewPerson = MainProgram.getViewPersonInBooking();
    private static ViewBooking viewBooking;

    // get + set--------------------------------------------------------------------------------------------------------
    public static int getIdMenu() {
        return idMenu;
    }
    public static void setIdMenu(int idMenu) {
        ControllerBooking.idMenu = idMenu;
    }
    public static int getIdPerson() {
        return idPerson;
    }
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
        System.out.println("ControllerBooking dc gọi a");

        // sự kiện mở bảng chọn menu
        JButton buttonSelectMenuFromList = viewBooking.getButtonSelectMenuFromList();
        buttonSelectMenuFromList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu
                viewBooking.getCenterViewBooking().add(viewMenuSelect, BorderLayout.CENTER);
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });

        // sự kiện mở bảng tạo mới menu
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

        // chuyển sang tab chọn bàn
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





        selectTable();
        selectPerson();
        selectMenu();

        // bảng table---------------------------------------------------------------------------------------------------

        // sự kiện click vào bảng
        JTable tables = viewTable.getTable();
        tables.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = tables.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = tables.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        idInTempBooking = (Integer.parseInt(id));
                        System.out.println("Bảng BookingListView đang chọn hàng có id là: "+ id);
                    }
                }
            }
        });


    }
    public static void selectPerson(){
        ViewPerson viewPerson = MainProgram.getViewPersonInBooking();
        JButton buttonSelectPerson = viewPerson.getButtonSelectPerson();
        buttonSelectPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBooking viewBooking = MainProgram.getViewBooking();
                ControllerBooking.setViewBooking(viewBooking);
                System.out.println("check");
                idPerson = viewPerson.getIdSelect();
                if (viewBooking!= null){
                    reloadBlockInfoPerson(viewBooking);
                }else {
                    System.out.println("null rồi bà con ơi");
                }
//                TransactionListView.reloadJpanel();
            }
        });
    }

    // chọn người
    public static void reloadBlockInfoPerson(ViewBooking viewBooking){
        int id = ControllerBooking.getIdPerson();
        Person person = PersonDAO.getInstance().getById(id);
        viewBooking.getLabelFirstNameValue().setText(person.getName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelLastNameValue().setText(person.getLastName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelFirstNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
        viewBooking.getLabelLastNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
    }

    public static void selectMenu(){
        ViewMenu viewMenu = MainProgram.getViewMenuSelectInBooking();
        JButton selectMenu = viewMenu.getButtonSelectMenu();
        selectMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                idMenu = viewMenu.getMenuIdSelect();
                System.out.println("Select Menu");
                MenuName menuName = MenuNameDAO.getInstance().getById(idMenu);
                if (bookings.size() == 0){
                    Booking booking = new Booking();
                    booking.setMenuName(menuName);
                    bookings.add(booking);
                }else {
                    boolean foundEmptyMenuName = false;
                    for (Booking booking : bookings) {
                        if (booking.getMenuName() == null) {
                            booking.setMenuName(menuName);
                            foundEmptyMenuName = true;
                            break;
                        }
                    }
                    if (!foundEmptyMenuName) {
                        // Nếu không tìm thấy booking có menuName null, thêm mới một booking với menuName vào danh sách.
                        Booking newBooking = new Booking();
                        newBooking.setMenuName(menuName);
                        bookings.add(newBooking);
                    }
                }
                MainProgram.getViewListBooking().loadData();
            }
        });
    }


    // đang lỗi kich hoạt hai lần
    public static void selectTable(){  // chọn bàn
        JButton buttonSelectTable = viewTable.getButtonSelectTable();
        buttonSelectTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int tableIdSelect = viewTable.getIdSelect();
                System.out.println("nhận :" + tableIdSelect);
                // lấy về đối tương table theo id bàn đã chọn từ bảng
                TableList tableObject = TableListDAO.getInstance().getById(tableIdSelect);
                System.out.println(tableObject.getType().getName());
                // nếu id bàn đã có trong set
                if (setTableId.contains(tableIdSelect)){
                    // đã tồn tại trong set
                    JOptionPane.showMessageDialog(null, "You can't book a table twice !", "Notice", JOptionPane.WARNING_MESSAGE);
                }else {
                    // chưa tồn tại trong set thì thêm vào
                    setTableId.add(tableIdSelect);
                    if (bookings.size() == 0){   // danh sách chưa có phần tử nào thì tạo mới và thêm thuộc tính
                        System.out.println("tạo mới");
                        Booking newBooking = new Booking();
                        newBooking.setTable(tableObject);
                        bookings.add(newBooking);
                    }else {
                        boolean foundEmptyMenuName = false;   // nếu đã có phần tử và có thuộc tính trống thì duyệt qua list và thêm thuộc tính trống cho 1 phần tử và thoát vòng
                        for (Booking booking : bookings) {
                            if (booking.getTable() == null) {
                                booking.setTable(tableObject);
                                foundEmptyMenuName = true;
                                break;
                            }
                        }
                        if (!foundEmptyMenuName) {    // nếu có phần tủ nhưng tất cả đã dc thêm thuộc tính thì tạo mới 1 phần tử và thêm thuộc tính cho nó
                            Booking newBooking = new Booking();
                            newBooking.setTable(tableObject);
                            bookings.add(newBooking);
                        }
                    }
                }
                MainProgram.getViewListBooking().loadData();
            }
        });
    }
}
