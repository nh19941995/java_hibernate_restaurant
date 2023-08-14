package view;


import controller.ControllerTime;
import dao.MenuDAO;
import dao.MenuNameDAO;
import dao.PersonDAO;
import model.MenuName;
import model.Person;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewMenu extends JPanel{
    // data-------------------------------------------------------------------------------------------------------------
    private JTable table = new JTable();
    private DefaultTableModel tableModel;
    private Object[][] data;
    private int menuIdSelect;
    private ViewMenuDetail menuDetail  = new ViewMenuDetail();
    // label------------------------------------------------------------------------------------------------------------
    private JLabel labelSumOutput = new JLabel();
    private JLabel title = new JLabel("List of menus");
    private JLabel labelSum = new JLabel("Total amount of the menu: ");
    private JLabel labelSearchByPrice = new JLabel("Search by brice: ");
    // button-----------------------------------------------------------------------------------------------------------
    private JButton buttonSelectMenu = new JButton("Select a Menu");
    private JButton buttonSearch = new JButton("Search");
    private JButton buttonDelete = new JButton("Delete");
    // input -----------------------------------------------------------------------------------------------------------
    private JTextField inputPrice = new JTextField();
    // get +set --------------------------------------------------------------------------------------------------------

    public JButton getButtonDelete() {
        return buttonDelete;
    }
    public ViewMenuDetail getMenuDetail() {
        return menuDetail;
    }

    public void setMenuDetail(ViewMenuDetail menuDetail) {
        this.menuDetail = menuDetail;
    }

    public int getMenuIdSelect() {
        return menuIdSelect;
    }

    public void setMenuIdSelect(int menuIdSelect) {
        this.menuIdSelect = menuIdSelect;
    }

    public JButton getButtonSelectMenu() {
        return buttonSelectMenu;
    }

    public JButton getButtonSearch() {
        return buttonSearch;
    }

    public JTextField getInputPrice() {
        return inputPrice;
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

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public ViewMenu() {
        setLayout(new BorderLayout());

    }
    public JPanel ViewCreatNewMenu(){
        ViewDish viewDish = new ViewDish();
        add(viewDish,BorderLayout.WEST);
        return this;
    }

    public ViewMenu ViewChoseMenu(){
        add(blockSearch(),BorderLayout.NORTH);
        add(blockTable(),BorderLayout.CENTER);
        add(blockMenuDetail(),BorderLayout.SOUTH);
        return this;
    }
    public JPanel blockMenuDetail(){
        BoderTool main = new BoderTool();
        GridTool grid2 = new GridTool();

        grid2.GridAddCustom(buttonSelectMenu,1,0,20,20,20,20,1);
        grid2.GridAddCustom(buttonDelete,0,0,20,20,20,20,1);


        buttonSelectMenu.setPreferredSize(new Dimension(150, 35));
        buttonDelete.setPreferredSize(new Dimension(150, 35));
        BoderTool bot = new BoderTool();
        bot.setPreferredSize(new Dimension(150, 75));
        bot.add(grid2,BorderLayout.CENTER);
        main.add(menuDetail,BorderLayout.CENTER);
        main.add(bot,BorderLayout.SOUTH);
        return main;
    }

    private JPanel blockTable(){
        BoderTool boderTool = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Menu name","Date Creat","Date update", "Price","Status"}
        ){
            Class[] types = new Class [] {
                    String.class, String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false
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
        // lấy dữ liệu từ sever
        List<MenuName> menuList = MenuNameDAO.getInstance().getAll();
        Object[][] data = menuList.stream()
                .filter(s->s.getFlag()>0)
                .map(s -> new Object[]{
                        s.getId(),
                        s.getName(),
                        ControllerTime.formatDateTime(2,s.getDateCreat()),
                        ControllerTime.formatDateTime(2,s.getDateUpdate()),
                        MenuDAO.getInstance().getTotalPriceByMenuNameID(s.getId()),
                        s.getFlag(),
                }
        ).toArray(Object[][]::new);
        setTableModel(model);
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
        boderTool.add(left,BorderLayout.EAST);
        boderTool.add(right,BorderLayout.WEST);
        boderTool.add(scrollPane, BorderLayout.CENTER);
        return boderTool;
    }
    public void reload(){
        // Clear existing data
        tableModel.setRowCount(0);
        // lấy dữ liệu từ sever
        List<MenuName> menuList = MenuNameDAO.getInstance().getAll();
        Object[][] data = menuList.stream()
                .filter(s->s.getFlag()>0)
                .map(s -> new Object[]{
                        s.getId(),
                        s.getName(),
                        ControllerTime.formatDateTime(2,s.getDateCreat()),
                        ControllerTime.formatDateTime(2,s.getDateUpdate()),
                        MenuDAO.getInstance().getTotalPriceByMenuNameID(s.getId()),
                        s.getFlag(),
                }
        ).toArray(Object[][]::new);
        setData(data);
        // thêm dữ liệu vào bảng
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }
        this.revalidate();
    }
    public JPanel blockSearch(){
        BoderTool boderTool = new BoderTool();
        GridTool grid = new GridTool();
        grid.GridAddCustom(labelSearchByPrice,0,0,20,20,5,5,1);
        grid.GridAddCustom(inputPrice,0,1,20,20,5,5,1);
        inputPrice.setPreferredSize(new Dimension(150, 25));
        boderTool.setPreferredSize(new Dimension(150, 75));

        GridTool grid2 = new GridTool();
        grid2.GridAddCustom(buttonSearch,0,0,20,20,5,5,1);
        buttonSearch.setPreferredSize(new Dimension(150, 35));

        boderTool.add(grid2,BorderLayout.EAST);
        boderTool.add(grid,BorderLayout.CENTER);
        return boderTool;
    }


}
