package controller;

import dao.DishDAO;
import dao.DishTypeDAO;
import model.Dish;
import model.DishType;
import view.ViewDish;

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
        // sự kiện click tạo món
        buttonSeach.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                   search(viewDish);
                }
            }
        });

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
