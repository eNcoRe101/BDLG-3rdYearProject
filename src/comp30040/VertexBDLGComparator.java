/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp30040;

import java.util.Comparator;

/**
 *
 * @author rich
 * @param <VertexBDLG>
 */
public class VertexBDLGComparator implements Comparator<VertexBDLG>
{
    @Override
    public int compare(VertexBDLG v, VertexBDLG u)
    {
        if (v.getPriority() < u.getPriority())
        {
            return -1;
        }
        if (v.getPriority() > u.getPriority())
        {
            return 1;
        }
        return 0;
    }
}
