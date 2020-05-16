package br.com.rbarbioni.unit;

import br.com.rbarbioni.Application;
import org.junit.jupiter.api.Test;

public class ApplicationTest {

  @Test
  public void applicationTest() {
    Application application = new Application();
    application.main(new String[] {});
  }
}
