package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class IngredientObject {

    private String name;
    private ScaleObject scale;
    private double amount;
    private boolean bought;

    public IngredientObject(String newName)
    {
        name = newName;
        bought = false;
    }

    public void addScale(ScaleObject toAdd)
    {
        scale = toAdd;
    }
    public void addAmount(double toAdd)
    {
        amount = toAdd;
    }

    public void toggleChecked()
    {
        bought = !bought;
    }


    //Getter Setter Methods
    public String getName() {return name;}
    public ScaleObject getScale() {return scale;}
    public double getAmount() {return amount;}
    public boolean getBought() {return bought;}

}
