package view;

import controller.ControllerNewMenu;
import controller.RegexMatcher;
import dao.DishDAO;
import model.Dish;
import model.Menu;
import view.tool.BoderTool;
import view.tool.GridTool;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class ViewNewMenu extends JPanel {
    // data-------------------------------------------------------------------------------------------------------------
    private int idSelect;
    private JTable table = new JTable();
    private DefaultTableModel tableModel;
    private Object[][] data;
    // button-----------------------------------------------------------------------------------------------------------
    private JButton buttonCreatNewMenu = new JButton("Creat new menu");
    private JButton buttonRemoveDish = new JButton("Remove");
    // input------------------------------------------------------------------------------------------------------------
    private JTextField inputNameNewMenu = new JTextField();
    // label------------------------------------------------------------------------------------------------------------
    private JLabel labelNameNewMenu = new JLabel("Menu name:");
    // get + set--------------------------------------------------------------------------------------------------------
    public int getIdSelect() {
        return idSelect;
    }
    public void setIdSelect(int idSelect) {
        this.idSelect = idSelect;
    }
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }
    public JTable getTable() {
        return table;
    }
    public void setTable(JTable table) {
        this.table = table;
    }
    public JButton getButtonCreatNewMenu() {
        return buttonCreatNewMenu;
    }
    public JButton getButtonRemoveDish() {
        return buttonRemoveDish;
    }
    public JTextField getInputNameNewMenu() {
        return inputNameNewMenu;
    }
    public Object[][] getData() {
        return data;
    }
    public void setData(Object[][] data) {
        this.data = data;
    }
    public void add(Menu menu) {
        MainProgram.getNewMenus().add(menu);
    }

    public ViewNewMenu() {

    }

    public ViewNewMenu ViewNewMenuMain() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(blockMenu());
        add(blockDishInNewMenuMain());
        return  this;
    }
    public ViewNewMenu ViewNewMenuInBooing() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(blockMenu());
        add(blockDishInNewMenuInBooking());
        return  this;
    }
    private JPanel blockDishInNewMenuMain(){
        BoderTool boderTool = new BoderTool();
        boderTool.add(MainProgram.getViewDishInNewMenuMain(),BorderLayout.CENTER);
        return boderTool;
    }
    private JPanel blockDishInNewMenuInBooking (){
        BoderTool boderTool = new BoderTool();
        boderTool.add(MainProgram.getViewDishInNewMenuInBooking(),BorderLayout.CENTER);
        return boderTool;
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

    private JPanel blockMenu(){
        BoderTool boderTool = new BoderTool();
        boderTool.add(blockTop(),BorderLayout.NORTH);
        boderTool.add(blockTableMenu(), BorderLayout.CENTER);
        boderTool.add(blockBot(),BorderLayout.SOUTH);
        return boderTool;
    }

    private JPanel blockTop(){
        JPanel jPanel = new JPanel();
        jPanel.setPreferredSize(new Dimension(150, 75));
        jPanel.setLayout(new BorderLayout());
        GridTool grid = new GridTool();
        // đặt kích thước
        inputNameNewMenu.setPreferredSize(new Dimension(200, 20));
        grid.GridAdd(labelNameNewMenu,0,0,10,10,27);
        grid.GridAdd(inputNameNewMenu,1,0,10,10,10);
        jPanel.add(grid,BorderLayout.CENTER);
        return jPanel;
    }

    private JPanel blockBot(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        GridTool grid = new GridTool();
        grid.GridAdd(buttonCreatNewMenu,1,0,20,20,20);
        grid.GridAdd(buttonRemoveDish,0,0,20,20,20);

        buttonRemoveDish.setPreferredSize(new Dimension(150, 35));
        buttonCreatNewMenu.setPreferredSize(new Dimension(150, 35));
        // Đặt màu cho nền của JButton
//        buttonCreatNewMenu.setBackground(Color.RED);
        // Đặt màu cho văn bản của JButton
//        buttonCreatNewMenu.setForeground(Color.WHITE);

        jPanel.add(grid, BorderLayout.CENTER);
        // Đặt kích thước chiều ngang cho jPanel
        int width = 550; // Đặt kích thước mong muốn tại đây
        Dimension preferredSize = new Dimension(width, jPanel.getPreferredSize().height);
        jPanel.setPreferredSize(preferredSize);
        return jPanel;
    }

    private JPanel blockTableMenu() {   // lấy dữ liệu từ list tạm newMenus
        BoderTool boderTool = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Dish name","Quantity","Price","Type"}
        ){
            Class[] types = new Class [] {
                    String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
            };
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        };

        // Khởi tạo mô hình dữ liệu cho bảng
        table.setModel(model);
        this.tableModel = model;
        Object[][] data = MainProgram.getNewMenus().stream().map(
                s -> {
                    Object[] row = new Object[5];
                    row[0] = "";
                    row[1] = s.getDish().getDishName();
                    row[2] = s.getQuantity();
                    row[3] = s.getUnitPrice();
                    row[4] = s.getDish().getType().getName();
                    return row;
                }
        ).toArray(Object[][]::new);
        int count = 1;
        for (Object[] row : data) {
            row[0] = count++;
        }
        setData(data);
        // thêm dữ liệu vào bảng
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }
        // Căn giữa chữ trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < model.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        // Thiết lập chiều rộng cho các cột
        table.getColumnModel().getColumn(0).setMinWidth(30); // Cột ID
        table.getColumnModel().getColumn(0).setMaxWidth(50); // Cột ID
        JScrollPane scrollPane = new JScrollPane(table);
        // Đặt layout cho table_Panel là BorderLayout
        JPanel left = new JPanel();
        JPanel right = new JPanel();
        left.setPreferredSize(new Dimension(20, 20));
        right.setPreferredSize(new Dimension(20, 20));
        boderTool.add(right,BorderLayout.WEST);
        boderTool.add(scrollPane,BorderLayout.CENTER);
        return boderTool;
    }

    public void loadData(){
        // Xóa hết dữ liệu hiện có trong bảng
        tableModel.setRowCount(0);
        ArrayList<Menu> menus = MainProgram.getNewMenus();
        Object[][] data = menus.stream().map(
                s -> {
                    Object[] row = new Object[5];
                    row[0] = 1;
                    row[1] = s.getDish().getDishName();
                    row[2] = s.getQuantity();
                    row[3] = s.getUnitPrice();
                    row[4] = s.getDish().getType().getName();
                    return row;
                }
        ).toArray(Object[][]::new);

        for (int i = 0; i < data.length; i++) {
            data[i][0] = i + 1; // Số thứ tự
        }
        setData(data);
        // Thêm dữ liệu mới vào bảng
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }
        // Thông báo cho bảng biết rằng dữ liệu đã thay đổi để nó vẽ lại giao diện
        tableModel.fireTableDataChanged();
    }




}
