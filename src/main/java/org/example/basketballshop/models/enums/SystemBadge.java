package org.example.basketballshop.models.enums;

public enum SystemBadge {
    NOVICE("Новичок"),
    BOLLER("Боллер"),
    DELIVERY("Знаток доставки");

    private final String title;

    SystemBadge(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}