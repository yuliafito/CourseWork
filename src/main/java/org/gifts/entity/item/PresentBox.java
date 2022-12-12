package org.gifts.entity.item;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class PresentBox {
    private static PresentBox presentBox;
    private int id;
    private PresentShape shape;
    private String color;
    private List<Sweet> components;

    public static PresentBox getPresentBox(int id,  PresentShape shape, String color, List<Sweet> components) {
        if (presentBox == null) {
            presentBox = new PresentBox(id,  shape, color, components);
        }
        return presentBox;
    }

    public static PresentBox getPresentBox() {
        if (presentBox == null) {
            System.out.println("error");
        }
        return presentBox;
    }

    private PresentBox(int id, PresentShape shape, String color, List<Sweet> components) {
        this.id = id;
        this.shape = shape;
        this.color = color;
        this.components = components;
    }



    public int getId(){
        return id;
    }
    public PresentShape getPresentShape(){
        return shape;
    }
    public String getColor(){
        return color;
    }
    public float getSugarLevel(){
        float sugar = 0.0F;
        for (Sweet sweets : components) sugar += sweets.getCurrentSugarLevel();
        return sugar;
    }

    public int getCalorieContent(){
        int CalorieContent = 0;
        for (Sweet sweets : components) CalorieContent += sweets.getCurrentCalorieContent();
        return CalorieContent;
    }

    public float getPresentWeight() {
        float weight = 0.0F;
        if(!components.isEmpty()){
        for (Sweet sweets : components) weight += sweets.getWeight();
        }
        return weight;
    }

    public float getPrice(){
        float price = 0.0F;
        for (Sweet sweets : components) price += sweets.getCurrentPrice();
        return price;
    }

    public List<Sweet> getComponents() {
        return components;
    }
    public void setComponents(List<Sweet> sweets){
        this.components = sweets;
    }

    public void addSweet(Sweet sweet){
        if(!components.contains(sweet)){
            components.add(sweet);
            System.out.println("The sweet is added to the gift");
        }
        else {
            System.out.println("The sweet already is in the gift.\nIf you want to delete or update this sweet choose this action in menu");
        }
    }

    @Override
    public String toString() {
        return "PresentBox{" +
                "id=" + id +
                ", shape=" + shape +
                ", color='" + color + '\'' +
                ", components=" + components +
                '}';
    }
}

