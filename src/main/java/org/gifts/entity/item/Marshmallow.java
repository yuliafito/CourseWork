package org.gifts.entity.item;

public class Marshmallow extends Sweet {
    private SweetsFlavors flavor;

    public Marshmallow(int id, int categoryId, String title, float price,
                       String description, boolean isAvailable, float sugarLevel, float weight, int calorieContent, SweetsFlavors flavor) {
        super(id, categoryId, title, price, description, isAvailable, sugarLevel, weight, calorieContent);
        this.flavor = flavor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marshmallow that = (Marshmallow) o;
        return flavor == that.flavor;
    }

    @Override
    public String getInfo() {
        return "Marshmallow{" +
                "title=" + title +
                ",\tprice=" + getPrice() +
                ",\tsugar level per 100g=" + sugarLevel +
                ",\tcalorie content per 100g=" + calorieContent +
                ",\tflavor=" + flavor +
                '}';
    }

    @Override
    public String toString() {
        return "Marshmallow{" +
                "title=" + title +
                ",\tweight=" + weight +
                ",\tcurrent price=" + getCurrentPrice() +
                ",\tsugar level=" + getCurrentSugarLevel() +
                ",\tcalorie content=" + getCurrentCalorieContent() +
                ",\tflavor=" + flavor +
                '}';
    }
}

