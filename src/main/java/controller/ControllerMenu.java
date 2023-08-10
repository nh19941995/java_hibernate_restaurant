package controller;

import view.ViewMenu;
import view.ViewPerson;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.Stream;

public class ControllerMenu {

    public ControllerMenu(ViewMenu viewMenu) {

        // sự kiện search
        JButton buttonUpdate = viewMenu.getButtonSearch();
        buttonUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                search(viewMenu);
            }
        });
    }

    private void search(ViewMenu viewMenu){
        Double price = Double.parseDouble(viewMenu.getInputPrice().getText());


        if (checkPrice(viewMenu)) {
            Object[][] originalData = viewMenu.getData();
            // Tạo luồng dữ liệu từ mảng
            Stream<Object[]> dataStream = Arrays.stream(originalData);
            // Sử dụng filter để lọc dữ liệu theo số điện thoại
            Object[][] filteredData = dataStream.filter(row -> row[4] != null && (Double)row[4]  < price).toArray(Object[][]::new);
            // Xóa dữ liệu hiện có trong bảng
            viewMenu.getTableModel().setRowCount(0);
            // Thêm từng hàng dữ liệu vào bảng
            for (Object[] row : filteredData) {
                viewMenu.getTableModel().addRow(row);
            }
            // Cập nhật bảng để hiển thị dữ liệu mới
            viewMenu.getTableModel().fireTableDataChanged();
        }
    }
    private boolean checkPrice(ViewMenu viewMenu){
        String price = viewMenu.getInputPrice().getText();
        System.out.println("price: "+price);

        int check = 1;
        if (!RegexMatcher.numberCheck(price,"").equals("")){
            if (check ==1){
                JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(price, "Date of birth: "), "Total price", JOptionPane.WARNING_MESSAGE);
            }
            check = 0;
        }
        return (check==1) ? true : false;
    }
}
