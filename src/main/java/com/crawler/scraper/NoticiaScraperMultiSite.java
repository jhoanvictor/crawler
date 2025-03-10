package com.crawler.scraper;

import com.crawler.model.Noticia;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Deprecated
public class NoticiaScraperMultiSite {

    private static final Map<String, String> SITES_OLD = Map.of(
            "https://g1.globo.com/", "a.feed-post-link",
            "https://www.folha.uol.com.br/", "block__news__thumb",
            "https://www.estadao.com.br/", "li.bullet"
    );

    private static final Map<String, String> SITES = Map.of(
            "https://www.cnnbrasil.com.br/", "li.block__news__item"
    );

    public List<Noticia> buscarNoticias(){

        List<Noticia> noticias = new ArrayList<Noticia>();

        try {
            for (Map.Entry<String, String> site : SITES.entrySet()) {

                Document doc = Jsoup.connect(site.getKey()).get();

                Elements manchetes = doc.select(site.getValue());

                for (Element manchete : manchetes) {
                    String titulo = manchete.select("h3.block__news__title").text();
                    String link = manchete.select("a").attr("href");
                    noticias.add(new Noticia(titulo, link, LocalDate.now()));
                }

            }

        } catch (IOException e) {
            System.err.println("Erro ao buscar notícias de vários sites: " + e.getMessage());
        }

        return noticias;

    }

}
