package tw.teng.hw2.resource.repository.model

import org.junit.Assert.assertEquals
import org.junit.Test
import tw.teng.hw2.resource.utils.TimeUtils

class ActivePassTest {

    @Test
    fun `test ActivePass getName 1`() {
        val expected = DayPass().name
        val activePass = ActivePass()
        activePass.dayPass = DayPass()
        assertEquals(expected, activePass.getName())
    }

    @Test
    fun `test ActivePass getName 2`() {
        val expected = Hour8Pass().name
        val activePass = ActivePass()
        activePass.hourPass = Hour8Pass()
        assertEquals(expected, activePass.getName())
    }

    @Test
    fun `test ActivePass getName 3`() {
        val expected = DayPass().name + " and " + Hour8Pass().name
        val activePass = ActivePass()
        activePass.dayPass = DayPass()
        activePass.hourPass = Hour8Pass()
        assertEquals(expected, activePass.getName())
    }

    @Test
    fun `test ActivePass getExpiredTime 1`() {
        val expected = Day3Pass().duration
        val activePass = ActivePass()
        activePass.dayPass = Day3Pass()
        val actual = activePass.getExpiredTime()
        assertEquals(TimeUtils.toString(activePass.passStart + expected), actual)
    }

    @Test
    fun `test ActivePass getExpiredTime 2`() {
        val expected = Hour8Pass().duration
        val activePass = ActivePass()
        activePass.hourPass = Hour8Pass()
        val actual = activePass.getExpiredTime()
        assertEquals(TimeUtils.toString(activePass.passStart + expected), actual)
    }

    @Test
    fun `test ActivePass getExpiredTime 3`() {
        val expected = Day7Pass().duration + Hour8Pass().duration
        val activePass = ActivePass()
        activePass.dayPass = Day7Pass()
        activePass.hourPass = Hour8Pass()
        val actual = activePass.getExpiredTime()
        assertEquals(TimeUtils.toString(activePass.passStart + expected), actual)
    }
}