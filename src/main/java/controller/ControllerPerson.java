package controller;

import dao.PersonDAO;
import model.Person;
import view.ViewPerson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.stream.Stream;

public class ControllerPerson {

    public ControllerPerson(ViewPerson viewPerson) {
        // click get id
        JTable table = viewPerson.getTable();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                    int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                    if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                        String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                        System.out.println("Person: "+ id);
                        viewPerson.setIdSelect(Integer.parseInt(id));
                        getDataToJtext(Integer.parseInt(id),viewPerson);
                    }
                }
            }

        });

        JButton buttonSearch = viewPerson.getButtonSearchPerson();
        // sự kiện thay status
        buttonSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String phone = viewPerson.getInputSearchByPhone().getText();
                if (checkPhone(phone,viewPerson)){
                    search(viewPerson);
                }
            }
        });
    }
    private void search(ViewPerson viewPerson){
        String phone = viewPerson.getInputSearchByPhone().getText();

        if (!phone.isEmpty()) {
            Object[][] originalData = viewPerson.getData();
            // Tạo luồng dữ liệu từ mảng
            Stream<Object[]> dataStream = Arrays.stream(originalData);
            // Sử dụng filter để lọc dữ liệu theo số điện thoại
            Object[][] filteredData = dataStream.filter(row -> row[3] != null && row[3].equals(phone)).toArray(Object[][]::new);

            // Xóa dữ liệu hiện có trong bảng
            viewPerson.getTableModel().setRowCount(0);

            // Thêm từng hàng dữ liệu vào bảng
            for (Object[] row : filteredData) {
                viewPerson.getTableModel().addRow(row);
            }
            // Cập nhật bảng để hiển thị dữ liệu mới
            viewPerson.getTableModel().fireTableDataChanged();
        }

    }
    private boolean checkPhone(String phone,ViewPerson viewPerson){
        int check = 1;
        if (!phone.isEmpty()){
            if (! RegexMatcher.phoneCheck(phone, "").isEmpty()){
                JOptionPane.showMessageDialog(null, RegexMatcher.phoneCheck(phone, "Phone number: "), "Notice", JOptionPane.WARNING_MESSAGE);
                check = 0;
            }
        }else {
            viewPerson.reload();
        }
        return (check==1) ? true : false;
    }
    private void getDataToJtext(int id,ViewPerson viewPerson){
        Person person = PersonDAO.getInstance().getById(id);
        viewPerson.getInputAdress().setText(person.getAddress());
        viewPerson.getInputBirthday().setText(person.getDateOfBirth().toString());
        viewPerson.getInputEmail().setText(person.getEmail());
        viewPerson.getInputLastName().setText(person.getLastName());
        viewPerson.getInputFirstName().setText(person.getName());
        viewPerson.getInputPhone().setText(person.getPhone());
    }

}
