package com.github.sourzo.a_rithist.general;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class VocabTable {
    //Fields-----------------------------------------------------------------------
    /**Location of vocab lists - currently the user home directory*/

    /**The names of the columns from the input csv table.*/
    String[] colNames;

    /**A list of vocabulary mappings. Each map-item in the list represents a word,
     * and each element of that map is a different grammatical form of that word.
     * <P> Each list entry (HashMap) represents a row in the input data table.
     * The HashMap key is the column name. The HashMap value
     * is the table entry in that column for that row.</P>*/
    public List<HashMap<String,String>> data = new ArrayList<>();


    //Constructors-----------------------------------------------------------------

    /**
     * Reads a csv file with headers to create a vocabulary dataset {@link #data}
     * the form of a list, where each item in the list is a row from the text file.
     * Every row is mapped to the column header.
     */
    public VocabTable(AppRes appRes, String filepath) {
        try (BufferedReader br = new BufferedReader(appRes.getFileStream(filepath))) {
            String line;
            colNames = br.readLine().split(","); //First line = headers
            while ((line = br.readLine()) != null) { //Subsequent lines = vocab
                String[] values = line.split(",");
                HashMap<String,String> newRow = new HashMap<>();
                for (int i = 0; i < colNames.length; i++) {
                    if (values.length <= i){
                        newRow.put(colNames[i],"");
                    } else {
                        newRow.put(colNames[i],values[i]);
                    }
                }
                data.add(newRow);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**Create a vocabList using an existing, extracted list*/
    public VocabTable(String[] colNames, List<HashMap<String,String>>vocabList) {
        this.colNames = colNames;
        this.data = vocabList;
    }

    //Properties--------------------------------------------------------------------
    public int size(){
        return data.size();
    }
    public Set<String> getColnames(){
        return new HashSet<>(Arrays.asList(colNames));
    }
    //Extraction methods------------------------------------------------------------

    /**Create a new vocab list where values in the
     * specified column match the specified matchWord
     * @param colname The name of the "column" in the data table
     * @param matchWord The value in that column to match on*/
    public VocabTable filterMatches(String colname, String matchWord) {
        List<HashMap<String,String>> rows = new ArrayList<>();
        for (HashMap<String,String> row : data) {
            if (row.get(colname).toLowerCase().equals(matchWord.toLowerCase())) {
                rows.add(row);
            }
        }
        return new VocabTable(colNames, rows);
    }

    /**Create a new VocabList by filtering this one by row numbers*/
    public VocabTable filterRows(Set<Integer> rowSet) {
        List<HashMap<String,String>> filteredList = new ArrayList<>();
        for (Integer i : rowSet) {
            filteredList.add(data.get(i));
        }
        return new VocabTable(colNames, filteredList);
    }

    /**Get a random selection of rows
     * @param numberOfRows the number of rows to select at random
     * @return a new VocabTable with a subset of the original rows*/
    public VocabTable getRandomRows(int numberOfRows) {
        Set<Integer> selectedRows = new HashSet<>();
        Random rand = new Random();
        while (selectedRows.size() < numberOfRows) {
            selectedRows.add(rand.nextInt(data.size()));
        }
        return filterRows(selectedRows);
    }

    /**Get a specific column from the vocabList*/
    public VocabTable getColumn(String colName){
        List<HashMap<String,String>> filteredList = new ArrayList<>();
        for (HashMap<String,String> map : data) {
            HashMap<String,String> hm = new HashMap<>();
            hm.put(colName, map.get(colName));
            filteredList.add(hm);
        }
        return new VocabTable(colNames, filteredList);
    }

    /**Get an item of the VocabTable
     * @param colname Column name
     * @param rowNum Row number
     * @return VocabTable entry as a string*/
    public String get(String colname, int rowNum){
        if (rowNum >= data.size()) {
            Log.e("Error","rowNum exceeds size of table");
            throw new RuntimeException(){};
        } else if (!data.get(rowNum).containsKey(colname)){
            Log.e("Error","column " + colname + " does not exist in table");
            throw new RuntimeException(){};
        } else {
            return data.get(rowNum).get(colname);
        }
    }

    /**Get an item of the VocabTable
     * @param colname Column name
     * @param rowNum Row number
     * @return VocabTable entry as a string*/
    public String get(int rowNum, String colname){
        if (rowNum >= data.size()) {
            Log.e("Error","rowNum exceeds size of table");
            throw new RuntimeException(){};
        } else if (!data.get(rowNum).containsKey(colname)){
            Log.e("Error","column " + colname + " does not exist in table");
            throw new RuntimeException(){};
        } else {
            return data.get(rowNum).get(colname);
        }
    }

    public int getRow(String colname, String value) {
        for (int i = 0; i < this.size(); i++) {
            if (data.get(i).get(colname).equals(value)){
                return i;
            }
        }
        return Integer.MAX_VALUE; //this shouldn't happen
    }

}
