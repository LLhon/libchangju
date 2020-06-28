package com.newproject.hardqing.ui.view;


import com.newproject.hardqing.ai_effects.tf.Classifier;

public interface SpecialEffectView {

    void onFaceBox(int x, int y, int width);

    void gestures(Classifier.Recognition result);

    void ifBlow(boolean isBlow);


}
