public abstract class LogicGate {
    protected LogicVariable output;

    public LogicGate(LogicVariable output) throws ColisionException {
        this.output = output;
        this.output.setCalculatedBy(this);
    }

    public abstract LogicVariable[] getInputs();

    public abstract String getSymbol();

    public LogicVariable getOutput() {
        return output;
    }

    public void setOutput(LogicVariable output) {
        this.output = output;
    }

    public abstract String getFormula();

    public abstract boolean getValue();
}
