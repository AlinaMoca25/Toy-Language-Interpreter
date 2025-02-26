package model.state;

import exception.MyException;
import model.adt.*;
import model.statements.IStatement;
import model.values.*;

import java.io.BufferedReader;

public class ProgramState {

    private IMyStack<IStatement> executionStack;
    private IMyMap<String, IValue> symbolTable;
    private IMyList<IValue> output;
    private IMyMap<StringValue, BufferedReader> fileTable;
    private IHeap<Integer, IValue> heap;
    private ILockTable<Integer, Integer> lockTable;
    IStatement originalProgramState;
    private static int general_id=0;
    private int id;

    public ProgramState(IMyStack<IStatement> exeStack, IMyMap<String, IValue> symTable, IMyList<IValue> out, IStatement program,
                        IMyMap<StringValue, BufferedReader> fileTable, IHeap<Integer, IValue> heap,
                        ILockTable<Integer, Integer> lockTable) {
        this.executionStack = exeStack;
        this.symbolTable = symTable;
        this.output = out;
        this.originalProgramState = program;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        exeStack.push(program);
        this.id = getNextId();
    }

    public ProgramState(IMyStack<IStatement> exeStack, IMyMap<String, IValue> symTable, IMyList<IValue> out,
                        IMyMap<StringValue, BufferedReader> fileTable, IHeap<Integer, IValue> heap,
                        ILockTable<Integer, Integer> lockTable) {
        this.executionStack = exeStack;
        this.symbolTable = symTable;
        this.output = out;
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.id = getNextId();
    }

    public ProgramState(ProgramState p) { //copy constructor
        this.executionStack = p.executionStack;
        this.symbolTable = p.symbolTable;
        this.output = p.output;
        this.heap = p.heap;
        this.lockTable = p.lockTable;
    }

    public int getId() { return id; }

    public IMyStack<IStatement> getExecutionStack() {
        return executionStack;
    }

    public IMyMap<String, IValue> getSymbolTable() {
        return symbolTable;
    }

    public IMyList<IValue> getOutput() {
        return output;
    }

    public IMyMap<StringValue, BufferedReader> getFileTable() {
        return fileTable;
    }

    public IHeap<Integer, IValue> getHeap() {
        return heap;
    }

    public ILockTable<Integer, Integer> getLockTable() {
        return lockTable;
    }

    public void setExecutionStack(IMyStack<IStatement> exeStack) {
        this.executionStack = exeStack;
    }

    public void setSymbolTable(IMyMap<String, IValue> symTable) {
        this.symbolTable = symTable;
    }

    public void setOutput(IMyList<IValue> output) {
        this.output = output;
    }

    public void setFileTable(IMyMap<StringValue, BufferedReader> fileTable) {
        this.fileTable = fileTable;
    }

    public void setHeap(IHeap<Integer, IValue> heap) { this.heap = heap; }

    public void setLockTable(ILockTable<Integer, Integer> lockTable) { this.lockTable = lockTable; }
    //setter for the heap?

    public IStatement getOriginalProgramState() {
        return this.originalProgramState;
    }

    @Override
    public String toString() {
        String exeStack = executionStack.toString();
        String symTable = symbolTable.toString();
        String out = output.toString();
        String fileT = fileTable.toString();
        String Heap = heap.toString();
        String lock = lockTable.toString();
        return "Current program state: id#" + this.id + "\n" +
                "Execution stack: \n" + exeStack + "\nSymbol table: \n" + symTable +
                "\nOutput: \n" + out + "\nFile table: \n" + fileT + "\nHeap: \n" + Heap +
                "\nLock table: \n" + lock
                + "-------------------------------\n";
    }

    public boolean isNotCompleted() {
        return !(this.executionStack.isEmpty());
    }

    public ProgramState oneStep() throws MyException {
        if (this.executionStack.isEmpty()) {
            throw new MyException("Execution stack is empty!");
        }
        else {
            IStatement currentStatement = this.executionStack.pop();
            return currentStatement.execute(this);
        }
    }

    public static synchronized int getNextId() {
        return ++general_id;
    }

}
