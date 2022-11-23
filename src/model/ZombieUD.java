package model;

import javax.sound.sampled.Clip;
import sound.Sound;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class  ZombieUD extends  Item {
    int orientation;
    ArrayList<Bullet> bullets;

    public ZombieUD(int id, int x, int y, int size, int orientation) {
        super(id, x, y, size);
        this.orientation = orientation;
        bullets = new ArrayList<Bullet>();
    }
    public void move(ArrayList<Item> items) {
        switch (orientation) {
            case Hero.LEFT:
                x = x - 1;
                boolean isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    x = x + 1;
                    changeOri(items);
                }else {
                    Random rd = new Random();
                    int rdInt = rd.nextInt(10000);
                    if ( rdInt > 9889 ) {
                        changeOri(items);
                    }
                }
                break;
            case Hero.RIGHT:
                x = x + 1;
                isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    x = x - 1;
                    changeOri(items);
                }else {
                    Random rd = new Random();
                    int rdInt = rd.nextInt(10000);
                    if ( rdInt > 9889 ) {
                        changeOri(items);
                    }
                }
                break;
            case Hero.UP:
                y = y - 1;
                isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    y = y + 1;
                    changeOri(items);
                }else {
                    Random rd = new Random();
                    int rdInt = rd.nextInt(10000);
                    if ( rdInt > 9889 ) {
                        changeOri(items);
                    }
                }
                break;
            case Hero.DOWN:
                y = y + 1;
                isIntesect = interactWithItems(items);
                if (isIntesect == true) {
                    y = y - 1;
                    changeOri(items);
                }else {
                    Random rd = new Random();
                    int rdInt = rd.nextInt(10000);
                    if ( rdInt > 9889 ) {
                        changeOri(items);
                    }
                }
                break;
        }
    }
    boolean interactWithItems(ArrayList<Item> items) {
        Rectangle rect1 = new Rectangle(x, y, size, size);
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            if (item.id == Images.TREE_ID) {
                continue;
            }
            Rectangle rect2 = new Rectangle(item.x, item.y, item.size, item.size);
            if (rect1.intersects(rect2) == true) {
                return true;
            }
        }
        return false;
    }
    public void fireBullet() {
        int sizeB = 7;
        int orB = orientation;
        int xB;
        int yB;
        switch (orientation) {
            case Hero.LEFT:
                xB = x - sizeB;
                yB = y + (size - sizeB) / 2;
                break;
            case Hero.RIGHT:
                xB = x + size;
                yB = y + (size - sizeB) / 2;
                break;
            case Hero.UP:
                xB = x + (size - sizeB) / 2;
                yB = y - sizeB;
                break;
            default:
                xB = x + (size - sizeB) / 2;
                yB = y + size;
                break;
        }
        Bullet bullet = new Bullet(Images.BULLET_ID, xB, yB, sizeB, orB);
        bullets.add(bullet);
    }
    public void changeOri(ArrayList<Item> items) {
        Random rd = new Random();
        int i = rd.nextInt(3) + 1;
        int ori = (orientation + i) % 4;
        orientation = ori;
        int id = Images.ID_ZOMBIEUD[orientation];
        img = Images.getImage(id);
    }
    public void drawAllBullet(Graphics2D g2d) {
        for ( int i = 0; i < bullets.size(); i++ ) {
            Bullet bullet = bullets.get(i);
            bullet.draw(g2d);
        }
    }
    public void moveAllBullet() {
        for ( int i = 0; i < bullets.size(); i++ ) {
            Bullet bullet = bullets.get(i);
            bullet.move();
        }
    }
    public void interactBullet(ArrayList<Item> items) {
        for ( int i = 0; i < bullets.size(); i++ ) {
            Bullet bullet = bullets.get(i);
            Rectangle rect1 = new Rectangle(bullet.x, bullet.y,  bullet.size, bullet.size);
            for ( int j = 0 ; j < items.size(); j++ ){
                Item item = items.get(j);
                if ( item.id == Images.TREE_ID ) {
                    continue;
                }
                if ( item.id == Images.WATER_ID ) {
                    continue;
                }
                Rectangle rect2 =new Rectangle(item.x, item.y, item.size, item.size);
                if ( rect1.intersects(rect2) == true ) {
                    if ( item.id == Images.ROCK_ID ) {
                        bullets.remove(i);
                        return;
                    }
                    if ( item.id == Images.BOX_ID ) {
                        bullets.remove(i);
                        items.remove(j);
                        return;
                    }
                }
            }
        }
    }

    public boolean killHero(Hero hero) {
        Rectangle rect1 = new Rectangle(hero.x, hero.y, hero.size, hero.size);
        for ( int i = 0; i < bullets.size(); i++ ) {
            Bullet bullet = bullets.get(i);
            Rectangle rect2 = new Rectangle(bullet.x, bullet.y, bullet.size, bullet.size);
            if (rect1.intersects(rect2) ) {
                Clip clip = Sound.getSound(getClass().getResource("/sound/explosion_hero.wav"));
                clip.start();
                return true;
            }
        }
        return false;
    }

    public boolean killHome(Item home) {
        Rectangle rect1 = new Rectangle(home.x, home.y, home.size, home.size);
        for ( int i = 0; i < bullets.size(); i++ ) {
            Bullet bullet = bullets.get(i);
            Rectangle rect2 = new Rectangle(bullet.x, bullet.y, bullet.size, bullet.size);
            if (rect1.intersects(rect2) ) {
                return true;
            }
        }
        return false;
    }

}
