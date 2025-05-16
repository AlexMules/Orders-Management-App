package app;

import gui.view.MainView;
import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new MainView("Orders Management App");
        frame.setVisible(true);
    }
}

