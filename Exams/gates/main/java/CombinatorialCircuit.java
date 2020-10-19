import java.util.ArrayList;
import java.util.List;

public class CombinatorialCircuit {
    List<LogicVariable> variableList;

    public CombinatorialCircuit() {
        this.variableList = new ArrayList<>();
    }

    public boolean addVariable(LogicVariable newv) {
        for (LogicVariable v : variableList) {
            if (v.getName().equals(newv.getName()))
                return false;
        }

        this.variableList.add(newv);
        return true;
    }

    public LogicVariable getVariableByName(String name) {
        for (LogicVariable v : variableList) {
            if (v.getName().equals(name))
                return v;
        }

        return null;
    }
}
