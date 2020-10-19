import java.util.LinkedList;
import java.util.List;

public class SmartStrategy implements OrderingStrategy {
    private List<StringDrink> drinks = new LinkedList<>();
    private List<StringRecipe> recipes = new LinkedList<>();
    private List<StringBar> bars = new LinkedList<>();

    @Override
    public void wants(StringDrink drink, StringRecipe recipe, StringBar bar) {
        if (bar.isHappyHour())
            bar.order(drink, recipe);
        else {
            drinks.add(drink);
            recipes.add(recipe);
            bars.add(bar);
        }
    }

    @Override
    public void happyHourStarted(StringBar bar) {
        if (!bar.isHappyHour())
            return;

        int n = 0;
        for (int i = 0; i < bars.size(); ++i) {
            if (bar == bars.get(i)) {
                ++n;
                bars.get(i).order(drinks.get(i), recipes.get(i));
            }
        }

        while (n > 0) {
            --n;
            for (int i = 0; i < bars.size(); ++i) {
                if (bar == bars.get(i)) {
                    drinks.remove(i);
                    recipes.remove(i);
                    bars.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public void happyHourEnded(StringBar bar) {

    }
}
