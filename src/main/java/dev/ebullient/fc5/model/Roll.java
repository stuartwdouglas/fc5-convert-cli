package dev.ebullient.fc5.model;

public class Roll {
    public static final Roll NONE = new Roll("");

    final String textContent;

    public Roll(String textContent) {
        this.textContent = textContent;
    }
    
}
