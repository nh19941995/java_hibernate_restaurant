package view;

import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import java.awt.*;

public class ViewBooking extends JPanel {
    // button-----------------------------------------------------------------------------------------------------------
    private JButton buttonSelectTable = new JButton("Select table from list");
    private JButton buttonRemoveARow = new JButton("Remove a row");
    private JButton buttonSelectClient = new JButton("Sellect client from list");
    private JButton buttonShowAllBooking = new JButton("Show all booking");
    private JButton buttonSelectMenu = new JButton("Select menu from list");
    private JButton buttonAddNewMenu = new JButton("Creat new menu");
    private JButton buttonSubmitBooking = new JButton("Submit a new booking");
    // label------------------------------------------------------------------------------------------------------------
    private JLabel labelFirstName = new JLabel("Fist name Client: ");
    private JLabel labelFirstNameValue = new JLabel();
    private JLabel labelLastName = new JLabel("Last name Client: ");
    private JLabel labelLastNameValue = new JLabel();
    private JLabel labelDeposit = new JLabel("Deposit: ");
    private JLabel labelDate = new JLabel("Date of Event: ");
    private JLabel labelStartTime = new JLabel("Start time: ");
    private JLabel labelEndTime = new JLabel("End time: ");
    private JLabel labelComment = new JLabel("Comment: ");
    // input -----------------------------------------------------------------------------------------------------------
    private  JTextField inputStartTime = new JTextField();
    private  JTextField inputDeposit = new JTextField();
    private  JTextField inputEndTime = new JTextField();
    private  JTextField inputComment = new JTextField();
    private  JTextField inputDate = new JTextField();

    public ViewBooking() {
        setLayout(new BorderLayout());
        BoderTool left = new BoderTool();
        BoderTool center = new BoderTool();
        left.add(blockLeft());
        add(left,BorderLayout.WEST);
        add(center,BorderLayout.CENTER);
    }
    public JPanel blockLeft(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BoxLayout(jPanel, BoxLayout.Y_AXIS));
        jPanel.add(block_1());
        jPanel.add(block_2());
        jPanel.add(block_3());

        return jPanel;
    }
    public JPanel block_1(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        GridTool grid = new GridTool();
//        JPanel table = new BookingListView();
//        grid.GridAddCustom(table,0,0,0,0,20,20,2);
        // đặt kích thước
//        table.setPreferredSize(new Dimension(400, 200));
        grid.GridAddCustom(buttonSelectMenu,0,1,20,20,20,20,1);
        grid.GridAddCustom(buttonAddNewMenu,1,1,20,20,20,20,1);
        grid.GridAddCustom(buttonSelectTable,0,2,20,20,20,20,1);
        grid.GridAddCustom(buttonRemoveARow,1,2,20,20,20,20,1);

        // đặt kích thước
        buttonSelectTable.setPreferredSize(new Dimension(150, 20));
        buttonSelectMenu.setPreferredSize(new Dimension(150, 20));
        buttonAddNewMenu.setPreferredSize(new Dimension(150, 20));
        buttonRemoveARow.setPreferredSize(new Dimension(150, 20));
        jPanel.add(grid,BorderLayout.CENTER);
        return jPanel;
    }

    public JPanel block_3(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        GridTool grid = new GridTool();
//        JPanel info = new InfoBookingView();
//        grid.GridAddCustom(info,0,0,0,0,20,20,2);
        // đặt kích thước
//        table.setPreferredSize(new Dimension(450, 200));

        grid.GridAddCustom(buttonSelectClient,0,1,20,20,20,20,1);
        grid.GridAddCustom(buttonShowAllBooking,1,1,20,20,20,20,1);
        grid.GridAddCustom(buttonSubmitBooking,0,2,20,20,20,20,2);

        // đặt kích thước
        buttonSelectClient.setPreferredSize(new Dimension(150, 20));
        buttonSubmitBooking.setPreferredSize(new Dimension(150, 20));
        buttonShowAllBooking.setPreferredSize(new Dimension(150, 20));
        buttonSubmitBooking.setPreferredSize(new Dimension(150, 35));
        // Đặt màu cho nền của JButton
        buttonSubmitBooking.setBackground(Color.RED);
        // Đặt màu cho văn bản của JButton
        buttonSubmitBooking.setForeground(Color.WHITE);
        jPanel.add(grid,BorderLayout.CENTER);
        return jPanel;
    }

    public JPanel block_2(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jPanel.setBackground(Color.red);
        GridTool grid = new GridTool();
        grid.GridAddCustom(labelFirstName,0,0,0,10,5,5,1);
        grid.GridAddCustom(labelFirstNameValue,1,0,10,10,5,5,1);

        grid.GridAddCustom(labelLastName,0,1,0,7,5,5,1);
        grid.GridAddCustom(labelLastNameValue,1,1,7,10,5,5,1);

        grid.GridAddCustom(labelDeposit,0,2,47,10,5,5,1);
        grid.GridAddCustom(inputDeposit,1,2,10,10,5,5,1);
        inputDeposit.setPreferredSize(new Dimension(200, 20));

        grid.GridAddCustom(labelDate,0,3,18,10,5,5,1);
        grid.GridAddCustom(inputDate,1,3,10,10,5,5,1);
        inputDate.setPreferredSize(new Dimension(200, 20));

        grid.GridAddCustom(labelStartTime,0,4,35,10,5,5,1);
        grid.GridAddCustom(inputStartTime,1,4,10,10,5,5,1);
        inputStartTime.setPreferredSize(new Dimension(200, 20));

        grid.GridAddCustom(labelEndTime,0,5,38,10,5,5,1);
        grid.GridAddCustom(inputEndTime,1,5,10,10,5,5,1);
        inputEndTime.setPreferredSize(new Dimension(200, 20));
        grid.GridAddCustom(labelComment,0,6,38,10,5,5,1);
        grid.GridAddCustom(inputComment,1,6,10,10,5,5,1);
        inputComment.setPreferredSize(new Dimension(200, 50));

        jPanel.add(grid,BorderLayout.CENTER);
        return  jPanel;
    }
}
