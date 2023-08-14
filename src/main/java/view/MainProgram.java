package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.ControllerDish;
import controller.ControllerMenu;
import controller.ControllerNewMenu;
import controller.ControllerPerson;
import model.Menu;

import javax.swing.*;
import java.util.ArrayList;

public class MainProgram {
    // data-------------------------------------------------------------------------------------------------------------
    // creat new menu
    private static ArrayList<Menu> newMenus = new ArrayList<>();   // dữ liệu menu tạm


    // view chính-------------------------------------------------------------------------------------------------------
    private static ViewMenu viewMenuMain ;
    private static ViewMenu viewMenuCreatInBooking ;
    private static ViewMenu viewMenuSelectInBooking ;



    private static ViewDish viewDishMain;
    private static ViewDish viewDishInNewMenu;
    private static ViewNewMenu viewNewMenuMain ;
    private static ViewNewMenu viewNewMenuInBooking ;
    private static ViewTable viewTable ;
    private static ViewPerson viewPersonMain;
    private static ViewPerson viewPersonInBooking;
    private static ViewBooking viewBooking;
    private static ViewTransaction viewTransaction;
    private static ViewHome viewHome ;
    // get + set -------------------------------------------------------------------------------------------------------

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

    public static ViewDish getViewDishInNewMenu() {
        return viewDishInNewMenu;
    }
    public static void setViewDishInNewMenu(ViewDish viewDishInNewMenu) {
        MainProgram.viewDishInNewMenu = viewDishInNewMenu;
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
    public static ViewTable getViewTable() {
        return viewTable;
    }
    public static void setViewTable(ViewTable viewTable) {
        MainProgram.viewTable = viewTable;
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

    public static void main(String[] args) {
        try {
            // chuyển giao diện sang giống ios
            UIManager.setLookAndFeel(new FlatLightLaf());
            // Khởi tạo các thành phần tĩnh và áp dụng giao diện
            // Menu
            viewMenuMain = new ViewMenu().ViewCreatNewMenu();
            viewMenuSelectInBooking = new ViewMenu().ViewChoseMenu();
            viewMenuCreatInBooking = new ViewMenu().ViewCreatNewMenu();
            // Dish
            viewDishMain = new ViewDish().ViewMainDish();
            viewDishInNewMenu = new ViewDish().ViewSelectDish();
            // new menu
            viewNewMenuMain = new ViewNewMenu();
            viewNewMenuInBooking = new ViewNewMenu();
            // table
            viewTable = new ViewTable();
            // person
            viewPersonMain = new ViewPerson().ViewPersonMain();
            viewPersonInBooking = new ViewPerson().ViewPersonSelect();
            // booing
            viewBooking = new ViewBooking();
            // transaction
            viewTransaction = new ViewTransaction();
            viewHome = new ViewHome();
        } catch (Exception e){
            e.printStackTrace();
        }
        // set controller cho các view
        // person
        ControllerPerson controllerPersonMain = new ControllerPerson(viewPersonMain);
        ControllerPerson controllerPersonInBooking = new ControllerPerson(viewPersonInBooking);
        // Dish
        ControllerDish controllerDishMain = new ControllerDish(viewDishMain);
        ControllerDish controllerDishInNewMenu = new ControllerDish(viewDishInNewMenu);
        // new menu
        ControllerNewMenu controllerNewMenu = new ControllerNewMenu(viewNewMenuMain);
        ControllerNewMenu controllerNewMenuInBooking = new ControllerNewMenu(viewNewMenuInBooking);
        // menu
        ControllerMenu controllerMenuMain = new ControllerMenu(viewMenuMain);
        ControllerMenu controllerMenuCreatInBooking = new ControllerMenu(viewMenuCreatInBooking);
        ControllerMenu controllerMenuSelectInBooking = new ControllerMenu(viewMenuSelectInBooking);


    }


}


