import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class ThirdFloor extends BasicGameState {

    private TiledMap map;

    public ThirdFloor(int state) {

    }

    @Override
    public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {

        Game game = (Game)stateBasedGame;
        //начальная анимация
        game.animation = game.animationDown;
        //карта
        map = new TiledMap("data/ThirdFloor.tmx");
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics g) throws SlickException {
        Game game = (Game)stateBasedGame;
        map.render(game.x, game.y, 0, 0, 226, 50);  //отрисовка карты с заданных координат
        game.animation.draw(game.puppyX, game.puppyY);                                //отрисовка героя на заданных координатах
        if (game.auditoryState > 0){
            game.renderAuditoryRect(g, game.auditoryString);
        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame stateBasedGame, int delta) throws SlickException {
        Game game = (Game)stateBasedGame;

        game.animation.update(delta); //обновление анимации

        //координаты персонажа относительно карты
        int absMapX = game.puppyX - game.x;
        int absMapY = game.puppyY - game.y;

        game.checkStairs(0, absMapX, absMapY, 3, 1, game.XYSecondFloor, stateBasedGame, map);
        game.checkStairs(1, absMapX, absMapY, 3, 2, game.XYSecondFloor, stateBasedGame, map);
        game.checkStairs(2, absMapX, absMapY, 3, 3, game.XYSecondFloor, stateBasedGame, map);
        game.checkStairs(3, absMapX, absMapY, 3, 3, game.XYSecondFloor, stateBasedGame, map);
        game.checkStairs(4, absMapX, absMapY, 3, 4, game.XYSecondFloor, stateBasedGame, map);
        game.checkStairs(5, absMapX, absMapY, 3, 5, game.XYSecondFloor, stateBasedGame, map);
        game.checkStairs(6, absMapX, absMapY, 3, 6, game.XYSecondFloor, stateBasedGame, map);
        game.checkStairs(7, absMapX, absMapY, 2, 0, game.XYSecondFloorRight, stateBasedGame, map);

        game.updateFloor(container, stateBasedGame, delta, map, absMapX, absMapY);

    }

    @Override
    public int getID() {
        return 6;
    }
}
