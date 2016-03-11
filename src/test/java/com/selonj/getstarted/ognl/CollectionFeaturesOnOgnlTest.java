package com.selonj.getstarted.ognl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import ognl.Ognl;
import ognl.OgnlException;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by Administrator on 2016-03-11.
 */
public class CollectionFeaturesOnOgnlTest {

  private final Collection<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7);

  private void assertEvalExpressionOnNumbersMatching(String expression, Object expected)
      throws OgnlException {
    Object actual = Ognl.getValue(expression, numbers);
    assertThat(actual, equalTo(expected));
  }

  @Test
  public void index() throws Exception {
    assertEvalExpressionOnNumbersMatching("[0]", 1);
  }

  @Test
  public void sizeProperty() throws Exception {
    assertEvalExpressionOnNumbersMatching("size", numbers.size());
  }

  @Test
  public void filtering() throws Exception {
    assertEvalExpressionOnNumbersMatching("#root.{? #this % 2==0}", Arrays.asList(2, 4, 6));
  }

  @Test
  public void matchingFirst() throws Exception {
    assertEvalExpressionOnNumbersMatching("#this.{^ #this % 2 ==0}", Collections.singletonList(2));
  }

  @Test
  public void matchingLast() throws Exception {
    assertEvalExpressionOnNumbersMatching("#this.{$ #this % 2 ==0}", Collections.singletonList(6));
  }
}
