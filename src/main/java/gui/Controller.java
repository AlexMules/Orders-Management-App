package gui;

import gui.view.ClientView;

public class Controller {

    public Controller() {
    }

    public void handleOpenClientWindow() {
        ClientView clientView = new ClientView("Client Menu");
        clientView.setVisible(true);
    }


}
