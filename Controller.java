package sample;

import java.net.URL;
import java.util.ResourceBundle;


import com.sun.org.apache.regexp.internal.RE;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class Controller {
    OpenHashTable hashTable = new OpenHashTable();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button insertB;

    @FXML
    private Button removeB;

    @FXML
    private Button searchB;

    @FXML
    private TextField FIELDINSERT;

    @FXML
    private TextField FIELDREMOVE;

    @FXML
    private TextField FIELDSEARCH;

    @FXML
    private TextArea RESULT;


    @FXML
    private TextArea VALUES;


    @FXML
    private Button CLEAR;


    @FXML
    void initialize() {

        insertB.setOnAction(event -> {
            RESULT.clear();
            String s = String.valueOf(FIELDINSERT.getCharacters());
            boolean b = hashTable.add(s);
            RESULT.appendText("Now size is: " + (hashTable.size()));
            if(b) RESULT.appendText("\nInserted: " + s);
            else RESULT.appendText("\nCan't insert: " + s);
            showTable(false,null);
        });
        searchB.setOnAction(event -> {
            RESULT.clear();
            String s = String.valueOf(FIELDSEARCH.getCharacters());
            Integer index = ( hashTable.searchIndex(s));
            Object o = hashTable.search(s);
            RESULT.appendText("Now size is: " + hashTable.size());

            if(o == null) RESULT.appendText("\nCan't search: " + s);
            else RESULT.appendText("\nSearched: " + o);

            showTable(true,index);

        });
        removeB.setOnAction(event -> {
            RESULT.clear();
            String s = String.valueOf(FIELDREMOVE.getCharacters());
            boolean b = hashTable.remove(s);
            RESULT.appendText("Now size is: " + (hashTable.size()));
            if(b) RESULT.appendText("\nRemoved: " + s);
            else RESULT.appendText("\nTable does not contain: " + s);
            showTable(false,null);
        });
        CLEAR.setOnAction(event -> {
            RESULT.clear();
            hashTable.clear();
            showTable(false,null);
            RESULT.appendText("Now size is: " + (hashTable.size() + "\nTable Cleared!"));
        });




        assert insertB != null : "fx:id=\"insertB\" was not injected: check your FXML file 'sample.fxml'.";
        assert removeB != null : "fx:id=\"removeB\" was not injected: check your FXML file 'sample.fxml'.";
        assert searchB != null : "fx:id=\"searchB\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDINSERT != null : "fx:id=\"FIELDINSERT\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDREMOVE != null : "fx:id=\"FIELDREMOVE\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDSEARCH != null : "fx:id=\"FIELDSEARCH\" was not injected: check your FXML file 'sample.fxml'.";

    }

    public void showTable(boolean isSearch,Integer index){
        String space = "          ";
        VALUES.clear();
        for(int i = 0; i < hashTable.table.length;i++){
            VALUES.appendText("\n" + i + spaceMaker(i));
            if(hashTable.deleted[i]) VALUES.appendText(hashTable.deleted[i]+ space + " " + hashTable.table[i]);
            else VALUES.appendText(hashTable.deleted[i]+ space + hashTable.table[i]);
            if(isSearch && (index == i && !hashTable.deleted[index])) VALUES.appendText("  Searched!");
        }
    }

    String spaceMaker(int number){
        String result = "                                                  ";
        int n = 1;
        while (number > 9){
            number /= 10;
            n++;
        }
        for(int i = 0;i < n;i++){
            result = result.substring(0,result.length()-2);
        }
        return result;
    }


}

