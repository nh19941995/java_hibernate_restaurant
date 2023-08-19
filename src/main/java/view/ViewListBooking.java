package view;

import controller.ControllerTime;
import dao.BookingsInfoDAO;
import dao.ClientPaymentInfoDAO;
import dao.PersonDAO;
import model.BookingsInfo;
import model.Person;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewListBooking extends JPanel {
    // data-------------------------------------------------------------------------------------------------------------
    private Object[][] data;
    private JTable table = new JTable();
    private DefaultTableModel tableModel;
    private int idSelect;
    // label------------------------------------------------------------------------------------------------------------
    private JLabel labelfilterByPhone = new JLabel("Search by phone number:");
    private JLabel labelfilterByDate= new JLabel("Search by date:");
    private JLabel labelPayment = new JLabel("Enter the payment amount");
    // input------------------------------------------------------------------------------------------------------------
    private JTextField inputSearchByPhone = new JTextField();
    private JTextField inputSearchByDate = new JTextField();
    private JTextField inputPaymentValue = new JTextField();
    private JButton buttonSearch = new JButton("Search");
    private JButton buttonPayment = new JButton("Payment");
    private JButton buttonGetBill = new JButton("Generate the invoice");
    // get + set -------------------------------------------------------------------------------------------------------

    public JTextField getInputPaymentValue() {
        return inputPaymentValue;
    }

    public JButton getButtonSearch() {
        return buttonSearch;
    }

    public JButton getButtonPayment() {
        return buttonPayment;
    }

    public JButton getButtonGetBill() {
        return buttonGetBill;
    }

    public JTextField getInputSearchByPhone() {
        return inputSearchByPhone;
    }
    public JTextField getInputSearchByDate() {
        return inputSearchByDate;
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

    public void setTable(JTable table) {
        this.table = table;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public int getIdSelect() {
        return idSelect;
    }

    public void setIdSelect(int idSelect) {
        this.idSelect = idSelect;
    }

    public ViewListBooking() {
        setLayout(new BorderLayout());
        add(blockTable(),BorderLayout.CENTER);
        add(blockBot(),BorderLayout.SOUTH);
        add(blockSearch(),BorderLayout.NORTH);
    }

    private JPanel blockSearch(){
        BoderTool jPanel = new BoderTool();
        jPanel.setPreferredSize(new Dimension(150, 75));
        GridTool grid = new GridTool();
        grid.GridAddCustom(labelfilterByPhone,0,0,20,20,5,5,1);
        grid.GridAddCustom(inputSearchByPhone,0,1,20,20,5,5,1);

        grid.GridAddCustom(labelfilterByDate,1,0,20,20,5,5,1);
        grid.GridAddCustom(inputSearchByDate,1,1,20,20,5,5,1);

        inputSearchByPhone.setPreferredSize(new Dimension(150, 25));
        inputSearchByDate.setPreferredSize(new Dimension(150, 25));

        GridTool grid2 = new GridTool();
        grid2.GridAddCustom(buttonSearch,0,0,20,20,5,5,1);
        buttonSearch.setPreferredSize(new Dimension(150, 35));
        jPanel.add(grid,BorderLayout.CENTER);
        jPanel.add(grid2,BorderLayout.EAST);
        return jPanel;
    }
    private  JPanel blockBot(){
        BoderTool jPanel = new BoderTool();
        jPanel.setPreferredSize(new Dimension(150, 75));

        GridTool gridTool1 = new GridTool();
        gridTool1.GridAddCustom(labelPayment,0,0,20,20,5,5,1);
        gridTool1.GridAddCustom(inputPaymentValue,0,1,20,20,5,5,1);
        gridTool1.GridAddCustom2(buttonGetBill,1,0,20,20,5,5,1,2);
        inputPaymentValue.setPreferredSize(new Dimension(150, 25));
        buttonGetBill.setPreferredSize(new Dimension(150, 35));

        GridTool gridTool2 = new GridTool();
        gridTool2.GridAddCustom(buttonPayment,0,0,20,20,20,20,1);
        buttonPayment.setPreferredSize(new Dimension(150, 35));
        jPanel.add(gridTool1,BorderLayout.CENTER);
        jPanel.add(gridTool2,BorderLayout.EAST);
        return jPanel;
    }

    private JPanel blockLeft(){
        BoderTool jPanel = new BoderTool();
        jPanel.setPreferredSize(new Dimension(450, 75));


        return jPanel;
    }



    private JPanel blockTable() {
        BoderTool jpanel = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Fisrt name","Last name","Phone number","Information","Amount Paid","Total bill", "Date created", "Status"}
        ){
            Class[] types = new Class [] {
                    String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false, false
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
        this.setTableModel(model);
        // lấy dữ liệu từ sever
        java.util.List<BookingsInfo> personList = BookingsInfoDAO.getInstance().getAll();
        Object[][] data = personList.stream()
                .filter(s->s.getFlag()>0)
                .map(s -> new Object[]{
                                s.getId(),
                                s.getPerson().getName(),
                                s.getPerson().getLastName(),
                                s.getPerson().getPhone(),
                                s.getInfo(),
                                ClientPaymentInfoDAO.getInstance().getTotalQuantityByBookingInfoID(s.getId()) ,
                                BookingsInfoDAO.getInstance().getTotalPriceByInfoBookingID(s.getId()),
                                ControllerTime.formatDateTime(2,s.getDateCreat()),
                                s.getFlag(),
                        }
                ).toArray(Object[][]::new);
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
        table.getColumnModel().getColumn(0).setMinWidth(30);
        table.getColumnModel().getColumn(0).setMaxWidth(50);

        table.getColumnModel().getColumn(1).setMinWidth(150);
        table.getColumnModel().getColumn(1).setMaxWidth(150);

        table.getColumnModel().getColumn(1).setMinWidth(100);
        table.getColumnModel().getColumn(2).setMaxWidth(100);

        table.getColumnModel().getColumn(3).setMinWidth(100);
        table.getColumnModel().getColumn(3).setMaxWidth(100);
        table.getColumnModel().getColumn(5).setMinWidth(100);
        table.getColumnModel().getColumn(5).setMaxWidth(100);
        table.getColumnModel().getColumn(6).setMinWidth(100);
        table.getColumnModel().getColumn(6).setMaxWidth(100);
        table.getColumnModel().getColumn(7).setMinWidth(100);
        table.getColumnModel().getColumn(7).setMaxWidth(100);
        table.getColumnModel().getColumn(8).setMinWidth(80);
        table.getColumnModel().getColumn(8).setMaxWidth(80);


        JScrollPane scrollPane = new JScrollPane(table);
        // Đặt layout cho table_Panel là BorderLayout

        JPanel left = new JPanel();
        JPanel right = new JPanel();
        left.setPreferredSize(new Dimension(20, 20));
        right.setPreferredSize(new Dimension(20, 20));
        jpanel.add(left,BorderLayout.EAST);
        jpanel.add(right,BorderLayout.WEST);
        jpanel.add(scrollPane,BorderLayout.CENTER);
        return jpanel;
    }

    public void reload(){
        // Clear existing data
        tableModel.setRowCount(0);
        // lấy dữ liệu từ sever
        java.util.List<BookingsInfo> personList = BookingsInfoDAO.getInstance().getAll();
        Object[][] data = personList.stream()
                .filter(s->s.getFlag()>0)
                .map(s -> new Object[]{
                                s.getId(),
                                s.getPerson().getName(),
                                s.getPerson().getLastName(),
                                s.getPerson().getPhone(),
                                s.getInfo(),
                                ClientPaymentInfoDAO.getInstance().getTotalQuantityByBookingInfoID(s.getId()) ,
                                BookingsInfoDAO.getInstance().getTotalPriceByInfoBookingID(s.getId()),
                                ControllerTime.formatDateTime(3,s.getDateCreat()),
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


}

