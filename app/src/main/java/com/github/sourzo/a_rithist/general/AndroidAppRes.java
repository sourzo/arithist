package com.github.sourzo.a_rithist.general;

import android.content.res.AssetManager;

import java.io.IOException;
import java.io.InputStreamReader;

public class AndroidAppRes implements AppRes {
    AssetManager am;

    public InputStreamReader getFileStream(String fileName) throws IOException {
        return new InputStreamReader(am.open(fileName));
    }

    public AndroidAppRes(AssetManager am){this.am = am;}


}
