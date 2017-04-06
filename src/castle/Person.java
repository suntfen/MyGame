package castle;

public class Person extends Character{
	private int point;
	
	public Person(String des, int level, int xp,int point) {
		super(des, level, xp);
		this.point = point;
	}

	public int getPoint(){
		return point;
	}
	
	public void addPoint(int point){
		this.point+= point;
	}
	
	public void minusPoint(int point){
		if(this.point>=point)
			this.point-= point;
	}
}
