/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Rich
 */
public class PathLengthComparator implements Comparator<List<PathPair>>{
    
    @Override
    public int compare(List<PathPair> one, List<PathPair> two){
        return one.size() - two.size();
    }
}
