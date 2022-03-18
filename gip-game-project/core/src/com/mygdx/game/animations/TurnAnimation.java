package com.mygdx.game.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.TurnManager;

public class TurnAnimation {
    private GipGameProject game;
    private Stage stage;
    private Group turnGroup;
    private TurnManager turnManager;
    private Image rectangle;
    private final Label.LabelStyle style;
    private final BitmapFont font;
    private Label turn;
    private Label start;
    private Label endturn;
    private Array<Label> animationLabels;

    public TurnAnimation(GipGameProject game, Stage stage, TurnManager turnManager) {
        this.game = game;
        this.stage = stage;
        this.turnManager = turnManager;

        animationLabels = new Array<>();

        turnGroup = new Group() {
            @Override
            public void setVisible(boolean visible) {
                super.setVisible(visible);


            }
        };
        game.textureAtlas = new TextureAtlas("skin/game-ui.atlas");
        rectangle = new Image(new TextureRegionDrawable(game.textureAtlas.findRegion("new turn rectangle")));
        rectangle.setPosition(0, -60);

        style = new Label.LabelStyle();
        font = new BitmapFont(Gdx.files.internal("fonts/pcsenior.fnt"));
        style.font = font;
        style.fontColor = Color.WHITE;

        Float scale = 2f;

        start = new Label("Battle\n Start", style);

        GlyphLayout glyphord = new GlyphLayout(font, start.getText());
        glyphord.setText(font, start.getText());
        start.setFontScale(scale);
        start.setPosition(rectangle.getWidth() / 2 - glyphord.width / 2, stage.getHeight() / 2 - glyphord.height / 2);
        start.setVisible(false);

        endturn = new Label("End Turn", style);
        glyphord.setText(font, endturn.getText());
        endturn.setScale(scale);
        endturn.setPosition(rectangle.getWidth() / 2 - glyphord.width / 2, stage.getHeight() / 2 - glyphord.height / 2);
        endturn.setVisible(false);


        turnGroup.addActor(rectangle);
        turnGroup.addActor(start);
        turnGroup.addActor(endturn);
        turnGroup.setVisible(false);


        stage.addActor(turnGroup);


    }

    public void startAnimation(int type, int turnCounter) {
        Label animationLabel = null;


        turn = new Label("", style);

        game.log.debug("ANIMATION TURN " + turnCounter);

        if (turnManager.isPlayerTurn()) {
            turn.setText(turnCounter);
            game.log.debug("Player turn " + turnManager.isPlayerTurn());
        } else if (turnManager.isMonsterTurn()) {
            turn.setText("Enemy turn");
        }


        GlyphLayout glyphord = new GlyphLayout(font, turn.getText());

        Float scale = 2f;


        turn.setFontScale(scale);
        turn.setPosition(rectangle.getWidth() / 2 - glyphord.width / 2, stage.getHeight() / 2 - glyphord.height / 2);
        turn.setVisible(false);

        turn.invalidate();
        turn.validate();


        SequenceAction sqa = new SequenceAction();
        Action fadein = Actions.fadeIn(3f);
        Action fadeout = Actions.fadeOut(3f);
        Action endAction = new Action() {
            @Override
            public boolean act(float v) {
                turnGroup.setVisible(false);
                return true;
            }
        };



        turnGroup.setVisible(true);
        animationLabel.setVisible(true);
        rectangle.addAction(Actions.alpha(0));
        sqa.addAction(fadein);
        sqa.addAction(fadeout);
        sqa.addAction(endAction);
        rectangle.addAction(sqa);


    }





    public GipGameProject getGame() {
        return game;
    }

    public void setGame(GipGameProject game) {
        this.game = game;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Group getTurnGroup() {
        return turnGroup;
    }

    public void setTurnGroup(Group turnGroup) {
        this.turnGroup = turnGroup;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public Image getRectangle() {
        return rectangle;
    }

    public void setRectangle(Image rectangle) {
        this.rectangle = rectangle;
    }

    public Label.LabelStyle getStyle() {
        return style;
    }

    public BitmapFont getFont() {
        return font;
    }

    public Label getTurn() {
        return turn;
    }

    public void setTurn(Label turn) {
        this.turn = turn;
    }

    public Label getStart() {
        return start;
    }

    public void setStart(Label start) {
        this.start = start;
    }

    public Label getEndturn() {
        return endturn;
    }

    public void setEndturn(Label endturn) {
        this.endturn = endturn;
    }
}
