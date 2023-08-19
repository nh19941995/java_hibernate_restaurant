package controller;

import dao.DishDAO;
import model.Dish;
import view.ViewTempMenu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ControllerViewTempMenu {




    public ControllerViewTempMenu(ViewTempMenu viewTempMenu) {
        // click đẩy id vào tempId
        JTable table = viewTempMenu.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 1).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        if (id != ""){
                            ControllerBooking.setIdTableInTempMenul(Integer.parseInt(id));
                            System.out.println("IdTableInTempMenul: "+ControllerBooking.getIdTableInTempMenul());
                        }
                    }
                }
            }

        });

    }
}
