package controller;

import dao.PermissionDAO;
import dao.PersonDAO;
import model.Permission;
import model.Person;
import view.ViewPerson;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDateTime;
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

        // sự kiện thay status
        JButton buttonSearch = viewPerson.getButtonSearchPerson();
        buttonSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("+++++++++++++++++++:"+viewPerson.getInputBirthday().getText());
                String phone = viewPerson.getInputSearchByPhone().getText();
                if (checkPhone(phone,viewPerson)){
                    search(viewPerson);
                }
            }
        });

        // sự kiện creat
        JButton buttonCreat = viewPerson.getButtonAddPerson();
        buttonCreat.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (check(viewPerson)){
                    creatNew(viewPerson);
                    getNullToJtext(viewPerson);
                    viewPerson.reload();
                }
            }
        });

        // sự kiện delete
        JButton buttonDelete = viewPerson.getButtonDeletePerson();
        buttonDelete.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = viewPerson.getIdSelect();
                getNullToJtext(viewPerson);
                delete(viewPerson,id);
            }
        });

        // sự kiện update
        JButton buttonUpdate = viewPerson.getButtonUpdatePerson();
        buttonUpdate.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int id = viewPerson.getIdSelect();
                update(viewPerson,id);
                getNullToJtext(viewPerson);

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
    private void getNullToJtext(ViewPerson viewPerson){
        viewPerson.getInputAdress().setText("");
        viewPerson.getInputBirthday().setText("");
        viewPerson.getInputEmail().setText("");
        viewPerson.getInputLastName().setText("");
        viewPerson.getInputFirstName().setText("");
        viewPerson.getInputPhone().setText("");
    }
    private void creatNew(ViewPerson viewPerson){
        String permissionString = (String) viewPerson.getSelecType().getSelectedItem();
        Permission permission = PermissionDAO.getInstance().getByString(permissionString);
        Person person = new Person();
        person.setAddress(viewPerson.getInputAdress().getText());
        person.setDateOfBirth(ControllerTime.creatLocalDateByString(viewPerson.getInputBirthday().getText()));
        person.setEmail(viewPerson.getInputEmail().getText());
        person.setLastName(viewPerson.getInputLastName().getText());
        person.setName(viewPerson.getInputFirstName().getText());
        person.setPhone(viewPerson.getInputPhone().getText());
        person.setUsername(viewPerson.getInputPhone().getText());
        person.setPermission(permission);
        person.setDateCreat(LocalDateTime.now());
        person.setDateUpdate(LocalDateTime.now());
        person.setFlag(1);
        PersonDAO.getInstance().insert(person);
    }
    private void delete(ViewPerson viewPerson,int id){
        Person person = PersonDAO.getInstance().getById(id);
        person.setFlag(0);
        PersonDAO.getInstance().update(person);
        viewPerson.reload();
    }
    private void update(ViewPerson viewPerson,int id){

        Person person = PersonDAO.getInstance().getById(id);
        String permissionString = (String) viewPerson.getSelecType().getSelectedItem();
        Permission permission = PermissionDAO.getInstance().getByString(permissionString);
        person.setAddress(viewPerson.getInputAdress().getText());
        person.setDateOfBirth(ControllerTime.creatLocalDateByString(viewPerson.getInputBirthday().getText()));
        person.setEmail(viewPerson.getInputEmail().getText());
        person.setLastName(viewPerson.getInputLastName().getText());
        person.setName(viewPerson.getInputFirstName().getText());
        person.setPhone(viewPerson.getInputPhone().getText());
        person.setUsername(viewPerson.getInputPhone().getText());
        person.setPermission(permission);
        person.setDateCreat(LocalDateTime.now());
        person.setDateUpdate(LocalDateTime.now());
        person.setFlag(1);
        PersonDAO.getInstance().update(person);
        viewPerson.reload();
    }
    private boolean check(ViewPerson viewPerson){

        String Adress =  viewPerson.getInputAdress().getText();
        String dob = viewPerson.getInputBirthday().getText();
        String email = viewPerson.getInputEmail().getText();
        String lastName = viewPerson.getInputLastName().getText();
        String firstName = viewPerson.getInputFirstName().getText();
        String phone = viewPerson.getInputPhone().getText();
        int check = 1;
        if (Adress.isEmpty()||dob.isEmpty()||email.isEmpty()||lastName.isEmpty()||firstName.isEmpty()||phone.isEmpty()){
            if (check==1){
                JOptionPane.showMessageDialog(null, "You must fill in all the required information before proceeding to make a reservation !", "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check =0;
        }

        if (!RegexMatcher.dayCheck(dob,"").equals("")||!RegexMatcher.emailCheck(email,"").equals("")||!RegexMatcher.phoneCheck(phone,"").equals("")){
            if (check ==1){
                JOptionPane.showMessageDialog(null,
                        RegexMatcher.dayCheck(dob, "Date of birth: ")+
                        RegexMatcher.emailCheck(email, "Email: ") +
                        RegexMatcher.phoneCheck(phone, "Phone number: ")
                        , "Notice", JOptionPane.WARNING_MESSAGE);
            }
            check = 0;
        }
        return (check==1) ? true : false;
    }

}
