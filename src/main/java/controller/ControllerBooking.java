package controller;


import dao.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import model.*;
import utils.PersistenceManager;
import view.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
    private static int idTableInTempMenul;

    // get + set--------------------------------------------------------------------------------------------------------


    public static int getIdTableInTempMenul() {
        return idTableInTempMenul;
    }

    public static void setIdTableInTempMenul(int idTableInTempMenul) {
        ControllerBooking.idTableInTempMenul = idTableInTempMenul;
    }

    public static int getIdInTempBooking() {
        return idInTempBooking;
    }

    public static void setIdInTempBooking(int idInTempBooking) {
        ControllerBooking.idInTempBooking = idInTempBooking;
    }

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
        System.out.println("call ControllerBooking");
        selectList();
        selectPerson();
        selectMenu();
        submitNewBooking();
    }
    public static void selectList(){
        System.out.println("ControllerBooking - selectList()");
        ViewBooking viewBooking = MainProgram.getViewBooking();
        ViewTable viewTable = MainProgram.getViewTableInBooking();
        ViewNewMenu viewNewMenu = MainProgram.getViewNewMenuInBooking();
        ViewMenu viewMenu = MainProgram.getViewMenuSelectInBooking();


        // remove
        JButton buttonRemoveARowFromTempBooking = viewBooking.getButtonRemoveARowFromTempBooking();
        buttonRemoveARowFromTempBooking.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Booking> bookings = getBookings();
                bookings.stream().forEach(s-> System.out.println(s.getId()));
                setTableId.remove(idTableInTempMenul);
                bookings.remove(idInTempBooking-1);
                idInTempBooking = 0;
                System.out.println("--------------------------");
                for (Integer value : setTableId) {
                    System.out.println(value);
                }
                System.out.println("--------------------------");

                MainProgram.getViewTempMenu().loadData();
                MainProgram.getViewTempBooking().loadData();
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
                MainProgram.getViewTableInBooking().reload();
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

        // sự kiện mở bảng tạo mới menu
        JButton buttonShowAllBooking = viewBooking.getButtonShowAllBooking();
        buttonShowAllBooking.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Xóa tất cả các thành phần con khỏi JPanel
                viewBooking.getCenterViewBooking().removeAll();
                // Gọi hàm searchTableList() để thực hiện tìm kiếm và cập nhật dữ liệu
                viewBooking.getCenterViewBooking().add(MainProgram.getViewListBooking(), BorderLayout.CENTER);

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
                MainProgram.getViewMenuSelectInBooking().reload();
                viewBooking.revalidate();
                viewBooking.repaint();
            }
        });
        clickOnTempBooking();
    }

    public static void submitNewBooking(){
        System.out.println("ControllerBooking - submitNewBooking()");
        ViewBooking viewBooking = MainProgram.getViewBooking();
        JButton buttonSubmit = viewBooking.getButtonSubmitBooking();
        buttonSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startTimeString = viewBooking.getInputStartTime().getText();
                String endTimeString = viewBooking.getInputEndTime().getText();
                System.out.println("startTimeString :" +startTimeString);
                System.out.println("endTimeString :" +endTimeString);
                String dateString = viewBooking.getInputDate().getText();
                String depositString = viewBooking.getInputDeposit().getText();
                BookingsInfo bookingsInfo = new BookingsInfo();

                if (checkBookingInfo(viewBooking)){
                    LocalDateTime startTime = ControllerTime.parseDateTime(startTimeString,dateString);
                    LocalDateTime endTime = ControllerTime.parseDateTime(endTimeString,dateString);
                    System.out.println("startTime :" +startTime);
                    System.out.println("endTime :" +endTime);
                    if (checkTimeBooking(viewBooking)){
                        // tạo giao dịch cọc tiền
                        int traransactionDepositID = creatDepositTransaction(viewBooking,bookingsInfo);
                        if(checkInfoTableAndMenu()){
                            Set<Integer> invalidTableIdsInTempBooking = checkHourTable(startTime,endTime);
                            System.out.println("Invalid Table IDs in Temp Booking: " + invalidTableIdsInTempBooking);
                            if (invalidTableIdsInTempBooking.isEmpty()){

                                for (Booking booking : bookings) {
                                    System.out.println("id table in booking: "+booking.getTable().getId());
                                }

                                Person person = PersonDAO.getInstance().getById(idPerson);
                                // thêm dữ liệu cho bookingInfo và đẩy lên sever
                                submidBookingInfo(viewBooking,bookingsInfo,person);

                                EntityManagerFactory entityManagerFactory = PersistenceManager.getEntityManagerFactory();
                                EntityManager entityManager = entityManagerFactory.createEntityManager();

                                getBookings().forEach(s->{
                                    boolean isManaged = entityManager.contains(s);

                                    if (isManaged) {
                                        System.out.println("Đối tượng có trong trạng thái managed (persistent).");
                                        s.setInfo(bookingsInfo);
                                        s.setFlag(1);
                                        BookingDAO.getInstance().insert(s);
                                    } else {
                                        System.out.println("Đối tượng không có trong trạng thái managed (persistent).");
                                        s.setInfo(bookingsInfo);
                                        s.setFlag(1);
                                        BookingDAO.getInstance().saveDetachedAsNew(s);
                                    }

                                });
                                entityManager.close();

                                // tạo đối tượng ClientPaymentInfo để lưu thông tin giao dịch cọc tiền
                                ClientPaymentInfo clientPaymentInfo = new ClientPaymentInfo();
                                clientPaymentInfo.setBookingInfo(bookingsInfo);
                                clientPaymentInfo.setTransaction(TransactionDAO.getInstance().getById(traransactionDepositID));
                                // tổng tiền phải thanh toán
                                Double bill = BookingsInfoDAO.getInstance().getTotalPriceByInfoBookingID(bookingsInfo.getId());
                                if (depositString.isEmpty()){
                                    clientPaymentInfo.setFlag(0);
                                }else {
                                    if (bill-Double.parseDouble(depositString) >=0){
                                        clientPaymentInfo.setFlag(0);
                                    }else {
                                        clientPaymentInfo.setFlag(1);
                                    }
                                }
                                ClientPaymentInfoDAO.getInstance().insert(clientPaymentInfo);
                                // giao dịch nợ
                                creatReceivableTransaction(viewBooking,bookingsInfo);
                                // làm trống data tạm
                                bookings.clear();
                                setTableId.clear();
                                setNullBlockInfoPerson(viewBooking);
                            }else {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (Integer tableId : invalidTableIdsInTempBooking) {
                                    stringBuilder.append(tableId).append(", ");
                                }

                                if (stringBuilder.length() > 0) {
                                    stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()); // Xóa dấu phẩy và khoảng trắng cuối cùng
                                    System.out.println("Invalid Table IDs in tempBookingListData: " + stringBuilder.toString());
                                    JOptionPane.showMessageDialog(null,
                                            "The reservation time is already taken, please choose another table or a different time slot.\n Id table:" + stringBuilder.toString(),
                                            "Notice",
                                            JOptionPane.WARNING_MESSAGE);
                                } else {
                                    System.out.println("No invalid Table IDs in tempBookingListData.");
                                }
                            }
                        }
                    }
                }
                MainProgram.getViewTableInBooking().reload();
                MainProgram.getViewListBooking().reload();
                MainProgram.getViewNewMenuMain().loadData();
                MainProgram.getViewTempMenu().loadData();
                MainProgram.getViewListBooking().reload();

            }
        });
    }

    public static void creatReceivableTransaction(ViewBooking viewBooking,BookingsInfo bookingsInfo){
        System.out.println("ControllerBooking - creatReceivableTransaction(ViewBooking viewBooking,BookingsInfo bookingsInfo)");
        String commentString = viewBooking.getInputComment().getText();
        Transaction tranReceivable = new Transaction();
        tranReceivable.setType(TransactionsTypeDAO.getInstance().getByName("Nợ - Khách hàng còn thiếu"));
        tranReceivable.setDateCreat(LocalDateTime.now());
        // tổng tiền phải thanh toán
        Double bill = BookingsInfoDAO.getInstance().getTotalPriceByInfoBookingID(bookingsInfo.getId());
        System.out.println("Khởi tạo bill: " + bill);
        tranReceivable.setQuantity(-bill);
        tranReceivable.setFlag(1);
        tranReceivable.setPerson(PersonDAO.getInstance().getById(idPerson));
        Person person = bookingsInfo.getPerson();
        tranReceivable.setComment("Receivable: "+person.getPhone() +" - "+ControllerTime.formatDateTime(1,LocalDateTime.now())+" - "+ commentString);
        TransactionDAO.getInstance().insert(tranReceivable);
        // tạo đối tượng clientPaymentInfo lưu thông tin giao dịch
        ClientPaymentInfo clientPaymentInfo = new ClientPaymentInfo();
        clientPaymentInfo.setBookingInfo(bookingsInfo);
        clientPaymentInfo.setTransaction(tranReceivable);
        clientPaymentInfo.setFlag(0);
        ClientPaymentInfoDAO.getInstance().insert(clientPaymentInfo);
        // reload form
        MainProgram.getViewTransaction().loadData();
    }
    public static void submidBookingInfo(ViewBooking viewBooking ,BookingsInfo bookingsInfo,Person person){
        System.out.println("ControllerBooking - submidBookingInfo(ViewBooking viewBooking ,BookingsInfo bookingsInfo,Person person)");
        String startTimeString = viewBooking.getInputStartTime().getText();
        String endTimeString = viewBooking.getInputEndTime().getText();
        String dateString = viewBooking.getInputDate().getText();
        String commentString = viewBooking.getInputComment().getText();


        LocalDateTime startTime = ControllerTime.parseDateTime(startTimeString,dateString);
        LocalDateTime endTime = ControllerTime.parseDateTime(endTimeString,dateString);
        LocalDateTime timeNow = LocalDateTime.now();

        bookingsInfo.setEnd(endTime);
        bookingsInfo.setStart(startTime);
        bookingsInfo.setDateCreat(timeNow);
        bookingsInfo.setPerson(person);
        bookingsInfo.setInfo(person.getPhone() +" - "+ControllerTime.formatDateTime(1,LocalDateTime.now())+" - "+ commentString);
        bookingsInfo.setFlag(1);
        BookingsInfoDAO.getInstance().insert(bookingsInfo);
    }

    public static int creatDepositTransaction(ViewBooking viewBooking,BookingsInfo bookingsInfo){
        System.out.println("ControllerBooking - creatDepositTransaction(ViewBooking viewBooking,BookingsInfo bookingsInfo)");

        String depositString = viewBooking.getInputDeposit().getText();
        String commentString = viewBooking.getInputComment().getText();
        Double doubleDeposit;
        if (depositString.isEmpty()){
            doubleDeposit =0d;
        }else {
            doubleDeposit = Double.parseDouble(depositString);
        }
        setDeposit(doubleDeposit);
        bookingsInfo.setDeposit(doubleDeposit);
        // tạo giao dịch cọc tiền
        Person person = PersonDAO.getInstance().getById(idPerson);
        Transaction tranDeposit = new Transaction();
        tranDeposit.setDateCreat(LocalDateTime.now());
        tranDeposit.setQuantity(doubleDeposit);
        tranDeposit.setType(TransactionsTypeDAO.getInstance().getByName("Thu - Khách hàng đặt cọc"));
        tranDeposit.setComment("Deposit      : "+person.getPhone() +" - "+ControllerTime.formatDateTime(1,LocalDateTime.now())+" - "+ commentString)  ;
        tranDeposit.setFlag(1);
        tranDeposit.setPerson(person);
        TransactionDAO.getInstance().insert(tranDeposit);
        System.out.println("Deposit creat: " + doubleDeposit);
        return tranDeposit.getId();

    }

    public static boolean checkTimeBooking(ViewBooking viewBooking){
        System.out.println("ControllerBooking - checkTimeBooking(ViewBooking viewBooking)");
        String startTimeString = viewBooking.getInputStartTime().getText();
        String endTimeString = viewBooking.getInputEndTime().getText();
        String dateString = viewBooking.getInputDate().getText();

        LocalDateTime startTime = ControllerTime.parseDateTime(startTimeString,dateString);
        LocalDateTime endTime = ControllerTime.parseDateTime(endTimeString,dateString);
        LocalDateTime timeNow = LocalDateTime.now();
        int check = 1;

        if (startTime.isBefore(timeNow)) { // ngày bắt đầu trước ngày hiện tại
            if (check == 1) {
                JOptionPane.showMessageDialog(null,
                        "Incorrect start date for the event. The start date must be after the current date. Please try again",
                        "Notice",
                        JOptionPane.WARNING_MESSAGE);
                check = 0;
            }
        }
        if (endTime.isBefore(startTime)) {  // ngày kết thúc trước ngày bắt đầu
            if (check == 1) {
                JOptionPane.showMessageDialog(null,
                        "The end time of the event must be after the start time. Please try again.",
                        "Notice",
                        JOptionPane.WARNING_MESSAGE);
                check = 0;
            }
        }
        return (check==1) ? true : false;
    }

    public static boolean checkBookingInfo(ViewBooking viewBooking){
        System.out.println("ControllerBooking - checkBookingInfo(ViewBooking viewBooking)");
        String startTimeString = viewBooking.getInputStartTime().getText();
        String dateString = viewBooking.getInputDate().getText();
        String endTimeString = viewBooking.getInputEndTime().getText();
        String depositString = viewBooking.getInputDeposit().getText();
        String commentString = viewBooking.getInputComment().getText();
        System.out.println("-------------------------- checkBookingInfo - ControllerBooking -------------------------------------");
        System.out.println("dateString :" + dateString);
        System.out.println("startTimeString :" + startTimeString);
        System.out.println("endTimeString :" + endTimeString);
        System.out.println("depositString :" + depositString);
        System.out.println("commentString :" + commentString);
        System.out.println("idPerson :" + idPerson);
        System.out.println("-------------------------- checkBookingInfo - ControllerBooking -------------------------------------");

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
        return (check==1) ? true : false;
    }

    public static Set<Integer> checkHourTable(LocalDateTime start,LocalDateTime end ){
        System.out.println("ControllerBooking - checkHourTable(LocalDateTime start,LocalDateTime end)");
        // Sử dụng HashSet để lưu trữ các phần tử không trùng lặp
        Set<Integer> invalidTableIds  = new HashSet<>();
        Set<Integer> invalidTableIdsInTempBooking  = new HashSet<>();
        // lấy ra các bàn không thỏa mãn giờ so với yêu cầu và lưu id vào invalidTableIds
        java.util.List<Booking> bookingList = BookingDAO.getInstance().getAll();


        List<Object[]> bookingListData = bookingList.stream()
                .filter(s ->
                        (end.isBefore(s.getInfo().getStart()) && end.isAfter(s.getInfo().getEnd())) ||
                                (start.isBefore(s.getInfo().getStart()) && start.isAfter(s.getInfo().getEnd())) ||
                                (start.isAfter(s.getInfo().getStart()) && end.isBefore(s.getInfo().getEnd()))
                )
                .map(s -> {
                    invalidTableIds.add(s.getTable().getId()); // Thêm ID bàn không hợp lệ vào tập hợp
                    return new Object[]{
                            s.getTable().getId(),             // id bàn
                            s.getTable().getType().getName(), // loại bàn
                            s.getTable().getSeatingCapacity(),// số ghế
                            s.getInfo().getStart(),           // giờ bắt đầu
                            s.getInfo().getEnd(),             // giờ kết thúc
                            s.getInfo().getStart(),           // ngày
                            s.getTable().getStatus().getName()     // trạng thái của bàn
                    };
                })
                .collect(Collectors.toList());

        for (Object[] data : bookingListData) {
            int tableId = (int) data[0];
            String tableType = (String) data[1];
            int seatingCapacity = (int) data[2];

            System.out.println("---------------------------- bookingListData --------------------------------");
            System.out.println("Table ID: " + tableId);
            System.out.println("Table Type: " + tableType);
            System.out.println("Seating Capacity: " + seatingCapacity);
            System.out.println("---------------------------- bookingListData --------------------------------");
        }

        List<Object[]> tempBookingListData = ControllerBooking.getBookings().stream().map(
                s -> new Object[]{
                        s.getTable().getId(),             // id bàn
                        s.getTable().getType().getName(), // loại bàn
                        s.getTable().getSeatingCapacity(),// số ghế
//                        s.getInfo().getStart(),           // giờ bắt đầu
////                        s.getInfo().getEnd(),             // giờ kết thúc
//                        s.getInfo().getStart(),           // ngày
//                        s.getTable().getStatus().getName()     // trạng thái của bàn
                }
        ).collect(Collectors.toList());

        for (Object[] data : tempBookingListData) {
            int tableId = (int) data[0];
            String tableType = (String) data[1];
            int seatingCapacity = (int) data[2];
//            LocalDateTime startTime = (LocalDateTime) data[3];
//            LocalDateTime endTime = (LocalDateTime) data[4];
//            LocalDateTime date = (LocalDateTime) data[5];
//            String tableStatus = (String) data[6];
            System.out.println("----------------- tempBookingListData ---------------------");
            System.out.println("Table ID: " + tableId);
            System.out.println("Table Type: " + tableType);
            System.out.println("Seating Capacity: " + seatingCapacity);
//            System.out.println("Start Time: " + startTime);
//            System.out.println("End Time: " + endTime);
//            System.out.println("Date: " + date);
//            System.out.println("Table Status: " + tableStatus);
            System.out.println("----------------- tempBookingListData ---------------------");
        }

        // Kiểm tra phần tử trong tempBookingListData có ID nằm trong invalidTableIds hay không
        for (Object[] data : tempBookingListData) {
            int tableId = (int) data[0];
            if (invalidTableIds.contains(tableId)) {
                System.out.println("tableId :"+tableId);
                invalidTableIdsInTempBooking.add(tableId);
            }
        }
        return invalidTableIdsInTempBooking;
    }

    public static boolean checkInfoTableAndMenu(){
        System.out.println("ControllerBooking - checkInfoTableAndMenu()");
//        ArrayList<Booking> bookings = getBookings();
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
        System.out.println("ControllerBooking - clickOnTempBooking()");
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
        System.out.println("ControllerBooking - selectPerson()");
        ViewPerson viewPerson = MainProgram.getViewPersonInBooking();
        JButton buttonSelectPerson = viewPerson.getButtonSelectPerson();
        buttonSelectPerson.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ViewBooking viewBooking = MainProgram.getViewBooking();
                idPerson = viewPerson.getIdSelect();
                reloadBlockInfoPerson(viewBooking);

            }
        });
    }

    // chọn người
    public static void reloadBlockInfoPerson(ViewBooking viewBooking){
        System.out.println("ControllerBooking - reloadBlockInfoPerson(ViewBooking viewBooking)");
        int id = ControllerBooking.getIdPerson();
        Person person = PersonDAO.getInstance().getById(id);
        viewBooking.getLabelFirstNameValue().setText(person.getName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelLastNameValue().setText(person.getLastName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelFirstNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
        viewBooking.getLabelLastNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
    }

    public static void setNullBlockInfoPerson(ViewBooking viewBooking){
        System.out.println("ControllerBooking - reloadBlockInfoPerson(ViewBooking viewBooking)");
        ControllerBooking.setIdPerson(0);
        viewBooking.getLabelFirstNameValue().setText(""); // Đặt giá trị cho JLabel
        viewBooking.getLabelLastNameValue().setText(""); // Đặt giá trị cho JLabel
        viewBooking.getInputComment().setText("");
        viewBooking.getInputDate().setText("");
        viewBooking.getInputDeposit().setText("");
        viewBooking.getInputEndTime().setText("");
        viewBooking.getInputStartTime().setText("");
        viewBooking.getLabelFirstNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
        viewBooking.getLabelLastNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
    }

    public static void selectMenu(){
        System.out.println("ControllerBooking - selectMenu()");
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
        System.out.println("ControllerBooking - selectTable()");
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

    public static void main(String[] args) {
        LocalDateTime start = ControllerTime.parseDateTime("04:00","2023-09-09");
        LocalDateTime end = ControllerTime.parseDateTime("11:00","2023-09-09");

        Set<Integer> invalidTableIdsInTempBooking = checkHourTable(start,end);

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer tableId : invalidTableIdsInTempBooking) {
            stringBuilder.append(tableId).append(", ");
            System.out.println("đm");
        }

        if (stringBuilder.length() > 0) {
            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length()); // Xóa dấu phẩy và khoảng trắng cuối cùng
            System.out.println("Invalid Table IDs in tempBookingListData: " + stringBuilder.toString());
            JOptionPane.showMessageDialog(null,
                    "The reservation time is already taken, please choose another table or a different time slot.\n Id table:" + stringBuilder.toString(),
                    "Notice",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            System.out.println("No invalid Table IDs in tempBookingListData.");
        }
    }
}
