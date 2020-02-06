package ru.alexskvortsov.lab1

import com.google.gson.*
import org.junit.Assert.assertEquals
import org.junit.Test
import java.lang.reflect.Type
import kotlin.random.Random


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

    data class BaseResponse<T>(
        val resultCount: Int,
        val results: List<T>
    )

    abstract class Answer {
        abstract val wrapperType: String
    }

    data class Collection(
        override val wrapperType: String,
        val collectionType: String
    ) : Answer()

    data class Track(
        override val wrapperType: String,
        val kind: String
    ) : Answer()

    class InterfaceAdapter<T>: JsonDeserializer<T>{
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext
        ): T {
            val member = json as JsonObject

            val type = when(member["wrapperType"]?.asString){
                "collection" -> Collection::class.java
                "track" -> Track::class.java
                else -> return context.deserialize(json, typeOfT)
            }


            return context.deserialize(json, type)
        }

    }

    @Test
    fun answer(json: String){
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Answer::class.java, InterfaceAdapter<Answer>())
        val gson = builder.create()

        val firstStep = api.getAlbumAndSongs(id)
        val secondStep = BaseResponse(firstStep.resultCount, firstStep.results.map {
            gson.fromJson(it.toString(), Answer::class.java)
        })

    }

    @Test
    fun testQuestion() {
        val builder = GsonBuilder()
        builder.registerTypeAdapter(Answer::class.java, InterfaceAdapter<Answer>())
        val gson = builder.create()

        val randomList = mutableListOf<Answer>()
        for (i in 0..5) {
            if (Random.nextBoolean()) randomList.add(Collection("collection", "Album"))
            else randomList.add(Track("track", "song"))
        }


        val response = BaseResponse(randomList.size, randomList)
        val json = gson.toJson(response)

        val result = gson.fromJson<BaseResponse<*>>(json, BaseResponse::class.java)

        result.results.forEach { println(gson.fromJson(it.toString(), Answer::class.java)) }
        val finalResult = BaseResponse(result.resultCount, result.results.map { gson.fromJson(it.toString(), Answer::class.java) })

        val collections = finalResult.results.filter { it.wrapperType == "collection" }. map { it as Collection }
        val tracks = finalResult.results.filter { it.wrapperType == "track" }.map { it as Track }
        println(collections)
        println(tracks)
    }
}
