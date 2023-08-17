package controller;

import view.MainProgram;
import view.ViewListBooking;
import view.ViewPerson;
import view.ViewTransaction;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.Stream;

public class ControllerListBooking {



    public ControllerListBooking(ViewListBooking viewListBooking) {
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
