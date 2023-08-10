package controller;

import model.Menu;
import view.ViewMenu;
import view.ViewNewMenu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
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

        // click get id
        JTable table = viewMenu.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        System.out.println("Menu: "+ id);
                        viewMenu.setMenuIdSelect(Integer.parseInt(id));
                        viewMenu.getMenuDetail().loadData(viewMenu.getMenuIdSelect());
                    }
                }
            }

        });
    }

    private void removeDishFromANewMenu(ViewNewMenu viewNewMenu,int id){
        ArrayList<Menu> newMenus = viewNewMenu.getNewMenus();

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
