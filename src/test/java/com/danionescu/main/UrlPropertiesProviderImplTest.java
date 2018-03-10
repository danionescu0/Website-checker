package com.danionescu.main;

import com.danionescu.model.UrlProperties;
import org.asynchttpclient.uri.Uri;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class UrlPropertiesProviderImplTest {
    @Test
    public void get() throws Exception {
        String fileName = "/some/file";
        UrlPropertiesProviderImpl urlPropertiesProvider = new UrlPropertiesProviderImpl(getFilestreamReader(fileName));
        ArrayList<UrlProperties> urlProperties = urlPropertiesProvider.get(fileName);
        Assert.assertEquals(2, urlProperties.size());

        Assert.assertEquals(550, urlProperties.get(0).getTimeout());
        Assert.assertEquals(URI.create("http://www.some-website.com"), urlProperties.get(0).getUri());
        List<String> regexes = urlProperties.get(0).getRegexMatchers();
        Assert.assertEquals("some_regex", regexes.get(0));
        Assert.assertEquals("some_regex2", regexes.get(1));

        Assert.assertEquals(5550, urlProperties.get(1).getTimeout());
        Assert.assertEquals(URI.create("http://www.some-other-website.com"), urlProperties.get(1).getUri());
        Assert.assertEquals(0, urlProperties.get(1).getRegexMatchers().size());
    }

    private FileStreamReader getFilestreamReader(String fileName) {
        Stream<String> stream = Stream.of(
                "http://www.some-website.com 550 some_regex some_regex2",
                "http://www.some-other-website.com 5550"
        );
        FileStreamReader fileStreamReader = Mockito.mock(FileStreamReader.class);
        try {
            Mockito.when(fileStreamReader.read(fileName))
                    .thenReturn(stream);
        } catch (IOException e) {
            throw new RuntimeException("should not be in this situation");
        }

        return fileStreamReader;
    }
}