package controller;

import dao.MenuDAO;
import dao.MenuNameDAO;
import model.Menu;
import model.MenuName;
import view.ViewMenu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class ControllerMenu {

    public ControllerMenu(ViewMenu viewMenu) {
        System.out.println("ControllerMenu dc gọi a");
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

        // sự kiện delete
        JButton buttonDelete = viewMenu.getButtonDelete();
        buttonDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = viewMenu.getMenuIdSelect();
                if (id==0){
                    JOptionPane.showMessageDialog(null, "You haven't selected the menu you want to delete !", "Total price", JOptionPane.WARNING_MESSAGE);
                }else {
                    delete(viewMenu,id);
                }

            }
        });



    }

    private void delete(ViewMenu viewMenu, int id){
        MenuName menuName = MenuNameDAO.getInstance().getById(id);
        menuName.setFlag(0);
        MenuNameDAO.getInstance().update(menuName);

        List<Menu> menu = MenuDAO.getInstance().getMenuByMenuNameID(id);
        for (Menu s: menu) {
            s.setFlag(0);
            MenuDAO.getInstance().update(s);
        }
        viewMenu.reload();
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
