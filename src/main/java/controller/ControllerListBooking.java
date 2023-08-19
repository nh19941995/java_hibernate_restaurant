package controller;

import dao.*;
import model.*;
import view.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

public class ControllerListBooking {



    public ControllerListBooking(ViewListBooking viewListBooking) {
        System.out.println("call ControllerListBooking");
        // click get id
        JTable table = viewListBooking.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        System.out.println("ViewListBooking: "+ id);
                        viewListBooking.setIdSelect(Integer.parseInt(id));
                        loadDataBooking(Integer.parseInt(id));
                        reloadBlockInfoPerson(MainProgram.getViewBooking(),Integer.parseInt(id));
                        reloadInputBooking(MainProgram.getViewBooking(),Integer.parseInt(id));
                    }
                }
            }

        });

        // sự kiện search
        JButton buttonCreat = viewListBooking.getButtonSearch();
        buttonCreat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkSearch(viewListBooking)){
                    search(viewListBooking);
                }
            }
        });

        // sự kiện payment
        JButton buttonPayment = viewListBooking.getButtonPayment();
        buttonPayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (viewListBooking.getIdSelect()==0){
                    JOptionPane.showMessageDialog(null, "You haven't selected a transaction to make a payment, Please choose a transaction", "Notice", JOptionPane.WARNING_MESSAGE);
                }else {
                    if (checkPayment(viewListBooking)){
                        creatPayment(viewListBooking);
                    }
                }
            }
        });
    }

    public static void reloadBlockInfoPerson(ViewBooking viewBooking, int bookingInforID){
        System.out.println("ControllerListBooking - reloadBlockInfoPerson(ViewBooking viewBooking)");
        BookingsInfo bookingsInfo = BookingsInfoDAO.getInstance().getById(bookingInforID);
        Person person = bookingsInfo.getPerson();
        MainProgram.getViewPersonInBooking().setIdSelect(person.getId());
        ControllerBooking.setIdPerson(person.getId());
        viewBooking.getLabelFirstNameValue().setText(person.getName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelLastNameValue().setText(person.getLastName()); // Đặt giá trị cho JLabel
        viewBooking.getLabelFirstNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
        viewBooking.getLabelLastNameValue().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
    }

    public static void reloadInputBooking(ViewBooking viewBooking, int bookingInforID){
        System.out.println("ControllerListBooking - reloadInputBooking(ViewBooking viewBooking, int bookingInforID)");
        BookingsInfo info = BookingsInfoDAO.getInstance().getById(bookingInforID);
        if (info.getDeposit()==null){
            viewBooking.getInputDeposit().setText( "");
        }else {
            viewBooking.getInputDeposit().setText( info.getDeposit().toString());
        }
        viewBooking.getInputDate().setText( ControllerTime.formatDateTime(2,info.getStart()));
        viewBooking.getInputStartTime().setText( ControllerTime.formatDateTime(1,info.getStart()));
        viewBooking.getInputEndTime().setText( ControllerTime.formatDateTime(1,info.getEnd()));
        viewBooking.getInputComment().setText(info.getInfo());
    }

    private void loadDataBooking (int inforID){
        System.out.println("ControllerListBooking - loadData(int inforID)");
        ArrayList<Booking> dataBooking  = BookingDAO.getInstance().getByInfoId(inforID);
        MainProgram.getControllerBooking().setBookings(dataBooking);
        MainProgram.getViewTempMenu().loadData();
    }


    private void creatPayment(ViewListBooking viewListBooking){
        String paymentValue = viewListBooking.getInputPaymentValue().getText();
        BookingsInfo bookingsInfo = BookingsInfoDAO.getInstance().getById(viewListBooking.getIdSelect());
        if(ClientPaymentInfoDAO.getClientPaymentInfo(viewListBooking.getIdSelect())==null){
            Person person = bookingsInfo.getPerson();
            TransactionsType transactionsType = TransactionsTypeDAO.getInstance().getByName("Thu - Khách hàng thanh toán");
            Transaction transaction = new Transaction();
            transaction.setType(transactionsType);
            transaction.setDateCreat(LocalDateTime.now());
            transaction.setQuantity(Double.parseDouble(paymentValue));
            transaction.setComment("Payment: "+bookingsInfo.getInfo());
            transaction.setPerson(person);
            transaction.setFlag(1);
            TransactionDAO.getInstance().insert(transaction);
            ClientPaymentInfo clientPaymentInfo = new ClientPaymentInfo();
            clientPaymentInfo.setTransaction(transaction);
            clientPaymentInfo.setBookingInfo(bookingsInfo);
            if (ClientPaymentInfoDAO.getInstance().getTotalQuantityByBookingInfoID(bookingsInfo.getId())+ Double.parseDouble(paymentValue) >=0) {
                clientPaymentInfo.setFlag(1);
            }else {
                clientPaymentInfo.setFlag(0);
            }
            ClientPaymentInfoDAO.getInstance().insert(clientPaymentInfo);
            MainProgram.getViewTransaction().loadData();
            MainProgram.getViewListBookingMain().reload();
            MainProgram.getViewListBookingInBooking().reload();

            JOptionPane.showMessageDialog(null,
                    "Payment successful, payment amount: "+paymentValue,
                    "Notice",
                    JOptionPane.WARNING_MESSAGE);
            viewListBooking.getInputPaymentValue().setText("");
        }else {
            JOptionPane.showMessageDialog(null, "The order has been fully paid", "Notice", JOptionPane.WARNING_MESSAGE);
        }

    }



    private boolean checkPayment(ViewListBooking viewListBooking){
        String paymentValue = viewListBooking.getInputPaymentValue().getText();
        int check = 1;
        if (paymentValue.isEmpty()){
            if (check==1){
                JOptionPane.showMessageDialog(null, "You need to fill in the payment amount !", "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check =0;
        }
        if (!RegexMatcher.numberCheck(paymentValue,"").equals("")){
            if (check ==1){
                JOptionPane.showMessageDialog(null,
                        RegexMatcher.numberCheck(paymentValue, "Payment amount: ")
                        , "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check = 0;
        }
        return (check==1) ? true : false;

    }



    private void search(ViewListBooking viewListBooking){
        System.out.println("ControllerListBooking - search(ViewListBooking viewListBooking)");
        String dateInput = viewListBooking.getInputSearchByDate().getText();
        String phoneInput = viewListBooking.getInputSearchByPhone().getText();


        Object[][] arr;
        Object[][] originData = viewListBooking.getData();

        // Tạo luồng dữ liệu từ mảng gốc
        Stream<Object[]> dataStream = Arrays.stream(originData);

        // Áp dụng các bộ lọc một cách tuần tự
        if (!dateInput.equals("")) {
            dataStream = dataStream.filter(row -> (row[7].toString()).equals(dateInput));
        }

        if (!phoneInput.equals("")) {
            dataStream = dataStream.filter(row -> row[3].toString().contains(phoneInput));
        }

        // Sau khi đã áp dụng tất cả các bộ lọc, chuyển đổi luồng dữ liệu thành mảng kết quả
        arr = dataStream.toArray(Object[][]::new);
        // Xóa dữ liệu hiện có trong bảng
        viewListBooking.getTableModel().setRowCount(0);

        // Thêm từng hàng dữ liệu vào bảng
        for (Object[] row : arr) {
            viewListBooking.getTableModel().addRow(row);
        }
        // Cập nhật bảng để hiển thị dữ liệu mới
        viewListBooking.getTableModel().fireTableDataChanged();
    }

    public static boolean checkSearch(ViewListBooking viewListBooking){
        String dateInput = viewListBooking.getInputSearchByDate().getText();
        String phoneInput = viewListBooking.getInputSearchByPhone().getText();

        System.out.println("------------- search - check - listBooking -------------------");
        System.out.println("dateInput :"+dateInput);
        System.out.println("phoneInput :"+phoneInput);
        System.out.println("------------- search - check - listBooking -------------------");

        int check = 1;
        if (!dateInput.isEmpty()){
            if (check ==1){
                if (!RegexMatcher.dayCheck(dateInput, "").equals("")){
                    JOptionPane.showMessageDialog(null, RegexMatcher.dayCheck(dateInput,
                            "Date : "),"Notice", JOptionPane.WARNING_MESSAGE);
                    check = 0;
                }
            }
        }
        if (!phoneInput.isEmpty()){
            if (check ==1){
                if (!RegexMatcher.phoneCheck(phoneInput, "").equals("")) {
                    JOptionPane.showMessageDialog(null, RegexMatcher.phoneCheck(phoneInput,
                            "Phone number : "), "Notice", JOptionPane.WARNING_MESSAGE);
                    check = 0;
                }
            }
        }
        return (check==1) ? true : false;
    }
}
