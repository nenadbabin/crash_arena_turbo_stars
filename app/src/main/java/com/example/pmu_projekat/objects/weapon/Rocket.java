package com.example.pmu_projekat.objects.weapon;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;

import com.example.pmu_projekat.R;
import com.example.pmu_projekat.constants.Constants;
import com.example.pmu_projekat.objects.CarElement;

public class Rocket extends CarElement {

    public Rocket (Context context, int x, int y) {
        super(context, x, y);
        this.width = 136;
        this.height = 120;

        this.d = context.getResources().getDrawable(R.drawable.rocket, null);
    }

    @Override
    public void draw(Canvas canvas) {
        d.setBounds(x, y, x + (int)(width * factor), y + (int)(height * factor));
        d.draw(canvas);
    }

    @Override
    public int getElementType() {
        return Constants.TYPE_WEAPON;
    }

    @Override
    public int getElementIdentity() {
        return Constants.WPN_ROCKET;
    }

    @Override
    public void setReverse(boolean reverse) {
        super.setReverse(reverse);
        d = context.getResources().getDrawable(R.drawable.rocket_reverse, null);
    }
}
