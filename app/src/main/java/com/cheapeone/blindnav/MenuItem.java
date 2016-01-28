package com.cheapeone.blindnav;

/**
 * Created by Chris on 2015-04-08.
 */
public class MenuItem {

    public MenuItem next, prev, down, up;
    public int sound_ID;

    public MenuItem(int sound_ID){
        this.sound_ID = sound_ID;
    }
}
