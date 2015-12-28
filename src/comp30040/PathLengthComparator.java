/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import java.util.ArrayList;
import java.util.Comparator;

/**
 *
 * @author Rich
 */
public class PathLengthComparator implements Comparator<ArrayList<PathPair>>{
    
    @Override
    public int compare(ArrayList<PathPair> one, ArrayList<PathPair> two){
        return one.size() - two.size();
    }
}
