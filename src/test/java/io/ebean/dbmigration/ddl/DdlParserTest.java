package io.ebean.dbmigration.ddl;

import java.io.StringReader;
import java.util.List;
import org.testng.annotations.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class DdlParserTest {


  private DdlParser parser = new DdlParser();

  @Test
  public void parse_ignoresEmptyLines() throws Exception {

    List<String> stmts = parser.parse(new StringReader("\n\none;\n\ntwo;\n\n"));
    assertThat(stmts).containsExactly("one;","two;");
  }

  @Test
  public void parse_ignoresComments_whenFirst() throws Exception {

    List<String> stmts = parser.parse(new StringReader("-- comment\ntwo;"));
    assertThat(stmts).containsExactly("two;");
  }

  @Test
  public void parse_ignoresEmptyLines_whenFirst() throws Exception {

    List<String> stmts = parser.parse(new StringReader("\n\n-- comment\ntwo;\n\n"));
    assertThat(stmts).containsExactly("two;");
  }

  @Test
  public void parse_inlineEmptyLines_replacedWithSpace() throws Exception {

    List<String> stmts = parser.parse(new StringReader("\n\n-- comment\none\ntwo;\n\n"));
    assertThat(stmts).containsExactly("one two;");
  }


  @Test
  public void parse_ignoresComments() throws Exception {

    List<String> stmts = parser.parse(new StringReader("one;\n-- comment\ntwo;"));
    assertThat(stmts).containsExactly("one;","two;");
  }

  @Test
  public void parse_ignoresEndOfLineComments() throws Exception {

    List<String> stmts = parser.parse(new StringReader("one; -- comment\ntwo;"));
    assertThat(stmts).containsExactly("one;", "two;");
  }
}
