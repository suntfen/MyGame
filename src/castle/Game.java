package castle;

import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    private Room recoverPoint;
    private Person player;
    private HashMap<String, Handler> handlers = new HashMap<String,Handler>();
    private String []ability = {"la","math","eng","phy","chem","bio","pol","his","geog"};
	private String []des = {"语文","数学","英语","物理","化学","生物","政治","历史","地理"};
	private int limitTime = 30;
    
    class Handler {
    	public void doCmd(String word){}
    	public boolean isBye() {return false;}
    }
        
    public Game() {
    	handlers.put("go", new Handler() {
    		@Override
    		public void doCmd(String word) {
    			goRoom(word);
    		}
    	});
    	handlers.put("take", new Handler() {
    		@Override
    		public void doCmd(String word) {
    			takeItems(word);
    		}
    	});
    	handlers.put("drop", new Handler() {
    		@Override
    		public void doCmd(String word) {
    			dropItems(word);
    		}
    	});
    	handlers.put("unlock", new Handler() {
    		@Override
    		public void doCmd(String word) {
    			unlockRoom(word);
    		}
    	});
    	handlers.put("attack", new Handler() {
    		@Override
    		public void doCmd(String word) {
    			attackMonster();
    		}
    	});
    	handlers.put("up", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			upAttribute(word);
    		}
    	});
    	handlers.put("levelup", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			upPersonlevel();
    		}
    	});
    	handlers.put("show", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			showPrompt();
    		}
    	});
    	handlers.put("help", new Handler(){
    		@Override
    		public void doCmd(String word) {
    			showGuide();
    		}
    	});
    	handlers.put("bye", new Handler(){
    		@Override
    		public boolean isBye() {
    			return true;
    		}
    	});
        createRooms();
        createPerson();
    }

    private void createRooms(){
        Room home_level3, home_mombedroom, home_guestroom,home_rooftop;
        Room home_level2, home_mybedroom, home_study, home_toilet;
        Room home_level1, home_kitchen, home_livingroom, home_dinningroom;
        Room home_basement;
        Room littletown;
      
        //	制造房间
        home_level3 = new Room("城堡三楼");
        home_mombedroom = new Room("妈妈的房间");
        home_guestroom = new Room("客房");
        home_rooftop = new Room("天台");
        
        home_level2 = new Room("城堡二楼");
        home_mybedroom = new Room("我的房间");
        home_study = new Room("书房");
        home_toilet = new Room("厕所");
        
        home_level1 = new Room("城堡一楼");
        home_livingroom = new Room("客厅");
        home_kitchen = new Room("厨房");
        home_dinningroom = new Room("餐厅");
        
        home_basement= new Room("地下室");
        
        littletown = new Room("小镇");
        
        //	初始化房间的出口
        home_rooftop.setExit("inside", home_level3);
        
        home_mombedroom.setExit("outside", home_level3);
        home_guestroom.setExit("outside", home_level3);
        home_level3.setExit("rooftop", home_rooftop);
        home_level3.setExit("mombedroom", home_mombedroom);
        home_level3.setExit("guestroom", home_guestroom);
        home_level3.setExit("downstairs", home_level2);
        
        home_study.setExit("outside", home_mybedroom);
        home_mybedroom.setExit("study", home_study);
        home_mybedroom.setExit("outside", home_level2);
        home_toilet.setExit("outside", home_level2);
        home_level2.setExit("upstairs", home_level3);
        home_level2.setExit("toilet", home_toilet);
        home_level2.setExit("mybedroom", home_mybedroom);
        home_level2.setExit("downstairs", home_level1);
        
        home_kitchen.setExit("outside", home_level1);
        home_livingroom.setExit("outside", home_level1);
        home_dinningroom.setExit("outside", home_level1);
        home_level1.setExit("upstairs", home_level2);
        home_level1.setExit("kitchen", home_kitchen);
        home_level1.setExit("livingroom", home_livingroom);
        home_level1.setExit("dinningroom", home_dinningroom);
        home_level1.setExit("basement", home_basement);
        home_level1.setExit("littletown", littletown);
        
        home_basement.setExit("upstairs", home_level1);
        
        littletown.setExit("castle", home_level1);
        
        // 初始化房间门锁
        littletown.changeDoorStat();
        home_basement.changeDoorStat();
        home_rooftop.changeDoorStat();
        home_study.changeDoorStat();
        home_dinningroom.changeDoorStat();
        
        
        // 初始化房间内物品
        home_dinningroom.setItems("littletown_key");
        home_mombedroom.setItems("rooftop_key");
        home_rooftop.setItems("study_key");
        home_guestroom.setItems("basement_key");
        home_basement.setItems("littletown_key");
        
        // 初始点
        recoverPoint = home_mybedroom;
        home_mybedroom.setSafety(true);
        currentRoom = home_mybedroom;
        littletown.setSafety(true);
    }
    
    private void createPerson(){
    	player = new Person("player",1,0,0);
    	for(int i = 0;i<ability.length;i++){
    		player.setAttribute(ability[i],des[i], (int)(Math.random()*5)+1);
    	}
    }
    
    private CourseMonster createMonster(){
    	int level = (int)(Math.random()*player.getLevel())+2;
    	int abi = (int)(Math.random()*9);
    	CourseMonster monster = new CourseMonster("Monster",level,level*100+(int)(Math.random()*level*10));
    	monster.setAttribute(ability[abi],des[abi], (int)(Math.random()*(level+2))+1);
    	return monster;
    }

    private void printWelcome() {
    	System.out.println("遥远的声音：快起来，别睡了，上课迟到了!");
    	try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println();
        try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("...");
        try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("谁叫我？这里是哪里？好像是我的房间");
        try {Thread.sleep(800);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("头好痛...");
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("原来我昨天在床上做作业，太累所以睡过去了");
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("天呐，今天还要上课，得赶去学校，还剩"+limitTime+"分钟了");
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("苍天呐，大地啊，为什么我偏偏住在这硕大的城堡里...");
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("可是我该怎么做？我怎么什么都不会了？");
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("让我看看我身上有什么...");
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("咦，口袋里有本操作手册，上面写着需要输入help来打开");
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("*  高傲的旁白：");
		System.out.println("*	请输入help");
		System.out.println();
    }

    // 以下为用户命令 
    // 进入房间
    protected void goRoom(String direction) {
    	Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
        	System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	System.out.println("*  高傲的旁白：");
            System.out.println("*	你在干什么，那个地方都不存在");
            if (currentRoom.containMonster()){
            	System.out.println("*	逃离失败，你浪费了1分钟时间 ");
            	minusTime();
            }
            System.out.println();
        }
        else if(nextRoom.getLockStat()){
        	System.out.println("+----+");
			System.out.println("|    |");
			System.out.println("|    |");
			System.out.println("|0   |");
			System.out.println("|    |");
			System.out.println("+----+");
        	System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	System.out.println("*  高傲的旁白：");
        	System.out.println("*	门锁住了，如果你有key可以使用unlock加上房间位置开门，不然需要想办法找到key");
        	if (currentRoom.containMonster()){
        		System.out.println("*	逃离失败，你浪费了1分钟时间");
        		minusTime();
            }
        	System.out.println();
        }
        else if(!currentRoom.containMonster()||(currentRoom.containMonster()&&(Math.random()>0.5))){
        	if(!currentRoom.containMonster()){
        		if((Math.random()>0.5)&&(!currentRoom.getSafety())){
        			currentRoom.setMonster(createMonster());
        		}
        	}
        	else {
        		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		System.out.println("*  高傲的旁白：");
	            System.out.println("*	恭喜你逃离成功了");
        	}
        	currentRoom = nextRoom;
        	if (direction.equals("littletown") ){
        		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		System.out.println("*  高傲的旁白：");
	            System.out.println("*	恭喜你终于离开了城堡，可以去上学了");
	            System.out.println("*	快输入bye离开这里冲向学校吧");
	            System.out.println();
	            limitTime--;
        	}
        	else{
            	minusTime();
            	showPrompt();
        	}
    	}
        else{
        	System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    		System.out.println("*  高傲的旁白：");
    		System.out.println("*	逃离失败，你浪费了1分钟时间");
    		minusTime();
            System.out.println();
    	}
    }
    
    // 对门进行解锁
    protected void unlockRoom(String direction) {
        Room nextRoom = currentRoom.getExit(direction);
        if (nextRoom == null) {
        	System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	System.out.println("*  高傲的旁白：");
        	System.out.println("*	你在干什么，那个地方都不存在");
        	System.out.println();
        }
        else {
        	if(nextRoom.getLockStat()){
        		if(player.containsItems(direction+"_key")){
        		nextRoom.changeDoorStat();
        		System.out.println("  -.");
        		System.out.println("+-| '+");
    			System.out.println("| |  |");
    			System.out.println("| |  |");
    			System.out.println("| |0 |");
    			System.out.println("| |  |");
    			System.out.println("+-| .+");
    			System.out.println("  -'");
        		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		System.out.println("*  高傲的旁白：");
        		System.out.println("*	门锁开了，可以用go移动了");
        		System.out.println();
        		}
        		else{
        			System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        			System.out.println("*  高傲的旁白：");
            		System.out.println("*	你还没有对应的钥匙，得想办法找到钥匙才能开门");
            		System.out.println();
        		}
        	}
        	else{
        		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        		System.out.println("*  高傲的旁白：");
        		System.out.println("*	这里本来就没锁");
        		System.out.println();
        	}
        }
    }
    
    // 获得物品
    protected void takeItems(String goods){
    	if (currentRoom.containsItems(goods)){
    		currentRoom.delItems(goods);
    		player.setItems(goods);
    		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    		System.out.println("*  高傲的旁白：");
    		System.out.println("*	你犹豫地拿走了"+goods);
    		System.out.println();
    	}
    	else{
    		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    		System.out.println("*  高傲的旁白：");
    		System.out.println("*	你想找的东西不存在，我可变不出来");
    		System.out.println();
    	}	
    }
    
    // 丢弃物品
    protected void dropItems(String goods){
    	if (player.containsItems(goods)){
    		currentRoom.setItems(goods);
    		player.delItems(goods);
    		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    		System.out.println("*  高傲的旁白：");
    		System.out.println("*	太无情了，你把"+goods+"抛弃了");
    		System.out.println();
    	}
    	else{
    		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    		System.out.println("*  高傲的旁白：");
    		System.out.println("*	你身上没这东西啊，要不把你自己扔了?");
    		System.out.println();
    	}
    }
    
    // 角色升级
    protected void upPersonlevel(){
		player.minusXP(100*player.getLevel());
		player.addPoint(1);
		player.levelUp();
    }
    
    // 角色属性加点
    protected void upAttribute(String attribute){
    	if(canupAttribute()){
    		if(player.containsAbility(attribute)){
	    		if(player.getAttribute(attribute)<10){
		    		player.upAttribute(attribute,1);
		    		player.minusPoint(1);
		    		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		    		System.out.println("*  高傲的旁白：");
	        		System.out.println("*	你提升了"+attribute+"能力1点 ");
	        		System.out.println("*	目前各科目能力值：" );
	            	System.out.println("*	"+player.getAllAttribute());
	            	System.out.println("*	剩余属性点:	" + player.getPoint());
	        		System.out.println();
	    		}
	    		else{
	    			System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
	    			System.out.println("*  高傲的旁白：");
	        		System.out.println("*	你该科目能力已经达到巅峰，再加会爆的");
	        		System.out.println();
	    		}
    		}
    		else{
    			System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    			System.out.println("*  高傲的旁白：");
        		System.out.println("*	你并没有该项能力");
        		System.out.println();
    		}
    	}
    	else{
    		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    		System.out.println("*  高傲的旁白：");
    		System.out.println("*	并没有可以加的属性点，别想作弊哦，i watch you");
    		System.out.println();
    	}
    }
    
    // 攻击怪物
    protected void attackMonster(){
    	if(currentRoom.containMonster()){
    		CourseMonster monster = currentRoom.getMonster(0);
    		String s = "";
    		for (int i=0;i<ability.length;i++){
    			s = ability[i];
    			if(monster.containsAbility(s))
    				break;
    		}
    		int diff = player.getAttribute(s)-monster.getAttribute(s);
    		diff = diff>0?diff:0;
    		if(Math.random()<(0.5+diff*Math.random())){
    			System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    			System.out.println("*  高傲的旁白：");
        		System.out.println("*	获得胜利，并得到了"+monster.getXP()+"点经验");
    			player.addXP(monster.getXP());
    			boolean flag = false;
    			while(canLevelup()){
    				upPersonlevel();
    				flag = true;
    			}
    			if(flag){
    				System.out.println("*	你已经升到了"+player.getLevel()+"级");
    				System.out.println("*	有"+player.getPoint()+"点未使用属性点");
    			}
    			System.out.println("*	可以继续前进了");
    			System.out.println();
    			currentRoom.delMonster(monster);
    		}
    		else{
    			System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    			System.out.println("*  高傲的旁白：");
        		System.out.println("*	你挑战失败，回到初始位置");
        		System.out.println();
        		currentRoom = recoverPoint;
        		showPrompt();
    		}
    	}
    }
    
    // 当前信息显示
    protected void showPrompt(){
    	System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	System.out.println("*  高傲的旁白：");
		System.out.println("*	你现在的等级为：	" + player.getLevel());
    	System.out.println("*	已有经验值：	" + player.getXP());
    	System.out.println("*	各科目能力值：" );
    	System.out.println("*	"+player.getAllAttribute());
    	System.out.println("*	剩余属性点:	" + player.getPoint());
    	if(player.containsItems()){
    		System.out.println("*	你身上带着：	"+ player.getItemsList());
    	}
    	else{
    		System.out.println("*	你个穷光蛋，身上啥也没有");
    	}
    	System.out.println("*	");
    	System.out.println("*	现在所在位置：	"+ currentRoom);
    	if(currentRoom.containsItems()){
			System.out.println("*	这里有物品：	"+ currentRoom.getItemsList());
    	}
    	else{
    		System.out.println("*	这里什么物品也没有");
    	}
    	System.out.println("*	好像有几个门:	"+currentRoom.getExitDesc());
    	System.out.println("*	");
    	System.out.println("*	距离上课迟到只剩： "+ limitTime+"分钟！");
    	System.out.println();
    	if(currentRoom.containMonster()){
    		System.out.println("  .-'      '-.");
    		System.out.println(" /            \\");
    		System.out.println("|              |");
			System.out.println("|,  .-.  .-.  ,|");
			System.out.println("| )(__/  \\__)( |");
			System.out.println("|/     /\\     \\|");
			System.out.println("(_     ^^     _)");
			System.out.println(" \\__|IIIIII|__/");
			System.out.println("  |-\\IIIIII/-|");
		    System.out.println("  \\          /");
		    System.out.println("   `--------`");
    		System.out.println("*  ！！！警  告！！！");
    		System.out.println("*	出现了课程怪兽！！！！出现了课程怪兽！！！！出现了课程怪兽！！！！");
			CourseMonster monster = currentRoom.getMonster(0);
			System.out.println("*	怪物等级为：	" + monster.getLevel());
	    	System.out.println("*	击败可获得经验：	" + monster.getXP());
	    	System.out.println("*	怪物各科目能力值：" );
	    	System.out.println("*	"+monster.getAllAttribute());
	    	System.out.println("*	你可以使用attack进行攻击，或者使用go逃跑，如果失败将遭受怪物的攻击");
	    	System.out.println();
    	}
    }
    
    // 操作指南显示
    protected void showGuide() {
    	System.out.println("┎═══════┒");
		System.out.println("║   操    ┃");
		System.out.println("║   作    ┃");
		System.out.println("║   手    ┃");
		System.out.println("║   册    ┃");
		System.out.println("┖═══════┚");
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("让我来学习学习怎么操作");
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("┎═══════┰═══════┒");
		System.out.println("║﹎﹎﹎﹎┃﹎﹎﹎﹎║");
		System.out.println("║﹎﹎﹎﹎┃﹎﹎﹎﹎║");
		System.out.println("║﹎﹎﹎﹎┃﹎﹎﹎﹎║");
		System.out.println("║﹎﹎﹎﹎┃﹎﹎﹎﹎║");
		System.out.println("┖═══════┸═══════┚");
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("*  操作手册：");
		System.out.println("*	可以做的命令有：");
        System.out.println("*	help： 读取操作手册");
        System.out.println("*	show： 查看人物当前信息");
        System.out.println("*	go： 加上位置名可以去到想去的地方，如：go outside");
        System.out.println("*	unlock： 可以开启锁着的房间，前提是你有钥匙");
        System.out.println("*	up： 加上属性名可以提高科目能力值，如：up chi" );
        System.out.println("*	take： 加上物品名可以带走对应的物品，如：take littletown_key" );
        System.out.println("*	drop： 加上物品名丢弃携带在身上的物品，如：drop littletown_key" );
        System.out.println("*	attack： 可以使用attack对怪物进行攻击" );
        System.out.println("*	bye： 可以离开这个烦人的世界");
        System.out.println();
    }
        
    public boolean canLevelup(){
    	boolean flag = false;
    	if(player.getXP()>=100*(2*player.getLevel()+1))
    		flag = true;
    	return flag;
    }
    
    public boolean canupAttribute(){
    	boolean flag = false;
    	if(player.getPoint()>=1)
    		flag = true;
    	return flag;
    }
    
    public void minusTime(){
    	limitTime--;
    	if(limitTime==0)
    	{
    		System.out.println("*	你没有在规定的时间离开城堡，被重置回初始点mybedroom");
    		currentRoom = recoverPoint;
    		limitTime = 30;
    	}
    }
    
    public void play(){
    	Scanner in = new Scanner(System.in);
    	while ( true ) {
    		String line = in.nextLine();
    		String[] words = line.split(" ");
    		if(words[0].equals(""))
    		{
    			System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    			System.out.println("*  高傲的旁白：");
        		System.out.println("*	你多输入了空格");
    		}
    		else if(words.length!=0)
    		{
    			if (handlers.containsKey(words[0]))
    			{
    				Handler handler = handlers.get(words[0]);
		    		String value = "";
		    		if (words.length>1)
		    			value = words[words.length-1];
		    		if (handler!=null)
		    		{
		    			handler.doCmd(value);
		    			if (handler.isBye())
		    				break;
		    		}
    			}
    			else{
    				System.out.println("*  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        			System.out.println("*  高傲的旁白：");
            		System.out.println("*	并没有这条指令，你得输入help好好看看操作手册了");
    			}
    		}
    	}
    	in.close();
    }
	
	public static void main(String[] args) {
		
		Game game = new Game();
		game.printWelcome();
		game.play();
        
		System.out.println("...");
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
		System.out.println("咦，什么情况？");
		try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("妈妈：该起床啦，再不起来要迟到了！！！");
        try {Thread.sleep(1000);} catch (InterruptedException e) {e.printStackTrace();}
        System.out.println("啊，原来是一场梦");
	}

}
