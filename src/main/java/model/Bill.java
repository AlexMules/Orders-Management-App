package model;

public record Bill(int id, int idOrder, String clientName, String productName, int quantity, double price){}
