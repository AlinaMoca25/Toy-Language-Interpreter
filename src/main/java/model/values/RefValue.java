package model.values;
import model.values.*;
import model.types.*;

public class RefValue implements IValue {
    int address;
    IType type;

    public RefValue(int address, IType type) {
        this.address = address;
        this.type = type;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
    }

    public IType getAddressType() {
        return type;
    }

    @Override
    public IType getType() {
        return new RefType(address, type);
    }

    @Override
    public boolean equals(IValue other) {
        return other instanceof RefValue && other.getType()==this.type && ((RefValue) other).getAddress() == address;
    }

    public String toString() {
        return type.toString() + "->" + String.valueOf(address);
    }
}
