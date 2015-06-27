package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/18/2015.
 */

//Object used for combining different scale values (Ex: 2 Quarts of Oil plus 1 Pint of Oil)
public class ScaleObject {

    //Name of the scale - Quart / Gallon / Teaspoon etc
    private String scaleName;
    //Number of Base Units it is (the smallest factor)-- Ex: 16 teaspoons in 1 X, thus multiplier is 16
    private double multiplier;

    //Constructor
    public ScaleObject(String newName, double newMultiplier)
    {
        scaleName = newName;
        multiplier = newMultiplier;
    }

    public String getName() {return scaleName;}
    public double getMultiplier() {return multiplier;}
}
