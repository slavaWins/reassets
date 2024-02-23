package org.slavawins.reassets.contracts;

import com.google.gson.Gson;

public class UploadResponseContract {
    public boolean success = false;
    public String message = "Error loaded";
    public String url;

    public static UploadResponseContract Error(String msg) {
        UploadResponseContract r = new UploadResponseContract();
        r.message = msg;
        return r;
    }

    public static UploadResponseContract Parse(String content) {
        UploadResponseContract r = new UploadResponseContract();
        r.message = "Not parsed response";

        Gson gson = new Gson();
        r = gson.fromJson(content, UploadResponseContract.class);

        return r;
    }
}
