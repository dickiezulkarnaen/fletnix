package com.dickiez.fletnix

import com.dickiez.fletnix.core.constants.MediaType
import com.dickiez.fletnix.utils.Tools
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
  @Test
  fun addition_isCorrect() {
    assertEquals(4, 2 + 2)
  }

  @Test
  fun mediaTypeTest() {
    MediaType.values().forEach {
      val media = MediaType.valueOf(it.name)
      println(media.name)
    }
    //assertEquals("movie", MediaType.valueOf())
  }

}