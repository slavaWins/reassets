package org.slavawins.reassets.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class SafleJsonPaste {

    public static void Paste(File file, String s, String json) {
        try {

            String content = Files.readString(file.toPath());


           if(content.indexOf(s)<0){
            //   s=s.replace(":[{")
           }

        } catch (IOException e) {
            System.out.println("----- [reassets] AtllasUpdate error");
            e.printStackTrace();
        }
    }

}
