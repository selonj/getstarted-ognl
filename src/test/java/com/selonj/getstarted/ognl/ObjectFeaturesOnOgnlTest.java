package com.selonj.getstarted.ognl;

import java.util.AbstractMap;
import java.util.Map;
import ognl.Ognl;
import org.junit.Test;

import static com.selonj.getstarted.ognl.Fixtures.CONTEXT_REQUIRED;
import static com.selonj.getstarted.ognl.Fixtures.OPTIONAL_ROOT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016-03-11.
 */
public class ObjectFeaturesOnOgnlTest {

  @Test
  public void callMethodOnAnyObjects() throws Exception {
    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>("foo", "bar");
    assertThat((String) Ognl.getValue("getKey()", CONTEXT_REQUIRED, entry), equalTo("foo"));
  }

  @Test
  public void callConstructors() throws Exception {
    assertThat((Integer) Ognl.getValue("new Integer(1)", OPTIONAL_ROOT),
        equalTo(1));
  }

  @Test
  public void readStaticFields() throws Exception {
    assertThat((Integer) Ognl.getValue("@java.lang.Integer@MAX_VALUE", OPTIONAL_ROOT),
        equalTo(Integer.MAX_VALUE));
  }

  @Test
  public void callStaticMethods() throws Exception {
    assertThat((Integer) Ognl.getValue("@java.lang.Integer@valueOf(1)", OPTIONAL_ROOT), equalTo(1));
  }

  @Test
  public void propertyChecking() throws Exception {
    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<String, String>("foo", "bar");

    assertThat((Boolean) Ognl.getValue("key in {'foo','<wrong>'}", CONTEXT_REQUIRED, entry),
        equalTo(Boolean.TRUE));
    assertThat((Boolean) Ognl.getValue("key in {'<unknown>','<wrong>'}", CONTEXT_REQUIRED, entry),
        equalTo(Boolean.FALSE));
  }
}
