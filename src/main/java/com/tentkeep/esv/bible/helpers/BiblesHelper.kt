package com.tentkeep.esv.bible.helpers

import com.tentkeep.esv.bible.data.Verse
import com.tentkeep.esv.bible.models.BooksResponse
import com.tentkeep.esv.bible.models.PassageQuery
import com.tentkeep.esv.bible.services.ServiceProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.io.IOException
import java.util.*
import java.util.regex.Pattern

@Component
class BiblesHelper {
    @Autowired private val serviceProvider: ServiceProvider? = null

    @Throws(IOException::class)
    fun getBooks(translation: String): List<BooksResponse.Response.Book> {
        val biblesOrgTranslation = convertTranslation(translation)
                ?: throw RuntimeException("Invalid Bible translation $translation")
        return serviceProvider!!.biblesService.fetchBooks(biblesOrgTranslation).execute().body().response.books
    }

    @Throws(IOException::class)
    fun getVerses(translation: String, book: String, chapter: String): List<Verse> {
        val biblesOrgTranslation = convertTranslation(translation)
                ?: throw RuntimeException("Invalid Bible translation $translation")
        val biblesOrgBook = convertBook(book)
                ?: throw RuntimeException("Invalid Bible book $book")

        val response = serviceProvider!!.biblesService.fetchVerses(biblesOrgTranslation, biblesOrgBook, chapter)
                .execute().body()

        return response.response.verses.map { v ->
            var cleanText = v.text.replace("<sup[\\S\\s]*</sup>".toRegex(), "")
            cleanText = cleanText.replace("<h\\d[\\S\\s]*</h\\d>".toRegex(), "")
            cleanText = cleanText.replace("\n", "")
            cleanText = cleanText.replace("<p class=\"q\">([\\S\\s]*)</p>".toRegex(), " \"$1\"")
            cleanText = cleanText.replace("<[^>]*>".toRegex(), "")

            val pattern = Pattern.compile("<h3.*>(.*?)</h3>")
            val matcher = pattern.matcher(v.text)
            val header = if (matcher.find()) matcher.group(1) else null

            val verse = Verse(book, PassageQuery.Passage.VerseUnit(Integer.parseInt(chapter), Integer.parseInt(v.verse), cleanText.trim()))
            verse.heading = header
            verse
        }
    }

    private fun convertTranslation(translation: String): String? {
        val translations: HashMap<String, String> = hashMapOf("rvr60" to "spa-RVR1960")
        return translations[translation.toLowerCase()] ?: translation
    }

    private fun convertBook(book: String): String? {
        val books: HashMap<String, String> = hashMapOf("genesis" to "Gen", "exodus" to "Exod", "leviticus" to "Lev",
                "numbers" to "Num", "deuteronomy" to "Deut", "joshua" to "Josh", "judges" to "Judg", "ruth" to "Ruth",
                "1samuel" to "1Sam", "2samuel" to "2Sam", "1kings" to "1Kgs", "2kings" to "2Kgs", "1chronicles" to "1Chr",
                "2chronicles" to "2Chr", "ezra" to "Ezra", "nehemiah" to "Neh", "esther" to "Esth", "job" to "Job",
                "psalm" to "Ps", "proverbs" to "Prov", "ecclesiastes" to "Eccl", "songof solomon" to "Song",
                "isaiah" to "Isa", "jeremiah" to "Jer", "lamentations" to "Lam", "ezekiel" to "Ezek", "daniel" to "Dan",
                "hosea" to "Hos", "joel" to "Joel", "amos" to "Amos", "obadiah" to "Obad", "jonah" to "Jonah",
                "micah" to "Mic", "nahum" to "Nah", "habakkuk" to "Hab", "zephaniah" to "Zeph", "haggai" to "Hag",
                "zechariah" to "Zech", "malachi" to "Mal", "matthew" to "Matt", "mark" to "Mark", "luke" to "Luke",
                "john" to "John", "acts" to "Acts", "romans" to "Rom", "1corinthians" to "1Cor", "2corinthians" to "2Cor",
                "galatians" to "Gal", "ephesians" to "Eph", "philippians" to "Phil", "colossians" to "Col",
                "1thessalonians" to "1Thess", "2thessalonians" to "2Thess", "1timothy" to "1Tim", "2timothy" to "2Tim",
                "titus" to "Titus", "philemon" to "Phlm", "hebrews" to "Heb", "james" to "Jas", "1peter" to "1Pet",
                "2peter" to "2Pet", "1john" to "1John", "2john" to "2John", "3john" to "3John", "jude" to "Jude", "revelation" to "Rev")
        return books[book.toLowerCase()] ?: book
    }
}
