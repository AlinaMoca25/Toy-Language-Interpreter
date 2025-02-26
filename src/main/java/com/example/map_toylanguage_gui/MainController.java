package com.example.map_toylanguage_gui;

//import com.sun.tools.javac.Main;

import controller.Controller;
import exception.MyException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.adt.*;
import model.state.ProgramState;
import model.statements.IStatement;
import model.values.IValue;
import repository.IRepository;
import repository.Repository;
import view.heapView;
import view.lockView;
import view.symTableView;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class MainController {

    private Controller controller;
    private final IStatement statement;

    MainController(IStatement statement) {
        this.statement = statement;
    }

    //GUI elements
    @FXML
    private Label programLabel;

    @FXML
    private TextField nrOfPrgStatesTextField; //a

    @FXML
    private TableView<heapView> heapTable; //b

    @FXML
    private TableColumn<heapView, Integer> heapTableAddrColumn;

    @FXML
    private TableColumn<heapView, IValue> heapTableVarColumn;

    @FXML
    private ListView<String> outList; //c

    @FXML
    private ListView<String> fileTable; //d

    @FXML
    private ListView<String> prgStatesIdList; //e

    @FXML
    private TableView<symTableView> symTable; //f

    @FXML
    private TableColumn<symTableView, String> symTableVarNameColumn;

    @FXML
    private TableColumn<symTableView, IValue> symTableVarValColumn;

    @FXML
    private ListView<String> exeStackList; //g

    @FXML
    private Button runOneStepButton; //h

    @FXML
    private TableView<lockView> lockTable;

    @FXML
    private TableColumn<lockView, String> lockLocationColumn;

    @FXML
    private TableColumn<lockView, String> lockValueColumn;

    public void initialize() {

        IMyStack<IStatement> stack = new MyStack<>(); stack.push(this.statement);
        ProgramState prgState = new ProgramState(stack, new MyMap<>(), new MyList<>(), new MyMap<>(), new MyHeap<>(), new LockTable<>());
        IRepository repo = new Repository("out.txt"); repo.add(prgState);

        this.controller = new Controller(repo);

        programLabel.setText("Program: " + this.statement.toString());
        heapTableAddrColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        heapTableVarColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        symTableVarNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        symTableVarValColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        lockLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        lockValueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));

//        prgStatesIdList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
//
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//
//
//                //System.out.println("Selected item: " + newValue);
//            }
//        });

        setProgramStates();
        setExeStack();
    }

    @FXML
    protected void runOneStepForAll(ActionEvent event) {
        try {
            if(controller.oneStepGUI()) {
                updateAll();
            }
            else {
                setProgramStates();
                symTable.getItems().clear();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Program finished", ButtonType.OK);
                alert.showAndWait();
                Node source = (Node) event.getSource();
                Stage stage = (Stage) source.getScene().getWindow();
                stage.close();
            }
        } catch (MyException | IOException | InterruptedException e) {
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
            stage.close();
        }
    }

    @FXML
    protected void selectProgramState() throws MyException {
        if(controller.getNrOfProgramStates() > 0) {
            updateAll();
        }
    }

    private ProgramState getCurrentState() {
        int index = prgStatesIdList.getSelectionModel().getSelectedIndex();
        if (index == -1) {
            index = 0;
        }
        return controller.getProgramStateIndex(index);
    }

    private void setProgramStates() {
        nrOfPrgStatesTextField.setText("" + controller.getNrOfProgramStates());

        //set program states
        ObservableList<String> prgStateIds = FXCollections.observableArrayList();
        for (ProgramState state : controller.getAllStates()) {
            prgStateIds.add("" + state.getId());
        }
        prgStatesIdList.setItems(prgStateIds);




    }

    private void setExeStack() {
        ObservableList<String> executionList = FXCollections.observableArrayList();
        ProgramState state = getCurrentState();

        for(IStatement statement: state.getExecutionStack().getAll()) {
            executionList.add(statement.toString());
        }

        exeStackList.setItems(executionList);
    }

    private void setHeapTable() {
        ObservableList<heapView> heapList = FXCollections.observableArrayList();
        ProgramState state = getCurrentState();
        IHeap<Integer, IValue> heap = state.getHeap();

        for(Integer address : heap.getContent().keySet()) {
            heapList.add(new heapView(address, heap.getContent().get(address)));
        }
        heapTable.setItems(heapList);
    }

    private void setOutList() {
        //set output
        ObservableList<String> changeOut = FXCollections.observableArrayList();
        for (ProgramState state : controller.getAllStates()) {
            changeOut.add("" + state.getOutput());
        }
        outList.setItems(changeOut);
    }

    private void setFileTable() {
        //set file table
        ObservableList<String> changeFileTable = FXCollections.observableArrayList();
        for (ProgramState state : controller.getAllStates()) {
            changeFileTable.add("" + state.getFileTable());
        }
        fileTable.setItems(changeFileTable);
    }

    private void setSymTable() {
        ObservableList<symTableView> symList = FXCollections.observableArrayList();
        ProgramState state = getCurrentState();
        IMyMap<String, IValue> symbolTable = state.getSymbolTable();

        for(String var : symbolTable.getContent().keySet()) {
            symList.add(new symTableView(var, symbolTable.getContent().get(var)));
        }
        symTable.setItems(symList);
    }

    private void setLockTable() throws MyException {
        ObservableList<lockView> lockList = FXCollections.observableArrayList();
        ProgramState state = getCurrentState();
        ILockTable<Integer, Integer> lock = state.getLockTable();

        for(Integer address : lock.getContent().keySet()) {
            lockList.add(new lockView(address, lock.get(address)));
        }
        lockTable.setItems(lockList);
    }

    private void updateAll() throws MyException {
        setHeapTable();
        setOutList();
        setFileTable();
        setProgramStates();
        setSymTable();
        setExeStack();
        setLockTable();
    }

}
