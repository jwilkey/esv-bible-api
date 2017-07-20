package com.tentkeep.esv.bible.models;

import java.util.List;

public class VersesResponse {
    private Response response;

    public Response getResponse() {
        return response;
    }

    public class Response {
        private List<Verse> verses;

        public List<Verse> getVerses() {
            return verses;
        }
    }

    public class Verse {
        private String reference;
        private String id;
        private String text;
        private String verse;

        public String getReference() {
            return reference;
        }

        public String getId() {
            return id;
        }

        public String getText() {
            return text;
        }

        public String getVerse() {
            return verse;
        }
    }
}
