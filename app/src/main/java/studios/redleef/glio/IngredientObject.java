package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/8/2015.
 */
public class IngredientObject {

    private String name;
    private ScaleObject scale;
    private double amount;
    private boolean bought;
    private double normalizedAmount;

    public IngredientObject(String newName)
    {
        name = newName;
        bought = false;
        amount = 0;
        normalizedAmount = 0;
        scale = new ScaleObject("N/A", 0);
    }

    public void addAmount(double amountToAdd, ScaleObject scaleToAdd)
    {
        amount = amountToAdd;
        scale = scaleToAdd;
        normalizedAmount = (amountToAdd * scaleToAdd.getMultiplier());
    }

    public void addNormalizedAmount(double toAdd)
    {
        normalizedAmount = normalizedAmount + toAdd;
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
    public double getNormalizedAmount() {return normalizedAmount;}

}
