package model.types;

import model.values.StringValue;
import model.values.IValue;

public class StringType implements IType {
    public boolean equals(IType other) {
        return other instanceof StringType;
    }

    @Override
    public String toString() {
        return "String";
    }

    public IValue defaultValue() {
        return new StringValue("");
    }
}
