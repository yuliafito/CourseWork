package org.gifts.entity.item;

public class ChocolateCandy extends Sweet {
    private float cocoaCount;
    private boolean waffle;
    private boolean nuts;
    private ChocolateCandyFillers filler;


    public ChocolateCandy(int id, int categoryId, String title, float price, String description, boolean isAvailable, float sugarLevel,
                          float weight, int calorieContent, float cocoaCount, boolean waffle, boolean nuts, ChocolateCandyFillers filler) {
        super(id, categoryId, title, price, description, isAvailable, sugarLevel, weight, calorieContent);
        this.cocoaCount = cocoaCount;
        this.waffle = waffle;
        this.nuts = nuts;
        this.filler = filler;
    }
    public float getCurrentCocoaCount(){return (cocoaCount/100)*1000 * weight;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChocolateCandy that = (ChocolateCandy) o;
        return cocoaCount == that.cocoaCount && waffle == that.waffle && nuts == that.nuts && filler == that.filler;
    }

    @Override
    public String getInfo() {
        return "ChocolateCandy{" +
                "title=" + title +
                ",\tprice=" + getPrice() +
                ",\tsugar level per 100g=" + sugarLevel +
                ",\tcalorie content per 100g=" + calorieContent +
                ",\tcocoaCount per 100g=" + cocoaCount +
                ",\twaffle=" + waffle +
                ",\tnuts=" + nuts +
                ",\tfiller=" + filler +
                '}';
    }

    @Override
    public String toString() {
        return "ChocolateCandy{" +
                "title=" + title +
                ",\tweight=" + weight +
                ",\tcurrent price=" + getCurrentPrice() +
                ",\tsugar level=" + getCurrentSugarLevel() +
                ",\tcalorie content=" + getCurrentCalorieContent() +
                ",\tcocoaCount=" + getCurrentCocoaCount() +
                ",\twaffle=" + waffle +
                ",\tnuts=" + nuts +
                ",\tfiller=" + filler +
                '}';
    }
}

