package com.cheapeone.blindnav;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.Menu;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * Created by Chris on 2015-04-07.
 */
public class NavController {

    private ArrayList<MenuItem> menu;
    private MenuItem current_item;
    //public static boolean playing = false;


    public NavController(){
        this.menu = new ArrayList<MenuItem>();
        current_item = this.construct_menu();
    }

    public void select_item(Context context) throws IOException, InterruptedException {
        if(current_item.down != null){
            //Selection Transition Sound
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw._beepboop);

            mediaPlayer.start();
            TimeUnit.MILLISECONDS.sleep(200);

            //Move down level
            this.current_item = current_item.down;
            mediaPlayer = MediaPlayer.create(context, this.current_item.sound_ID);
            mediaPlayer.start();
        }
    }

    public void deselect_item(Context context) throws InterruptedException {

        if(current_item.up != null){
            //Selection Transition Sound
            MediaPlayer mediaPlayer = MediaPlayer.create(context, R.raw._undo);

            mediaPlayer.start();
            TimeUnit.MILLISECONDS.sleep(200);

            //Move down level
            this.current_item = current_item.up;
            mediaPlayer = MediaPlayer.create(context, this.current_item.sound_ID);
            mediaPlayer.start();
        }
    }

    public void repeat_item(Context context){
        MediaPlayer mediaPlayer = MediaPlayer.create(context, this.current_item.sound_ID);
        mediaPlayer.start();
    }

    public void next_item(Context context){
        if(current_item.next != null){
            this.current_item = current_item.next;
            MediaPlayer mediaPlayer = MediaPlayer.create(context, this.current_item.sound_ID);
            mediaPlayer.start();
        }
    }

    public void prev_item(Context context){
        if(current_item.prev != null){
            this.current_item = current_item.prev;
            MediaPlayer mediaPlayer = MediaPlayer.create(context, this.current_item.sound_ID);
            mediaPlayer.start();
        }
    }

    public MenuItem construct_menu(){

        //Menu 1
        MenuItem get_directions = new MenuItem(R.raw.directions);
        MenuItem manage_locations = new MenuItem(R.raw.manage_locations);
        MenuItem manage_people = new MenuItem(R.raw.manage_friends);
        MenuItem settings = new MenuItem(R.raw.settings);

        MenuItem beep = new MenuItem(R.raw._lozselect);
        MenuItem complete = new MenuItem(R.raw._completion);

            //Menu 1a
            MenuItem to_location = new MenuItem(R.raw.to_location);
            MenuItem to_person = new MenuItem(R.raw.to_friend);

                //Menu 1a-1
                MenuItem to_saved_location = new MenuItem(R.raw.to_saved_location);
                MenuItem lookup_location = new MenuItem(R.raw.lookup_location);

            //Menu 1b
            MenuItem add_location = new MenuItem(R.raw.add_location);
            MenuItem remove_location = new MenuItem(R.raw.remove_location);

            //Menu 1b-2
            MenuItem add_person = new MenuItem(R.raw.add_friend);
            MenuItem remove_person = new MenuItem(R.raw.remove_friend);

            //Menu 1c
            MenuItem setup_account = new MenuItem(R.raw.setup_account);
            MenuItem setup_wipe = new MenuItem(R.raw.reset_settings);






        //Setup Adjacencies
        get_directions.next = manage_locations;
        get_directions.prev = settings;
        get_directions.down = to_location;

            to_location.next = to_person;
            to_location.prev = to_person;
            to_location.up = get_directions;
            to_location.down = to_saved_location;

                to_saved_location.next = lookup_location;
                to_saved_location.prev = lookup_location;
                to_saved_location.up = to_location;
                to_saved_location.down = beep;

                lookup_location.next = to_saved_location;
                lookup_location.prev = to_saved_location;
                lookup_location.up = to_location;
                lookup_location.down = beep;

            to_person.next = to_location;
            to_person.prev = to_location;
            to_person.up = get_directions;
            to_person.down = beep;

        manage_locations.next = manage_people;
        manage_locations.prev = get_directions;
        manage_locations.down = add_location;

            add_location.next = remove_location;
            add_location.prev = remove_location;
            add_location.up = manage_locations;
            add_location.down = beep;

            remove_location.next = add_location;
            remove_location.prev = add_location;
            remove_location.up = manage_locations;
            remove_location.down = beep;

        manage_people.next = settings;
        manage_people.prev = manage_locations;
        manage_people.down = add_person;

                add_person.next = remove_person;
                add_person.prev = remove_person;
                add_person.up = manage_people;
                add_person.down = beep;

                remove_person.next = add_person;
                remove_person.prev = add_person;
                remove_person.up = manage_people;
                remove_person.down = beep;

        settings.next = get_directions;
        settings.prev = manage_people;
        settings.down = setup_account;

            setup_account.next = setup_wipe;
            setup_account.prev = setup_wipe;
            setup_account.up = settings;
            setup_wipe.down = complete;

            setup_wipe.next = setup_account;
            setup_wipe.prev = setup_account;
            setup_wipe.up = settings;
            setup_wipe.down = complete;

            complete.up = settings;
            beep.up = get_directions;

        this.menu.add(get_directions);
        this.menu.add(settings);
        this.menu.add(to_location);
        this.menu.add(to_person);
        this.menu.add(to_saved_location);
        this.menu.add(lookup_location);
        this.menu.add(manage_locations);
        this.menu.add(manage_people);
        this.menu.add(add_location);
        this.menu.add(remove_location);
        this.menu.add(add_person);
        this.menu.add(remove_person);
        this.menu.add(setup_account);
        this.menu.add(setup_wipe);

        this.menu.add(beep);
        this.menu.add(complete);

        return get_directions;
    }

}
