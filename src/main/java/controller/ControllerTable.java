package controller;

import dao.TableListDAO;
import dao.TableStatusDAO;
import model.TableList;
import model.TableStatus;
import view.ViewTable;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.Stream;

public class ControllerTable {
    public ControllerTable(ViewTable viewTable) {
        // sự kiện tìm kiếm
        JButton buttonSearch = viewTable.getButtonSearch();
        buttonSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (checkInput(viewTable)){
                    search(viewTable);
                }
            }
        });

        // click get id
        JTable table = viewTable.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        System.out.println("Đang chọn bàn có id là: "+ id);
                        viewTable.setIdSelect(Integer.parseInt(id));

                    }else {
                        JOptionPane.showMessageDialog(null, "Please select the row you want to delete !", "Notice", JOptionPane.WARNING_MESSAGE);

                    }
                }
            }

        });

        JButton buttonChangeStatus = viewTable.getButtonChangeStatus();
        // sự kiện thay status
        buttonChangeStatus.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String status = (String)viewTable.getSelecStatusTable().getSelectedItem();
                int idTable =  viewTable.getIdSelect();
                changeStatus(idTable,status);
                // Cập nhật bảng để hiển thị dữ liệu mới
                viewTable.reload();
                viewTable.getTable().revalidate();
            }
        });
    }

    private boolean checkInput(ViewTable viewTable){
        String seatingCapacityString = viewTable.getInputFilterBySeatingCapacity().getText();
        String dateString = viewTable.getInputFilterByDate().getText();
        System.out.println("Check input table search !");
        int check = 1;

        if (!seatingCapacityString.isEmpty()){
            if (!RegexMatcher.numberCheck(seatingCapacityString,"").equals("")){
                if (check ==1){
                    JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(seatingCapacityString, "Seating capacity: "), "Notice", JOptionPane.WARNING_MESSAGE);
                }
                check = 0;
            }
        }
        if (!dateString.isEmpty()){
            if (!RegexMatcher.dayCheck(dateString,"").equals("")){
                if (check ==1){
                    JOptionPane.showMessageDialog(null, RegexMatcher.dayCheck(dateString,"Date: "), "Notice", JOptionPane.WARNING_MESSAGE);
                }
                check = 0;
            }
        }
        return (check==1) ? true : false;
    }

    public void changeStatus(int id,String status){
        TableStatus tableStatus = TableStatusDAO.getInstance().getByStringName(status);
        TableList tableList = TableListDAO.getInstance().getById(id);
        tableList.setStatus(tableStatus);
        TableListDAO.getInstance().update(tableList);

    }

    public void search(ViewTable viewTable) {
        Object lockObject = new Object();
        String seatingCapacityString = viewTable.getInputFilterBySeatingCapacity().getText();
        String dateString = viewTable.getInputFilterByDate().getText();
        String type = (String) viewTable.getSelecTypeSearch().getSelectedItem();
        Object[][] arr;
        Object[][] originData = viewTable.getData();

        // Tạo luồng dữ liệu từ mảng gốc
        Stream<Object[]> dataStream = Arrays.stream(originData);

        // Áp dụng các bộ lọc một cách tuần tự
        if (!type.equals("")) {
            dataStream = dataStream.filter(row -> row[1].equals(type));
        }

        if (!seatingCapacityString.equals("")) {
            dataStream = dataStream.filter(row -> (row[2].toString()).equals(seatingCapacityString));
        }

        if (!dateString.equals("")) {
            dataStream = dataStream.filter(row -> row[5].toString().contains(dateString));
        }

        // Sau khi đã áp dụng tất cả các bộ lọc, chuyển đổi luồng dữ liệu thành mảng kết quả
        arr = dataStream.toArray(Object[][]::new);
        // Xóa dữ liệu hiện có trong bảng
        viewTable.getTableModel().setRowCount(0);

        // Thêm từng hàng dữ liệu vào bảng
        for (Object[] row : arr) {
            viewTable.getTableModel().addRow(row);
        }
        // Cập nhật bảng để hiển thị dữ liệu mới
        viewTable.getTableModel().fireTableDataChanged();
    }
}
