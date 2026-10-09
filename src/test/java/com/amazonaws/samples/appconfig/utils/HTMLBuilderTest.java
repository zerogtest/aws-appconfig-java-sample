package com.amazonaws.samples.appconfig.utils;

import com.amazonaws.samples.appconfig.movies.Movie;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HTMLBuilderTest {

    private HTMLBuilder htmlBuilder;

    @Before
    public void setUp() {
        htmlBuilder = new HTMLBuilder();
    }

    @Test
    public void testGetMoviesHtmlWithMultipleMovies() {
        Movie[] movies = {
                new Movie(1L, "Movie One"),
                new Movie(2L, "Movie Two")
        };

        String result = htmlBuilder.getMoviesHtml(movies);

        assertTrue(result.contains("<div id='movies-container'>"));
        assertTrue(result.contains("FREE Movie List for this Month"));
        assertTrue(result.contains("Movie One"));
        assertTrue(result.contains("Movie Two"));
        assertTrue(result.contains("<p>ID: 1</p>"));
        assertTrue(result.contains("<p>ID: 2</p>"));
        assertTrue(result.contains("</div>"));
    }

    @Test
    public void testGetMoviesHtmlWithSingleMovie() {
        Movie[] movies = {new Movie(42L, "The Answer")};

        String result = htmlBuilder.getMoviesHtml(movies);

        assertTrue(result.contains("The Answer"));
        assertTrue(result.contains("<p>ID: 42</p>"));
    }

    @Test
    public void testGetMoviesHtmlWithEmptyArray() {
        Movie[] movies = {};

        String result = htmlBuilder.getMoviesHtml(movies);

        assertTrue(result.contains("<div id='movies-container'>"));
        assertTrue(result.contains("FREE Movie List for this Month"));
        assertFalse(result.contains("<div class='movie-item'>"));
    }

    @Test
    public void testGetMoviesHtmlContainsHorizontalRules() {
        Movie[] movies = {new Movie(1L, "Test Movie")};

        String result = htmlBuilder.getMoviesHtml(movies);

        assertTrue(result.contains("<hr>"));
        assertTrue(result.contains("<hr width=\"100%\" size=\"2\" color=\"blue\" noshade>"));
    }
}
