package comp30040;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by rich on 27/10/2015.
 */
public class NetworkEvent {
    private final int eventId;
    private final String eventLabel;
    private ArrayList<Actor> actorsAtEvent;

    public NetworkEvent(Actor[] theActors, int eventId, String eventLabel){
        this.eventId = eventId;
        this.eventLabel = eventLabel;
        actorsAtEvent = new ArrayList<>(Arrays.asList(theActors));
    }
    
    public NetworkEvent(int eventId, String eventLabel){
        this.eventId = eventId;
        this.eventLabel = eventLabel;
        actorsAtEvent = new ArrayList<>();
    }
    
    public void addActor(Actor a){
        actorsAtEvent.add(a);
    }
    
    public Actor[] getActorsAtEvent(){
        return actorsAtEvent.toArray(new Actor[actorsAtEvent.size()]);
    }
    
    public boolean isActorAtEvent(Actor a){
        return this.actorsAtEvent.contains(a);
    }
    
    public int getNumberOfActorsAtEvent(){
        return actorsAtEvent.size();
    }
    
    public String getLabel(){
        return eventLabel;
    }
    
    public int getEventId(){
        return this.eventId;
    }
    
    @Override
    public String toString(){
        return eventLabel + "\n" + actorsAtEvent;
    }
}
