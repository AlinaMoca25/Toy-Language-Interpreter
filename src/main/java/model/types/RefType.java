package model.types;

import model.values.*;

public class RefType implements IType {
    int address;
    IType inner;

    public RefType(int address, IType inner) {
        this.address = address;
        this.inner = inner;
    }

//    public RefType() {
//        this.defaultValue();
//    }

    public IType getInner() {
        return inner;
    }

    public boolean equals(IType other) {
        return other instanceof RefType && inner.equals(((RefType) other).getInner());
    }

    public String toString() {
        return "Ref(" + address + ", " + inner.toString() + ")";
    }

    public IValue defaultValue() {
        return new RefValue(0, inner);
    }

}
