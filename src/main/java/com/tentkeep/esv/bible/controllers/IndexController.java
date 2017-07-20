package com.tentkeep.esv.bible.controllers;

import com.tentkeep.esv.bible.data.Verse;
import com.tentkeep.esv.bible.helpers.BiblesHelper;
import com.tentkeep.esv.bible.helpers.EsvHelper;
import com.tentkeep.esv.bible.models.BooksResponse;
import com.tentkeep.esv.bible.models.PassageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
public class IndexController {
    @Autowired private EsvHelper esvHelper;
    @Autowired private BiblesHelper biblesHelper;

    @RequestMapping("/")
    public String index() {
        return "home";
    }

    @RequestMapping(value = "/search/{query}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    @ResponseBody
    public PassageQuery search(@RequestHeader("x-esv-api-key") String esvApiKey, @PathVariable("query") String query) throws Exception {
        return esvHelper.fetch(esvApiKey, query);
    }

    @RequestMapping(value = "/esv/{book}/{chapter}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    @ResponseBody
    public Iterable<Verse> esv(@RequestHeader("x-esv-api-key") String esvApiKey, @PathVariable("book") String book, @PathVariable("chapter") int chapter) throws Exception {
        return esvHelper.fetch(esvApiKey, book, chapter);
    }

    @RequestMapping(value = "/books/{translation}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    @ResponseBody
    public List<BooksResponse.Response.Book> books(@PathVariable("translation") String translation) throws IOException {
        return biblesHelper.getBooks(translation);
    }

    @RequestMapping(value = "/verses/{translation}/{book}/{chapter}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @CrossOrigin
    @ResponseBody
    public List<Verse> verses(@PathVariable("translation") String translation,
                                                    @PathVariable("book") String book,
                                                    @PathVariable("chapter") String chapter) throws IOException {
        return biblesHelper.getVerses(translation, book, chapter);
    }
}
