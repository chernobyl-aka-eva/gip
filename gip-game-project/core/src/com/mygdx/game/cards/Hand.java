package com.mygdx.game.cards;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;

public class Hand extends Group {
    @Override
    protected void childrenChanged() {
        if (hasChildren()) {
            Array<Actor> children = super.getChildren();
            for (int i = 0; i < children.size; i++) {
                if (children.get(i) instanceof Card) {
                    Card card = (Card)children.get(i);
                    card.setHandslot(i);

                    card.setPosition(700 + card.getTextureRegion().getRegionWidth()* card.getHandslot(), -300);
                    setBounds(card.getImageX(), card.getImageY(), card.getWidth(), card.getHeight());
                    /*
                    card.getTitle().setBounds(
                            card.getBackground().getX(),
                            card.getBackground().getY(),
                            card.getBackground().getWidth(),
                            card.getBackground().getHeight()-10);
                    card.getTitle().setAlignment(Align.top);

                    card.getDescription().setBounds(card.getBackground().getX(),
                            card.getBackground().getY()+10,
                            card.getBackground().getWidth(),
                            card.getBackground().getHeight());
                    card.getDescription().setAlignment(Align.bottom);
                     */


                }
            }
        }
    }
}
