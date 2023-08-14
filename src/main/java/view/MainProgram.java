package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.*;
import model.Menu;

import javax.swing.*;
import java.util.ArrayList;

public class MainProgram {
    // data-------------------------------------------------------------------------------------------------------------
    // creat new menu
    private static ArrayList<Menu> newMenus = new ArrayList<>();   // dữ liệu menu tạm


    // view chính-------------------------------------------------------------------------------------------------------
    // menu
    private static ViewMenu viewMenuMain ;
    private static ViewMenu viewMenuCreatInBooking ;
    private static ViewMenu viewMenuSelectInBooking ;
    // dish
    private static ViewDish viewDishMain;  // tab chính của dish
    private static ViewDish viewDishInNewMenuInBooking;  // bảng dish trong bảng newMenu trong tab booking
    private static ViewDish viewDishInNewMenuMain;  // bảng dish trong tab newMenu
    // new menu
    private static ViewNewMenu viewNewMenuMain ;
    private static ViewNewMenu viewNewMenuInBooking ;
    // table
    private static ViewTable viewTableMain ;
    private static ViewTable viewTableInBooking ;
    // person
    private static ViewPerson viewPersonMain;
    private static ViewPerson viewPersonInBooking;
    // booking
    private static ViewBooking viewBooking;
    private static ViewListBooking viewListBooking;
    private static ViewTransaction viewTransaction;
    private static ViewHome viewHome ;
    // get + set -------------------------------------------------------------------------------------------------------
    public static ViewTable getViewTableInBooking() {
        return viewTableInBooking;
    }
    public static void setViewTableInBooking(ViewTable viewTableInBooking) {
        MainProgram.viewTableInBooking = viewTableInBooking;
    }
    public static ViewListBooking getViewListBooking() {
        return viewListBooking;
    }
    public static void setViewListBooking(ViewListBooking viewListBooking) {
        MainProgram.viewListBooking = viewListBooking;
    }
    public static ViewDish getViewDishInNewMenuMain() {
        return viewDishInNewMenuMain;
    }

    public static void setViewDishInNewMenuMain(ViewDish viewDishInNewMenuMain) {
        MainProgram.viewDishInNewMenuMain = viewDishInNewMenuMain;
    }
    public static ViewNewMenu getViewNewMenuInBooking() {
        return viewNewMenuInBooking;
    }
    public static void setViewNewMenuInBooking(ViewNewMenu viewNewMenuInBooking) {
        MainProgram.viewNewMenuInBooking = viewNewMenuInBooking;
    }
    public static ViewMenu getViewMenuCreatInBooking() {
        return viewMenuCreatInBooking;
    }
    public static void setViewMenuCreatInBooking(ViewMenu viewMenuCreatInBooking) {
        MainProgram.viewMenuCreatInBooking = viewMenuCreatInBooking;
    }
    public static ViewMenu getViewMenuSelectInBooking() {
        return viewMenuSelectInBooking;
    }
    public static void setViewMenuSelectInBooking(ViewMenu viewMenuSelectInBooking) {
        MainProgram.viewMenuSelectInBooking = viewMenuSelectInBooking;
    }
    public static ViewDish getViewDishInNewMenuInBooking() {
        return viewDishInNewMenuInBooking;
    }
    public static void setViewDishInNewMenuInBooking(ViewDish viewDishInNewMenuInBooking) {
        MainProgram.viewDishInNewMenuInBooking = viewDishInNewMenuInBooking;
    }
    public static ViewMenu getViewMenuMain() {
        return viewMenuMain;
    }
    public static void setViewMenuMain(ViewMenu viewMenuMain) {
        MainProgram.viewMenuMain = viewMenuMain;
    }
    public static ViewDish getViewDishMain() {
        return viewDishMain;
    }
    public static void setViewDishMain(ViewDish viewDishMain) {
        MainProgram.viewDishMain = viewDishMain;
    }
    public static ViewNewMenu getViewNewMenuMain() {
        return viewNewMenuMain;
    }
    public static void setViewNewMenuMain(ViewNewMenu viewNewMenuMain) {
        MainProgram.viewNewMenuMain = viewNewMenuMain;
    }

    public static ViewTable getViewTableMain() {
        return viewTableMain;
    }

    public static void setViewTableMain(ViewTable viewTableMain) {
        MainProgram.viewTableMain = viewTableMain;
    }

    public static ViewPerson getViewPersonMain() {
        return viewPersonMain;
    }
    public static void setViewPersonMain(ViewPerson viewPersonMain) {
        MainProgram.viewPersonMain = viewPersonMain;
    }
    public static ViewPerson getViewPersonInBooking() {
        return viewPersonInBooking;
    }
    public static void setViewPersonInBooking(ViewPerson viewPersonInBooking) {
        MainProgram.viewPersonInBooking = viewPersonInBooking;
    }
    public static ViewBooking getViewBooking() {
        return viewBooking;
    }
    public static void setViewBooking(ViewBooking viewBooking) {
        MainProgram.viewBooking = viewBooking;
    }
    public static ViewTransaction getViewTransaction() {
        return viewTransaction;
    }
    public static void setViewTransaction(ViewTransaction viewTransaction) {
        MainProgram.viewTransaction = viewTransaction;
    }
    public static ViewHome getViewHome() {
        return viewHome;
    }
    public static void setViewHome(ViewHome viewHome) {
        MainProgram.viewHome = viewHome;
    }
    public static ArrayList<Menu> getNewMenus() {
        return newMenus;
    }
    public static void setNewMenus(ArrayList<Menu> newMenus) {
        MainProgram.newMenus = newMenus;
    }

    public MainProgram() {

    }

    public static void main(String[] args) {
        try {
            // chuyển giao diện sang giống ios
            UIManager.setLookAndFeel(new FlatLightLaf());
            // Khởi tạo các thành phần tĩnh và áp dụng giao diện
            // Menu
            viewMenuMain = new ViewMenu().ViewChoseMenu();
            viewMenuSelectInBooking = new ViewMenu().ViewChoseMenu();
            viewMenuCreatInBooking = new ViewMenu().ViewCreatNewMenu();
            // Dish
            viewDishMain = new ViewDish().ViewMainDish();
            viewDishInNewMenuInBooking = new ViewDish().ViewSelectDish();
            viewDishInNewMenuMain = new ViewDish().ViewSelectDish();
            // new menu
            viewNewMenuMain = new ViewNewMenu().ViewNewMenuMain();
            viewNewMenuInBooking = new ViewNewMenu().ViewNewMenuInBooing();
            // table
            viewTableMain = new ViewTable();
            viewTableInBooking = new ViewTable().viewTableSelectTable();
            // person
            viewPersonMain = new ViewPerson().ViewPersonMain();
            viewPersonInBooking = new ViewPerson().ViewPersonSelect();
            // booing
            viewBooking = new ViewBooking();
            // list booking
            viewListBooking = new ViewListBooking();
            setViewBooking(viewBooking);
            // transaction
            viewTransaction = new ViewTransaction();
            viewHome = new ViewHome();

        } catch (Exception e){
            e.printStackTrace();
        }

//        if (viewBooking!= null){
//            System.out.println("main không null nhé");
//        }else {
//            System.out.println("null rồi bà con ơi");
//        }
        // set controller cho các view
        // person
        ControllerPerson controllerPersonMain = new ControllerPerson(viewPersonMain);
        ControllerPerson controllerPersonInBooking = new ControllerPerson(viewPersonInBooking);
        // Dish
        ControllerDish controllerDishMain = new ControllerDish(viewDishMain);
        ControllerDish controllerDishInNewMenuInBooking = new ControllerDish(viewDishInNewMenuInBooking);
        ControllerDish controllerDishInNewMenuMain = new ControllerDish(viewDishInNewMenuMain);
        // new menu
        ControllerNewMenu controllerNewMenu = new ControllerNewMenu(viewNewMenuMain);
        ControllerNewMenu controllerNewMenuInBooking = new ControllerNewMenu(viewNewMenuInBooking);
        // menu
        ControllerMenu controllerMenuMain = new ControllerMenu(viewMenuMain);
        ControllerMenu controllerMenuCreatInBooking = new ControllerMenu(viewMenuCreatInBooking);
        ControllerMenu controllerMenuSelectInBooking = new ControllerMenu(viewMenuSelectInBooking);
        // booking
        ControllerBooking controllerBooking = new ControllerBooking(viewBooking);
        // table
        ControllerTable controllerTableMain = new ControllerTable(viewTableMain);
        ControllerTable controllerTableInBooking = new ControllerTable(viewTableInBooking);




    }


}


