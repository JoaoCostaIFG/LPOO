package org.g73.skanedweller.view.element_views;

import com.googlecode.lanterna.graphics.TextGraphics;

public interface ElementDrawer<T> {
    void draw(TextGraphics gra, T e);
}
