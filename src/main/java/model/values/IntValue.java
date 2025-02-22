package model.values;

import model.types.IType;
import model.types.IntType;

public class IntValue implements IValue {

    private final int value;

    public int getValue() {
        return value;
    }

    public IType getType() {
        return new IntType();
    }

    public boolean equals(IValue other) {
        return other.getType() instanceof IntValue
                && this.getValue() == ((IntValue)other).getValue();
    }

    public IntValue(final int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}