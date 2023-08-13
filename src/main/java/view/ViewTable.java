package view;

import controller.ControllerDish;
import controller.ControllerTable;
import controller.ControllerTime;
import dao.*;
import model.Dish;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class ViewTable extends JPanel {
    // data-------------------------------------------------------------------------------------------------------------
    private Object lockObject = new Object();
    private JTable table = new JTable();
    private DefaultTableModel tableModel;
    private Object[][] data;
    private int idSelect;
    // input------------------------------------------------------------------------------------------------------------
    private JTextField inputFilterBySeatingCapacity = new JTextField();
    private JTextField inputFilterByDate = new JTextField();
    private JComboBox<String> selecTypeSearch = new JComboBox<>();
    private JComboBox<String> selecStatusTable = new JComboBox<>();
    // label -----------------------------------------------------------------------------------------------------------
    private JLabel labelSeatingCapacity = new JLabel("Filter by seating capacity");
    private JLabel labelDate = new JLabel("Filter by date");
    private JLabel labelType = new JLabel("Select type");
    private JLabel labelStatus = new JLabel("Select status");
    // button ----------------------------------------------------------------------------------------------------------
    private JButton buttonSelectTable = new JButton("Select table");
    private JButton buttonSearch = new JButton("Search");
    private JButton buttonChangeStatus = new JButton("Change status");
//    private JButton buttonHideTable = new JButton("Hide table");
    // get + set -------------------------------------------------------------------------------------------------------
    public JButton getButtonSelectTable() {
        return buttonSelectTable;
    }
    public JButton getButtonSearch() {
        return buttonSearch;
    }
    public JTextField getInputFilterBySeatingCapacity() {
        return inputFilterBySeatingCapacity;
    }
    public JTextField getInputFilterByDate() {
        return inputFilterByDate;
    }
    public JComboBox<String> getSelecTypeSearch() {
        return selecTypeSearch;
    }
    public Object[][] getData() {
        return data;
    }
    public void setData(Object[][] data) {
        this.data = data;
    }
    public DefaultTableModel getTableModel() {
        return tableModel;
    }
    public JButton getButtonChangeStatus() {
        return buttonChangeStatus;
    }
    public JTable getTable() {
        return table;
    }
    public int getIdSelect() {
        return idSelect;
    }
    public void setIdSelect(int idSelect) {
        this.idSelect = idSelect;
    }
    public JComboBox<String> getSelecStatusTable() {
        return selecStatusTable;
    }

    public ViewTable() {
        setLayout(new BorderLayout());
        // thêm data cho boder box
        String[] selectList = TableTypeDAO.getInstance().getAll().stream()
                .map(s -> s.getName())
                .toArray(String[]::new);
        selecTypeSearch.setModel(new javax.swing.DefaultComboBoxModel<>(selectList));
        // thêm data cho status
        String[] status = TableStatusDAO.getInstance().getAll().stream()
                .map(s -> s.getName())
                .toArray(String[]::new);
        selecStatusTable.setModel(new javax.swing.DefaultComboBoxModel<>(status));
        // thêm controller
        new ControllerTable(this);
    }

    public JPanel viewTableMain (){
        add(blockTable(),BorderLayout.CENTER);
        add(blockSearch(),BorderLayout.NORTH);
        add(blockBotMain(),BorderLayout.SOUTH);
        return this;
    }

    public ViewTable viewTableSelectTable(){
        add(blockTable(),BorderLayout.CENTER);
        add(blockSearch(),BorderLayout.NORTH);
        add(blockBotSelectTable(),BorderLayout.SOUTH);
        return this;
    }

    private JPanel blockTable() {
        BoderTool jpanel = new BoderTool();

        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Table type", "Seating Capacity","Rental start time","Rental end time","Date time","Status"}
        ){
            Class[] types = new Class [] {
                    String.class, String.class, String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false
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
        List<model.Booking> bookingList = BookingDAO.getInstance().getAll();
        // Sử dụng HashSet để lưu trữ các phần tử không trùng lặp
        Set<Integer> uniqueElements = new HashSet<>();
        Object[][] dataOnBooking = bookingList.stream()
                .filter(s->s.getInfo().getStart().isAfter(LocalDateTime.now())) // chỉ lấy các booking sau ngày hiện tại
                .filter(s->s.getFlag() > 0)  // chỉ lấy các booking flag > 0
                .map(s -> new Object[]{
                        s.getTable().getId(),
                        s.getTable().getType().getName(),
                        s.getTable().getSeatingCapacity(),
                        ControllerTime.formatDateTime(1,s.getInfo().getStart()),
                        ControllerTime.formatDateTime(1,s.getInfo().getEnd()),
                        ControllerTime.formatDateTime(2,s.getInfo().getStart()),
                        s.getTable().getStatus().getName(),  // trạng thái của bàn
                        uniqueElements.add(s.getTable().getId())
                }
        ).toArray(Object[][]::new);
        List<model.TableList> tableLists = TableListDAO.getInstance().getAll();
        Object[][] dataNoneBooking = tableLists.stream()
                .map(s -> {
                    int tableId = s.getId().intValue();
                    if (!uniqueElements.contains(tableId)) { // Đảo ngược điều kiện từ contains thành không contains
                        return new Object[]{
                                s.getId(),
                                s.getType().getName(),
                                s.getSeatingCapacity(),
                                "",
                                "",
                                "",
                                s.getStatus().getName()
                        };
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Object[][]::new);
        Object[][] allBooking = concatenateArrays(dataNoneBooking,dataOnBooking);
        //        sắp xếp theo id
        Arrays.sort(allBooking, Comparator.comparingInt(arr -> (int) arr[0]));
        this.data = allBooking;
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
        jpanel.add(left,BorderLayout.EAST);
        jpanel.add(right,BorderLayout.WEST);
        jpanel.add(scrollPane, BorderLayout.CENTER);
        return jpanel;
    }
    private JPanel blockSearch(){
        BoderTool seach = new BoderTool();
        seach.setPreferredSize(new Dimension(150, 75));
        GridTool input = new GridTool();
        GridTool button = new GridTool();
        input.GridAdd(labelDate,0,0,20,20,5);
        input.GridAdd(inputFilterByDate,0,1,20,20,5);
        inputFilterByDate.setPreferredSize(new Dimension(150, 25));

        input.GridAdd(labelSeatingCapacity,1,0,20,20,5);
        input.GridAdd(inputFilterBySeatingCapacity,1,1,20,20,5);
        inputFilterBySeatingCapacity.setPreferredSize(new Dimension(150, 25));

        input.GridAdd(labelType,2,0,20,20,5);
        input.GridAdd(selecTypeSearch,2,1,20,20,5);
        selecTypeSearch.setPreferredSize(new Dimension(150, 25));

        button.GridAdd(buttonSearch,0 ,0,20,20,5);
        buttonSearch.setPreferredSize(new Dimension(150, 35));

        seach.add(input,BorderLayout.CENTER);
        seach.add(button,BorderLayout.EAST);
        return seach;
    }

    private JPanel blockBotSelectTable(){
        BoderTool bot = new BoderTool();
        bot.setPreferredSize(new Dimension(150, 75));
        GridTool select = new GridTool();
        select.GridAddCustom(buttonSelectTable,0,0,20,20,5 ,5,1);
        buttonSelectTable.setPreferredSize(new Dimension(150, 35));

        bot.add(select,BorderLayout.EAST);
        return bot;
    }

    private JPanel blockBotMain(){
        BoderTool bot = new BoderTool();
        bot.setPreferredSize(new Dimension(150, 75));
        GridTool select = new GridTool();
        select.GridAddCustom(labelStatus,0,0,20,20,5 ,5,1);
        select.GridAddCustom(selecStatusTable,0,1,20,20,5 ,5,1);
//        select.GridAddCustom2(buttonChangeStatus,1,0,20,20,5 ,5,1,2);

        selecStatusTable.setPreferredSize(new Dimension(150, 25));

        buttonChangeStatus.setPreferredSize(new Dimension(150, 35));
        GridTool select2 = new GridTool();
        select2.GridAddCustom2(buttonChangeStatus,2,0,20,20,5 ,5,1,2);


        bot.add(select,BorderLayout.CENTER);
        bot.add(select2,BorderLayout.EAST);

        return bot;
    }

    // hàm nối mảng
    public static Object[][] concatenateArrays(Object[][] arr1, Object[][] arr2) {
        int arr1Length = arr1.length;
        int arr2Length = arr2.length;
        Object[][] result = new Object[arr1Length + arr2Length][];
        System.arraycopy(arr1, 0, result, 0, arr1Length);
        System.arraycopy(arr2, 0, result, arr1Length, arr2Length);
        return result;
    }
    public void reload(){
        // Clear existing data
        tableModel.setRowCount(0);
        // lấy dữ liệu từ sever
        List<model.Booking> bookingList = BookingDAO.getInstance().getAll();
        // Sử dụng HashSet để lưu trữ các phần tử không trùng lặp
        Set<Integer> uniqueElements = new HashSet<>();
        Object[][] dataOnBooking = bookingList.stream()
                .filter(s->s.getInfo().getStart().isAfter(LocalDateTime.now())) // chỉ lấy các booking sau ngày hiện tại
                .filter(s->s.getFlag() > 0)  // chỉ lấy các booking flag > 0
                .map(s -> new Object[]{
                                s.getTable().getId(),
                                s.getTable().getType().getName(),
                                s.getTable().getSeatingCapacity(),
                                ControllerTime.formatDateTime(1,s.getInfo().getStart()),
                                ControllerTime.formatDateTime(1,s.getInfo().getEnd()),
                                ControllerTime.formatDateTime(2,s.getInfo().getStart()),
                                s.getTable().getStatus().getName(),  // trạng thái của bàn
                                uniqueElements.add(s.getTable().getId())
                        }
                ).toArray(Object[][]::new);
        List<model.TableList> tableLists = TableListDAO.getInstance().getAll();
        Object[][] dataNoneBooking = tableLists.stream()
                .map(s -> {
                    int tableId = s.getId().intValue();
                    if (!uniqueElements.contains(tableId)) { // Đảo ngược điều kiện từ contains thành không contains
                        return new Object[]{
                                s.getId(),
                                s.getType().getName(),
                                s.getSeatingCapacity(),
                                "",
                                "",
                                "",
                                s.getStatus().getName()
                        };
                    } else {
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .toArray(Object[][]::new);
        Object[][] allBooking = concatenateArrays(dataNoneBooking,dataOnBooking);
        //        sắp xếp theo id
        Arrays.sort(allBooking, Comparator.comparingInt(arr -> (int) arr[0]));
        this.data = allBooking;
        // thêm dữ liệu vào bảng
        for (Object[] rowData : data) {
            tableModel.addRow(rowData);
        }
    }

}
