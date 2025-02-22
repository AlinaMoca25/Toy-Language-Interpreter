package model.values;

import model.types.BoolType;
import model.types.IType;

public class BoolValue implements IValue {
    private boolean value;

    public boolean getValue() {
        return value;
    }

    public IType getType() {
        return new BoolType();
    }

    public boolean equals(IValue other) {
        return other.getType() instanceof BoolValue
                && this.getValue() == ((BoolValue)other).getValue();
    }

    public BoolValue(final boolean value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}