package controller;

import model.Menu;
import view.ViewNewMenu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class ControllerNewMenu {

    public ControllerNewMenu(ViewNewMenu viewNewMenu) {

        // click get id
        JTable table = viewNewMenu.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        System.out.println("Dish in new menu: "+ id);
                        viewNewMenu.setIdSelect(Integer.parseInt(id)-1);
                    }
                }
            }

        });

        // sự kiện remove
        JButton buttonUpdate = viewNewMenu.getButtonRemoveDish();
        buttonUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                removeDishFromANewMenu(viewNewMenu,viewNewMenu.getIdSelect());
                viewNewMenu.loadData();
            }
        });


    }
    private void removeDishFromANewMenu(ViewNewMenu viewNewMenu,int id){
        ArrayList<Menu> newMenus = viewNewMenu.getNewMenus();
        newMenus.remove(id);
        viewNewMenu.setNewMenus(newMenus);
        viewNewMenu.loadData();
    }
}
