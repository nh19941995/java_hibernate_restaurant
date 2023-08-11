package view;

import controller.ControllerDish;
import dao.DishDAO;
import dao.DishTypeDAO;
import model.Dish;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewDish extends JPanel{
    // data ------------------------------------------------------------------------------------------------------------
    private Object lockObject = new Object();
    private JTable table = new JTable();
    private DefaultTableModel tableModel;
    private Object[][] data;
    private int idSelectDish;
    private int tempId;
    // input -----------------------------------------------------------------------------------------------------------
    private JTextField inputEnterNumber = new JTextField();
    private JTextField inputEnterPrice = new JTextField();


    private JTextField inputFilerByPrice = new JTextField();
    private JTextField inputNewDishName = new JTextField();
    private JTextField inputReferencePrice = new JTextField();
    private JTextField inputComment = new JTextField();
    // combo box
    private JComboBox<String> selectType = new JComboBox<>();
    private JComboBox<String> selectTypeForNewDish = new JComboBox<>();
    // button ----------------------------------------------------------------------------------------------------------
    private JButton buttonSlectDish = new JButton("Select dish");
    private JButton buttonSeach = new JButton("Seach");
    private JButton buttonCreatNewDish = new JButton("Creat new dish");
    // label -----------------------------------------------------------------------------------------------------------
    private JLabel labelEnterPrice = new JLabel("Enter price: ");
    private JLabel labelFilerByType = new JLabel("Select type: ");
    private JLabel labelFilerByPrice = new JLabel("Enter price: ");
    private JLabel labelEnterNumber = new JLabel("Enter number: ");
    private JLabel labelEnterComment = new JLabel("Enter Comment: ");
    private JLabel labelNewDishName = new JLabel("Enter the new dish name: ");
    private JLabel labelReferencePrice = new JLabel("Enter reference price: ");
    private JLabel LabelNewDishType = new JLabel("Select new dish type: ");
    // get + set -------------------------------------------------------------------------------------------------------
    public int getIdSelectDish() {
        return idSelectDish;
    }
    public void setIdSelectDish(int idSelectDish) {
        this.idSelectDish = idSelectDish;
    }
    public int getTempId() {
        return tempId;
    }
    public void setTempId(int tempId) {
        this.tempId = tempId;
    }
    public Object[][] getData() {
        return data;
    }
    public void setData(Object[][] data) {
        this.data = data;
    }
    public JTable getTable() {
        return table;
    }
    public JButton getButtonSlectDish() {
        return buttonSlectDish;
    }
    public JButton getButtonSeach() {
        return buttonSeach;
    }

    public JButton getButtonCreatNewDish() {
        return buttonCreatNewDish;
    }

    public JTextField getInputEnterNumber() {
        return inputEnterNumber;
    }

    public JTextField getInputEnterPrice() {
        return inputEnterPrice;
    }

    public JTextField getInputFilerByPrice() {
        return inputFilerByPrice;
    }

    public JTextField getInputNewDishName() {
        return inputNewDishName;
    }

    public JTextField getInputReferencePrice() {
        return inputReferencePrice;
    }

    public JTextField getInputComment() {
        return inputComment;
    }

    public JComboBox<String> getSelectType() {
        return selectType;
    }

    public JComboBox<String> getSelectTypeForNewDish() {
        return selectTypeForNewDish;
    }

    public ViewDish() {
        setLayout(new BorderLayout());

        // load data combobox
        String[] selectList = DishTypeDAO.getInstance().getAll().stream()
                .map(s -> s.getName())
                .toArray(String[]::new);
        selectType.setModel(new javax.swing.DefaultComboBoxModel<>(selectList));
        selectTypeForNewDish.setModel(new javax.swing.DefaultComboBoxModel<>(selectList));
        new ControllerDish(this);

    }
    public JPanel ViewMainDish(){
        add(blockTable(),BorderLayout.CENTER);
        add(blockCreatNew(),BorderLayout.SOUTH);
        add(blockSearchBar(),BorderLayout.NORTH);
        return this;
    }

    public JPanel ViewSelectDish(){
        add(blockSearchBar(),BorderLayout.NORTH);
        add(blockTable(),BorderLayout.CENTER);
        add(blockSelectDish(),BorderLayout.SOUTH);
        return this;
    }

    private JPanel blockSelectDish(){
        BoderTool boderTool = new BoderTool();
        GridTool gridTool = new GridTool();
        gridTool.GridAddCustom(buttonSlectDish,0,0,20,20,20,20,1);
        buttonSlectDish.setPreferredSize(new Dimension(150, 35));
        GridTool gridTool1 = new GridTool();

        gridTool1.GridAddCustom(labelEnterPrice,0,0,20,20,5,5,1);
        gridTool1.GridAddCustom(inputEnterPrice,0,1,20,20,5,5,1);
        inputEnterPrice.setPreferredSize(new Dimension(150, 25));

        gridTool1.GridAddCustom(labelEnterNumber,1,0,20,20,5,5,1);
        gridTool1.GridAddCustom(inputEnterNumber,1,1,20,20,5,5,1);
        inputEnterNumber.setPreferredSize(new Dimension(150, 25));


        boderTool.add(gridTool,BorderLayout.EAST);
        boderTool.add(gridTool1,BorderLayout.CENTER);
        return boderTool;
    }


    private JPanel blockTable() {
        BoderTool jpanel = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Dish name","reference price","Dish type", "Note"}
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
        // lấy dữ liệu từ sever
        List<Dish> dishList = DishDAO.getInstance().getAll();
        Object[][] dataFromDB = dishList.stream()
                .filter(s->s.getFlag() > 0)  // chỉ lấy flag > 0
                .map(s -> new Object[]{
                        s.getId(),
                        s.getDishName(),
                        s.getPrice(),
                        s.getType().getName(),
                        s.getDishComment(),
                }
        ).toArray(Object[][]::new);
        this.data = dataFromDB;
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
        jpanel.add(scrollPane, BorderLayout.CENTER);
        return jpanel;
    }

    private JPanel blockCreatNew(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setPreferredSize(new Dimension(150, 75));
        GridTool grid = new GridTool();
        grid.GridAddCustom(labelNewDishName,0,0,20,20,5,5,1);
        grid.GridAddCustom(inputNewDishName,0,1,20,20,5,5,1);
        inputNewDishName.setPreferredSize(new Dimension(150, 25));
        grid.GridAddCustom(labelReferencePrice,1,0,20,20,5,5,1);
        grid.GridAddCustom(inputReferencePrice,1,1,20,20,5,5,1);
        inputReferencePrice.setPreferredSize(new Dimension(150, 25));
        grid.GridAddCustom(labelEnterComment,2,0,20,20,5,5,1);
        grid.GridAddCustom(inputComment,2,1,20,20,5,5,1);
        inputComment.setPreferredSize(new Dimension(150, 25));
        grid.GridAddCustom(selectTypeForNewDish,3,1,20,20,5,5,1);
        grid.GridAddCustom(LabelNewDishType,3,0,20,20,5,5,1);
        selectTypeForNewDish.setPreferredSize(new Dimension(150, 25));

        GridTool grid1 = new GridTool();
        grid1.GridAddCustom2(buttonCreatNewDish,0,0,20,20,5,5,1,1);
        buttonCreatNewDish.setPreferredSize(new Dimension(150, 35));
        // Đặt màu cho nền của JButton
        buttonCreatNewDish.setBackground(Color.RED);
        // Đặt màu cho văn bản của JButton
        buttonCreatNewDish.setForeground(Color.WHITE);
        jPanel.add(grid,BorderLayout.CENTER);
        jPanel.add(grid1,BorderLayout.EAST);
        return  jPanel;
    }

    private JPanel blockSearchBar(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        // đăt chiều cao cho jpanel
        jPanel.setPreferredSize(new Dimension(jPanel.getPreferredSize().width, 75));
        // đặt kích thước
        selectType.setPreferredSize(new Dimension(150, 25));
        inputFilerByPrice.setPreferredSize(new Dimension(200, 25));
        buttonSeach.setPreferredSize(new Dimension(150, 30));
        GridTool grid = new GridTool();
        grid.GridAddCustom(labelFilerByType, 0,0,10,10,10,0,1);
        grid.GridAdd(selectType, 0,1,10,10,10);
        grid.GridAddCustom(labelFilerByPrice, 1,0,10,10,10,0,1);
        grid.GridAdd(inputFilerByPrice, 1,1,10,10,10);
        GridTool grid2 = new GridTool();
        grid2.GridAddCustom(buttonSeach,0,0,10,10,10,10,1);
        jPanel.add(grid,BorderLayout.CENTER);
        jPanel.add(grid2,BorderLayout.EAST);
        return jPanel;
    }

    public void reloadTable(){
        // Clear existing data
        tableModel.setRowCount(0);
        // lấy dữ liệu từ sever
        List<Dish> dishList = DishDAO.getInstance().getAll();
        Object[][] dataFromDB = dishList.stream()
                .filter(s->s.getFlag()>0)
                .map(
                s -> new Object[]{
                        s.getId(),
                        s.getDishName(),
                        s.getPrice(),
                        s.getType().getName(),
                        s.getDishComment(),
                }
        ).toArray(Object[][]::new);
        this.data = dataFromDB;
        // thêm dữ liệu vào bảng
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }
        this.revalidate();
    }
}
