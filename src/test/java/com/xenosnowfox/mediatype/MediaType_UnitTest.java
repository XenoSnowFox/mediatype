package com.xenosnowfox.mediatype;

import org.junit.jupiter.api.Test;

public class MediaType_UnitTest {
    @Test
    public void run() throws Exception {
        MediaType mediaType = new MediaType(RegistrationTree.VENDOR, "application", "aroflo.timers.timer", "json");
        mediaType.putParameter("version", "1");
        mediaType.putParameter("charset", "UTF-8");

        System.out.println(mediaType.toString());
    }
}
