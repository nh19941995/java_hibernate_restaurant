package controller;

import dao.*;
import model.*;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ControllerBooking {
    // data-------------------------------------------------------------------------------------------------------------
    private static ArrayList<Booking> bookings = new ArrayList<>();
    private static Set<Integer> setTableId = new HashSet<>(); // tạo set chứa id bàn
    private static int idPerson;
    private static int idMenu;
    private static int idInTempBooking;
    private static Double deposit;





    // get + set--------------------------------------------------------------------------------------------------------


    public static Double getDeposit() {
        return deposit;
    }

    public static void setDeposit(Double deposit) {
        ControllerBooking.deposit = deposit;
    }

    public static int getIdPerson() {
        return idPerson;
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
        System.out.println("ControllerBooking dc gọi");

        selectList();
        selectPerson();
        selectMenu();
        submitNewBooking();
    }

    public static void selectList(){
        ViewBooking viewBooking = MainProgram.getViewBooking();
        ViewTable viewTable = MainProgram.getViewTableInBooking();
        ViewNewMenu viewNewMenu = MainProgram.getViewNewMenuInBooking();
        ViewMenu viewMenu = MainProgram.getViewMenuSelectInBooking();


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

        // sự kiện mở bảng chọn menu
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
        clickOnTempBooking();
    }

    public static void submitNewBooking(){
        ViewBooking viewBooking = MainProgram.getViewBooking();
        JButton buttonSubmid = viewBooking.getButtonSubmitBooking();
        buttonSubmid.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                System.out.println(personIdSelect);
                BookingsInfo newInfo = new BookingsInfo();
//                String startTimeString = InfoBookingView.getInputStartTime().getText();
                String startTimeString = viewBooking.getInputStartTime().getText();
//                String dateString = InfoBookingView.getInputDate().getText();
                String dateString = viewBooking.getInputDate().getText();
//                String endTimeString = InfoBookingView.getInputEndTime().getText();
                String endTimeString = viewBooking.getInputEndTime().getText();
//                String depositString = InfoBookingView.getInputDeposit().getText();
                String depositString = viewBooking.getInputDeposit().getText();
//                String commentString = InfoBookingView.getInputComment().getText();
                String commentString = viewBooking.getInputComment().getText();

                int check = 1;
                if (startTimeString.isEmpty()||endTimeString.isEmpty()||commentString.isEmpty()||idPerson==0||dateString.isEmpty()){
                    if (check==1){
                        JOptionPane.showMessageDialog(null, "You must fill in all the required information before proceeding to make a reservation !", "Notice", JOptionPane.WARNING_MESSAGE);
                    }
                    check =0;
                }
                if (!RegexMatcher.hourCheck(
                        startTimeString,"").equals("")
                        ||!RegexMatcher.hourCheck(endTimeString, "").equals("")
                        ||!RegexMatcher.dayCheck(dateString,"").equals("")
                ){
                    if (check ==1){
                        JOptionPane.showMessageDialog(null,
                                RegexMatcher.hourCheck(startTimeString, "Star time: ")+
                                        RegexMatcher.hourCheck(endTimeString, "End time: ")+
                                        RegexMatcher.dayCheck(dateString,"Date of event: "),
                                "Notice", JOptionPane.WARNING_MESSAGE);
                    }
                    check = 0;
                }
                if (!depositString.equals("")){
                    if (!RegexMatcher.numberCheck(depositString,"").equals("")){
                        if (check ==1){
                            JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(depositString, "Deposit: "), "Notice", JOptionPane.WARNING_MESSAGE);
                        }
                        check = 0;
                    }
                }
                BookingsInfo bookingsInfo = new BookingsInfo();
                if (check==1){
                    LocalDateTime instantStartTime = ControllerTime.parseDateTime(startTimeString,dateString);
                    LocalDateTime instantEndTime = ControllerTime.parseDateTime(startTimeString,dateString);

                    System.out.println("instantEndTime: "+instantEndTime);
                    System.out.println("startTimeString: " + startTimeString);
                    System.out.println("endTimeString: " + endTimeString);
                    System.out.println("dateString: " + dateString);
                    System.out.println("instantStartTime: "+instantStartTime);

                    LocalDateTime instantNow = LocalDateTime.now();


                    if (!depositString.isEmpty()){
                        Double doubleDeposit = Double.parseDouble(depositString);
                        setDeposit(doubleDeposit);
                        bookingsInfo.setDeposit(doubleDeposit);
                        // tạo giao dịch cọc tiền
                        Transaction tranDeposit = new Transaction();
                        tranDeposit.setDateCreat(LocalDateTime.now());
                        tranDeposit.setQuantity(doubleDeposit);
                        tranDeposit.setType(TransactionsTypeDAO.getInstance().getByName("Thu - Khách hàng đặt cọc"));
                        tranDeposit.setComment("Deposit: "+ commentString);
                        tranDeposit.setFlag(1);
                        tranDeposit.setPerson(PersonDAO.getInstance().getById(idPerson));
                        TransactionDAO.getInstance().insert(tranDeposit);
                        System.out.println("Khách hàng đã đặt cọc: " + doubleDeposit);
                    }
                    System.out.println(instantNow);

                    if (instantStartTime.isBefore(instantNow)) {
                        if (check == 1) {
                            JOptionPane.showMessageDialog(null,
                                    "Incorrect start date for the event. The start date must be after the current date. Please try again",
                                    "Notice",
                                    JOptionPane.WARNING_MESSAGE);
                            check = 0;
                        }
                    }
                    if (instantEndTime.isAfter(instantStartTime)) {
                        if (check == 1) {
                            JOptionPane.showMessageDialog(null,
                                    "The end date of the event must be after the start date. Please try again.",
                                    "Notice",
                                    JOptionPane.WARNING_MESSAGE);
                            check = 0;
                        }
                    }

                    if(checkInfoTableAndMenu()){
                        ArrayList<Integer> table = checkHourTable(instantStartTime,instantEndTime);
                        if (table.isEmpty()){
                            Person person = PersonDAO.getInstance().getById(idPerson);
                            bookingsInfo.setEnd(instantEndTime);
                            bookingsInfo.setStart(instantStartTime);
                            bookingsInfo.setDateCreat(instantNow);
                            bookingsInfo.setPerson(person);
                            bookingsInfo.setInfo(commentString);
                            bookingsInfo.setFlag(1);
                            BookingsInfoDAO.getInstance().insert(bookingsInfo);
                            System.out.println("khởi tạo id info: "+bookingsInfo.getId());
//                        check
                            getBookings().forEach(s->{
                                s.setInfo(bookingsInfo);
                                s.setFlag(1);
                                BookingDAO.getInstance().insert(s);
                            });
                            // tạo giao dịch nợ (chỉ tạo được sau khi tạo xong booking )
                            Transaction tranReceivable = new Transaction();
                            tranReceivable.setType(TransactionsTypeDAO.getInstance().getByName("Nợ - Khách hàng còn thiếu"));
                            tranReceivable.setDateCreat(LocalDateTime.now());
                            tranReceivable.setComment("Debt: "+commentString);
                            // tổng tiền phải thanh toán
                            Double bill = BookingsInfoDAO.getInstance().getTotalPriceByInfoBookingID(bookingsInfo.getId());
                            System.out.println("Khởi tạo bill: " + bill);
                            Double receivable = bill - getDeposit() ;
                            tranReceivable.setQuantity(receivable);
                            tranReceivable.setFlag(1);
                            tranReceivable.setPerson(PersonDAO.getInstance().getById(idPerson));
                            TransactionDAO.getInstance().insert(tranReceivable);
                            System.out.println("id khách hàng là: "+idPerson);
                            System.out.println("id info là: "+bookingsInfo.getId());
                            System.out.println("khách hàng còn nợ: "+ receivable);
                        }else {

                        }


                    }

                }
            }
        });
    }

    public static ArrayList<Integer> checkHourTable(LocalDateTime start,LocalDateTime end ){
        java.util.List<Booking> bookingList = BookingDAO.getInstance().getAll();

        List<Object[]> bookingListData = bookingList.stream().map(
                s -> new Object[]{
                        s.getTable().getId(),             // id bàn
                        s.getTable().getType().getName(), // loại bàn
                        s.getTable().getSeatingCapacity(),// số ghế
                        s.getInfo().getStart(),           // giờ bắt đầu
                        s.getInfo().getEnd(),             // giờ kết thúc
                        s.getInfo().getStart(),           // ngày
                        s.getTable().getStatus().getName()     // trạng thái của bàn
                }
        ).collect(Collectors.toList());

        ArrayList<Integer> tableId = new ArrayList<>();
        for (Object[] data : bookingListData) {
            LocalDateTime startTime = (LocalDateTime) data[3];
            LocalDateTime endTime = (LocalDateTime) data[4];


            boolean check= startTime.isAfter(end)||endTime.isBefore(start);
            if (!check) {
                tableId.add((Integer) data[0]);
            }
        }


        if (!tableId.isEmpty()){
            // Chuyển danh sách thành chuỗi bằng cách sử dụng StringBuilder
            StringBuilder stringBuilder = new StringBuilder();
            for (Integer number : tableId) {
                stringBuilder.append(number).append(", "); // Thêm mỗi số và dấu phẩy vào chuỗi
            }
            // Xóa dấu phẩy cuối cùng và khoảng trắng
            if (stringBuilder.length() > 0) {
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
            }
            // Lấy chuỗi kết quả
            String resultString = stringBuilder.toString();
            JOptionPane.showMessageDialog(null,
                    "The reservation time is already taken, please choose another table or a different time slot.\n Id table:"+resultString,
                    "Notice",
                    JOptionPane.WARNING_MESSAGE);
        }
        tableId.stream().forEach(s-> System.out.println("bàn trùng: "+s));
        return tableId;
    }

    public static boolean checkInfoTableAndMenu(){
        ArrayList<Booking> bookings = getBookings();
        for (Booking element : bookings) {
            if (element.getTable() == null) {
                JOptionPane.showMessageDialog(null,
                        "The number of tables and the menu must be equal.",
                        "Notice",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }
            if (element.getMenuName() == null) {
                JOptionPane.showMessageDialog(null,
                        "The number of tables and the menu must be equal.",
                        "Notice",
                        JOptionPane.WARNING_MESSAGE);
                return false;
            }

        }
        return true;
    }



    public static void clickOnTempBooking(){
//         sự kiện click vào bảng
        ViewTempMenu viewTempMenu = MainProgram.getViewTempMenu();
        JTable tables =viewTempMenu.getTable();
        tables.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = tables.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = tables.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        idInTempBooking = (Integer.parseInt(id));
                        System.out.println("Bảng TempBooking đang chọn hàng có id là: "+ id);
                    }
                }
            }
        });
    }

    public static void selectPerson(){  // lấy ra dc id của khách và lưu và lớp
        ViewPerson viewPerson = MainProgram.getViewPersonInBooking();
        JButton buttonSelectPerson = viewPerson.getButtonSelectPerson();
        buttonSelectPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBooking viewBooking = MainProgram.getViewBooking();
                System.out.println("check");
                idPerson = viewPerson.getIdSelect();
                reloadBlockInfoPerson(viewBooking);
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
                System.out.println("Select Menu id:" + idMenu);
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
                MainProgram.getViewTempMenu().loadData();
            }
        });
    }



    public static void selectTable(){  // chọn bàn
        ViewTable viewTable = MainProgram.getViewTableInBooking();
        JButton buttonSelectTable = viewTable.getButtonSelectTable();
        buttonSelectTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("selectTable");
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
                MainProgram.getViewTempMenu().loadData();
            }
        });
    }
}
