package org.gifts.entity.item;

public class Lollipop extends Sweet {
    private SweetsFlavors flavor;
    private LollipopFiller filler;

    public Lollipop(int id, int categoryId, String title, float price, String description, boolean isAvailable,
                    float sugarLevel, float weight, int calorieContent, SweetsFlavors flavor, LollipopFiller filler) {
        super(id, categoryId, title, price, description, isAvailable, sugarLevel, weight, calorieContent);
        this.flavor = flavor;
        this.filler = filler;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lollipop lollipop = (Lollipop) o;
        return flavor == lollipop.flavor;
    }

    @Override
    public String getInfo(){
        return "Lollipop{" +
                "title=" + title +
                ",\tprice=" + getPrice() +
                ",\tsugar level per 100g=" + sugarLevel +
                ",\tcalorie content per 100g=" + calorieContent +
                ",\tflavor=" + flavor +
                ",\tfiller=" + filler +
                '}';
    }

    @Override
    public String toString() {
        return "Lollipop{" +
                "title=" + title +
                ",\tweight=" + weight +
                ",\tcurrent price=" + getCurrentPrice() +
                ",\tsugar level=" + getCurrentSugarLevel() +
                ",\tcalorie content=" + getCurrentCalorieContent() +
                ",\tflavor=" + flavor +
                ",\tfiller=" + filler +
                '}';
    }
}

