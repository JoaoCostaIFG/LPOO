public class HumanClient implements Client {
    OrderingStrategy strat;

    public HumanClient(OrderingStrategy strategy) {
        this.strat = strategy;
    }

    @Override
    public void wants(StringDrink drink, StringRecipe recipe, StringBar bar) {
        strat.wants(drink, recipe, bar);
    }

    @Override
    public void happyHourStarted(Bar bar) {
        strat.happyHourStarted((StringBar) bar);
    }

    @Override
    public void happyHourEnded(Bar bar) {
        strat.happyHourEnded((StringBar) bar);
    }
}
