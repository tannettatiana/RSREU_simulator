import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends BasicGameState {

    Image playNow;
    Image exitGame;
    Image instruction, research, name;
    public static final int WINDOW_HEIGHT = 410;

    public Menu(int state) {

    }

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
        try {
            Music music = new Music("data/music/menu.ogg");
            music.loop();
        } catch (SlickException e) {
            throw new RuntimeException(e);
        }
        playNow = new Image("data/interface/play.png");
        exitGame = new Image("data/interface/exit.png");
        instruction = new Image("data/interface/instruction.png");
        research = new Image("data/interface/research.png");
        name = new Image("data/interface/name.png");
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g)
            throws SlickException {
        g.setBackground(Color.black);
        name.draw(325, 25);
        research.draw(154, 150);
        instruction.draw(550, 150);
        playNow.draw(154, 250);
        exitGame.draw(550, 250);
    }

    @Override
    public void update(GameContainer container, StateBasedGame stateBasedGame, int delta)
            throws SlickException {
        int posX = Mouse.getX();
        int posY = WINDOW_HEIGHT - Mouse.getY();

        Game game = (Game)stateBasedGame;

        if ((posX > 154 && posX < 450) && (posY > 150 && posY < 210)) {
            if (Mouse.isButtonDown(0)) {
                try {
                    Music music = new Music("data/music/play.ogg");
                    music.loop();
                } catch (SlickException e) {
                    throw new RuntimeException(e);
                }
                game.auditoryState = -100;
                stateBasedGame.enterState(1);
                game.x = game.XYFirstFloorRight[0][1];
                game.y = game.XYFirstFloorRight[1][1];
                game.xPos = game.x;
                game.yPos = game.y;
            }
        }

        if ((posX > 154 && posX < 450) && (posY > 250 && posY < 310)) {
            if (Mouse.isButtonDown(0)) {
                try {
                    Music music = new Music("data/music/play.ogg");
                    music.loop();
                } catch (SlickException e) {
                    throw new RuntimeException(e);
                }
                game.auditoryState = (int)(Math.random() * 6) + 1;
                game.auditory = (int)(Math.random() * game.auditoryDict.get(game.auditoryState).length);
                game.auditoryString = (game.auditoryDict.get(game.auditoryState)[game.auditory]);

                game.x = game.XYFirstFloorRight[0][1];
                game.y = game.XYFirstFloorRight[1][1];
                game.xPos = game.x;
                game.yPos = game.y;

                game.startTime = System.nanoTime();
                stateBasedGame.enterState(1);
            }
        }

        if ((posX > 550 && posX < 846) && (posY > 150 && posY < 210)) {
            if (Mouse.isButtonDown(0)) {
                stateBasedGame.enterState(-1);
            }
        }

        if ((posX > 550 && posX < 846) && (posY > 250 && posY < 310)) {
            if (Mouse.isButtonDown(0)) {
                System.exit(0);
            }
        }

        Input input = container.getInput();                 //получение нажатой клавиши
        if (input.isKeyPressed(Input.KEY_F1)){
            game.previousState = stateBasedGame.getCurrentStateID();
            stateBasedGame.enterState(-1);
        }
    }

    @Override
    public int getID() {
        return 0;
    }

}
