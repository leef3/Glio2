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
    /*
        USED FOR NEW RECIPE FRAGMENT -- ADDING AN INGREDIENT WHERE SCALE APPEARS WITH IT
     */
    //If no scale object is given, return the amount
    public double getAmount() {return amount;}
    //Method above goes hand in hand/ if no scale given then get the original scale
    public ScaleObject getScale() {return scale;}

    /*
        USED FOR WEEKLY INGREDIENT LIST -- WHERE NORMALIZED AMOUNTS NEED TO COMBINE
     */
    public double getAmount(ScaleObject displayScale)
    {
        return (normalizedAmount/displayScale.getMultiplier());
    }

    //TODO: TRACKS BOUGHT ITEMS IN THE WEEKLY INGREDIENT LIST
    public void toggleChecked()
    {
        bought = !bought;
    }
    public boolean getChecked() {return bought;}


    //Getter Setter Methods
    public String getName() {return name;}
    public double getNormalizedAmount() {return normalizedAmount;}

}
