package view;

import controller.ControllerTime;
import dao.TransactionDAO;
import dao.TransactionsTypeDAO;
import model.Transaction;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewTransaction extends JPanel{
    private Object[][] data;
    // Khởi tạo lockObject
    private final Object lockObject = new Object();
    private JTable table = new JTable();
    // label------------------------------------------------------------------------------------------------------------
    private JLabel labelType = new JLabel("Transaction type");
    private JLabel labelVale = new JLabel("Value:");
    private JLabel labelDate = new JLabel("Date:");
    private JLabel labelTime = new JLabel("Time:");
    private JLabel labelFilerByType = new JLabel("Filer by type:");
    private JLabel labelFilerByPhone = new JLabel("Filer by phone number:");
    private JLabel labelFilerByDate = new JLabel("Filer by date:");
    private JLabel labelComment = new JLabel("Transaction content:");
    private JButton buttonFilter = new JButton("Filter");

    // input------------------------------------------------------------------------------------------------------------
    private JTextField inputValue = new JTextField();
    private JTextField inputTime = new JTextField();
    private JTextField inputDate = new JTextField();
    private JTextField inputFilterPhone = new JTextField();
    private JTextField inputFilterDate = new JTextField();
    private JTextField inputComment = new JTextField();
    private JComboBox<String> SelecFilterType = new JComboBox<String>();
    private JComboBox<String> SelecType = new JComboBox<String>();

    // info block-------------------------------------------------------------------------------------------------------
    private JLabel labelFirstName = new JLabel("First name: ");
    private JLabel FirstName = new JLabel();
    private JLabel labelLastName = new JLabel("Last name: ");
    private JLabel LastName = new JLabel();
    private JLabel labelPhone = new JLabel("Phone: ");
    private JLabel Phone = new JLabel();
    // button-----------------------------------------------------------------------------------------------------------
    private JButton buttonCreatTransaction = new JButton("Creat transaction");
    private JButton buttonAllTransaction = new JButton("All transaction");
    private JButton buttonSelect = new JButton("Select person by list");
    private JButton buttonDelete = new JButton("Delete");
    private JButton buttonExportToExcel = new JButton("Export to Excel");
    // get + set--------------------------------------------------------------------------------------------------------


    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public ViewTransaction() {
        setLayout(new BorderLayout());
        // Khởi tạo bảng với mô hình dữ liệu trống
//        table.setModel(new DefaultTableModel());
        // layout chính
        BoderTool boderCenter = new BoderTool() ;
        BoderTool boderRight = new BoderTool();

        add(boderCenter,BorderLayout.CENTER);
        add(boderRight,BorderLayout.WEST);

        boderCenter.add(blockTable(),BorderLayout.CENTER);
        boderRight.add(blockTool(),BorderLayout.WEST);

    }

    private JPanel blockInforPerson(){
        BoderTool jPanel = new BoderTool();
        GridTool grid = new GridTool();
        int left =10;
        int right =10;
        int top =10;
        int bot =10;
        // đặt kích thước
        jPanel.setPreferredSize(new Dimension(300, 200));
        grid.GridAddCustom(labelFirstName,0,0,left,right,top,bot,1);
        grid.GridAddCustom(FirstName,1,0,left,right,top,bot,1);

        grid.GridAddCustom(labelLastName,0,1,left,right,top,bot,1);
        grid.GridAddCustom(LastName,1,1,left,right,top,bot,1);

        grid.GridAddCustom(labelPhone,0,2,left,right,top,bot,1);
        grid.GridAddCustom(Phone,1,2,left,right,top,bot,1);

        grid.GridAddCustom(buttonSelect,0,6,left,right,top,bot,2);
        grid.GridAddCustom(buttonAllTransaction,0,7,left,right,top,bot,2);
        grid.GridAddCustom(buttonCreatTransaction,0,8,left,right,top,bot,2);
        buttonSelect.setPreferredSize(new Dimension(150, 20));
        buttonAllTransaction.setPreferredSize(new Dimension(150, 20));
        buttonCreatTransaction.setPreferredSize(new Dimension(150, 35));

        jPanel.add(grid,BorderLayout.CENTER);
        return  jPanel;
    }
    private JPanel blockTool(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(blockCreatNewTransaction());
        jPanel.add(blockInforPerson());
        return jPanel;
    }


    private JPanel blockTable(){
        BoderTool jPanel = new BoderTool();
        JPanel scrollPane = Table();
        jPanel.add(blockFilter(),BorderLayout.NORTH);
        jPanel.add(scrollPane,BorderLayout.CENTER);
        jPanel.add(blockBottomTransaction(),BorderLayout.SOUTH);
        return jPanel;
    }
    private JPanel blockBottomTransaction(){
        BoderTool jPanel = new BoderTool();
        GridTool grid = new GridTool();
        GridTool grid2 = new GridTool();
        grid.GridAddCustom(buttonExportToExcel,0,0,20,20,20,20,1);
        grid2.GridAddCustom(buttonDelete,0,0,20,20,20,20,1);
//        buttonCreatTransaction.setPreferredSize(new Dimension(150, 35));
        buttonExportToExcel.setPreferredSize(new Dimension(150, 35));
        // Đặt màu cho nền của JButton
        buttonExportToExcel.setBackground(Color.RED);
        // Đặt màu cho văn bản của JButton
        buttonExportToExcel.setForeground(Color.WHITE);

        jPanel.add(grid,BorderLayout.EAST);
        jPanel.add(grid2,BorderLayout.WEST);
        return jPanel;
    }


    private JPanel Table() {
        BoderTool jPanel = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Content","Type","Value","Time", "Date","Person Name", "Phone number", "Status"}
        ){
            Class[] types = new Class [] {
                    String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class, String.class
            };
            boolean[] canEdit = new boolean [] {
                    false, false, false, false, false, false, false, false
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
        List<Transaction> transactionList = TransactionDAO.getInstance().getAll();
        Object[][] data = transactionList.stream()
                .filter(s -> s.getFlag() > 0)
                .map(s -> new Object[]{
                                s.getId(),
                                s.getComment(),
                                s.getType().getType(),
                                s.getQuantity(),
                                ControllerTime.formatDateTime(1,s.getDateCreat()),
                                ControllerTime.formatDateTime(2,s.getDateCreat()),
                                s.getPerson().getName()+" "+s.getPerson().getLastName(),
                                s.getPerson().getPhone(),
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
        table.getColumnModel().getColumn(0).setMinWidth(30); // Cột ID
        table.getColumnModel().getColumn(0).setMaxWidth(50); // Cột ID

        table.getColumnModel().getColumn(1).setMinWidth(250); // Cột ID
//        table.getColumnModel().getColumn(1).setMaxWidth(300); // Cột ID

        table.getColumnModel().getColumn(2).setMinWidth(180); // Cột ID
        table.getColumnModel().getColumn(2).setMaxWidth(190); // Cột ID

        table.getColumnModel().getColumn(3).setMinWidth(80); // Cột ID
        table.getColumnModel().getColumn(3).setMaxWidth(90); // Cột ID

        table.getColumnModel().getColumn(4).setMinWidth(90); // Cột ID
        table.getColumnModel().getColumn(4).setMaxWidth(100); // Cột ID

        table.getColumnModel().getColumn(5).setMinWidth(100); // Cột ID
        table.getColumnModel().getColumn(5).setMaxWidth(120); // Cột ID

        table.getColumnModel().getColumn(8).setMinWidth(30); // Cột ID
        table.getColumnModel().getColumn(8).setMaxWidth(50); // Cột ID

        JScrollPane scrollPane = new JScrollPane(table);
        // Đặt layout cho table_Panel là BorderLayout

        JPanel left = new JPanel();
        JPanel right = new JPanel();
        left.setPreferredSize(new Dimension(20, 20));
        right.setPreferredSize(new Dimension(20, 20));
        jPanel.add(left,BorderLayout.EAST);
        jPanel.add(right,BorderLayout.WEST);



        jPanel.add(scrollPane, BorderLayout.CENTER);
        return jPanel;
    }

    public void loadData(){
        // Lấy model của bảng
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Xóa hết dữ liệu hiện có trong bảng
        model.setRowCount(0);
        // lấy dữ liệu từ sever
        List<model.Transaction> transactionList = TransactionDAO.getInstance().getAll();
        Object[][] data = transactionList.stream()
                .filter(s -> s.getFlag() > 0)
                .map(s -> new Object[]{
                                s.getId(),
                                s.getComment(),
                                s.getType().getType(),
                                s.getQuantity(),
                                ControllerTime.formatDateTime(1,s.getDateCreat()),
                                ControllerTime.formatDateTime(2,s.getDateCreat()),
                                s.getPerson().getName()+" "+s.getPerson().getLastName(),
                                s.getPerson().getPhone(),
                                s.getFlag(),
                        }
                ).toArray(Object[][]::new);
//        ClientListView.setData(data);
        setData(data);
        // Thêm dữ liệu mới vào bảng
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }
        // Thông báo cho bảng biết rằng dữ liệu đã thay đổi để nó vẽ lại giao diện
        model.fireTableDataChanged();
    }

    private JPanel blockFilter() {
        int top = 10;
        int bot = 10;
        int bot2 = 15;
        int left = 10;
        int right = 0;
        int filterBarHeight = 50; // Đặt chiều cao cho filterBar ở đây

        BoderTool boder = new BoderTool();
        boder.setPreferredSize(new Dimension(0, filterBarHeight)); // Đặt chiều cao cho boder
        boder.setPreferredSize(new Dimension(200, 75));

        GridTool grid = new GridTool();
        grid.GridAddCustom(labelFilerByType, 0, 0, left, 0, top, bot, 1);
        grid.GridAddCustom(SelecFilterType, 1, 0, left, 0, top, bot, 1);
        grid.GridAddCustom(labelFilerByPhone, 2, 0, left, 0, top, bot, 1);
        grid.GridAddCustom(inputFilterPhone, 3, 0, left, 0, top, bot, 1);

        grid.GridAddCustom(labelFilerByDate, 4, 0, left, 0, top, bot, 1);
        grid.GridAddCustom(inputFilterDate, 5, 0, left, 0, top, bot, 1);

        grid.GridAddCustom(buttonFilter, 6, 0, left, 0, top, bot, 1);
        SelecFilterType.setPreferredSize(new Dimension(150, 20));
        inputFilterPhone.setPreferredSize(new Dimension(150, 20));
        inputFilterDate.setPreferredSize(new Dimension(150, 20));
        boder.add(grid, BorderLayout.CENTER);
        return boder;
    }

    private JPanel blockCreatNewTransaction(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        int top = 5;
        int bot = 2;
        int bot2 = 15;
        int left = 10;
        int right = 10;
        GridTool grid = new GridTool();
        grid.GridAddCustom(labelType,0,0,left,right,top,bot,1);
        grid.GridAddCustom(SelecType,0,1,left,right,top,bot,1);

        grid.GridAddCustom(labelVale,0,2,left,right,top,bot,1);
        grid.GridAddCustom(inputValue,0,3,left,right,top,bot,1);
        SelecType.setPreferredSize(new Dimension(200, 20));
        inputValue.setPreferredSize(new Dimension(200, 20));

        grid.GridAddCustom(labelTime,0,4,left,right,top,bot,1);
        grid.GridAddCustom(inputTime,0,5,left,right,top,bot,1);
        grid.GridAddCustom(labelDate,0,6,left,right,top,bot,1);
        grid.GridAddCustom(inputDate,0,7,left,right,top,bot,1);
        grid.GridAddCustom(labelComment,0,8,left,right,top,bot,1);
        grid.GridAddCustom(inputComment,0,9,left,right,top,bot,1);

        inputTime.setPreferredSize(new Dimension(200, 20));
        inputDate.setPreferredSize(new Dimension(200, 20));
        inputComment.setPreferredSize(new Dimension(200, 20));
        // thêm data cho boder box
        String[] selectList = TransactionsTypeDAO.getInstance().getAll().stream()
                .map(s -> s.getType())
                .toArray(String[]::new);
        SelecType.setModel(new javax.swing.DefaultComboBoxModel<>(selectList));
        SelecFilterType.setModel(new javax.swing.DefaultComboBoxModel<>(selectList));
        jPanel.add(grid,BorderLayout.CENTER);
        return jPanel;
    }

}
