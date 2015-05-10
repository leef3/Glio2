package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class IngredientObject {

    private String name, scale;
    private double amount;

    public IngredientObject(String newName, String newScale, double newAmount)
    {
        name = newName;
        scale = newScale;
        amount = newAmount;
    }

    //Getter Setter Methods
    public String getName() {return name;}
    public String getScale() {return scale;}
    public double getAmount() {return amount;}

}
