package com.xenosnowfox.mediatype;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MediaType_UnitTest {
    @Test
    public void run() throws Exception {
        MediaType mediaType = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");
        mediaType.putParameter("version", "1");
        mediaType.putParameter("charset", "UTF-8");

        mediaType = new MediaType("application/vnd.aroflo-1+json;   \r\n   version=1    ;    something=\"other\"");

        System.out.println(mediaType.toString());
        System.out.println(mediaType.getType());
        System.out.println(mediaType.getSubType());
        System.out.println(mediaType.getRegistrationTree());
        System.out.println(mediaType.getSuffix());
        System.out.println(mediaType.getParameter("version"));
        System.out.println(mediaType.getParameter("something"));
    }

    @Test
    public void testMethod_equals_exactMatch() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");
        mediaType1.putParameter("version", "1");
        mediaType1.putParameter("charset", "UTF-8");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");
        mediaType2.putParameter("version", "1");
        mediaType2.putParameter("charset", "UTF-8");

        Assertions.assertEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testMethod_equals_exactMatchWithNoParameters() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");

        Assertions.assertEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testMethod_equals_nonMatchWithDifferentParameters() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");
        mediaType1.putParameter("version", "1");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");
        mediaType2.putParameter("version", "1");
        mediaType2.putParameter("charset", "UTF-8");

        Assertions.assertNotEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testMethod_equals_nonMatchWithSimilarParameters() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");
        mediaType1.putParameter("version", "2");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");
        mediaType2.putParameter("version", "1");

        Assertions.assertNotEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testMethod_equals_nonMatchWithDifferentRegistrationTrees() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.UNREGISTERED, "application", "com.example", "json");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");

        Assertions.assertNotEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testMethod_equals_nonMatchWithDifferentTypes() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "image", "com.example", "json");

        Assertions.assertNotEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testMethod_equals_nonMatchWithDifferentSubTypes() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example-1", "json");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example-2", "json");

        Assertions.assertNotEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testMethod_equals_nonMatchWithDifferentSuffix() throws Exception {

        MediaType mediaType1 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "json");

        MediaType mediaType2 = new MediaType(RegistrationTree.STANDARDS, "application", "com.example", "xml");

        Assertions.assertNotEquals(
                mediaType1
                , mediaType2
        );
    }

    @Test
    public void testConstructor_withOnlyTypeAndSubType() throws Exception {
        MediaType mediaType = new MediaType("application", "com.example");

        Assertions.assertAll(
                () -> Assertions.assertEquals("application", mediaType.getType())
                , () -> Assertions.assertEquals("com.example", mediaType.getSubType())
                , () -> Assertions.assertNull(mediaType.getSuffix())
                , () -> Assertions.assertEquals(RegistrationTree.STANDARDS, mediaType.getRegistrationTree())
        );
    }

    @Test
    public void testConstructor_withOnlyTypeAndVendorSubType() throws Exception {
        MediaType mediaType = new MediaType("application", "vnd.example");

        Assertions.assertAll(
                () -> Assertions.assertEquals("application", mediaType.getType())
                , () -> Assertions.assertEquals("example", mediaType.getSubType())
                , () -> Assertions.assertNull(mediaType.getSuffix())
                , () -> Assertions.assertEquals(RegistrationTree.VENDOR, mediaType.getRegistrationTree())
        );
    }
}
