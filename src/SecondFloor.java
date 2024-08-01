import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

public class SecondFloor extends BasicGameState {

    private TiledMap map;

    public SecondFloor(int state) {

    }

    @Override
    public void init(GameContainer container, StateBasedGame stateBasedGame) throws SlickException {

        Game game = (Game)stateBasedGame;
        //начальная анимация
        game.animation = game.animationDown;
        //карта первого этажа правого крыла
        map = new TiledMap("data/SecondFloor.tmx");
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

        game.checkStairs(0, absMapX, absMapY, 1, 1, game.XYFirstFloorRight, stateBasedGame, map);
        game.checkStairs(3, absMapX, absMapY, 4, 2, game.XYFirstFloorCenter, stateBasedGame, map);
        game.checkStairs(5, absMapX, absMapY, 4, 3, game.XYFirstFloorCenter, stateBasedGame, map);
        game.checkStairs(6, absMapX, absMapY, 4, 3, game.XYFirstFloorCenter, stateBasedGame, map);
        game.checkStairs(7, absMapX, absMapY, 4, 4, game.XYFirstFloorCenter, stateBasedGame, map);
        game.checkStairs(8, absMapX, absMapY, 5, 5, game.XYFirstFloorLeft, stateBasedGame, map);
        game.checkStairs(11, absMapX, absMapY, 5, 6, game.XYFirstFloorLeft, stateBasedGame, map);

        game.checkStairs(1, absMapX, absMapY, 6, 1, game.XYThirdFloor, stateBasedGame, map);
        game.checkStairs(2, absMapX, absMapY, 6, 2, game.XYThirdFloor, stateBasedGame, map);
        game.checkStairs(4, absMapX, absMapY, 6, 3, game.XYThirdFloor, stateBasedGame, map);
        game.checkStairs(9, absMapX, absMapY, 6, 4, game.XYThirdFloor, stateBasedGame, map);
        game.checkStairs(10, absMapX, absMapY, 6, 5, game.XYThirdFloor, stateBasedGame, map);
        game.checkStairs(12, absMapX, absMapY, 6, 6, game.XYThirdFloor, stateBasedGame, map);

        game.updateFloor(container, stateBasedGame, delta, map, absMapX, absMapY);

    }

    @Override
    public int getID() {
        return 3;
    }
}
