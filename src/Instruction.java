import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Instruction extends BasicGameState {

    Image instruction, ok;
    public static final int WINDOW_HEIGHT = 410;

    public Instruction(int state) {

    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        instruction = new Image("data/interface/instruction_text.png");
        ok = new Image("data/interface/ok.png");;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        g.setBackground(Color.black);
        instruction.draw(0, 0);
        ok.draw(830, 350);
    }

    @Override
    public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
        int posX = Mouse.getX();
        int posY = WINDOW_HEIGHT - Mouse.getY();
        Game game = (Game)stateBasedGame;

        if ((posX > 830 && posX < 980) && (posY > 350 && posY < 389)) {
            if (Mouse.isButtonDown(0)) {
                stateBasedGame.enterState(game.previousState);
            }
        }

        Input input = container.getInput();                 //получение нажатой клавиши
        if (input.isKeyDown(Input.KEY_ESCAPE)){
            try {
                Music music = new Music("data/music/menu.ogg");
                music.loop();
            } catch (SlickException e) {
                throw new RuntimeException(e);
            }
            stateBasedGame.enterState(0);
        }
    }

    @Override
    public int getID() {
        return -1;
    }

}
