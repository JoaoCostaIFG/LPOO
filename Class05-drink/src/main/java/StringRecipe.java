import java.util.List;

public class StringRecipe {
    private List<StringTransformer> transfs;

    public StringRecipe(List<StringTransformer> transformers) {
        this.transfs = transformers;
    }

    public void mix(StringDrink drink) {
        for (StringTransformer transf : transfs) {
            transf.execute(drink);
        }
    }
}
