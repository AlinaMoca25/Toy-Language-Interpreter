package model.types;

import model.values.*;

public class IntType implements IType{
    public boolean equals(IType other) {
        return other instanceof IntType;
    }

    @Override
    public String toString() {
        return "Int";
    }

    public IValue defaultValue() {
        return new IntValue(0);
    }
}