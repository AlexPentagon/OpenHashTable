package sample;

import java.net.URL;
import java.util.ResourceBundle;


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
            for(int i = 0;i < 1000;i++){
                hashTable.add(i);
            }
            RESULT.clear();
            hashTable.add((String.valueOf(FIELDINSERT.getCharacters())));
            RESULT.appendText("Now size is: " + (hashTable.size()) +
                            "\nInserted: " + String.valueOf(FIELDINSERT.getCharacters()));
            showTable(false,null);
        });
        searchB.setOnAction(event -> {
            RESULT.clear();
            Integer index = ( hashTable.searchIndex((String.valueOf(FIELDSEARCH.getCharacters()))));
            Object o = hashTable.search(String.valueOf(FIELDSEARCH.getCharacters()));

            if(o == null) RESULT.appendText("Can't search: " + String.valueOf(FIELDSEARCH.getCharacters()));
            else RESULT.appendText("Now size is: " + hashTable.size() + "\nSearched: " + o);

            showTable(true,index);

        });
        removeB.setOnAction(event -> {
            RESULT.clear();
            if(hashTable.remove(String.valueOf(FIELDREMOVE.getCharacters())))
            RESULT.appendText("Now size is: " + (hashTable.size() +
                    "\nRemoved: " + String.valueOf(FIELDREMOVE.getCharacters())));
            else RESULT.appendText( "Now size is: " + (hashTable.size() + "\nTable does not contain " +
                    String.valueOf(FIELDREMOVE.getCharacters())));
            showTable(false,null);
        });
        CLEAR.setOnAction(event -> {
            hashTable.clear();
            showTable(false,null);
        });




        assert insertB != null : "fx:id=\"insertB\" was not injected: check your FXML file 'sample.fxml'.";
        assert removeB != null : "fx:id=\"removeB\" was not injected: check your FXML file 'sample.fxml'.";
        assert searchB != null : "fx:id=\"searchB\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDINSERT != null : "fx:id=\"FIELDINSERT\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDREMOVE != null : "fx:id=\"FIELDREMOVE\" was not injected: check your FXML file 'sample.fxml'.";
        assert FIELDSEARCH != null : "fx:id=\"FIELDSEARCH\" was not injected: check your FXML file 'sample.fxml'.";

    }

    public void showTable(boolean isSearch,Integer index){


        VALUES.clear();
        for(int i = 0; i < hashTable.table.length;i++){
            VALUES.appendText("\n" + i + spaceMaker(i));
            if(hashTable.deleted[i]) VALUES.appendText(null);
            else VALUES.appendText("" + hashTable.table[i]);
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
        return result.toString();
    }


}

