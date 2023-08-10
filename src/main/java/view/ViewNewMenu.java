package view;

import model.Menu;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class ViewNewMenu extends JPanel {
    // data-------------------------------------------------------------------------------------------------------------
    private ArrayList<Menu> newMenus = new ArrayList<>();

    private JTable table = new JTable();
    private DefaultTableModel tableModel;
    private Object[][] data;
    // button-----------------------------------------------------------------------------------------------------------
    private JButton buttonCreatNewMenu = new JButton("Creat new menu");
    // input------------------------------------------------------------------------------------------------------------
    private JTextField inputNameNewMenu = new JTextField();
    // label------------------------------------------------------------------------------------------------------------
    private JLabel labelNameNewMenu = new JLabel("Menu name:");
    // get + set--------------------------------------------------------------------------------------------------------
    public Object[][] getData() {
        return data;
    }
    public void setData(Object[][] data) {
        this.data = data;
    }

    public void add(Menu menu) {
        newMenus.add(menu);
    }
    public ViewNewMenu() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        add(blockMenu());
        add(blockDish());
    }

    private JPanel blockMenu(){
        BoderTool boderTool = new BoderTool();
        boderTool.add(blockTop(),BorderLayout.NORTH);
        boderTool.add(blockTableMenu(), BorderLayout.CENTER);
        boderTool.add(blockBot(),BorderLayout.SOUTH);
        return boderTool;
    }

    private JPanel blockDish(){
        BoderTool boderTool = new BoderTool();
        ViewDish viewDish = new ViewDish();
        boderTool.add(viewDish.ViewSelectDish(),BorderLayout.CENTER);
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
        grid.GridAdd(buttonCreatNewMenu,0,0,20,20,20);

        buttonCreatNewMenu.setPreferredSize(new Dimension(150, 35));
        // Đặt màu cho nền của JButton
        buttonCreatNewMenu.setBackground(Color.RED);
        // Đặt màu cho văn bản của JButton
        buttonCreatNewMenu.setForeground(Color.WHITE);

        jPanel.add(grid, BorderLayout.CENTER);
        // Đặt kích thước chiều ngang cho jPanel
        int width = 550; // Đặt kích thước mong muốn tại đây
        Dimension preferredSize = new Dimension(width, jPanel.getPreferredSize().height);
        jPanel.setPreferredSize(preferredSize);
        return jPanel;
    }

    private JPanel blockTableMenu() {
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
        final int count = 1;
        // Biến count là final, vì vậy nó sẽ không gây ra lỗi.
        Object[][] data = newMenus.stream().map(
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
        boderTool.add(scrollPane,BorderLayout.CENTER);
        return boderTool;
    }

    public void loadData(){
        // Lấy model của bảng
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        // Xóa hết dữ liệu hiện có trong bảng
        model.setRowCount(0);
        // Biến count là final, vì vậy nó sẽ không gây ra lỗi.
        Object[][] data = newMenus.stream().map(
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
        setData(data);
        // Thêm dữ liệu mới vào bảng
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }
        // Thông báo cho bảng biết rằng dữ liệu đã thay đổi để nó vẽ lại giao diện
        model.fireTableDataChanged();
    }

}
