package com.example.map_toylanguage_gui;

import exception.MyException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.stage.Modality;
import javafx.stage.Stage;
import model.adt.MyMap;
import model.expressions.*;
import model.statements.*;
import model.types.BoolType;
import model.types.IntType;
import model.types.RefType;
import model.types.StringType;
import model.values.BoolValue;
import model.values.IntValue;
import model.values.StringValue;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HelloController { //<=> view + main?

    //Example 1:
    // int v; v=2;Print(v)
    IStatement ex1 = new CompoundStatement(new VarDeclarationStatement("v",new IntType()),
        new CompoundStatement(new AssignmentStatement("v",new ValueExpression(new IntValue(2))), new PrintStatement(new
            VariableExpression("v"))));

    //Example2:
    //int a; int b; a=2+3*5;b=a+1;Print(b)
    IStatement ex2 = new CompoundStatement(new VarDeclarationStatement("a",new IntType()),
        new CompoundStatement(new VarDeclarationStatement("b",new IntType()),
            new CompoundStatement(new AssignmentStatement("a", new ArithmeticExpression(new ValueExpression(new IntValue(2)),new
                ArithmeticExpression(new ValueExpression(new IntValue(3)), new ValueExpression(new IntValue(5)), '*'), '+')),
                    new CompoundStatement(new AssignmentStatement("b",new ArithmeticExpression(new VariableExpression("a"), new ValueExpression(new
                        IntValue(1)), '+')), new PrintStatement(new VariableExpression("b"))))));

    //Example3:
    //bool a; int v; a=true;(If a Then v=2 Else v=3);Print(v)
    IStatement ex3 = new CompoundStatement(new VarDeclarationStatement("a",new BoolType()),
            new CompoundStatement(new VarDeclarationStatement("v", new IntType()),
                    new CompoundStatement(new AssignmentStatement("a", new ValueExpression(new BoolValue(true))),
                            new CompoundStatement(new IfStatement(new VariableExpression("a"),new AssignmentStatement("v",new ValueExpression(new
                                    IntValue(2))), new AssignmentStatement("v", new ValueExpression(new IntValue(3)))), new PrintStatement(new
                                    VariableExpression("v"))))));

    //Example 4:
//        string varf;
//        varf="test.in";
//        openRFile(varf);
//        int varc;
//        readFile(varf,varc);print(varc);
//        readFile(varf,varc);print(varc);
//        closeRFile(varf);
    IStatement ex4 = new CompoundStatement(new VarDeclarationStatement("varf", new StringType()),
        new CompoundStatement(new AssignmentStatement("varf", new ValueExpression(new StringValue("src/main/java/test.in"))),
            new CompoundStatement(new OpenRFileStatement(new VariableExpression("varf")),
                new CompoundStatement(new VarDeclarationStatement("varc",new IntType()),
                    new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                        new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                            new CompoundStatement(new ReadFileStatement(new VariableExpression("varf"), "varc"),
                                new CompoundStatement(new PrintStatement(new VariableExpression("varc")),
                                    new closeRFileStatement(new VariableExpression("varf"))))))))));

    //Example 5:
//        Ref int v;new(v,20);Ref Ref int a; new(a,v);print(v);print(a)
    IStatement ex5 = new CompoundStatement(new VarDeclarationStatement("v", new RefType(0, new IntType())),
        new CompoundStatement(new HeapAllocation(new StringValue("v"), new ValueExpression(new IntValue(20))),
            new CompoundStatement(new VarDeclarationStatement("a", new RefType(0, new RefType(0, new IntType()))),
                new CompoundStatement(new HeapAllocation(new StringValue("a"), new VariableExpression("v")),
                    new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                        new PrintStatement(new VariableExpression("a")))))));

    //Example 6:
//        Ref int v;new(v,20);Ref Ref int a; new(a,v);print(rH(v));print(rH(rH(a))+5)
    IStatement ex6 = new CompoundStatement(new VarDeclarationStatement("v", new RefType(0, new IntType())),
        new CompoundStatement(new HeapAllocation(new StringValue("v"), new ValueExpression(new IntValue(20))),
            new CompoundStatement(new VarDeclarationStatement("a", new RefType(0, new RefType(0, new IntType()))),
                new CompoundStatement(new HeapAllocation(new StringValue("a"), new VariableExpression("v")),
                    new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                        new PrintStatement(new ArithmeticExpression(new ValueExpression(new IntValue(5)),
                            new HeapReading(new HeapReading(new VariableExpression("a"))), '+')))))));

    //Example 7:
//        Ref int v;new(v,20);print(rH(v)); wH(v,30);print(rH(v)+5);
    IStatement ex7 = new CompoundStatement(new VarDeclarationStatement("v", new RefType(0, new IntType())),
        new CompoundStatement(new HeapAllocation(new StringValue("v"), new ValueExpression(new IntValue(20))),
            new CompoundStatement(new PrintStatement(new HeapReading(new VariableExpression("v"))),
                new CompoundStatement(new HeapWriting(new StringValue("v"), new ValueExpression(new IntValue(30))),
                    new PrintStatement(new ArithmeticExpression(new ValueExpression(new IntValue(5)),
                        new HeapReading(new VariableExpression("v")), '+'))))));

    //Example 8:
//      int v; v=4; (while (v>0) print(v);v=v-1);print(v)
    IStatement ex8 = new CompoundStatement(new VarDeclarationStatement("v", new IntType()),
        new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(4))),
            new CompoundStatement(new WhileStatement(
                new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(0)), ">"),
                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                    new AssignmentStatement("v", new ArithmeticExpression(
                        new VariableExpression("v"), new ValueExpression(new IntValue(1)), '-')))),
                            new PrintStatement(new VariableExpression("v")))));

    //Example 9:
//      Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30);print(rH(rH(a)))
    IStatement ex9 = new CompoundStatement(new VarDeclarationStatement("v", new RefType(0, new IntType())),
        new CompoundStatement(new HeapAllocation(new StringValue("v"), new ValueExpression(new IntValue(20))),
            new CompoundStatement(new VarDeclarationStatement("a", new RefType(0, new RefType(0, new IntType()))),
                new CompoundStatement(new HeapAllocation(new StringValue("a"), new VariableExpression("v")),
                    new CompoundStatement(new HeapAllocation(new StringValue("v"), new ValueExpression(new IntValue(30))),
                        new PrintStatement(new HeapReading(new HeapReading(new VariableExpression("a")))))))));

    //Example 10:
//      Ref int v;new(v,20);Ref Ref int a; new(a,v); new(v,30); new(v,60); print(rH(rH(a)))
    IStatement ex10 = new CompoundStatement(new VarDeclarationStatement("v", new RefType(0, new IntType())),
        new CompoundStatement(new HeapAllocation(new StringValue("v"), new ValueExpression(new IntValue(20))),
            new CompoundStatement(new VarDeclarationStatement("a", new RefType(0, new RefType(0, new IntType()))),
                new CompoundStatement(new HeapAllocation(new StringValue("a"), new VariableExpression("v")),
                    new CompoundStatement(new HeapAllocation(new StringValue("v"), new ValueExpression(new IntValue(30))),
                        new CompoundStatement( new HeapAllocation(new StringValue("v"), new ValueExpression( new IntValue(60))),
                            new PrintStatement(new HeapReading(new HeapReading(new VariableExpression("a"))))))))));

    //Example 11:
    // int v; Ref int a; v=10;new(a,22);
    // fork(wH(a,30);v=32;print(v);print(rH(a)));
    // print(v);print(rH(a))
    IStatement ex11 = new CompoundStatement( new VarDeclarationStatement("v", new IntType()),
        new CompoundStatement(new VarDeclarationStatement("a", new RefType(0, new IntType())),
            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(10))),
                new CompoundStatement(new HeapAllocation(new StringValue("a"), new ValueExpression(new IntValue(22))),
                    new CompoundStatement(
                        new ForkStatement(new CompoundStatement(new HeapWriting(new StringValue("a"), new ValueExpression(new IntValue(30))),
                            new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(32))),
                                new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                    new PrintStatement(new HeapReading(new VariableExpression("a"))))))),
                                        new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                            new PrintStatement(new HeapReading(new VariableExpression("a")))))))));

    //Example 12:
//    int v; int x; int y; v=0;
//    (repeat (fork(print(v);v=v-1);v=v+1) until v==3);
//    x=1;nop;y=3;nop;
//    print(v*10)
//    The final Out should be {0,1,2,30}
    IStatement ex12 = new CompoundStatement(new VarDeclarationStatement("v", new IntType()),
        new CompoundStatement(new VarDeclarationStatement("x", new IntType()),
            //new CompoundStatement(new VarDeclarationStatement("x", new IntType()),
                new CompoundStatement(new VarDeclarationStatement("y", new IntType()),
                    new CompoundStatement(new AssignmentStatement("v", new ValueExpression(new IntValue(0))),
                        new CompoundStatement(new RepeatUntilStatement(
                                new CompoundStatement(
                                new ForkStatement(new CompoundStatement(new PrintStatement(new VariableExpression("v")),
                                        new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"),
                                                new ValueExpression(new IntValue(1)), '-')))),
                                new AssignmentStatement("v", new ArithmeticExpression(new VariableExpression("v"), new ValueExpression(new IntValue(1)), '+'))),
                                new RelationalExpression(new VariableExpression("v"), new ValueExpression(new IntValue(3)), "=="))

                                ,
                            new CompoundStatement(new AssignmentStatement("x", new ValueExpression(new IntValue(1))),
                                new CompoundStatement(new NoOperation(),
                                        new CompoundStatement(new AssignmentStatement("y", new ValueExpression(new IntValue(3))),
                                            new CompoundStatement(new NoOperation(),
                                                    new PrintStatement(new ArithmeticExpression(
                                                            new VariableExpression("v"), new ValueExpression(new IntValue(10)), '*'))))))
                        )))));


    List<IStatement> examples = new ArrayList<>();

    //GUI elements
    @FXML
    private Label titleLabel;

    @FXML
    private Label infoLabel;

    @FXML
    private ListView<String> programsList;

    @FXML
    public void initialize() {
        examples.add(ex1);
        examples.add(ex2);
        examples.add(ex3);
        examples.add(ex4);
        examples.add(ex5);
        examples.add(ex6);
        examples.add(ex7);
        examples.add(ex8);
        examples.add(ex9);
        examples.add(ex10);
        examples.add(ex11);
        examples.add(ex12);

        try {
            ex12.typecheck(new MyMap<>());
        } catch (MyException e) {
            System.err.println(e.getMessage());
        }

        ObservableList<String> statements = FXCollections.observableArrayList();
        int count = 1;
        for(IStatement statement : examples) {
            statements.add(count + ": " + statement.toString());
            count++;
        }
        programsList.setItems(statements);

        //add listener for changes in selecting the items
        programsList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
                loader.setController(new MainController(examples.get(programsList.getSelectionModel().getSelectedIndex())));

                Stage stage = new Stage();
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setTitle("Current program: ");
                stage.setScene(new Scene(root, 800, 600));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.show();
                //System.out.println("Selected item: " + newValue);
            }
        });

    }

}