import org.lwjgl.input.Mouse;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Congratulations extends BasicGameState {

    Image congratulations, toMenu;
    Animation animation;
    public static final int WINDOW_HEIGHT = 410;

    public Congratulations(int state) {

    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        congratulations = new Image("data/interface/congratulations.png");
        toMenu = new Image("data/interface/to_menu.png");
        SpriteSheet sprite = new SpriteSheet("data/animations/Firework.png", 256, 256);
        animation = new Animation(sprite, 80);
    }

    @Override
    public void render(GameContainer container, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        g.setBackground(new Color(255, 219, 139));
        Game game = (Game)stateBasedGame;
        double resTime = (game.endTime - game.startTime) / 10e8;
        g.setColor(Color.black);
        java.awt.Font font = new java.awt.Font("Century Gothic", java.awt.Font.BOLD, 27);
        TrueTypeFont fnt = new TrueTypeFont(font, true);
        g.setFont(fnt);
        g.drawString(String.format("%.2f",resTime) + " c", 683, 149);
        congratulations.draw(0, 0);
        toMenu.draw(352, 310);
        animation.draw(-20, 174);
        animation.draw(764, 174);
    }

    @Override
    public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
        animation.update(delta);
        int posX = Mouse.getX();
        int posY = WINDOW_HEIGHT - Mouse.getY();
        Game game = (Game)stateBasedGame;

        if ((posX > 352 && posX < 648) && (posY > 310 && posY < 370)) {
            if (Mouse.isButtonDown(0)) {
                try {
                    Music music = new Music("data/music/menu.ogg");
                    music.loop();
                } catch (SlickException e) {
                    throw new RuntimeException(e);
                }
                stateBasedGame.enterState(0);
            }
        }

        Input input = container.getInput();                 //получение нажатой клавиши
        if (input.isKeyPressed(Input.KEY_ESCAPE)){
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
        return -2;
    }

}

