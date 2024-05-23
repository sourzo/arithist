package com.github.sourzo.a_rithist.general;

import java.io.IOException;
import java.io.InputStreamReader;

public interface AppRes {
    InputStreamReader getFileStream(String filename) throws IOException;
}
