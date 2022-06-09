package com.mygdx.game.infobox;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.Align;
import com.mygdx.game.GipGameProject;

public class InfoBox extends Window {
    private GipGameProject game;
    private Stage stage;
    public InfoBox(Skin skin, String type, GipGameProject game, Stage stage) {
        super("", game.skin, "card-info");
        this.top().center().align(Align.center|Align.top);
        float height = 200;
        setSize(300, height);
            String words = "";
            for (InfoboxTypes.CardInfoType value : InfoboxTypes.CardInfoType.values()) {
                if (value.name().equals(type)) {
                    switch (value) {
                        case EXHAUST:
                            words = "Exhausting a\ncard puts it\nin your exhaust\npile.";
                            break;
                        case COMPILE:
                            words = "An additional\neffect that\ntriggers when\nthis card,\nwhile in\nthe sequence,\nis combined into\na function.\nCan be a bad\neffect called\na compile error.";
                            break;
                        case ENCODE:
                            words = "Add card to\nsequence.";
                            break;
                        case DEXTERITY:
                            words = "A buff that\nincreases block\ngiven by cards.";
                            break;
                        case DISCARD_PILE:
                            words = "The Discard Pile \n is where cards \n from your hand \n go at the end \n of your turn.";
                            break;
                        case CYCLE:
                            words = "Discard a card, drawing a new\ncard to replace it.";
                            break;
                        case BURNS:
                            words = "Burns are unplayable status\ncards that damage you.";
                            break;
                        case WOUND:
                            words = "Unplayable status card that\nis removed at\n the end of combat.";
                            break;
                        case STRENGTH:
                            words = "Strength adds additional\ndamage to attack cards.";
                            break;
                        case SLIMED:
                            words = "Status card that\ncan be exhausted\n";
                            break;
                        case INSERT:
                            words = "Shuffle a card\ninto your draw pile.";
                            break;
                        case STATUS:
                            words = "Status cards are\nremoved at the end\nof the combat.";
                            break;
                        case BURN:
                            words = "Unplayable status card that\ndamage you.";
                            break;
                        case UNPLAYABLE:
                            words = "Unplayable cards cannot be\nplayed from your hand.";
                            break;
                        case ETHEREAL:
                            words = "If this card is in your hand at\nthe end of turn, it is exhausted.\nExhausted cards are " +
                                "removed\nfrom your deck until the end of combat.";
                        break;
                    }
                }
            }
            for (InfoboxTypes.ItemInfoType value : InfoboxTypes.ItemInfoType.values()) {
                if (value.name().equals(type)) {
                    switch (value) {
                        case DATADISK:
                            words = "This is the\ninfobox that\nexplains exactly\nwhat a Datadisk is";
                    }
                }
            }
            type = type.replace("_", " ");
            Label infoLabelTitle = new Label(type, game.skin);
            infoLabelTitle.setColor(Color.GOLDENROD);

            GlyphLayout glyphLayout = new GlyphLayout(game.font, type);
            float titleHeight = glyphLayout.height;

            Label infoLabel = new Label(words, game.skin);
            infoLabel.setColor(Color.BLACK);

            glyphLayout.setText(game.font, words);

            this.add(infoLabelTitle).top().center().row();
            this.add(infoLabel).top().center();
            this.pad(20F);
            this.setHeight(titleHeight+glyphLayout.height+getPadBottom()+getPadTop()+10);

    }
}
