package be.iccbxl.pid.reservationsSpringBoot.controller;

import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndContentImpl;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedOutput;

import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.service.ShowService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringWriter;
import java.util.List;

@RestController
public class RssRestController {

    @Autowired
    private ShowService showService;

    @GetMapping(value = "/rss/shows", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getShowRSS() {
        List<Show> shows = showService.getAll(); // Fetch all shows from the service
        String rssFeed = generateShowRSS(shows); // Generate the RSS feed as XML String

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE);

        return ResponseEntity.ok().headers(responseHeaders).body(rssFeed);
    }

    private String generateShowRSS(List<Show> shows) {
        SyndFeed feed = new SyndFeedImpl();
        feed.setFeedType("rss_2.0"); 

        feed.setTitle("Sample Feed (created with Rome)");
        feed.setLink("http://rome.dev.java.net");
        feed.setDescription("This feed has been created using Rome (Java syndication utilities)");

        for (Show show : shows) {
            SyndEntry entry = new SyndEntryImpl();
            entry.setTitle(show.getTitle());
            entry.setLink("https://localhost:8090/show/" + show.getId()); // Lien vers les détails individuels du spectacle

            SyndContent description = new SyndContentImpl();
            description.setValue(show.getDescription());
            entry.setDescription(description);
            

            feed.getEntries().add(entry);
        }

        SyndFeedOutput output = new SyndFeedOutput();
        StringWriter writer = new StringWriter();

        try {
            output.output(feed, writer);
        } catch (FeedException | IOException e) {
            e.printStackTrace();
            return "";
        }

        return writer.toString();
    }

}
