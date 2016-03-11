package com.selonj.getstarted.ognl;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import ognl.Ognl;
import org.junit.Test;

import static com.selonj.getstarted.ognl.Fixtures.OPTIONAL_ROOT;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016-03-11.
 */
public class MapFeaturesOnOgnlTest {

  @Test
  public void createAMap() throws Exception {
    Map result = (Map) Ognl.getValue("#{'foo':'bar',1:2}", OPTIONAL_ROOT);

    assertThat(result, equalTo((Map) new HashMap<Object, Object>() {{
      put("foo", "bar");
      put(1, 2);
    }}));
  }

  @Test
  public void createACustomTypeMap() throws Exception {
    Map result = (Map) Ognl.getValue("#@java.util.LinkedHashMap@{'foo':'bar',1:2}",
        OPTIONAL_ROOT);

    assertThat(result, instanceOf(LinkedHashMap.class));
  }

  @Test
  public void getValue() throws Exception {
    Map root = new HashMap<Object, Object>() {{
      put("foo", "bar");
      put(1, 2);
    }};

    assertThat((String) Ognl.getValue("['foo']", root), equalTo("bar"));
    assertThat((String) Ognl.getValue("foo", root), equalTo("bar"));
    assertThat((Integer) Ognl.getValue("#this[1]", root), equalTo(2));
    //can't work
    assertThat((Integer) Ognl.getValue("1", root), equalTo(1));
  }
}
