package com.craig.scholar.happy.service.codeexchange.bible.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.jupiter.api.Test;

class BibleReferenceTest {

  @Test
  void getBibleReference_StartChapterAndVerseNextVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4:24")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:24\\s)([\\s\\S]*?))(?:(\\s|\\n)(4:25|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and
        sevenfold.

        4:25 And Adam knew his wife again; and she bare a son, and called his
        name Seth: For God, said she, hath appointed me another seed instead
        of Abel, whom Cain slew.

        4:26 And to Seth, to him also there was born a son; and he called his
        name Enos: then began men to call upon the name of the LORD.

        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        5:3 And Adam lived an hundred and thirty years, and begat a son in his
        own likeness, and after his image; and called his name Seth: 5:4 And
        the days of Adam after he had begotten Seth were eight hundred years:
        and he begat sons and daughters: 5:5 And all the days that Adam lived
        were nine hundred and thirty years: and he died.

        5:6 And Seth lived an hundred and five years, and begat Enos: 5:7 And
        Seth lived after he begat Enos eight hundred and seven years, and
        begat sons and daughters: 5:8 And all the days of Seth were nine
        hundred and twelve years: and he died.""";
    String expectedPassage = "4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and sevenfold.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndVerseNextChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4:26")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:26\\s)([\\s\\S]*?))(?:(\\s|\\n)(4:27|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and
        sevenfold.

        4:25 And Adam knew his wife again; and she bare a son, and called his
        name Seth: For God, said she, hath appointed me another seed instead
        of Abel, whom Cain slew.

        4:26 And to Seth, to him also there was born a son; and he called his
        name Enos: then began men to call upon the name of the LORD.

        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        5:3 And Adam lived an hundred and thirty years, and begat a son in his
        own likeness, and after his image; and called his name Seth: 5:4 And
        the days of Adam after he had begotten Seth were eight hundred years:
        and he begat sons and daughters: 5:5 And all the days that Adam lived
        were nine hundred and thirty years: and he died.

        5:6 And Seth lived an hundred and five years, and begat Enos: 5:7 And
        Seth lived after he begat Enos eight hundred and seven years, and
        begat sons and daughters: 5:8 And all the days of Seth were nine
        hundred and twelve years: and he died.""";
    String expectedPassage = "4:26 And to Seth, to him also there was born a son; and he called his name Enos: then began men to call upon the name of the LORD.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndVerseEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4:26")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:26\\s)([\\s\\S]*?))(?:(\\s|\\n)(4:27|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and
        sevenfold.

        4:25 And Adam knew his wife again; and she bare a son, and called his
        name Seth: For God, said she, hath appointed me another seed instead
        of Abel, whom Cain slew.

        4:26 And to Seth, to him also there was born a son; and he called his
        name Enos: then began men to call upon the name of the LORD.

        """;
    String expectedPassage = "4:26 And to Seth, to him also there was born a son; and he called his name Enos: then began men to call upon the name of the LORD.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getPattern_StartChapterAndVerseMissing() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4:23")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:23\\s)([\\s\\S]*?))(?:(\\s|\\n)(4:24|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:24 If Cain shall be avenged sevenfold, truly Lamech seventy and
        sevenfold.

        4:25 And Adam knew his wife again; and she bare a son, and called his
        name Seth: For God, said she, hath appointed me another seed instead
        of Abel, whom Cain slew.

        4:26 And to Seth, to him also there was born a son; and he called his
        name Enos: then began men to call upon the name of the LORD.

        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        5:3 And Adam lived an hundred and thirty years, and begat a son in his
        own likeness, and after his image; and called his name Seth: 5:4 And
        the days of Adam after he had begotten Seth were eight hundred years:
        and he begat sons and daughters: 5:5 And all the days that Adam lived
        were nine hundred and thirty years: and he died.

        5:6 And Seth lived an hundred and five years, and begat Enos: 5:7 And
        Seth lived after he begat Enos eight hundred and seven years, and
        begat sons and daughters: 5:8 And all the days of Seth were nine
        hundred and twelve years: and he died.""";
    String expectedPassage = "";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterNextChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(5:1|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and
        said, I have gotten a man from the LORD.

        4:2 And she again bare his brother Abel. And Abel was a keeper of
        sheep, but Cain was a tiller of the ground.

        4:3 And in process of time it came to pass, that Cain brought of the
        fruit of the ground an offering unto the LORD.

        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        """;
    String expectedPassage =
        "4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and "
            + "said, I have gotten a man from the LORD. "
            + "4:2 And she again bare his brother Abel. And Abel was a keeper of "
            + "sheep, but Cain was a tiller of the ground. "
            + "4:3 And in process of time it came to pass, that Cain brought of the "
            + "fruit of the ground an offering unto the LORD.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(5:1|5:1)(\\s|\\n)|\\z)";
    String text = """
        4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and
        said, I have gotten a man from the LORD.

        4:2 And she again bare his brother Abel. And Abel was a keeper of
        sheep, but Cain was a tiller of the ground.

        4:3 And in process of time it came to pass, that Cain brought of the
        fruit of the ground an offering unto the LORD.

        """;
    String expectedPassage =
        "4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and "
            + "said, I have gotten a man from the LORD. "
            + "4:2 And she again bare his brother Abel. And Abel was a keeper of "
            + "sheep, but Cain was a tiller of the ground. "
            + "4:3 And in process of time it came to pass, that Cain brought of the "
            + "fruit of the ground an offering unto the LORD.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterPassageMissing() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 6")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(6:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(7:1|7:1)(\\s|\\n)|\\z)";
    String text = """
        4:1 And Adam knew Eve his wife; and she conceived, and bare Cain, and
        said, I have gotten a man from the LORD.

        4:2 And she again bare his brother Abel. And Abel was a keeper of
        sheep, but Cain was a tiller of the ground.

        4:3 And in process of time it came to pass, that Cain brought of the
        fruit of the ground an offering unto the LORD.

        5:1 This is the book of the generations of Adam. In the day that God
        created man, in the likeness of God made he him; 5:2 Male and female
        created he them; and blessed them, and called their name Adam, in the
        day when they were created.

        """;
    String expectedPassage = "";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartVerseNextVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("2 John 18")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(1:18\\s)([\\s\\S]*?))(?:(\\s|\\n)(1:19|2:1)(\\s|\\n)|\\z)";
    String text = """
        1:16 These are murmurers, complainers, walking after their own lusts;
        and their mouth speaketh great swelling words, having men’s persons in
        admiration because of advantage.

        1:17 But, beloved, remember ye the words which were spoken before of
        the apostles of our Lord Jesus Christ; 1:18 How that they told you
        there should be mockers in the last time, who should walk after their
        own ungodly lusts.

        1:19 These be they who separate themselves, sensual, having not the
        Spirit.

        1:20 But ye, beloved, building up yourselves on your most holy faith,
        praying in the Holy Ghost, 1:21 Keep yourselves in the love of God,
        looking for the mercy of our Lord Jesus Christ unto eternal life.

        1:22 And of some have compassion, making a difference: 1:23 And others
        save with fear, pulling them out of the fire; hating even the garment
        spotted by the flesh.

        1:24 Now unto him that is able to keep you from falling, and to
        present you faultless before the presence of his glory with exceeding
        joy, 1:25 To the only wise God our Saviour, be glory and majesty,
        dominion and power, both now and ever. Amen.

        """;
    String expectedPassage = "1:18 How that they told you "
        + "there should be mockers in the last time, who should walk after their "
        + "own ungodly lusts.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartVerseEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("2 John 25")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(1:25\\s)([\\s\\S]*?))(?:(\\s|\\n)(1:26|2:1)(\\s|\\n)|\\z)";
    String text = """
        1:16 These are murmurers, complainers, walking after their own lusts;
        and their mouth speaketh great swelling words, having men’s persons in
        admiration because of advantage.

        1:17 But, beloved, remember ye the words which were spoken before of
        the apostles of our Lord Jesus Christ; 1:18 How that they told you
        there should be mockers in the last time, who should walk after their
        own ungodly lusts.

        1:19 These be they who separate themselves, sensual, having not the
        Spirit.

        1:20 But ye, beloved, building up yourselves on your most holy faith,
        praying in the Holy Ghost, 1:21 Keep yourselves in the love of God,
        looking for the mercy of our Lord Jesus Christ unto eternal life.

        1:22 And of some have compassion, making a difference: 1:23 And others
        save with fear, pulling them out of the fire; hating even the garment
        spotted by the flesh.

        1:24 Now unto him that is able to keep you from falling, and to
        present you faultless before the presence of his glory with exceeding
        joy, 1:25 To the only wise God our Saviour, be glory and majesty,
        dominion and power, both now and ever. Amen.

        """;
    String expectedPassage = "1:25 To the only wise God our Saviour, be glory and majesty, "
        + "dominion and power, both now and ever. Amen.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartVerse_PassageMissing() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("2 John 26")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(1:26\\s)([\\s\\S]*?))(?:(\\s|\\n)(1:27|2:1)(\\s|\\n)|\\z)";
    String text = """
        1:16 These are murmurers, complainers, walking after their own lusts;
        and their mouth speaketh great swelling words, having men’s persons in
        admiration because of advantage.

        1:17 But, beloved, remember ye the words which were spoken before of
        the apostles of our Lord Jesus Christ; 1:18 How that they told you
        there should be mockers in the last time, who should walk after their
        own ungodly lusts.

        1:19 These be they who separate themselves, sensual, having not the
        Spirit.

        1:20 But ye, beloved, building up yourselves on your most holy faith,
        praying in the Holy Ghost, 1:21 Keep yourselves in the love of God,
        looking for the mercy of our Lord Jesus Christ unto eternal life.

        1:22 And of some have compassion, making a difference: 1:23 And others
        save with fear, pulling them out of the fire; hating even the garment
        spotted by the flesh.

        1:24 Now unto him that is able to keep you from falling, and to
        present you faultless before the presence of his glory with exceeding
        joy, 1:25 To the only wise God our Saviour, be glory and majesty,
        dominion and power, both now and ever. Amen.

        """;
    String expectedPassage = "";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterStartVerseAndEndVerseNextVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 2:5-7")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(2:5\\s)([\\s\\S]*?))(?:(\\s|\\n)(2:8|3:1)(\\s|\\n)|\\z)";
    String text = """

        2:1 Unto the angel of the church of Ephesus write; These things saith
        he that holdeth the seven stars in his right hand, who walketh in the
        midst of the seven golden candlesticks; 2:2 I know thy works, and thy
        labour, and thy patience, and how thou canst not bear them which are
        evil: and thou hast tried them which say they are apostles, and are
        not, and hast found them liars: 2:3 And hast borne, and hast patience,
        and for my name’s sake hast laboured, and hast not fainted.

        2:4 Nevertheless I have somewhat against thee, because thou hast left
        thy first love.

        2:5 Remember therefore from whence thou art fallen, and repent, and do
        the first works; or else I will come unto thee quickly, and will
        remove thy candlestick out of his place, except thou repent.

        2:6 But this thou hast, that thou hatest the deeds of the
        Nicolaitanes, which I also hate.

        2:7 He that hath an ear, let him hear what the Spirit saith unto the
        churches; To him that overcometh will I give to eat of the tree of
        life, which is in the midst of the paradise of God.

        2:8 And unto the angel of the church in Smyrna write; These things
        saith the first and the last, which was dead, and is alive; 2:9 I know
        thy works, and tribulation, and poverty, (but thou art rich) and I
        know the blasphemy of them which say they are Jews, and are not, but
        are the synagogue of Satan.

        2:10 Fear none of those things which thou shalt suffer: behold, the
        devil shall cast some of you into prison, that ye may be tried; and ye
        shall have tribulation ten days: be thou faithful unto death, and I
        will give thee a crown of life.

        2:11 He that hath an ear, let him hear what the Spirit saith unto the
        churches; He that overcometh shall not be hurt of the second death.

        2:12 And to the angel of the church in Pergamos write; These things
        saith he which hath the sharp sword with two edges; 2:13 I know thy
        works, and where thou dwellest, even where Satan’s seat is: and thou
        holdest fast my name, and hast not denied my faith, even in those days
        wherein Antipas was my faithful martyr, who was slain among you, where
        Satan dwelleth.

        2:14 But I have a few things against thee, because thou hast there
        them that hold the doctrine of Balaam, who taught Balac to cast a
        stumblingblock before the children of Israel, to eat things sacrificed
        unto idols, and to commit fornication.""";
    String expectedPassage =
        "2:5 Remember therefore from whence thou art fallen, and repent, and do "
            + "the first works; or else I will come unto thee quickly, and will "
            + "remove thy candlestick out of his place, except thou repent. "
            + "2:6 But this thou hast, that thou hatest the deeds of the "
            + "Nicolaitanes, which I also hate. "
            + "2:7 He that hath an ear, let him hear what the Spirit saith unto the "
            + "churches; To him that overcometh will I give to eat of the tree of "
            + "life, which is in the midst of the paradise of God.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterStartVerseAndEndVerseNextChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 2:5-7")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(2:5\\s)([\\s\\S]*?))(?:(\\s|\\n)(2:8|3:1)(\\s|\\n)|\\z)";
    String text = """

        2:1 Unto the angel of the church of Ephesus write; These things saith
        he that holdeth the seven stars in his right hand, who walketh in the
        midst of the seven golden candlesticks; 2:2 I know thy works, and thy
        labour, and thy patience, and how thou canst not bear them which are
        evil: and thou hast tried them which say they are apostles, and are
        not, and hast found them liars: 2:3 And hast borne, and hast patience,
        and for my name’s sake hast laboured, and hast not fainted.

        2:4 Nevertheless I have somewhat against thee, because thou hast left
        thy first love.

        2:5 Remember therefore from whence thou art fallen, and repent, and do
        the first works; or else I will come unto thee quickly, and will
        remove thy candlestick out of his place, except thou repent.

        2:6 But this thou hast, that thou hatest the deeds of the
        Nicolaitanes, which I also hate.

        2:7 He that hath an ear, let him hear what the Spirit saith unto the
        churches; To him that overcometh will I give to eat of the tree of
        life, which is in the midst of the paradise of God.

        3:1 And unto the angel of the church in Sardis write; These things
        saith he that hath the seven Spirits of God, and the seven stars; I
        know thy works, that thou hast a name that thou livest, and art dead.

        """;
    String expectedPassage =
        "2:5 Remember therefore from whence thou art fallen, and repent, and do "
            + "the first works; or else I will come unto thee quickly, and will "
            + "remove thy candlestick out of his place, except thou repent. "
            + "2:6 But this thou hast, that thou hatest the deeds of the "
            + "Nicolaitanes, which I also hate. "
            + "2:7 He that hath an ear, let him hear what the Spirit saith unto the "
            + "churches; To him that overcometh will I give to eat of the tree of "
            + "life, which is in the midst of the paradise of God.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterStartVerseAndEndVerseEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 2:5-7")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(2:5\\s)([\\s\\S]*?))(?:(\\s|\\n)(2:8|3:1)(\\s|\\n)|\\z)";
    String text = """

        2:1 Unto the angel of the church of Ephesus write; These things saith
        he that holdeth the seven stars in his right hand, who walketh in the
        midst of the seven golden candlesticks; 2:2 I know thy works, and thy
        labour, and thy patience, and how thou canst not bear them which are
        evil: and thou hast tried them which say they are apostles, and are
        not, and hast found them liars: 2:3 And hast borne, and hast patience,
        and for my name’s sake hast laboured, and hast not fainted.

        2:4 Nevertheless I have somewhat against thee, because thou hast left
        thy first love.

        2:5 Remember therefore from whence thou art fallen, and repent, and do
        the first works; or else I will come unto thee quickly, and will
        remove thy candlestick out of his place, except thou repent.

        2:6 But this thou hast, that thou hatest the deeds of the
        Nicolaitanes, which I also hate.

        2:7 He that hath an ear, let him hear what the Spirit saith unto the
        churches; To him that overcometh will I give to eat of the tree of
        life, which is in the midst of the paradise of God.



        """;
    String expectedPassage =
        "2:5 Remember therefore from whence thou art fallen, and repent, and do "
            + "the first works; or else I will come unto thee quickly, and will "
            + "remove thy candlestick out of his place, except thou repent. "
            + "2:6 But this thou hast, that thou hatest the deeds of the "
            + "Nicolaitanes, which I also hate. "
            + "2:7 He that hath an ear, let him hear what the Spirit saith unto the "
            + "churches; To him that overcometh will I give to eat of the tree of "
            + "life, which is in the midst of the paradise of God.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndEndChapterNextChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4-6")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(7:1|7:1)(\\s|\\n)|\\z)";
    String text = """

        4:1 After this I looked, and, behold, a door was opened in heaven: and
        the first voice which I heard was as it were of a trumpet talking with
        me; which said, Come up hither, and I will shew thee things which must
        be hereafter.

        4:2 And immediately I was in the spirit: and, behold, a throne was set
        in heaven, and one sat on the throne.

        5:1 And I saw in the right hand of him that sat on the throne a book
        written within and on the backside, sealed with seven seals.

        5:2 And I saw a strong angel proclaiming with a loud voice, Who is
        worthy to open the book, and to loose the seals thereof? 5:3 And no
        man in heaven, nor in earth, neither under the earth, was able to open
        the book, neither to look thereon.

        6:1 And I saw when the Lamb opened one of the seals, and I heard, as
        it were the noise of thunder, one of the four beasts saying, Come and
        see.

        6:2 And I saw, and behold a white horse: and he that sat on him had a
        bow; and a crown was given unto him: and he went forth conquering, and
        to conquer. 7:1 And after these things I saw four
        angels standing on the four corners of the earth, holding the four
        winds of the earth, that the wind should not blow on the earth, nor on
        the sea, nor on any tree.

        """;
    String expectedPassage =
        "4:1 After this I looked, and, behold, a door was opened in heaven: and "
            + "the first voice which I heard was as it were of a trumpet talking with "
            + "me; which said, Come up hither, and I will shew thee things which must "
            + "be hereafter. "
            + "4:2 And immediately I was in the spirit: and, behold, a throne was set "
            + "in heaven, and one sat on the throne. "
            + "5:1 And I saw in the right hand of him that sat on the throne a book "
            + "written within and on the backside, sealed with seven seals. "
            + "5:2 And I saw a strong angel proclaiming with a loud voice, Who is "
            + "worthy to open the book, and to loose the seals thereof? 5:3 And no "
            + "man in heaven, nor in earth, neither under the earth, was able to open "
            + "the book, neither to look thereon. "
            + "6:1 And I saw when the Lamb opened one of the seals, and I heard, as "
            + "it were the noise of thunder, one of the four beasts saying, Come and "
            + "see. "
            + "6:2 And I saw, and behold a white horse: and he that sat on him had a "
            + "bow; and a crown was given unto him: and he went forth conquering, and "
            + "to conquer.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndEndChapterEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4-6")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(7:1|7:1)(\\s|\\n)|\\z)";
    String text = """

        4:1 After this I looked, and, behold, a door was opened in heaven: and
        the first voice which I heard was as it were of a trumpet talking with
        me; which said, Come up hither, and I will shew thee things which must
        be hereafter.

        4:2 And immediately I was in the spirit: and, behold, a throne was set
        in heaven, and one sat on the throne.

        5:1 And I saw in the right hand of him that sat on the throne a book
        written within and on the backside, sealed with seven seals.

        5:2 And I saw a strong angel proclaiming with a loud voice, Who is
        worthy to open the book, and to loose the seals thereof? 5:3 And no
        man in heaven, nor in earth, neither under the earth, was able to open
        the book, neither to look thereon.

        6:1 And I saw when the Lamb opened one of the seals, and I heard, as
        it were the noise of thunder, one of the four beasts saying, Come and
        see.

        6:2 And I saw, and behold a white horse: and he that sat on him had a
        bow; and a crown was given unto him: and he went forth conquering, and
        to conquer.

        """;
    String expectedPassage =
        "4:1 After this I looked, and, behold, a door was opened in heaven: and "
            + "the first voice which I heard was as it were of a trumpet talking with "
            + "me; which said, Come up hither, and I will shew thee things which must "
            + "be hereafter. "
            + "4:2 And immediately I was in the spirit: and, behold, a throne was set "
            + "in heaven, and one sat on the throne. "
            + "5:1 And I saw in the right hand of him that sat on the throne a book "
            + "written within and on the backside, sealed with seven seals. "
            + "5:2 And I saw a strong angel proclaiming with a loud voice, Who is "
            + "worthy to open the book, and to loose the seals thereof? 5:3 And no "
            + "man in heaven, nor in earth, neither under the earth, was able to open "
            + "the book, neither to look thereon. "
            + "6:1 And I saw when the Lamb opened one of the seals, and I heard, as "
            + "it were the noise of thunder, one of the four beasts saying, Come and "
            + "see. "
            + "6:2 And I saw, and behold a white horse: and he that sat on him had a "
            + "bow; and a crown was given unto him: and he went forth conquering, and "
            + "to conquer.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndEndChapter_PassageMissing() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 3-6")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(3:1\\s)([\\s\\S]*?))(?:(\\s|\\n)(7:1|7:1)(\\s|\\n)|\\z)";
    String text = """

        4:1 After this I looked, and, behold, a door was opened in heaven: and
        the first voice which I heard was as it were of a trumpet talking with
        me; which said, Come up hither, and I will shew thee things which must
        be hereafter.

        4:2 And immediately I was in the spirit: and, behold, a throne was set
        in heaven, and one sat on the throne.

        5:1 And I saw in the right hand of him that sat on the throne a book
        written within and on the backside, sealed with seven seals.

        5:2 And I saw a strong angel proclaiming with a loud voice, Who is
        worthy to open the book, and to loose the seals thereof? 5:3 And no
        man in heaven, nor in earth, neither under the earth, was able to open
        the book, neither to look thereon.

        6:1 And I saw when the Lamb opened one of the seals, and I heard, as
        it were the noise of thunder, one of the four beasts saying, Come and
        see.

        6:2 And I saw, and behold a white horse: and he that sat on him had a
        bow; and a crown was given unto him: and he went forth conquering, and
        to conquer.

        """;
    String expectedPassage = "";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndStartVerseAndEndChapterAndEndVerseNextVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4:2-6:1")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:2\\s)([\\s\\S]*?))(?:(\\s|\\n)(6:2|7:1)(\\s|\\n)|\\z)";
    String text = """

        4:1 After this I looked, and, behold, a door was opened in heaven: and
        the first voice which I heard was as it were of a trumpet talking with
        me; which said, Come up hither, and I will shew thee things which must
        be hereafter.

        4:2 And immediately I was in the spirit: and, behold, a throne was set
        in heaven, and one sat on the throne.

        5:1 And I saw in the right hand of him that sat on the throne a book
        written within and on the backside, sealed with seven seals.

        5:2 And I saw a strong angel proclaiming with a loud voice, Who is
        worthy to open the book, and to loose the seals thereof? 5:3 And no
        man in heaven, nor in earth, neither under the earth, was able to open
        the book, neither to look thereon.

        6:1 And I saw when the Lamb opened one of the seals, and I heard, as
        it were the noise of thunder, one of the four beasts saying, Come and
        see.

        6:2 And I saw, and behold a white horse: and he that sat on him had a
        bow; and a crown was given unto him: and he went forth conquering, and
        to conquer. 7:1 And after these things I saw four
        angels standing on the four corners of the earth, holding the four
        winds of the earth, that the wind should not blow on the earth, nor on
        the sea, nor on any tree.

        """;
    String expectedPassage =
        "4:2 And immediately I was in the spirit: and, behold, a throne was set "
            + "in heaven, and one sat on the throne. "
            + "5:1 And I saw in the right hand of him that sat on the throne a book "
            + "written within and on the backside, sealed with seven seals. "
            + "5:2 And I saw a strong angel proclaiming with a loud voice, Who is "
            + "worthy to open the book, and to loose the seals thereof? 5:3 And no "
            + "man in heaven, nor in earth, neither under the earth, was able to open "
            + "the book, neither to look thereon. "
            + "6:1 And I saw when the Lamb opened one of the seals, and I heard, as "
            + "it were the noise of thunder, one of the four beasts saying, Come and "
            + "see.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndStartVerseAndEndChapterAndEndVerseNextChapter() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4:2-6:4")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:2\\s)([\\s\\S]*?))(?:(\\s|\\n)(6:5|7:1)(\\s|\\n)|\\z)";
    String text = """

        4:1 After this I looked, and, behold, a door was opened in heaven: and
        the first voice which I heard was as it were of a trumpet talking with
        me; which said, Come up hither, and I will shew thee things which must
        be hereafter.

        4:2 And immediately I was in the spirit: and, behold, a throne was set
        in heaven, and one sat on the throne.

        5:1 And I saw in the right hand of him that sat on the throne a book
        written within and on the backside, sealed with seven seals.

        5:2 And I saw a strong angel proclaiming with a loud voice, Who is
        worthy to open the book, and to loose the seals thereof? 5:3 And no
        man in heaven, nor in earth, neither under the earth, was able to open
        the book, neither to look thereon.

        6:1 And I saw when the Lamb opened one of the seals, and I heard, as
        it were the noise of thunder, one of the four beasts saying, Come and
        see.

        6:2 And I saw, and behold a white horse: and he that sat on him had a
        bow; and a crown was given unto him: and he went forth conquering, and
        to conquer. 7:1 And after these things I saw four
        angels standing on the four corners of the earth, holding the four
        winds of the earth, that the wind should not blow on the earth, nor on
        the sea, nor on any tree.

        """;
    String expectedPassage =
        "4:2 And immediately I was in the spirit: and, behold, a throne was set "
            + "in heaven, and one sat on the throne. "
            + "5:1 And I saw in the right hand of him that sat on the throne a book "
            + "written within and on the backside, sealed with seven seals. "
            + "5:2 And I saw a strong angel proclaiming with a loud voice, Who is "
            + "worthy to open the book, and to loose the seals thereof? 5:3 And no "
            + "man in heaven, nor in earth, neither under the earth, was able to open "
            + "the book, neither to look thereon. "
            + "6:1 And I saw when the Lamb opened one of the seals, and I heard, as "
            + "it were the noise of thunder, one of the four beasts saying, Come and "
            + "see. "
            + "6:2 And I saw, and behold a white horse: and he that sat on him had a "
            + "bow; and a crown was given unto him: and he went forth conquering, and "
            + "to conquer.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartChapterAndStartVerseAndEndChapterAndEndVerseEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("John 4:2-6:4")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(4:2\\s)([\\s\\S]*?))(?:(\\s|\\n)(6:5|7:1)(\\s|\\n)|\\z)";
    String text = """

        4:1 After this I looked, and, behold, a door was opened in heaven: and
        the first voice which I heard was as it were of a trumpet talking with
        me; which said, Come up hither, and I will shew thee things which must
        be hereafter.

        4:2 And immediately I was in the spirit: and, behold, a throne was set
        in heaven, and one sat on the throne.

        5:1 And I saw in the right hand of him that sat on the throne a book
        written within and on the backside, sealed with seven seals.

        5:2 And I saw a strong angel proclaiming with a loud voice, Who is
        worthy to open the book, and to loose the seals thereof? 5:3 And no
        man in heaven, nor in earth, neither under the earth, was able to open
        the book, neither to look thereon.

        6:1 And I saw when the Lamb opened one of the seals, and I heard, as
        it were the noise of thunder, one of the four beasts saying, Come and
        see.

        6:2 And I saw, and behold a white horse: and he that sat on him had a
        bow; and a crown was given unto him: and he went forth conquering, and
        to conquer.

        """;
    String expectedPassage =
        "4:2 And immediately I was in the spirit: and, behold, a throne was set "
            + "in heaven, and one sat on the throne. "
            + "5:1 And I saw in the right hand of him that sat on the throne a book "
            + "written within and on the backside, sealed with seven seals. "
            + "5:2 And I saw a strong angel proclaiming with a loud voice, Who is "
            + "worthy to open the book, and to loose the seals thereof? 5:3 And no "
            + "man in heaven, nor in earth, neither under the earth, was able to open "
            + "the book, neither to look thereon. "
            + "6:1 And I saw when the Lamb opened one of the seals, and I heard, as "
            + "it were the noise of thunder, one of the four beasts saying, Come and "
            + "see. "
            + "6:2 And I saw, and behold a white horse: and he that sat on him had a "
            + "bow; and a crown was given unto him: and he went forth conquering, and "
            + "to conquer.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_StartVerseAndEndVerse() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("Jude 17-21")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(1:17\\s)([\\s\\S]*?))(?:(\\s|\\n)(1:22|2:1)(\\s|\\n)|\\z)";
    String text = """
        1:16 These are murmurers, complainers, walking after their own lusts;
        and their mouth speaketh great swelling words, having men’s persons in
        admiration because of advantage.

        1:17 But, beloved, remember ye the words which were spoken before of
        the apostles of our Lord Jesus Christ; 1:18 How that they told you
        there should be mockers in the last time, who should walk after their
        own ungodly lusts.

        1:19 These be they who separate themselves, sensual, having not the
        Spirit.

        1:20 But ye, beloved, building up yourselves on your most holy faith,
        praying in the Holy Ghost, 1:21 Keep yourselves in the love of God,
        looking for the mercy of our Lord Jesus Christ unto eternal life.

        1:22 And of some have compassion, making a difference: 1:23 And others
        save with fear, pulling them out of the fire; hating even the garment
        spotted by the flesh.

        1:24 Now unto him that is able to keep you from falling, and to
        present you faultless before the presence of his glory with exceeding
        joy, 1:25 To the only wise God our Saviour, be glory and majesty,
        dominion and power, both now and ever. Amen.

        """;
    String expectedPassage = "1:17 But, beloved, remember ye the words which were spoken before of "
        + "the apostles of our Lord Jesus Christ; 1:18 How that they told you "
        + "there should be mockers in the last time, who should walk after their "
        + "own ungodly lusts. "
        + "1:19 These be they who separate themselves, sensual, having not the "
        + "Spirit. "
        + "1:20 But ye, beloved, building up yourselves on your most holy faith, "
        + "praying in the Holy Ghost, 1:21 Keep yourselves in the love of God, "
        + "looking for the mercy of our Lord Jesus Christ unto eternal life.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }


  @Test
  void getBibleReference_StartVerseAndEndVerseEndOfBook() {
    BibleReference bibleReference = BibleReference.builder()
        .reference("Jude 17-21")
        .build();
    String expectedPatternString = "(^|\\s|\\n)(?<passages>(1:17\\s)([\\s\\S]*?))(?:(\\s|\\n)(1:22|2:1)(\\s|\\n)|\\z)";
    String text = """
        1:16 These are murmurers, complainers, walking after their own lusts;
        and their mouth speaketh great swelling words, having men’s persons in
        admiration because of advantage.

        1:17 But, beloved, remember ye the words which were spoken before of
        the apostles of our Lord Jesus Christ; 1:18 How that they told you
        there should be mockers in the last time, who should walk after their
        own ungodly lusts.

        1:19 These be they who separate themselves, sensual, having not the
        Spirit.

        1:20 But ye, beloved, building up yourselves on your most holy faith,
        praying in the Holy Ghost, 1:21 Keep yourselves in the love of God,
        looking for the mercy of our Lord Jesus Christ unto eternal life.

        """;
    String expectedPassage = "1:17 But, beloved, remember ye the words which were spoken before of "
        + "the apostles of our Lord Jesus Christ; 1:18 How that they told you "
        + "there should be mockers in the last time, who should walk after their "
        + "own ungodly lusts. "
        + "1:19 These be they who separate themselves, sensual, having not the "
        + "Spirit. "
        + "1:20 But ye, beloved, building up yourselves on your most holy faith, "
        + "praying in the Holy Ghost, 1:21 Keep yourselves in the love of God, "
        + "looking for the mercy of our Lord Jesus Christ unto eternal life.";
    assertBibleReferencePattern(bibleReference.getPattern(), expectedPatternString, text,
        expectedPassage);
  }

  @Test
  void getBibleReference_OnlyName() {
    assertThatThrownBy(() -> BibleReference.builder()
        .reference("John")
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getBibleReference_InvalidName() {
    assertThatThrownBy(() -> BibleReference.builder()
        .reference("Mary")
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getBibleReference_ChapterButMissingVerse() {
    assertThatThrownBy(() -> BibleReference.builder()
        .reference("John 1:")
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void getBibleReference_StartChapterAndEndChapterAndEndVerse() {
    assertThatThrownBy(() -> BibleReference.builder()
        .reference("John 1-4:5")
        .build())
        .isInstanceOf(IllegalArgumentException.class);
  }

  private void assertBibleReferencePattern(Pattern pattern, String expectedPatternString,
      String text,
      String expectedPassage) {
    assertThat(pattern.pattern())
        .isEqualTo(expectedPatternString);
    Matcher nextVerseMatcher = pattern.matcher(text);
    if (expectedPassage.isEmpty()) {
      assertThat(nextVerseMatcher.find()).isFalse();
    } else {
      assertThat(nextVerseMatcher.find()).isTrue();
      String passage = nextVerseMatcher.group("passages");
      passage = passage.trim();
      passage = passage.replaceAll("\\R", " ");
      passage = passage.replaceAll("\\s+", " ");
      assertThat(passage).isEqualTo(expectedPassage);
    }
  }

}