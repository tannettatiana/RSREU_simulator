import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.tiled.TiledMap;

import java.util.HashMap;
import java.util.Map;

public class Game extends StateBasedGame {

    public static final String GAME_NAME = "Puppy university";
    public static final int CONGRATULATIONS = -2;
    public static final int INSTRUCTION = -1;
    public static final int MENU = 0;
    public static final int FIRST_FLOOR_RIGHT = 1;
    public static final int SECOND_FLOOR_RIGHT = 2;
    public static final int SECOND_FLOOR = 3;
    public static final int FIRST_FLOOR_CENTER = 4;
    public static final int FIRST_FLOOR_LEFT = 5;
    public static final int THIRD_FLOOR = 6;


    public static final int puppyX = 500;
    public static final int puppyY = 205;

    public static final double speed = 0.1;
    public double startTime, endTime;
    public Animation animation, animationUp, animationDown, animationLeft, animationRight;
    public Animation animationRunUp, animationRunDown, animationRunLeft, animationRunRight;
    public Animation animationStopUp, animationStopDown, animationStopLeft, animationStopRight;
    public Sound sound;
    public int stairs = 1;
    public int x, y;
    public double xPos, yPos;
    public int previousState = 0;
    public int auditoryState;
    public int auditory;
    public String auditoryString;
    public String [] auditoryFirstFloorRight = {"104", "105", "106", "106a", "107", "108", "109", "110", "111", "111a"};
    public String [] auditorySecondFloorRight = {"201", "202", "203", "203a", "204", "204a"};
    public String [] auditorySecondFloor = {"206", "208a", "209", "209a", "210",
            "211", "212", "213", "214", "218", "221", "221a", "223", "224", "225",
            "226", "227", "228", "228a", "229", "230", "231", "232", "234", "235",
            "237", "238", "241", "243", "244", "245", "247", "248", "249", "250",
            "252", "254", "255", "257", "258", "260", "261", "262", "264", "266", "267", "268", "270"};
    public String [] auditoryFirstFloorCenter = {"112", "113", "114", "115", "116", "117",
            "118", "119", "120", "121", "122", "123", "124", "125", "126", "127", "128",
            "128a", "135", "139", "141", "143", "145", "147", "149", "151", "153", "155", "155a", "157"};
    public String [] auditoryFirstFloorLeft = {"130a", "130", "132", "159", "134", "136"};

    public String [] auditoryThirdFloor = {"306", "308", "308a", "309", "309a", "310", "311", "312",
            "313", "314", "318", "321", "321a", "323", "324", "325", "326", "327", "328", "330a",
            "329", "330", "331", "332", "334", "335", "337", "338", "341", "343", "344", "345",
            "347", "348", "349", "350", "352", "354", "355", "357", "358", "360", "361", "362",
            "364", "366", "363", "268", "270", "304", "302", "307", "305", "303", "301"};
    public Map<Integer, String[]> auditoryDict = new HashMap<Integer, String[]>();
    public int [][] XYFirstFloorRight = {{-1608,-100,-80,-80,-80,-80,-80},
                                         {-160,-180,-180,-180,-180,-180,-180}};
    public int [][] XYSecondFloorRight = {{-1002,-1002,-1002,-1002,-1002,-1002,-1002},
                                          {-116,-116,-116,-116,-116,-116,-116}};
    public int [][] XYSecondFloor = {{-4843,-4939,-4167,-3005,-1840,-1270,-80},
                                     {-915,-915,-235,-200,-235,-915,-915}};
    public int [][] XYFirstFloorCenter = {{-2811,-2811,-2800,-1624,-466,-466,-466},
                                          {-280,-280,-235,-178,-235,-235,-235}};
    public int [][] XYFirstFloorLeft = {{-1142,-1142,-1142,-1142,-1142,-1142,-46},
                                         {-277,-277,-277,-277,-277,-277,-277}};

    public int [][] XYThirdFloor = {{-6211,-4939,-4167,-3005,-1840,-1270,-80},
                                     {-915,-915,-235,-200,-235,-915,-915}};

    public Game(String gamename) {

        super(gamename);

        auditoryDict.put(1, auditoryFirstFloorRight);
        auditoryDict.put(2, auditorySecondFloorRight);
        auditoryDict.put(3, auditorySecondFloor);
        auditoryDict.put(4, auditoryFirstFloorCenter);
        auditoryDict.put(5, auditoryFirstFloorLeft);
        auditoryDict.put(6, auditoryThirdFloor);

        x = XYFirstFloorRight[0][1];
        y = XYFirstFloorRight[1][1];

        xPos = x;
        yPos = y;

        try {
            sound = new Sound("data/music/win.wav");
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }

        this.addState(new Menu(MENU));
        this.addState(new Instruction(INSTRUCTION));
        this.addState(new Congratulations(CONGRATULATIONS));
        this.addState(new FirstFloorRight(FIRST_FLOOR_RIGHT));
        this.addState(new SecondFloorRight(SECOND_FLOOR_RIGHT));
        this.addState(new SecondFloor(SECOND_FLOOR));
        this.addState(new FirstFloorCenter(FIRST_FLOOR_CENTER));
        this.addState(new FirstFloorLeft(FIRST_FLOOR_LEFT));
        this.addState(new ThirdFloor(THIRD_FLOOR));

    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {

        SpriteSheet sprite;

        //анимации для героя при движении вперед
        sprite = new SpriteSheet("data/animations/dog_walk_up.png", 47, 43);//48, 42);
        animationUp = new Animation(sprite, 400);
        animationRunUp = new Animation(sprite, 200);
        sprite = new SpriteSheet("data/animations/dog_stop_up.png", 47, 43);
        animationStopUp = new Animation(sprite, 400);

        //анимации для героя при движении назад
        sprite = new SpriteSheet("data/animations/dog_walk_down.png", 47, 43);//48, 42);
        animationDown = new Animation(sprite, 400);
        animationRunDown = new Animation(sprite, 200);
        sprite = new SpriteSheet("data/animations/dog_stop_down.png", 47, 43);
        animationStopDown = new Animation(sprite, 400);

        //анимации для героя при движении влево
        sprite = new SpriteSheet("data/animations/dog_walk_left.png", 47, 43);//48, 42);
        animationLeft = new Animation(sprite, 400);
        sprite = new SpriteSheet("data/animations/dog_run_left.png", 47, 43);//48, 42);
        animationRunLeft = new Animation(sprite, 300);
        sprite = new SpriteSheet("data/animations/dog_stop_left.png", 47, 43);
        animationStopLeft = new Animation(sprite, 400);

        //анимации для героя при движении вправо
        sprite = new SpriteSheet("data/animations/dog_walk_right.png", 47, 43);//48, 42);
        animationRight = new Animation(sprite, 400);
        sprite = new SpriteSheet("data/animations/dog_run_right.png", 47, 43);//48, 42);
        animationRunRight = new Animation(sprite, 300);
        sprite = new SpriteSheet("data/animations/dog_stop_right.png", 47, 43);
        animationStopRight = new Animation(sprite, 400);

        container.setShowFPS(false);

        this.getState(MENU).init(container, this);
        this.getState(INSTRUCTION).init(container, this);
        this.getState(CONGRATULATIONS).init(container, this);
        this.getState(FIRST_FLOOR_RIGHT).init(container, this);
        this.getState(SECOND_FLOOR_RIGHT).init(container, this);
        this.getState(SECOND_FLOOR).init(container, this);
        this.getState(FIRST_FLOOR_CENTER).init(container, this);
        this.getState(FIRST_FLOOR_LEFT).init(container, this);
        this.getState(THIRD_FLOOR).init(container, this);

        this.enterState(MENU);
    }

    private double runOrWalk(Input input, Animation animationRun, Animation animationWalk, Game game){
        double currentSpeed;
        if (input.isKeyDown(Input.KEY_LSHIFT) || input.isKeyDown(Input.KEY_RSHIFT)){
            currentSpeed = speed * 3;
            game.animation = animationRun;
        } else {
            currentSpeed = speed;
            game.animation = animationWalk;
        }
        return currentSpeed;
    }

    public void checkAuditory(int objectID, int absMapX, int absMapY, int CongratulationState, StateBasedGame stateBasedGame, TiledMap map){
        int objX = map.getObjectX(0, objectID);
        int objY = map.getObjectY(0, objectID);
        int objW = map.getObjectWidth(0, objectID);
        int objH = map.getObjectHeight(0, objectID);
        if ((absMapX >= objX) && (absMapX <= objX + objW) && (absMapY >= objY) && (absMapY <= objY + objH)){
            Game game = (Game)stateBasedGame;
            game.endTime = System.nanoTime();
            sound.play();
            stateBasedGame.enterState(CongratulationState, new EmptyTransition(), new FadeInTransition());
        }
    }
    //функция для определения переходов по лестницам
    public void checkStairs(int objectID, int absMapX, int absMapY, int state, int stairs, int[][] array, StateBasedGame stateBasedGame, TiledMap map){
        int objX = map.getObjectX(1, objectID) - 3;
        int objY = map.getObjectY(1, objectID);
        int objW = map.getObjectWidth(1, objectID) + 3;
        int objH = map.getObjectHeight(1, objectID);
        if ((absMapX >= objX) && (absMapX <= objX + objW) && (absMapY >= objY) && (absMapY <= objY + objH)){
            Game game = (Game)stateBasedGame;
            game.stairs = stairs;
            game.x = array[0][stairs];
            game.y = array[1][stairs];
            game.xPos = game.x;
            game.yPos = game.y;
            stateBasedGame.enterState(state, new EmptyTransition(), new FadeInTransition());
        }
    }

    public void renderAuditoryRect(Graphics g, String auditory){
        g.setColor(new Color(255, 228, 181));
        g.fillRoundRect(845, -10, 170, 50, 10);
        g.setColor(Color.black);
        java.awt.Font font = new java.awt.Font("Verdana", java.awt.Font.BOLD, 15);
        TrueTypeFont fnt = new TrueTypeFont(font, true);
        g.setFont(fnt);
        g.drawString("Auditory: " + auditory, 855, 10);
    }

    public void updateFloor(GameContainer container, StateBasedGame stateBasedGame, int delta, TiledMap map, int absMapX, int absMapY){
        Game game = (Game)stateBasedGame;

        int objectLayer = map.getLayerIndex("obj");   //получение индекса слоя со стенами и прочими объектами, с которыми происходят коллизии
        Input input = container.getInput();

        if (game.auditoryState == stateBasedGame.getCurrentStateID()){
            checkAuditory(game.auditory, absMapX, absMapY, -2, stateBasedGame, map);
        }

        if (input.isKeyDown(Input.KEY_ESCAPE) || input.isKeyPressed(Input.KEY_ESCAPE)){
            try {
                Music music = new Music("data/music/menu.ogg");
                music.loop();
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
            stateBasedGame.enterState(0);
        }

        if (input.isKeyPressed(Input.KEY_F1)){
            game.previousState = stateBasedGame.getCurrentStateID();
            game.enterState(-1);
        }

        //определение коллизии при нажатии стрелки вправо
        if (input.isKeyDown(Input.KEY_RIGHT) || input.isKeyPressed(Input.KEY_RIGHT)) {
            double currentSpeed = runOrWalk(input, game.animationRunRight, game.animationRight, game);
            if ((map.getTileId((absMapX+37)/32, (absMapY+33)/32, objectLayer) == 0) && (map.getTileId((absMapX+37)/32, (absMapY + 12)/32, objectLayer) == 0)){
                game.xPos -= currentSpeed * delta;
                game.x = (int)game.xPos;
            }
        }

        //определение коллизии при нажатии стрелки влево
        if (input.isKeyDown(Input.KEY_LEFT) || input.isKeyPressed(Input.KEY_LEFT)) {
            double currentSpeed = runOrWalk(input, game.animationRunLeft, game.animationLeft, game);
            if ((map.getTileId((absMapX+10)/32, (absMapY+33)/32, objectLayer) == 0) && (map.getTileId((absMapX+10)/32, (absMapY + 12)/32, objectLayer) == 0)){
                game.xPos += currentSpeed * delta;
                game.x = (int)game.xPos;
            }
        }

        //определение коллизии при нажатии стрелки вверх
        if (input.isKeyDown(Input.KEY_UP) || input.isKeyPressed(Input.KEY_UP)) {
            double currentSpeed = runOrWalk(input, game.animationRunUp, game.animationUp, game);
            if ((map.getTileId((absMapX+12)/32, (absMapY+3)/32, objectLayer) == 0) && (map.getTileId((absMapX+31)/32, (absMapY+3)/32, objectLayer) == 0)){
                game.yPos += currentSpeed * delta;
                game.y = (int)game.yPos;
            }
        }

        //определение коллизии при нажатии стрелки вниз
        if (input.isKeyDown(Input.KEY_DOWN) || input.isKeyPressed(Input.KEY_DOWN)) {
            double currentSpeed = runOrWalk(input, game.animationRunDown, game.animationDown, game);
            if ((map.getTileId((absMapX+12)/32, (absMapY+39)/32, objectLayer) == 0) && (map.getTileId((absMapX+31)/32, (absMapY+39)/32, objectLayer) == 0)){
                game.yPos -= currentSpeed * delta;
                game.y = (int)game.yPos;
            }
        }

        //задание анимации при остановке персонажа
        if (!input.isKeyDown(Input.KEY_DOWN) && !input.isKeyDown(Input.KEY_UP) && !input.isKeyDown(Input.KEY_LEFT) && !input.isKeyDown(Input.KEY_RIGHT)
                && !input.isKeyPressed(Input.KEY_DOWN) && !input.isKeyPressed(Input.KEY_UP) && !input.isKeyPressed(Input.KEY_LEFT) && !input.isKeyPressed(Input.KEY_RIGHT)){
            if (animation.equals(game.animationDown) || animation.equals(game.animationRunDown)){
                game.animation = game.animationStopDown;
            }
            if (animation.equals(game.animationUp) || animation.equals(game.animationRunUp)){
                game.animation = game.animationStopUp;
            }
            if (animation.equals(game.animationLeft) || animation.equals(game.animationRunLeft)){
                game.animation = game.animationStopLeft;
            }
            if (animation.equals(game.animationRight) || animation.equals(game.animationRunRight)){
                game.animation = game.animationStopRight;
            }
        }
    }

    public static void main(String[] args) {
        AppGameContainer appgc;
        try {
            appgc = new AppGameContainer(new Game(GAME_NAME));
            appgc.setDisplayMode(1000, 410, false);

            appgc.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}