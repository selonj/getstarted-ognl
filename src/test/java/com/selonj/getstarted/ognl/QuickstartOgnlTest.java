package com.selonj.getstarted.ognl;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import ognl.Ognl;
import org.junit.Test;

import static com.selonj.getstarted.ognl.Fixtures.CONTEXT_REQUIRED;
import static com.selonj.getstarted.ognl.Fixtures.EMPTY_ROOT;
import static com.selonj.getstarted.ognl.Fixtures.OPTIONAL_ROOT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016-03-10.
 */
public class QuickstartOgnlTest {

  @Test
  public void string() throws Exception {
    assertThat((String) Ognl.getValue("'string'", OPTIONAL_ROOT), equalTo("string"));
  }

  @Test
  public void number() throws Exception {
    assertThat((Integer) Ognl.getValue("1", OPTIONAL_ROOT), equalTo(1));
  }

  @Test
  public void decimal() throws Exception {
    assertThat((Double) Ognl.getValue("1.", OPTIONAL_ROOT), equalTo(1.));
  }

  @Test
  public void truth() throws Exception {
    assertThat((Boolean) Ognl.getValue("true", OPTIONAL_ROOT), equalTo(Boolean.TRUE));
  }

  @Test
  public void resolveVariableThroughContextMap() throws Exception {
    Map<String, String> context = new HashMap<String, String>() {{
      put("foo", "bar");
    }};
    assertThat((String) Ognl.getValue("#foo", context, OPTIONAL_ROOT), equalTo("bar"));
  }

  @Test
  public void resolveVariableThroughRootObject() throws Exception {
    Map.Entry<String, String> root = new AbstractMap.SimpleEntry<>("foo", "bar");
    assertThat((String) Ognl.getValue("key", CONTEXT_REQUIRED, root), equalTo("foo"));
    assertThat((String) Ognl.getValue("#root['key']", CONTEXT_REQUIRED, root), equalTo("foo"));
  }

  @Test
  public void rootInstance() throws Exception {
    assertThat(Ognl.getValue("#root", CONTEXT_REQUIRED, EMPTY_ROOT), sameInstance(
        EMPTY_ROOT));
  }

  @Test
  public void thisInstanceIsSameWithRootObject() throws Exception {
    assertThat(Ognl.getValue("#this", CONTEXT_REQUIRED, EMPTY_ROOT), sameInstance(
        EMPTY_ROOT));
  }

  @Test
  public void ternaryConditionOp() throws Exception {
    assertThat(
        (String) Ognl.getValue("true?'foo':'bar'", CONTEXT_REQUIRED, EMPTY_ROOT),
        equalTo("foo")
    );
    assertThat(
        (String) Ognl.getValue("false?'foo':'bar'", CONTEXT_REQUIRED, EMPTY_ROOT),
        equalTo("bar")
    );
  }
}
