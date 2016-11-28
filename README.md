<h1>ESV Bible API</h1>
A wrapper API around the ESV api with a focus on clean, easily consumable JSON response data.

<h2><strong>GET</strong> /search/{query}    </h2>
Required header: 'x-esv-api-key': YOUR_ESV_API_KEY (<a href="http://www.esvapi.org/signup">ESV API signup page</a>)    
<blockquote>
    <strong>GET</strong> https://BASE_URL/search/jn1:10-14
    <br/>
    <br/>
    <pre>
{
  "passage": {
    "reference": "John 1:10-14",
    "verses": [
      {
        "number": 10,
        "text": "He was in the world, and the world was made through him, yet the world did not know him.",
        "heading": null,
        "subheading": null,
        "footnotes": []
      },
      {
        "number": 11,
        "text": "He came to his own, and his own people did not receive him.",
        "heading": null,
        "subheading": null,
        "footnotes": [
          {
            "text": "Greek <i>to his own things</i>; that is, to his own domain, or to his own people",
            "id": "1"
          },
          {
            "text": "<i>People</i> is implied in Greek",
            "id": "2"
          }
        ]
      },
      {
        "number": 12,
        "text": "But to all who did receive him, who believed in his name, he gave the right to become children of God,",
        "heading": null,
        "subheading": null,
        "footnotes": []
      },
      {
        "number": 13,
        "text": "who were born, not of blood nor of the will of the flesh nor of the will of man, but of God.",
        "heading": null,
        "subheading": null,
        "footnotes": []
      },
      {
        "number": 14,
        "text": "And the Word became flesh and dwelt among us, and we have seen his glory, glory as of the only Son from the Father, full of grace and truth.",
        "heading": null,
        "subheading": null,
        "footnotes": []
      }
    ]
  },
  "copyright": "The Holy Bible, English Standard Version copyright (c)2001 by Crossway Bibles, a division of Good News Publishers. Used by permission. All rights reserved. http://www.esv.org",
  "options": {
    "showParagraphMarkings": false,
    "showWordsOfChristMarkings": false,
    "showFootnotes": false,
    "showFormatting": false
  }
}
        </pre>
    </blockquote>
