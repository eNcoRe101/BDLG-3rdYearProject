package comp30040;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rich on 27/10/2015.
 */
public class NetworkEvent {
    private int eventId;
    private ArrayList<Actor> actorsAtEvent;

    public NetworkEvent(Actor[] theActors, int eventId){
        this.eventId = eventId;
        actorsAtEvent = new ArrayList<>(Arrays.asList(theActors));
    }

    public int getNumberOfActorsAtEvent(){
        return actorsAtEvent.size();
    }
}
