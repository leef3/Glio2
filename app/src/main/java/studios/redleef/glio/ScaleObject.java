package studios.redleef.glio;

/**
 * Created by Fred Lee on 5/18/2015.
 */
public class ScaleObject {

    private String scaleName;
    private double multiplier;

    public ScaleObject(String newName, double newMultiplier)
    {
        scaleName = newName;
        multiplier = newMultiplier;
    }

    public String getName() {return scaleName;}
    public double getMultiplier() {return multiplier;}
}
