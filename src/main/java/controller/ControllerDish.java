package controller;

import dao.DishDAO;
import dao.DishTypeDAO;
import dao.MenuDAO;
import dao.MenuNameDAO;
import model.Dish;
import model.DishType;
import model.Menu;
import view.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Stream;

public class ControllerDish {
    private Dish dish = new Dish();


    public ControllerDish(ViewDish viewDish) {
        JButton buttonCreatNewDish = viewDish.getButtonCreatNewDish();
        // sự kiện click tạo món
        buttonCreatNewDish.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    if (checkInput(viewDish)){
                        creatNewDish(viewDish);
                    }
                }
            }
        });

        JButton buttonSeach = viewDish.getButtonSeach();
        // sự kiện tìm kiếm
        buttonSeach.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                   search(viewDish);
                }




            }
        });

        // click đẩy id vào tempId
        JTable table = viewDish.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        System.out.println("Temp id dish: "+ id);
                        viewDish.setTempId(Integer.parseInt(id));

                        Dish dish = DishDAO.getInstance().getById(Integer.parseInt(id));
                        viewDish.getInputEnterPrice().setText(dish.getPrice().toString());
                        viewDish.getInputEnterNumber().setText("1");
                    }
                }
            }

        });

        //         sự kiện chọn

        JButton buttonSelect = viewDish.getButtonSlectDish();
        buttonSelect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
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
                        MainProgram.getNewMenus().add(menu);
                        MainProgram.getViewNewMenuMain().loadData();
                        MainProgram.getViewNewMenuInBooking().loadData();
                        for (Menu s:MainProgram.getNewMenus()
                             ) {
                            System.out.println("Controller new menu: "+s.getDish().getDishName());
                        }
                        MainProgram.getViewNewMenuMain().loadData();
                    }
                }
                System.out.println("in ra ");
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



    private boolean checkInput(ViewDish viewDish){
        String name = viewDish.getInputNewDishName().getText();
        String referencePriceString = viewDish.getInputReferencePrice().getText();
        System.out.println("Check input dish !");
        int check = 1;
        if (name.isEmpty()||referencePriceString.isEmpty()){
            if (check==1){
                JOptionPane.showMessageDialog(null, "You must fill in all the required information before proceeding to create a new dish !", "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check =0;
        }
        if (!RegexMatcher.numberCheck(referencePriceString,"").equals("")){
            if (check ==1){
                JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(referencePriceString, "Reference: "), "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check = 0;
        }
        return (check==1) ? true : false;
    }

    private void creatNewDish(ViewDish viewDish){
        String name = viewDish.getInputNewDishName().getText();
        String referencePriceString = viewDish.getInputReferencePrice().getText();
        String comment = viewDish.getInputComment().getText();
        String type = (String) viewDish.getSelectTypeForNewDish().getSelectedItem();
        System.out.println("Check input dish !");
        DishType dishType = DishTypeDAO.getInstance().getByString(type);
        Dish dish = new Dish();
        dish.setDishName(name);
        dish.setDishComment(comment);
        dish.setPrice(Double.parseDouble(referencePriceString));
        dish.setDateCreat(LocalDateTime.now());
        dish.setFlag(1);
        dish.setType(dishType);
        DishDAO.getInstance().insert(dish);
        viewDish.reloadTable();
    }

    private void search(ViewDish viewDish){
        Object lockObject = new Object();
        String priceString = viewDish.getInputFilerByPrice().getText();
        String type = (String) viewDish.getSelectType().getSelectedItem();
        JTable table = viewDish.getTable();
        System.out.println("Search dish !");
        if (!priceString.isEmpty()){
            if (!RegexMatcher.numberCheck(priceString,"").equals("")){
                JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(priceString, "Price: "), "Notice", JOptionPane.WARNING_MESSAGE);
            }
        }
        Object[][] arr = viewDish.getData();
        Object[][] originData = viewDish.getData();

        synchronized (lockObject) {
            Stream<Object[]> dataStream = Arrays.stream(originData);

            // Áp dụng tất cả các điều kiện lọc trong một Stream duy nhất
            if (!type.equals("")) {
                dataStream = dataStream.filter(row -> row[3].equals(type));
            }
            if (!priceString.equals("")) {
                Double targetPrice = Double.parseDouble(priceString); // Chuyển đổi price sang kiểu Double
                dataStream = dataStream.filter(row -> {
                    Double currentPrice = Double.parseDouble(row[2].toString()); // Chuyển đổi dữ liệu của hàng hiện tại sang kiểu Double
                    return currentPrice <= targetPrice; // So sánh giá trị hiện tại với giá trị target
                });
            }
            // Gán kết quả vào mảng arr
            arr = dataStream.toArray(Object[][]::new);
        }
        // Lấy model của bảng
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Xóa dữ liệu hiện có trong bảng
        model.setRowCount(0);

        // Thêm từng hàng dữ liệu vào bảng
        for (Object[] row : arr) {
            model.addRow(row);
        }
        // Cập nhật bảng để hiển thị dữ liệu mới
        model.fireTableDataChanged();
    }
}
