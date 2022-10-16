package ua.shpp.properties;

public class Human implements HumanInterface, Constants{
    private String name;
    public Human(String name) {
        this.name = name;
    }
    public void printName() {
        log.info("\tMy name is {}. I am Instance {} class\n", name, this.getClass().getName());
    }
}