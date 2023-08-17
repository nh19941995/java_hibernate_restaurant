package view;

import controller.ControllerBooking;
import view.tool.BoderTool;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ViewTempBooking extends JPanel{
    // data ------------------------------------------------------------------------------------------------------------
    private Object lockObject = new Object();
    private JTable table = new JTable();
    private int tempId;

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    private DefaultTableModel tableModel;
    private Object[][] data;

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
    public ViewTempBooking() {
        setLayout(new BorderLayout());
        this.add(blockTable(), BorderLayout.CENTER);


        // click đẩy id vào tempId
        JTable table = getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        System.out.println("ViewTempBooking tempId : "+ id);
                        setTempId(Integer.parseInt(id));
                    }
                }
            }

        });
    }

    private JPanel blockTable() {
        BoderTool boderTool = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID","Table ID", "Table type","Menu name","Status"}
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

        Object[][] data = ControllerBooking.getBookings().stream().map(
                s -> new Object[]{
                        null,
                        s.getTable() != null ? s.getTable().getId() : "", // Thay thế bằng giá trị mặc định nếu s.getTable() là null
                        s.getTable() != null ? s.getTable().getType().getName() : "", // Thay thế bằng giá trị mặc định nếu s.getTable() là null
                        s.getMenuName() != null ? s.getMenuName().getName() : "", // Thay thế bằng giá trị mặc định nếu s.getMenuName() là null
                        s.getTable() != null ? s.getTable().getStatus().getName() : "", // Thay thế bằng giá trị mặc định nếu s.getTable() là null
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
        table.getColumnModel().getColumn(0).setMinWidth(25); // Cột ID
        table.getColumnModel().getColumn(0).setMaxWidth(35); // Cột ID
        table.getColumnModel().getColumn(1).setMaxWidth(50);
        table.getColumnModel().getColumn(2).setMaxWidth(60);
        table.getColumnModel().getColumn(4).setMaxWidth(50);
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
        Object[][] data = ControllerBooking.getBookings().stream().map(
                s -> new Object[]{
                        null,
                        s.getTable() != null ? s.getTable().getId() : "", // Thay thế bằng giá trị mặc định nếu s.getTable() là null
                        s.getTable() != null ? s.getTable().getType().getName() : "", // Thay thế bằng giá trị mặc định nếu s.getTable() là null
                        s.getMenuName() != null ? s.getMenuName().getName() : "", // Thay thế bằng giá trị mặc định nếu s.getMenuName() là null
                        s.getTable() != null ? s.getTable().getStatus().getName() : "", // Thay thế bằng giá trị mặc định nếu s.getTable() là null
                }
        ).toArray(Object[][]::new);
        int n = 1;
        for (Object[] a : data) {
            a[0] = n++;
        }
        setData(data);
        // Thêm dữ liệu mới vào bảng
        for (Object[] rowData : data) {
            model.addRow(rowData);
        }
        // Thông báo cho bảng biết rằng dữ liệu đã thay đổi để nó vẽ lại giao diện
        model.fireTableDataChanged();
    }
}
