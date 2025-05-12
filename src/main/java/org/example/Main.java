package org.example;

import access.ClientDAO;
import access.OrderDAO;
import access.ProductDAO;
import gui.view.MainView;
import logic.ClientBLL;
import logic.OrderBLL;
import logic.ProductBLL;
import model.Client;
import model.Order;
import model.Product;

import javax.swing.*;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    protected static final Logger LOGGER = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {

        JFrame frame = new MainView("Orders Management App");
        frame.setVisible(true);
    }
}

