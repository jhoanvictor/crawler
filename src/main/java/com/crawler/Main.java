package com.crawler;

import com.crawler.scraper.NoticiaScraperConcorrente;

public class Main {

    public static void main(String[] args) {

        NoticiaScraperConcorrente scraper = new NoticiaScraperConcorrente();
        scraper.buscarNoticiasComThreads();

    }

}
