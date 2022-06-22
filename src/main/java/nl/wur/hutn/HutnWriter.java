package nl.wur.hutn;

import nl.wur.model.Viewpoint;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class HutnWriter {

    private static HutnWriter hutnWriter;

    private HutnWriter() {
    }

    public void write(Viewpoint... viewpoints) {
        for (Viewpoint viewpoint : viewpoints) {
            write(viewpoint);
        }
    }

    public void write(Viewpoint viewpoint) {
        try {
            FileWriter fileWriter = new FileWriter(new File(viewpoint.getName() + ".hutn"));
            fileWriter.write(viewpoint.toString());
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static HutnWriter getHutnWriter(){
        if(hutnWriter == null) {
            hutnWriter = new HutnWriter();
        }
        return hutnWriter;
    }
}
