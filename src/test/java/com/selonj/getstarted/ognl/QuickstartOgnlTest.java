package com.selonj.getstarted.ognl;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ognl.Ognl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016-03-10.
 */
public class QuickstartOgnlTest {

  public static final Object OPTIONAL_ROOT = null;
  public static final Map<Object, Object> CONTEXT_REQUIRED = Collections.emptyMap();
  private static final Object EMPTY_ROOT = new Object();

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
  public void canCallMethodOnAnyObjects() throws Exception {
    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>("foo", "bar");
    assertThat((String) Ognl.getValue("getKey()", CONTEXT_REQUIRED, entry), equalTo("foo"));
  }

  @Test
  public void rootInstance() throws Exception {
    assertThat(Ognl.getValue("#root", CONTEXT_REQUIRED, EMPTY_ROOT), sameInstance(EMPTY_ROOT));
  }

  @Test
  public void thisInstanceIsRootObject() throws Exception {
    assertThat(Ognl.getValue("#this", CONTEXT_REQUIRED, EMPTY_ROOT), sameInstance(EMPTY_ROOT));
  }

  @Test
  public void logicOperator() throws Exception {
    assertThat(
        (String) Ognl.getValue("true?'foo':'bar'", CONTEXT_REQUIRED, EMPTY_ROOT),
        equalTo("foo")
    );
    assertThat(
        (String) Ognl.getValue("false?'foo':'bar'", CONTEXT_REQUIRED, EMPTY_ROOT),
        equalTo("bar")
    );
  }

  @Test
  public void readStaticFields() throws Exception {
    assertThat((Integer) Ognl.getValue("@java.lang.Integer@MAX_VALUE", OPTIONAL_ROOT),
        equalTo(Integer.MAX_VALUE));
  }



  @Test
  public void callStaticMethod() throws Exception {
    assertThat((Integer) Ognl.getValue("@java.lang.Integer@valueOf(1)", OPTIONAL_ROOT), equalTo(1));
  }

  @Test
  public void filtering() throws Exception {
    Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
    //#collection.{? logic expression}
    List<Integer> evenNumbers = (List<Integer>) Ognl.getValue("#root.{? #this % 2==0}", numbers);
    assertThat(evenNumbers, equalTo(Arrays.asList(2, 4)));
  }

  @Test
  public void createAMap() throws Exception {
    Map result = (Map) Ognl.getValue("#{'foo':'bar',1:2}", OPTIONAL_ROOT);

    assertThat(result, equalTo((Map) new HashMap<Object, Object>() {{
      put("foo", "bar");
      put(1, 2);
    }}));
  }
}
