package com.tentkeep.esv.bible.controllers;

import com.tentkeep.esv.bible.data.Verse;
import com.tentkeep.esv.bible.helpers.EsvHelper;
import com.tentkeep.esv.bible.models.PassageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {
    @Autowired private EsvHelper esvHelper;

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
}
