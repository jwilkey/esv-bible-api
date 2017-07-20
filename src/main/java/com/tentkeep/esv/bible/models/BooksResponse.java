package com.tentkeep.esv.bible.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BooksResponse {
    @SerializedName("response")
    private Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response {
        private List<Book> books;

        public List<Book> getBooks() {
            return books;
        }

        public void setBooks(List<Book> books) {
            this.books = books;
        }

        public class Book {
            private String name;
            private String id;

            public String getName() {
                return name;
            }

            public String getId() {
                return id;
            }
        }
    }
}
