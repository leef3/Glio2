package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class IngredientObject {

    private static String name, scale;
    private static double amount;

    public IngredientObject(String newName, String newScale, double newAmount)
    {
        name = newName;
        scale = newScale;
        amount = newAmount;
    }

    //Getter Setter Methods
    public static String getName() {return name;}
    public static String getScale() {return scale;}
    public static double getAmount() {return amount;}

}
