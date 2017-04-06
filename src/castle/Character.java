package castle;

import java.util.ArrayList;
import java.util.HashMap;

public class Character {
	private String description;
	private HashMap<String,Ability> attribute = new HashMap<String,Ability>();
	private ArrayList<String> items = new ArrayList<String>();
	private int level;
	private int xp;
	
	public Character(String description, int level, int xp){
		this.description = description;
		this.level = level;
		this.xp = xp;
	}
	
	public void setAttribute(String id ,String course ,int val){
		Ability ability = new Ability(course, val);
		attribute.put(id,ability);
	}
	
	public void setItems(String goods)
    {
    	this.items.add(goods);
    }
    
    public void delItems(String goods)
    {
    	this.items.remove(this.items.indexOf(goods));
    }
	
    public boolean containsItems(String goods)
    {
    	return items.contains(goods);
    }
    
    public boolean containsItems()
    {
    	return (!items.isEmpty());
    }
    
    public boolean containsAbility(String s)
    {
    	return attribute.containsKey(s);
    }
    
	public int getAttribute(String id){
		Ability ability = attribute.get(id);
		return ability.getVal();
	}
	
	public String getDes(){
		return this.description;
	}
	
	public int getXP(){
		return xp;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getAllAttribute()
    {
    	StringBuffer sb = new StringBuffer();
    	for(String dir:attribute.keySet())
    	{
    		sb.append(dir);
    		sb.append(attribute.get(dir));
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
	
	public void addXP(int xp){
		this.xp+=xp;
	}
	
	public void minusXP(int xp){
		if(this.xp>=xp)
			this.xp-=xp;
	}
	
	public void levelUp(){
		level++;
	}
	
	public void upAttribute(String id,int val){
		Ability ability = attribute.get(id);
		ability.setVal(ability.getVal()+val);
	}
}
