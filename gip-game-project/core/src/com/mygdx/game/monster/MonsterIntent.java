package com.mygdx.game.monster;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Array;
import com.mygdx.game.GipGameProject;
import com.mygdx.game.cards.CardManager;

public class MonsterIntent {
    final private GipGameProject game;
    final private CardManager cardManager;

    public MonsterIntent(GipGameProject game, CardManager cardManager) {
        this.game = game;
        this.cardManager = cardManager;
    }

    public void monsterTurn(Array<Integer> monsterIntents) {

        for (int i = 0; i < monsterIntents.size; i++) {
            switch (monsterIntents.get(i)) {
                case 0:
                    int damage;
                    if (cardManager.getVirusManager().getPlayer().getBlock() > 0) { // if the player has block
                        game.log.debug("block before damage : " + cardManager.getVirusManager().getPlayer().getBlock());
                        if (cardManager.getVirusManager().getPlayer().getBlock() >= 32) { // if the player can block fully
                            damage = 32;
                            cardManager.getVirusManager().getPlayer().setBlock(cardManager.getVirusManager().getPlayer().getBlock()-damage);
                            //game.log.debug("damage : " + damage);
                            //game.log.debug("block after damage : " + cardManager.getVirusManager().getPlayer().getBlock());
                        } else { // else if the player can't block fully but can block some...
                            int damageAfterBlock;
                            damage = 32 - cardManager.getVirusManager().getPlayer().getBlock();
                            //game.log.debug("damage : " + damage);
                            cardManager.getVirusManager().getPlayer().setBlock(cardManager.getVirusManager().getPlayer().getBlock()-damage);
                            damageAfterBlock = 32 - damage; // damage done after breaking block
                            //game.log.debug("damage after block : " + damageAfterBlock);
                            //game.log.debug("health before damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                            cardManager.getVirusManager().getPlayer().setHealth(cardManager.getVirusManager().getPlayer().getHealth() - damageAfterBlock);
                            //game.log.debug("health after damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                        }
                    } else { // if the player doesn't have block
                        //game.log.debug("health before damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                        cardManager.getVirusManager().getPlayer().setHealth(cardManager.getVirusManager().getPlayer().getHealth()-32);
                        //game.log.debug("health after damage : " + cardManager.getVirusManager().getPlayer().getHealth());
                    }
                    break;
                case 1:
                    int block = 12;
                    for (Actor actor : cardManager.getMonsterManager().getMonsterGroup().getChildren()) {
                        if (actor instanceof Monster) {
                            Monster monster = (Monster) actor;
                            game.log.debug("before block monster : " + monster.getBlock());
                            monster.setblock(monster.getblock() + block);
                            game.log.debug("after block monster : " + monster.getBlock());
                        }
                    }
            }
        }
    }
}
