public class StringCaseChanger implements StringTransformer {

    @Override
    public void execute(StringDrink drink) {
        char[] str = drink.getText().toCharArray();
        for (int i = 0; i < str.length; ++i) {
            if (Character.isLowerCase(str[i]))
                str[i] = Character.toUpperCase(str[i]);
            else
                str[i] = Character.toLowerCase(str[i]);
        }

        drink.setText(new String(str));
    }
}
