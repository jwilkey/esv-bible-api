package com.tentkeep.esv.bible.controllers;

import com.tentkeep.esv.bible.helpers.EsvHelper;
import com.tentkeep.esv.bible.models.PassageQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController {
    @Autowired private EsvHelper esvHelper;

    @RequestMapping(value = "/search/{query}", produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public PassageQuery search(@RequestHeader("x-esv-api-key") String esvApiKey, @PathVariable("query") String query) throws Exception {
        return esvHelper.fetch(esvApiKey, query);
    }
}
