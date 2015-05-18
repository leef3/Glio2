package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class IngredientObject {

    private String name;
    private ScaleObject scale;
    private double amount;

    public IngredientObject(String newName)
    {
        name = newName;
    }

    public void addScale(ScaleObject toAdd)
    {
        scale = toAdd;
    }
    public void addAmount(double toAdd)
    {
        amount = toAdd;
    }


    //Getter Setter Methods
    public String getName() {return name;}
    public ScaleObject getScale() {return scale;}
    public double getAmount() {return amount;}

}
