public class GateOr extends LogicGate {
    private LogicVariable i1, i2;

    public GateOr(LogicVariable output, LogicVariable i1, LogicVariable i2) throws ColisionException, CycleException {
        super(output);
        this.i1 = i1;
        this.i2 = i2;

        LogicGate l1 = i1.getCalculatedBy();
        if (l1 != null) {
            for (LogicVariable lv : l1.getInputs()) {
                if (lv.equals(output))
                    throw new CycleException();
            }
        }

        LogicGate l2 = i2.getCalculatedBy();
        if (l2 != null) {
            for (LogicVariable lv : l2.getInputs()) {
                if (lv.equals(output))
                    throw new CycleException();
            }
        }
    }

    @Override
    public LogicVariable[] getInputs() {
        return new LogicVariable[]{i1, i2};
    }

    @Override
    public String getSymbol() {
        return "OR";
    }

    @Override
    public String getFormula() {
        return getSymbol() + "(" + i1.getFormula() + "," + i2.getFormula() + ")";
    }

    @Override
    public boolean getValue() {
        return i1.getValue() || i2.getValue();
    }
}
