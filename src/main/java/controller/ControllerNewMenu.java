package controller;

import dao.DishDAO;
import dao.MenuDAO;
import dao.MenuNameDAO;
import model.Dish;
import model.Menu;
import model.MenuName;
import view.ViewDish;
import view.ViewHome;
import view.ViewMenu;
import view.ViewNewMenu;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ControllerNewMenu {
    private static ViewMenu viewMenu = ViewHome.getViewMenu();

    public static ViewMenu getViewMenu() {
        return viewMenu;
    }

    public static void setViewMenu(ViewMenu viewMenu) {
        ControllerNewMenu.viewMenu = viewMenu;
    }

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

        // sự kiện creat new
        JButton buttonCreat = viewNewMenu.getButtonCreatNewMenu();
        buttonCreat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (check(viewNewMenu)){
                    creatNewMenu(viewNewMenu);
                    viewMenu.reload();
                    ArrayList<Menu> newMenus = viewNewMenu.getNewMenus();
                    newMenus.clear();
                    viewNewMenu.setNewMenus(newMenus);
                    viewNewMenu.loadData();
                }

            }
        });
        JButton buttonAddToMenu = viewNewMenu.getButtonAddDishToMenu();
        buttonAddToMenu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                ViewDish viewDish = viewNewMenu.getViewDish();
                int id = viewDish.getTempId();
                if (id==0) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    JOptionPane.showMessageDialog(null, "Please choose a dish", "Notice", JOptionPane.WARNING_MESSAGE);
                }else {
                    Dish dish = DishDAO.getInstance().getById(id);
                    Menu menu = new Menu();
                    menu.setDish(dish);

                    if (checkPriceAndNumber(viewDish)){
                        String price = viewDish.getInputEnterPrice().getText();
                        String number = viewDish.getInputEnterNumber().getText();
                        menu.setQuantity(Integer.parseInt(number));
                        menu.setUnitPrice(Double.parseDouble(price));
                        viewNewMenu.add(menu);
                        viewNewMenu.loadData();
                    }

                }
            }
        });


    }
    private boolean checkPriceAndNumber(ViewDish viewDish){
        String price = viewDish.getInputEnterPrice().getText();
        String number = viewDish.getInputEnterNumber().getText();
        int check = 1;
        if (price.isEmpty()||number.isEmpty()){
            if (check==1){
                JOptionPane.showMessageDialog(null, "You must fill in all the required information before proceeding to create a new dish !", "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check =0;
        }
        if (!RegexMatcher.numberCheck(price,"").equals("")||!RegexMatcher.numberCheck(number,"").equals("")){
            if (check ==1){
                JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(price,"Price: ")+RegexMatcher.numberCheck(number,"Quantity: "), "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check = 0;
        }
        return (check==1) ? true : false;
    }

    private void removeDishFromANewMenu(ViewNewMenu viewNewMenu,int id){
        ArrayList<Menu> newMenus = viewNewMenu.getNewMenus();
        newMenus.remove(id);
        viewNewMenu.setNewMenus(newMenus);
        viewNewMenu.loadData();
    }

    private void creatNewMenu(ViewNewMenu viewNewMenu){
        ArrayList<Menu> newMenus = viewNewMenu.getNewMenus();
        String name = viewNewMenu.getInputNameNewMenu().getText();
        MenuName menuName = new MenuName();
        menuName.setFlag(1);
        menuName.setDateCreat(LocalDateTime.now());
        menuName.setDateUpdate(LocalDateTime.now());
        menuName.setName(name);
        MenuNameDAO.getInstance().insert(menuName);
        newMenus.stream().forEach(s->{
            s.setMenuName(menuName);
            s.setFlag(1);
            MenuDAO.getInstance().insert(s);
        });
    }

    private boolean check(ViewNewMenu viewNewMenu){
        String name = viewNewMenu.getInputNameNewMenu().getText();
        ArrayList<Menu> newMenus = viewNewMenu.getNewMenus();

        int check = 1;
        if (name.isEmpty()){
            if (check==1){
                JOptionPane.showMessageDialog(null, "You must fill in all the required information before proceeding to create a new dish !", "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check =0;
        }
        if (newMenus.size()==0){
            JOptionPane.showMessageDialog(null, "The menu doesn't have any dishes yet, please select dishes for the menu. !", "Notice", JOptionPane.WARNING_MESSAGE);
            check =0;
        }
        return (check==1) ? true : false;
    }
}
