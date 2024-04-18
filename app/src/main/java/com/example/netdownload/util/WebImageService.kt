import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface WebImageService {
    @GET
    @Streaming
    fun downloadImage(@Url imageUrl: String): Response<ResponseBody>
}