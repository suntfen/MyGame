package castle;

public class Ability {
	private String description;
	private int val;
	
	public Ability(String description, int val){
        this.description = description;
        this.val = val;
    }
	
	public int getVal(){
		return val;
	}
	
	public String getDescription(){
		return description;
	}
	
	public void setVal(int val){
		this.val = val;
	}

	@Override
	public String toString() {
		return description+":"+val;
	}
}
