package controller;

import model.statements.IStatement;
import repository.IRepository;
import exception.MyException;
import model.state.ProgramState;
import model.adt.*;
import model.values.IValue;
import model.values.RefValue;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    protected IRepository repo;
    boolean display = false;
    protected ExecutorService executor;

    public Controller(IRepository repository, ExecutorService exe) {
        this.repo = repository;
        this.executor = exe;
    }

    public Controller(IRepository repository) {
        this.repo = repository;
        this.executor = Executors.newFixedThreadPool(2);
    }

    public void setDisplayOn() {
        this.display = true;
    }

    public void setDisplayOff() {
        this.display = false;
    }

    public void setStatePosition(int position) {
        this.repo.setStatePosition(position);
    }

    public Map<Integer, IValue> unsafeGarbageCollector(List<Integer> symTableAddresses, Map<Integer, IValue> heap) {
        return heap.entrySet().stream()
                .filter(e->symTableAddresses.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, IValue> garbageCollector(List<IValue> symTable, Map<Integer, IValue> heap) {
        return heap.entrySet().stream().filter(e -> symTable.contains(e.getKey())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

//    public List<Integer> getAddressesFromSymTable(Collection<IValue> symTableValues) {
//        return symTableValues.stream()
//                .filter(v->v instanceof RefValue)
//                .map(v-> {RefValue v1 = (RefValue) v; return v1.getAddress();})
//                .collect(Collectors.toList());

    public List<Integer> getAddressesFromSymTables(Collection<IValue> symTableValues) {
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v-> {RefValue v1 = (RefValue) v; return v1.getAddress();})
                .collect(Collectors.toList());
    }

    public String displayCurrentProgram(ProgramState state) {
        return state.toString();
    }

    public List<ProgramState> removeCompletedPrograms(List<ProgramState> inProgramList) {
        //return inProgramList.stream().filter(p -> p.isNotCompleted())
        //        .collect(Collectors.toList());
        return inProgramList.stream().filter(ProgramState::isNotCompleted)
                .collect(Collectors.toList());
    }

    public void oneStepForAll(List<ProgramState> programsList) throws MyException, IOException, InterruptedException {
        List<Callable<ProgramState>> callList = programsList.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> { return p.oneStep(); }))
                .collect(Collectors.toList());
        List<ProgramState> newProgramList = executor.invokeAll(callList).stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                } )
                .filter(p -> p!=null)
                .collect(Collectors.toList());
        programsList.addAll(newProgramList);
        programsList.forEach(prg -> {
            try {
                repo.logProgramStateExec(prg);
            } catch (MyException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        repo.setProgramStatesList(programsList);
    }

    public void allStep() throws MyException, IOException, InterruptedException {
        executor = Executors.newFixedThreadPool(2);
        List<ProgramState> programsList = removeCompletedPrograms(repo.getProgramStates());

        while(programsList.size()>0) {
//            program.getHeap().setContent(unsafeGarbageCollector(
//                        getAddressesFromSymTables(program.getSymbolTable().values()),
//                        program.getHeap().getContent()));
            //one heap, multiple symbol tables
            programsList.stream().forEach(programState -> programState.getHeap().setContent(
                    garbageCollector(programState.getSymbolTable().values(), programState.getHeap().getContent())));
            oneStepForAll(programsList);
            programsList = removeCompletedPrograms(repo.getProgramStates());
            //repo.setProgramStatesList(programsList);
        }
        executor.shutdown();
        repo.setProgramStatesList(programsList);
    }

    public int getNrOfProgramStates() {
        return repo.getProgramStates().size();
    }

    public List<ProgramState> getAllStates() {
        return repo.getProgramStates();
    }

    public ProgramState getProgramStateIndex(int index) {
        return repo.getProgramStates().get(index);
    }

    public boolean oneStepGUI() throws MyException, IOException, InterruptedException {
        List<ProgramState> programList = removeCompletedPrograms(repo.getProgramStates());
        if(programList.size()>0) {
            this.oneStepForAll(programList);
            removeCompletedPrograms(repo.getProgramStates());
            return true;
        }
        else {
            executor.shutdown();
            repo.setProgramStatesList(programList);
            return false;
        }
    }

}
