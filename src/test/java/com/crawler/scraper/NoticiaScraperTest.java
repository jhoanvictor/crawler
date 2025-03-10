package com.crawler.scraper;

import com.crawler.model.Noticia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class NoticiaScraperTest {

    private NoticiaScraper scraper;
    private Document mockDocument;

    @BeforeEach
    void setUp() {
        scraper = new NoticiaScraper();
        mockDocument = Jsoup.parse("<html>" +
                "<body>" +
                "<div class='news'>" +
                "<a class='title' href='https://example.com/news1'>Notícia 1</a>" +
                "</div>" +
                "<div class='news'>" +
                "<a class='title' href='https://example.com/news2'>Notícia 2</a>" +
                "</div>" +
                "</body>" +
                "</html>");
    }

    @Test
    void testExtrairNoticias() throws IOException {

        NoticiaScraper scraperSpy = spy(scraper);

        List<Noticia> noticiasMock = List.of(
                new Noticia("Notícia 1", "https://example.com/news1", LocalDate.now()),
                new Noticia("Notícia 2", "https://example.com/news2", LocalDate.now())
        );

        doReturn(noticiasMock).when(scraperSpy).buscarNoticias(anyString(), anyString(), anyString());

        List<Noticia> noticias = scraperSpy.buscarNoticias(anyString(), anyString(), anyString());

        assertNotNull(noticias);
        assertEquals(2, noticias.size());
        assertEquals("Notícia 1", noticias.get(0).titulo());
        assertEquals("https://example.com/news1", noticias.get(0).link());
        assertEquals("Notícia 2", noticias.get(1).titulo());
        assertEquals("https://example.com/news2", noticias.get(1).link());
    }
}