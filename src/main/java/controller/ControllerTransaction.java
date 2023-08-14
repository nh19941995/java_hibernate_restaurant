package controller;

import dao.PersonDAO;
import dao.TransactionDAO;
import dao.TransactionsTypeDAO;
import model.Person;
import model.Transaction;
import view.MainProgram;
import view.ViewTransaction;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.Instant;
import java.time.LocalDateTime;

public class ControllerTransaction {
    private static JButton delete = MainProgram.getViewTransaction().getButtonDelete();
    private static JTable table = MainProgram.getViewTransaction().getTable();

    public ControllerTransaction() {
        creatTransaction();
    }
    public static void creatTransaction(){
        ViewTransaction viewTransaction = MainProgram.getViewTransaction();
        JButton buttonNewTransaction = viewTransaction.getButtonCreatTransaction();

        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // click get id
                table.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getClickCount() == 1) { // Kiểm tra nếu chỉ là một lần click chuột (clickCount = 1)
                            int row = table.getSelectedRow(); // Lấy chỉ số dòng đã được chọn
                            if (row != -1) { // Kiểm tra xem có dòng nào được chọn không (-1 nghĩa là không có dòng nào được chọn)
                                String id = table.getValueAt(row, 0).toString(); // Lấy giá trị từ ô ở cột đầu tiên (cột ID) của dòng đã chọn
                                System.out.println("Transaction : "+ id);
                                Transaction transaction = TransactionDAO.getInstance().getById(Integer.parseInt(id));
                                transaction.setFlag(0);
                                TransactionDAO.getInstance().update(transaction);
                            }else {
                                JOptionPane.showMessageDialog(null, "Please select the row you want to delete !", "Notice", JOptionPane.WARNING_MESSAGE);

                            }
                        }
                    }


                });
                viewTransaction.loadData();
            }
        });

        buttonNewTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transaction newTransaction = new Transaction();
                int id = ControllerBooking.getIdPerson();
                String Stringvalue = viewTransaction.getInputValue().getText();
                String type = (String) viewTransaction.getSelecType().getSelectedItem();
                String comment = viewTransaction.getInputComment().getText();
                String day = viewTransaction.getInputDate().getText();
                String hour = viewTransaction.getInputTime().getText();

                int check = 1;
                if (hour.isEmpty()||Stringvalue.isEmpty()||comment.isEmpty()||id==0||day.isEmpty()){
                    if (check==1){
                        JOptionPane.showMessageDialog(null, "You must fill in all the required information before proceeding to make a reservation !", "Notice", JOptionPane.WARNING_MESSAGE);
                    }
                    check =0;
                }
                if (!RegexMatcher.hourCheck(hour, "").equals("") ||!RegexMatcher.dayCheck(day,"").equals(""))
                {
                    if (check ==1){
                        JOptionPane.showMessageDialog(null,
                                RegexMatcher.hourCheck(hour, "End time: ")+
                                        RegexMatcher.dayCheck(day,"Date of event: "),
                                "Notice", JOptionPane.WARNING_MESSAGE);
                    }
                    check = 0;
                }
                if (!Stringvalue.equals("")){
                    if (!RegexMatcher.numberCheck(Stringvalue,"").equals("")){
                        if (check ==1){
                            JOptionPane.showMessageDialog(null, RegexMatcher.numberCheck(Stringvalue, "Deposit: "), "Notice", JOptionPane.WARNING_MESSAGE);
                        }
                        check = 0;
                    }
                }

                if (check==1 ){
                    Person person = PersonDAO.getInstance().getById(id);
                    Double value = Double.parseDouble(Stringvalue);
                    LocalDateTime instant = ControllerTime.parseDateTime(hour,day);
                    newTransaction.setPerson(person);
                    newTransaction.setQuantity(value);
                    newTransaction.setComment(comment);
                    newTransaction.setType(TransactionsTypeDAO.getInstance().getByName(type));
                    newTransaction.setDateCreat(instant);
                    newTransaction.setFlag(1);
                    TransactionDAO.getInstance().insert(newTransaction);
                    viewTransaction.loadData();
                    setNullData();
                }

            }
        });
    }

    public static void setNullData(){
        ViewTransaction viewTransaction = MainProgram.getViewTransaction();

        viewTransaction.getFirstName().setText(""); // Đặt giá trị cho JLabel
        viewTransaction.getLastName().setText("");
        viewTransaction.getPhone().setText("");

        viewTransaction.getFirstName().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
        viewTransaction.getLastName().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới
        viewTransaction.getPhone().repaint(); // Thông báo cho nhãn vẽ lại để hiển thị nội dung mới

        viewTransaction.getInputValue().setText("");
        viewTransaction.getInputTime().setText("");
        viewTransaction.getInputDate().setText("");
        viewTransaction.getInputFilterPhone().setText("");
        viewTransaction.getInputComment().setText("");
    }
}
