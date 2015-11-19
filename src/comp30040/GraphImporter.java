package comp30040;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by rich on 04/11/2015.
 */
public class GraphImporter {
    private File theFile = null;
    private ArrayList<String> fileLineArray = null;
    private Actor[] actors = null;
    private NetworkEvent[] events = null;

    public GraphImporter(String fileName) throws FileNotFoundException {
        theFile = new File(fileName);
        fileLineArray = readFile();
        getEventsFromFile();
        //for(NetworkEvent e : events)
        //    System.out.println(e);
    }

    private ArrayList<String> readFile(){
        Scanner theFileScanner = null;
        ArrayList<String> matrixHolder = new ArrayList<String>();
        try{
            theFileScanner = new Scanner(theFile);
            while (theFileScanner.hasNextLine()){
                matrixHolder.add(theFileScanner.nextLine());
            }
        }
        catch (Exception e){
            System.out.println("Could not read file correctly: " + e);
        }
        finally {
            if (theFileScanner != null) theFileScanner.close();
        }
        return matrixHolder;
    }

    private Actor[] getActorsFromFile(){
        ArrayList<Actor> tempActors = new ArrayList<Actor>();
        int currentActorID = 0;
        for(String line : fileLineArray) {
            String currentValue = line.split(",")[0];
            if(currentValue.length() > 0){
                currentActorID++;
                tempActors.add(new Actor(currentActorID,currentValue));
            }
        }
        return tempActors.toArray(new Actor[tempActors.size()]);
    }

    private void getEventsFromFile(){
        ArrayList<NetworkEvent> tempArrayOfEvents = new ArrayList<NetworkEvent>();
        ArrayList<Actor> tempActors = new ArrayList<Actor>();
        
        int numberOfEventsSeen = 0;

        for(String e : fileLineArray.get(0).split(",")){
            numberOfEventsSeen++;
            tempArrayOfEvents.add(new NetworkEvent(numberOfEventsSeen, e));
        }
        
        for(int i = 1; i < fileLineArray.size(); i++){
            String[] lineAsArray = fileLineArray.get(i).split(",");
            //for(String s : lineAsArray) System.out.println(s);
            Actor currentActor = new Actor(i, lineAsArray[0]);
            tempActors.add(currentActor);
            for(int j = 1; j < lineAsArray.length; j++)
            {
                if(lineAsArray[j].equals("1"))
                    tempArrayOfEvents.get(j).addActor(currentActor);
            }
        }

        this.events = tempArrayOfEvents.toArray(new NetworkEvent[tempArrayOfEvents.size()]);
        if(this.actors == null)
            this.actors = tempActors.toArray(new Actor[tempActors.size()]);
    }
    
    public Actor[] getActors(){
        return actors;
    }
    
    public int getNumberOfActors(){
        return actors.length;
    }
    
    public NetworkEvent[] getEvents(){
        return events;
    }
}
