import java.util.List;

public class StringTransformerGroup implements StringTransformer {
    private List<StringTransformer> transfs;

    public StringTransformerGroup(List<StringTransformer> transformers) {
        this.transfs = transformers;
    }

    public void execute(StringDrink drink) {
        for (StringTransformer transf : transfs) {
            transf.execute(drink);
        }
    }
}
