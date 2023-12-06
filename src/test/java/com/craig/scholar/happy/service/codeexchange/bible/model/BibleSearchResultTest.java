package com.craig.scholar.happy.service.codeexchange.bible.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Objects;
import org.junit.jupiter.api.Test;

class BibleSearchResultTest {

  @Test
  void getBibleSearchResult() {
    BibleSearchResult bibleSearchResult = new BibleSearchResult(
        """
            1:14 His head and his hairs were white like wool, as white as snow;
            and his eyes were as a flame of fire; 1:15 And his feet like unto fine
            brass, as if they burned in a furnace; and his voice as the sound of
            many waters.

            1:16 And he had in his right hand seven stars: and out of his mouth
            went a sharp twoedged sword: and his countenance was as the sun
            shineth in his strength.

            1:17 And when I saw him, I fell at his feet as dead. And he laid his
            right hand upon me, saying unto me, Fear not; I am the first and the
            last: 1:18 I am he that liveth, and was dead; and, behold, I am alive
            for evermore, Amen; and have the keys of hell and of death.

            1:19 Write the things which thou hast seen, and the things which are,
            and the things which shall be hereafter; 1:20 The mystery of the seven
            stars which thou sawest in my right hand, and the seven golden
            candlesticks. The seven stars are the angels of the seven churches:
            and the seven candlesticks which thou sawest are the seven churches.

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

            """
    );
    assertThat(bibleSearchResult.getBiblePassages())
        .hasSize(14)
        .extracting(BiblePassage::chapter, BiblePassage::verse, BiblePassage::text)
        .allMatch(Objects::nonNull);
  }
}