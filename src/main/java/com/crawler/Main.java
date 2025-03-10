package com.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.crawler.model.Noticia;
import com.crawler.scraper.NoticiaScraper;
import com.crawler.scraper.NoticiaScraperConcorrente;
import com.crawler.scraper.NoticiaScraperMultiSite;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        NoticiaScraperConcorrente scraper = new NoticiaScraperConcorrente();
        scraper.buscarNoticiasComThreads();

    }

}
