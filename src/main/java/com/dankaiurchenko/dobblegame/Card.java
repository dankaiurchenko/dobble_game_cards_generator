package com.dankaiurchenko.dobblegame;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Card {
    private final ArrayList<Integer> icons;
    List<String> iconsPaths;

    public Card(int number_of_icons_on_a_card) {
        this.icons = new ArrayList<>(number_of_icons_on_a_card);
    }


    public void add(int i) {
        this.icons.add(i);
    }

    public void setIcons(List<String> icons) {
        iconsPaths = new ArrayList<>(this.icons.size());
        for (Integer iconsNumber : this.icons) {
            this.iconsPaths.add(icons.get(iconsNumber));
        }
    }

    public Collection<Integer> getIcons() {
        return this.icons;
    }
}
