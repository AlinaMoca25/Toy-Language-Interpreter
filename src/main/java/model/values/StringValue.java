package model.values;

import model.types.IType;
import model.types.StringType;

public class StringValue implements IValue {
    private final String value;

    public String getValue() {
        return value;
    }

    public IType getType() {
        return new StringType();
    }

    public boolean equals(IValue other) {
        return other.getType() instanceof StringValue
                && this.getValue().equals(((StringValue)other).getValue());
    }

    public StringValue(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
