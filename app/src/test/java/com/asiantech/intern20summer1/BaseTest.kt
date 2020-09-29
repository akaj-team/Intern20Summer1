package com.asiantech.intern20summer1

import com.google.gson.Gson
import org.junit.Before
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.nio.charset.StandardCharsets
import java.nio.file.Files
import java.nio.file.Paths
import java.util.stream.Collectors

/**
 *
 * @author at-hoavo.
 */
abstract class BaseTest {

    @Suppress("UNCHECKED_CAST")
    fun <T> any(): T {
        Mockito.any<T>()
        return null as T
    }

    @Before
    fun beforeTest() {
        MockitoAnnotations.initMocks(this)
        initTest()
    }

    abstract fun initTest()

    internal fun <T> getObjectWithJson(fileName: String, clazz: Class<T>): T {
        val json =
            Files.lines(
                Paths.get(javaClass.classLoader.getResource("responses/$fileName").toURI()),
                StandardCharsets.UTF_8
            )
                .parallel()
                .collect(Collectors.joining())
        return Gson().fromJson<T>(json, clazz)
    }
}
