package com.tentkeep.esv.bible.data;

import org.springframework.data.repository.CrudRepository;

public interface VerseRepository extends CrudRepository<Verse, Long> {
    Iterable<Verse> findByBookAndChapter(String book, int chapter);
}
