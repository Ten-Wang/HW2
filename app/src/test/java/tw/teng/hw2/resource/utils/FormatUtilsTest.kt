package tw.teng.hw2.resource.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class FormatUtilsTest {

    @Test
    fun `test getRpDecimalFormat case 1`() {
        val expected = "500"
        val actual = 500
        assertEquals(expected, FormatUtils.getRpDecimalFormat(actual))
    }

    @Test
    fun `test getRpDecimalFormat case 2`() {
        val expected = "10.000"
        val actual = 10000
        assertEquals(expected, FormatUtils.getRpDecimalFormat(actual))
    }
}