public class GateNot extends LogicGate {
    private LogicVariable i;

    public GateNot(LogicVariable output, LogicVariable i) throws ColisionException, CycleException {
        super(output);
        this.i = i;

        LogicGate l = i.getCalculatedBy();
        if (l != null) {
            for (LogicVariable lv : l.getInputs()) {
                if (lv.equals(output))
                    throw new CycleException();
            }
        }
    }

    @Override
    public LogicVariable[] getInputs() {
        return new LogicVariable[]{i};
    }

    @Override
    public String getSymbol() {
        return "NOT";
    }

    @Override
    public String getFormula() {
        return getSymbol() + "(" + i.getFormula() + ")";
    }

    @Override
    public boolean getValue() {
        return !i.getValue();
    }
}
