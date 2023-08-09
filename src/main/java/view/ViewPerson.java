package view;

import controller.ControllerPerson;
import controller.ControllerTime;
import dao.PermissionDAO;
import dao.PersonDAO;
import model.Person;
import view.tool.BoderTool;
import view.tool.GridTool;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ViewPerson extends JPanel {
    // data-------------------------------------------------------------------------------------------------------------
    private Object[][] data;
    private static JTable table = new JTable();
    private DefaultTableModel tableModel;
    private int idSelect;
    // tiêu đề----------------------------------------------------------------------------------------------------------
    private JLabel labelTitle = new JLabel("Person Information");
    private JLabel labelFirstName = new JLabel("First name:");
    private JLabel labelLastName = new JLabel("Last name:");
    private JLabel labelEmail = new JLabel("Email:");
    private JLabel labelAdress = new JLabel("Adress:");
    private JLabel labelBirthday = new JLabel("Birthday:");
    private JLabel labelPhone = new JLabel("Phone number:");
    private JLabel labelPermission = new JLabel("Permission:");
    private JLabel labelfilterByPhone = new JLabel("Search by phone number:");
    // input------------------------------------------------------------------------------------------------------------
    private JTextField inputFirstName = new JTextField();
    private JTextField inputLastName = new JTextField();
    private JTextField inputEmail = new JTextField();
    private JTextField inputAdress = new JTextField();
    private JTextField inputBirthday = new JTextField();
    private JTextField inputPhone = new JTextField();
    private JComboBox<String> SelecType = new JComboBox<String>();
    private JTextField inputSearchByPhone = new JTextField();
    // button-----------------------------------------------------------------------------------------------------------
    private JButton buttonAddPerson = new JButton("Add new person");
    private JButton buttonDeletePerson = new JButton("Delete person");
    private JButton buttonUpdatePerson = new JButton("Update person");
    private JButton buttonSearchPerson = new JButton("Search");
    private JButton buttonSelectPerson = new JButton("Select person");
    //    các giá trị---------------------------------------------------------------------------------------------------
    private String firstName;
    private String lasttName;
    private String email;
    private String address;
    private String  birthday;
    private String phone;
    private String permission;
    private int menuIdSelect;
    private String searchPhone;
    // get + set -------------------------------------------------------------------------------------------------------


    public JComboBox<String> getSelecType() {
        return SelecType;
    }

    public JTextField getInputFirstName() {
        return inputFirstName;
    }

    public JTextField getInputLastName() {
        return inputLastName;
    }

    public JTextField getInputEmail() {
        return inputEmail;
    }

    public JTextField getInputAdress() {
        return inputAdress;
    }

    public JTextField getInputBirthday() {
        return inputBirthday;
    }

    public JTextField getInputPhone() {
        return inputPhone;
    }

    public DefaultTableModel getTableModel() {
        return tableModel;
    }

    public void setTableModel(DefaultTableModel tableModel) {
        this.tableModel = tableModel;
    }

    public JTextField getInputSearchByPhone() {
        return inputSearchByPhone;
    }

    public int getIdSelect() {
        return idSelect;
    }

    public void setIdSelect(int idSelect) {
        this.idSelect = idSelect;
    }

    public static JTable getTable() {
        return table;
    }

    public static void setTable(JTable table) {
        ViewPerson.table = table;
    }

    public JButton getButtonAddPerson() {
        return buttonAddPerson;
    }

    public JButton getButtonDeletePerson() {
        return buttonDeletePerson;
    }

    public JButton getButtonUpdatePerson() {
        return buttonUpdatePerson;
    }

    public JButton getButtonSearchPerson() {
        return buttonSearchPerson;
    }

    public JButton getButtonSelectPerson() {
        return buttonSelectPerson;
    }

    public Object[][] getData() {
        return data;
    }

    public void setData(Object[][] data) {
        this.data = data;
    }

    public ViewPerson() {
        setLayout(new BorderLayout());
        new ControllerPerson(this);

    }

    public JPanel ViewPersonMain(){
        add(blockTable(),BorderLayout.CENTER);
        add(blockAddPerson(),BorderLayout.SOUTH);
        add(blockSearch(),BorderLayout.NORTH);
        return this;
    }

    private JPanel blockSearch(){
        BoderTool jPanel = new BoderTool();
        jPanel.setPreferredSize(new Dimension(150, 75));
        GridTool grid = new GridTool();
        grid.GridAddCustom(labelfilterByPhone,0,0,20,20,5,5,1);
        grid.GridAddCustom(inputSearchByPhone,0,1,20,20,5,5,1);
        GridTool grid2 = new GridTool();
        grid2.GridAddCustom(buttonSearchPerson,0,0,20,20,5,5,1);
        buttonSearchPerson.setPreferredSize(new Dimension(150, 35));

        jPanel.add(grid,BorderLayout.CENTER);
        jPanel.add(grid2,BorderLayout.EAST);
        return jPanel;
    }

    private JPanel blockAddPerson(){
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        // đặt kích thước
        // cột 1
        inputFirstName.setPreferredSize(new Dimension(200, 20));
        inputEmail.setPreferredSize(new Dimension(200, 20));
        // cột 2
        inputLastName.setPreferredSize(new Dimension(100, 20));
        SelecType.setPreferredSize(new Dimension(100, 20));
        // cột 3
        inputBirthday.setPreferredSize(new Dimension(100, 20));
        inputPhone.setPreferredSize(new Dimension(100, 20));
        // cột 4 + 5
        inputSearchByPhone.setPreferredSize(new Dimension(138, 20));
        inputAdress.setPreferredSize(new Dimension(230, 20));
        buttonAddPerson.setPreferredSize(new Dimension(150, 20));
        buttonDeletePerson.setPreferredSize(new Dimension(150, 20));
        buttonUpdatePerson.setPreferredSize(new Dimension(150, 20));
        buttonSelectPerson.setPreferredSize(new Dimension(150, 35));
        // Đặt màu cho nền của JButton
        buttonSelectPerson.setBackground(Color.RED);
        // Đặt màu cho văn bản của JButton
        buttonSelectPerson.setForeground(Color.WHITE);
        // bố trí các phần tử
        GridTool grid = new GridTool();
        // cột 1
        grid.GridAdd(labelFirstName,0,0,10,10,5);
        grid.GridAddCustom(inputFirstName,0,1,10,10,5,5,1);
        grid.GridAddCustom(labelEmail,0,2,10,10,5,5,1);
        grid.GridAddCustom(inputEmail,0,3,10,10,5,15,1);
        // cột 2
        grid.GridAdd(labelLastName,1,0,10,10,5);
        grid.GridAddCustom(inputLastName,1,1,10,10,5,5,1);
        grid.GridAddCustom(labelPermission,1,2,10,10,5,5,1);
        grid.GridAddCustom(SelecType,1,3,10,10,5,15,1);
        // cột 3
        grid.GridAdd(labelBirthday,2,0,10,10,5);
        grid.GridAdd(inputBirthday,2,1,10,10,5);
        // cột 4
        grid.GridAdd(labelPhone,3,0,10,10,5);
        grid.GridAddCustom(inputPhone,3,1,10,10,5,5,1);
        // cột 4
//        grid.GridAddCustom(labelfilterByPhone,3,0,10,10,5,5,2);
//        grid.GridAddCustom(inputfilterByPhone,3,1,10,10,10,10,1);
        // cột 3-5
//        grid.GridAddCustom(buttonSearchPerson,4,1,10,10,5,5,1);
        grid.GridAddCustom(labelAdress,2,2,10,10,5,5,2);
        grid.GridAddCustom(inputAdress,2,3,10,10,5,15,2);
        // cột 5
        grid.GridAdd(buttonDeletePerson,5,1,20,10,3);
        grid.GridAdd(buttonUpdatePerson,5,2,20,10,3);
        grid.GridAddCustom(buttonAddPerson,5,3,20,10,3,15,1);
        // thêm data cho boder box
        String[] selectList = PermissionDAO.getInstance().getAll().stream()
                .filter(s->s.getFlag()>0)
                .map(s -> s.getPermissionName())
                .toArray(String[]::new);
        SelecType.setModel(new DefaultComboBoxModel<>(selectList));
        // nút select
        GridTool gridAddPerson = new GridTool();
        gridAddPerson.GridAddCustom(buttonSelectPerson,0,1,10,20,5,5,2);

        // thêm toàn bộ các phần tử vào layout chính
        jPanel.add(grid,BorderLayout.CENTER);
        jPanel.add(gridAddPerson,BorderLayout.EAST);
        return jPanel;
    }

    private JPanel blockTable() {
        BoderTool jpanel = new BoderTool();
        DefaultTableModel model = new DefaultTableModel(
                new Object [][] {
                },
                new String [] {"ID", "Fisrt name","Last name", "Phone number","Address", "Date of birth", "Email", "Date created", "Permission"}
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
        List<Person> personList = PersonDAO.getInstance().getAll();
        Object[][] data = personList.stream()
                .filter(s->s.getFlag()>0)
                .map(s -> new Object[]{
                        s.getId(),
                        s.getName(),
                        s.getLastName(),
                        s.getPhone(),
                        s.getAddress(),
                        s.getDateOfBirth().toString(),
                        s.getEmail(),
                        ControllerTime.formatDateTime(2,s.getDateCreat()),
                        s.getPermission().getPermissionName(),
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
        jpanel.add(scrollPane,BorderLayout.CENTER);
        return jpanel;
    }

    public void reload(){
        // Clear existing data
        tableModel.setRowCount(0);
        // lấy dữ liệu từ sever
        List<Person> personList = PersonDAO.getInstance().getAll();
        Object[][] data = personList.stream()
                .filter(s->s.getFlag()>0)
                .map(s -> new Object[]{
                        s.getId(),
                        s.getName(),
                        s.getLastName(),
                        s.getPhone(),
                        s.getAddress(),
                        s.getDateOfBirth().toString(),
                        s.getEmail(),
                        ControllerTime.formatDateTime(2,s.getDateCreat()),
                        s.getPermission().getPermissionName(),
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
