package view;

import dao.MenuDAO;
import model.Menu;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewMenuDetail extends JPanel{
    // data ------------------------------------------------------------------------------------------------------------
    private DefaultTableModel tableModel;
    private Object[][] data;
    private int menuNameID;
    private double sum;
    private JTable table = new JTable();
    // label -----------------------------------------------------------------------------------------------------------
    private JLabel title = new JLabel("      List of dishes");
    // get + set -------------------------------------------------------------------------------------------------------


    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public ViewMenuDetail() {
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(150, 300));
//        GridTool gridTool = new GridTool();
//        gridTool.GridAddCustom(title,0,0,20,20,20,20,1);
        title.setPreferredSize(new Dimension(150, 30));
//        title.setFont(new Font("Arial", Font.BOLD, 15));
        add(title,BorderLayout.NORTH);
        add(blockTable(),BorderLayout.CENTER);
    }


    private JPanel blockTable() {
        BoderTool boderTool = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Dish name","Price","Quantity", "Total"}
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
        tableModel = model;
        table.setModel(tableModel);
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
        boderTool.add(scrollPane, BorderLayout.CENTER);
        return boderTool;
    }

    public void loadData(int id){
        // Xóa hết dữ liệu trong JTable
        tableModel.setRowCount(0); // Xóa hết các dòng trong model
        // lấy dữ liệu từ sever
        List<Menu> dishList = MenuDAO.getInstance().getMenuByMenuNameID(id);
        Object[][] xxx = dishList.stream()
                .filter(s->s.getFlag()>0)
                .map(
                s -> new Object[]{
                        "",
                        s.getDish().getDishName(),
                        s.getUnitPrice(),
                        s.getQuantity(),
                        s.getUnitPrice()*s.getQuantity(),
                }
        ).toArray(Object[][]::new);

        int count = 1;
        for (Object[] s : xxx) {
            s[0] = count++;
        }
        Double sum = 0d;
        for (Object[] s : xxx) {
            Double value = (Double) s[4]; // Ép kiểu về Double
            sum += value;
        }
        setSum(sum);
        for (Object[] rowData : xxx) {
            tableModel.addRow(rowData);
        }
    }
}
