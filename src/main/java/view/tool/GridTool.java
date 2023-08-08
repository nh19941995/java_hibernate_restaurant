
package view.tool;

import javax.swing.*;
import java.awt.*;

public class GridTool extends JPanel {

    public GridTool() {
        setLayout(new GridBagLayout());
    }



    public void GridAdd(Component element, int x, int y,int left,int right,int topBot){
        Insets paddingInput = new Insets(topBot, left, topBot, right);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = paddingInput;
        gbc.gridx = x;
        gbc.gridy = y;

        add(element, gbc); // Thêm lớp trung gian vào JPanel
        // Đặt padding về giá trị mặc định
        gbc.insets = new Insets(0, 0, 0, 0);
    }

    public void GridAddCustom (Component element, int x, int y,int left,int right,int top,int Bot,int width){
        Insets paddingInput = new Insets(top, left, Bot, right);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = paddingInput;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        add(element, gbc); // Thêm lớp trung gian vào JPanel
        // Đặt padding về giá trị mặc định
        gbc.insets = new Insets(0, 0, 0, 0);
    }
    public void GridAddCustom2 (Component element, int x, int y,int left,int right,int top,int Bot,int width,int height){
        Insets paddingInput = new Insets(top, left, Bot, right);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = paddingInput;
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = width;
        gbc.gridheight = height;
        add(element, gbc); // Thêm lớp trung gian vào JPanel
        // Đặt padding về giá trị mặc định
        gbc.insets = new Insets(0, 0, 0, 0);
    }
}

