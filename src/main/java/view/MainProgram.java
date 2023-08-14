package view;

import com.formdev.flatlaf.FlatLightLaf;
import controller.ControllerDish;
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
    private static ViewMenu viewMenu ;
    private static ViewDish viewDishMain;
    private static ViewDish viewDishInNewMenu;
    private static ViewNewMenu viewNewMenu ;
    private static ViewTable viewTable ;
    private static ViewPerson viewPersonMain;
    private static ViewPerson viewPersonInBooking;
    private static ViewBooking viewBooking;
    private static ViewTransaction viewTransaction;
    private static ViewHome viewHome ;
    // get + set -------------------------------------------------------------------------------------------------------
    public static ViewDish getViewDishInNewMenu() {
        return viewDishInNewMenu;
    }
    public static void setViewDishInNewMenu(ViewDish viewDishInNewMenu) {
        MainProgram.viewDishInNewMenu = viewDishInNewMenu;
    }
    public static ViewMenu getViewMenu() {
        return viewMenu;
    }
    public static void setViewMenu(ViewMenu viewMenu) {
        MainProgram.viewMenu = viewMenu;
    }
    public static ViewDish getViewDishMain() {
        return viewDishMain;
    }
    public static void setViewDishMain(ViewDish viewDishMain) {
        MainProgram.viewDishMain = viewDishMain;
    }
    public static ViewNewMenu getViewNewMenu() {
        return viewNewMenu;
    }
    public static void setViewNewMenu(ViewNewMenu viewNewMenu) {
        MainProgram.viewNewMenu = viewNewMenu;
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
            viewMenu = new ViewMenu();
            // Dish
            viewDishMain = new ViewDish().ViewMainDish();
            viewDishInNewMenu = new ViewDish().ViewSelectDish();

            viewNewMenu = new ViewNewMenu();
            viewTable = new ViewTable();
            viewPersonMain = new ViewPerson().ViewPersonMain();
            viewPersonInBooking = new ViewPerson().ViewPersonSelect();
            viewBooking = new ViewBooking();
            viewTransaction = new ViewTransaction();
            viewHome = new ViewHome();
        } catch (Exception e){
            e.printStackTrace();
        }
        // set controller cho các view
        ControllerPerson controllerPersonMain = new ControllerPerson(viewPersonMain);
        ControllerPerson controllerPersonInBooking = new ControllerPerson(viewPersonInBooking);
        ControllerDish controllerDishMain = new ControllerDish(viewDishMain);
        ControllerDish controllerDishInNewMenu = new ControllerDish(viewDishInNewMenu);
        ControllerNewMenu controllerNewMenu = new ControllerNewMenu(viewNewMenu);

    }


}


