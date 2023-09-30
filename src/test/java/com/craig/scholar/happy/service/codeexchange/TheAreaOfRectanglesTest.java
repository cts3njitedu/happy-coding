package com.craig.scholar.happy.service.codeexchange;

import static org.assertj.core.api.Assertions.assertThat;

import com.craig.scholar.happy.model.Point;
import com.craig.scholar.happy.model.Rectangle;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class TheAreaOfRectanglesTest {

  private final TheAreaOfRectangles theAreaOfRectangles = new TheAreaOfRectangles();

  private static Stream<Arguments> areaCases() {
    return Stream.of(
        Arguments.of(
            List.of(new Rectangle(
                new Point(3, 7),
                new Point(9, 3)
            )),
            24
        ),
        Arguments.of(
            List.of(new Rectangle(
                new Point(8, 10),
                new Point(14, 4)
            )),
            36
        ),
        Arguments.of(
            List.of(
                new Rectangle(
                    new Point(3, 7),
                    new Point(9, 3)
                ),
                new Rectangle(
                    new Point(8, 10),
                    new Point(14, 4)
                )
            ),
            57
        ),
        Arguments.of(
            List.of(
                new Rectangle(
                    new Point(8, 10),
                    new Point(14, 4)
                ),
                new Rectangle(
                    new Point(3, 7),
                    new Point(9, 3)
                ),
                new Rectangle(
                    new Point(5, 8),
                    new Point(10, 3)
                )
            ),
            61
        ),
        Arguments.of(
            List.of(
                new Rectangle(
                    new Point(1, 1),
                    new Point(8, 8)
                ),
                new Rectangle(
                    new Point(2, 3),
                    new Point(3, 5)
                )
            ),
            49
        )
    );
  }

  @ParameterizedTest
  @MethodSource("areaCases")
  void Area_Success(List<Rectangle> rectangles, long expectedArea) {
    assertThat(theAreaOfRectangles.area(rectangles)).isEqualTo(expectedArea);
  }
}
