package app;

import gui.view.MainView;

import javax.swing.*;
import java.util.logging.Logger;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        JFrame frame = new MainView("Orders Management App");
        frame.setVisible(true);
    }
}

