package tw.teng.hw2.resource.api;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import tw.teng.hw2.resource.network.api_interface.ContactInterface;
import tw.teng.hw2.resource.network.model.APIResponse;

public class MockServerTest {
    MockWebServer mockWebServer;

    @Before
    public void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.setDispatcher(new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(@NotNull RecordedRequest recordedRequest) throws InterruptedException {
                return new MockResponse().setResponseCode(200).
                        setBody(new Gson().toJson(new APIResponse("200", "Test")));
            }
        });
        mockWebServer.start();
    }

    @Test
    public void test() {
        HttpUrl url = mockWebServer.url("");
        CurrentThreadExecutor currentThreadExecutor = new CurrentThreadExecutor();
        okhttp3.Dispatcher dispatcher = new okhttp3.Dispatcher(currentThreadExecutor);
        OkHttpClient okHttpClient = new
                OkHttpClient.Builder().dispatcher(dispatcher).build();

        Retrofit builder = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .callbackExecutor(currentThreadExecutor)
                .build();

        ContactInterface contactInterface = builder.create(ContactInterface.class);
        Call<APIResponse> call = contactInterface.getStatus();
        call.enqueue(new Callback<APIResponse>() {
            @Override
            public void onResponse(Call<APIResponse> call, Response<APIResponse> response) {
                assert response.body() != null;
                Assert.assertEquals(0, response.body().getMessage().compareTo("Test"));
            }

            @Override
            public void onFailure(Call<APIResponse> call, Throwable t) {
            }
        });
        try {
            RecordedRequest request = mockWebServer.takeRequest();
            Assert.assertEquals(0, request.getPath().compareTo("/status"));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @After
    public void after() {
        try {
            mockWebServer.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
