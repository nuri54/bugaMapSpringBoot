package de.hhn.se.labswp.bugamap;

import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class WsBuga23TestApplicationTests {

  Calculator underTest = new Calculator();

  //@Test
  void itShouldAddNumbers() {
    // given
    int numberOne = 20;
    int numberTwo = 30;

    // when
    int result = underTest.add(numberOne,numberTwo);

    // then
    int expected = 50;
    assertThat(result).isEqualTo(expected);
  }

  class Calculator {
    int add(int a, int b){
      return a + b;
    }
  }

}
