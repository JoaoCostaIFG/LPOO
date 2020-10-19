public class StringReplacer implements StringTransformer {
    private char old_c, new_c;

    public StringReplacer(char old_c, char new_c) {
        this.old_c = old_c;
        this.new_c = new_c;
    }

    @Override
    public void execute(StringDrink drink) {
        char[] str = drink.getText().toCharArray();

        for (int i = 0; i < str.length; ++i) {
            if (str[i] == old_c)
                str[i] = new_c;
        }

        drink.setText(new String(str));
    }
}
