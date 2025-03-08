package com.crawler;

import com.crawler.model.Noticia;
import com.crawler.scraper.NoticiaScraper;
import com.crawler.scraper.NoticiaScraperConcorrente;
import com.crawler.scraper.NoticiaScraperMultiSite;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        //mainNoticiaScrapper();
        //mainNoticiasScrapperMultiSite();
        mainNoticiasScrapperConcorrente();

    }

    private static void mainNoticiasScrapperConcorrente() {
        NoticiaScraperConcorrente scraper = new NoticiaScraperConcorrente();
        scraper.buscarNoticiasComThreads();
    }

    private static void mainNoticiasScrapperMultiSite() {
        NoticiaScraperMultiSite scraperMultiSite = new NoticiaScraperMultiSite();
        List<Noticia> noticias = scraperMultiSite.buscarNoticias();
        noticias.forEach(noticia -> System.out.println(noticia.titulo() + " - " + noticia.link()));
    }

    private static void mainNoticiaScrapper() {
        NoticiaScraper scraper = new NoticiaScraper();

        String url = "https://g1.globo.com/";
        String seletorTitulo = "a.feed-post-link"; // Seletor CSS das manchetes
        String seletorLink = "a.feed-post-link";

        List<Noticia> noticias = scraper.buscarNoticias(url, seletorTitulo, seletorLink);
        noticias.forEach(n -> System.out.println(n.titulo() + " - " + n.link()));

        //Usando seletores diferentes
        try {
            // Conectar ao site
            Document doc = Jsoup.connect("https://www.estadao.com.br/").get();

            // Selecionar os elementos que contêm tanto o título quanto o link
            Elements newsElements = doc.select("div.info");

            // Iterar pelos elementos e imprimir os títulos e links
            for (Element newsElement : newsElements) {
                String title = newsElement.select("b").text();
                String link = newsElement.select("a").attr("href");
                System.out.println("Título: " + title);
                System.out.println("Link: " + link);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
