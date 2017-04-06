package castle;

import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String description;
    private HashMap <String, Room> exits= new HashMap <String, Room>();
    private ArrayList<String> items = new ArrayList<String>();
    private ArrayList<CourseMonster>monster = new ArrayList<CourseMonster>();
    private boolean locked = false;
    private boolean safe = false;

    public Room(String description) 
    {
        this.description = description;
    }
    
    public void setExit(String dir ,Room room)
    {
    	exits.put(dir, room);
    }
    
    public void setMonster(CourseMonster monster){
		this.monster.add(monster);
    }
    
    public void setItems(String goods)
    {
    	this.items.add(goods);
    }
    
    public void delItems(String goods)
    {
    	this.items.remove(this.items.indexOf(goods));
    }
    
    public void delMonster(CourseMonster monster){
    	if(this.monster.contains(monster)){
    		this.monster.remove(this.monster.indexOf(monster));
    	}
    }
    
    public boolean containsItems(String goods)
    {
    	return items.contains(goods);
    }
    
    public boolean containsItems()
    {
    	return (!items.isEmpty());
    }
    
    public boolean containMonster(){
    	return (!this.monster.isEmpty());
    }
    
    public void changeDoorStat ()
    {
    	if (!this.locked)
    		this.locked = true;
    	else
    		this.locked = false;
    }
    
    public void setSafety(boolean safe){
    	this.safe = safe;
    }

    @Override
    public String toString()
    {
        return description;
    }
    
    public CourseMonster getMonster(int indx){
    	return monster.get(indx);
    }
    
    public boolean getLockStat(){
    	return locked;
    }
    
    public boolean getSafety(){
    	return this.safe;
    }
    
    public String getExitDesc()
    {
    	StringBuffer sb = new StringBuffer();
    	for(String dir:exits.keySet())
    	{
    		sb.append(dir);
    		sb.append(":");
    		sb.append(exits.get(dir));
    		sb.append(" ");
    	}
    	return sb.toString();
    }
    
    public String getItemsList()
    {
    	StringBuffer sb = new StringBuffer();
    	for(String dir:this.items)
    	{
    		sb.append(dir);
    		sb.append(" ");
    	}
    	return sb.toString();
    }
    
    public Room getExit(String direction)
    {
    	return exits.get(direction);
    }
}