package controller;

import model.TableList;
import view.ViewDish;
import view.ViewTable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.Stream;

public class ControllerTable {


    public ControllerTable(ViewTable viewTable) {



        JButton buttonsearch = viewTable.getButtonSearch();

        // sự kiện click tạo món
        buttonsearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkInput(viewTable)){
                    search(viewTable);
                }
            }
        });


    }
    private boolean checkInput(ViewTable viewTable){
        String seatingCapacityString = viewTable.getInputFilterBySeatingCapacity().getText();
        String dateString = viewTable.getInputFilterByDate().getText();

        System.out.println("Check input table search !");
        int check = 1;
//        if (seatingCapacityString.isEmpty()||dateString.isEmpty()){
//            if (check==1){
//                JOptionPane.showMessageDialog(null, "You must fill in all the required information before searching !", "Notice", JOptionPane.WARNING_MESSAGE);
//            }
//            check =0;
//        }

        if (!seatingCapacityString.isEmpty()){
            if (!RegexMatcher.numberCheck(seatingCapacityString,"").equals("")){
                if (check ==1){
                    JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(seatingCapacityString, "Seating capacity: "), "Notice", JOptionPane.WARNING_MESSAGE);
                }
                check = 0;
            }
        }
        if (!dateString.isEmpty()){
            if (!RegexMatcher.dayCheck(dateString,"").equals("")){
                if (check ==1){
                    JOptionPane.showMessageDialog(null, RegexMatcher.dayCheck(dateString,"Date: "), "Notice", JOptionPane.WARNING_MESSAGE);
                }
                check = 0;
            }
        }



        return (check==1) ? true : false;
    }

    public void search(ViewTable viewTable) {
        Object lockObject = new Object();
        String seatingCapacityString = viewTable.getInputFilterBySeatingCapacity().getText();
        String dateString = viewTable.getInputFilterByDate().getText();
        String type = (String) viewTable.getSelecTypeSearch().getSelectedItem();


        Object[][] arr;
        Object[][] originData = viewTable.getData();

        // Tạo luồng dữ liệu từ mảng gốc
        Stream<Object[]> dataStream = Arrays.stream(originData);

        // Áp dụng các bộ lọc một cách tuần tự
        if (!type.equals("")) {
            dataStream = dataStream.filter(row -> row[1].equals(type));
        }

        if (!seatingCapacityString.equals("")) {
            dataStream = dataStream.filter(row -> (row[2].toString()).equals(seatingCapacityString));
        }

        if (!dateString.equals("")) {
            dataStream = dataStream.filter(row -> row[5].toString().contains(dateString));
        }

        // Sau khi đã áp dụng tất cả các bộ lọc, chuyển đổi luồng dữ liệu thành mảng kết quả
        arr = dataStream.toArray(Object[][]::new);
        // Xóa dữ liệu hiện có trong bảng
        viewTable.getTableModel().setRowCount(0);

        // Thêm từng hàng dữ liệu vào bảng
        for (Object[] row : arr) {
            viewTable.getTableModel().addRow(row);
        }
        // Cập nhật bảng để hiển thị dữ liệu mới
        viewTable.getTableModel().fireTableDataChanged();
    }

}
