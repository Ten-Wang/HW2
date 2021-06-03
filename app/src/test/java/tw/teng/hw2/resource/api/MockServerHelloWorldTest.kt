package tw.teng.hw2.resource.api

import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import tw.teng.hw2.resource.network.model.APIResponse
import java.io.IOException
import java.util.*

class MockServerHelloWorldTest {
    private var mockWebServer: MockWebServer? = null

    @Before
    @Throws(IOException::class)
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer!!.enqueue(MockResponse().setBody(Gson().toJson(APIResponse("200", "Test"))))
        mockWebServer!!.start()
    }

    @Throws(IOException::class)
    private fun sendGetRequest(okHttpClient: OkHttpClient, base: HttpUrl): String {
        val body: RequestBody = "hi, there".toRequestBody("text/plain".toMediaTypeOrNull())
        val request: Request = Request.Builder()
            .post(body)
            .url(base)
            .build()
        val response = okHttpClient.newCall(request).execute()
        return Objects.requireNonNull(response.body)!!.string()
    }

    @Test
    @Throws(Exception::class)
    fun test() {
        val url = mockWebServer!!.url("/api/hello")
        val request = sendGetRequest(OkHttpClient(), url)
        print(request + "\n")
    }

    @After
    fun after() {
        try {
            mockWebServer!!.shutdown()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}