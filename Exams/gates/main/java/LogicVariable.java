import java.util.Objects;

public class LogicVariable {
    private String name;
    private boolean value;
    private LogicGate calculatedBy = null;

    public LogicVariable(String name, boolean value) {
        this.name = name;
        this.value = value;
    }

    public LogicVariable(String name) {
        this(name, false);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getValue() {
        if (calculatedBy != null)
            return calculatedBy.getValue();
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogicVariable that = (LogicVariable) o;
        return value == that.value &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, value);
    }

    public LogicGate getCalculatedBy() {
        return calculatedBy;
    }

    public void setCalculatedBy(LogicGate calculatedBy) throws ColisionException {
        if (this.calculatedBy != null)
            throw new ColisionException();

        this.calculatedBy = calculatedBy;
    }

    public String getFormula() {
        if (this.calculatedBy != null)
            return calculatedBy.getFormula();
        return name;
    }
}
