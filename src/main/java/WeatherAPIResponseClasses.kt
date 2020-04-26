import com.google.gson.annotations.SerializedName
import kotlin.collections.List

/*
Copyright (c) 2020 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class WeatherApiResponse (
	@SerializedName("cod") val cod : Int,
	@SerializedName("message") val message : Double,
	@SerializedName("cnt") val cnt : Int,
	@SerializedName("list") val list : List<LList>,
	@SerializedName("city") val city : City
)

data class LList (
	@SerializedName("dt") val dt : Int,
	@SerializedName("main") val main : Main,
	@SerializedName("weather") val weather : List<Weather>,
	@SerializedName("clouds") val clouds : Clouds,
	@SerializedName("wind") val wind : Wind,
	@SerializedName("rain") val snow : Rain,
	@SerializedName("sys") val sys : Sys,
	@SerializedName("dt_txt") val dt_txt : String
)

data class Main (
	@SerializedName("temp") val temp : Double,
	@SerializedName("temp_min") val temp_min : Double,
	@SerializedName("temp_max") val temp_max : Double,
	@SerializedName("pressure") val pressure : Double,
	@SerializedName("sea_level") val sea_level : Double,
	@SerializedName("grnd_level") val grnd_level : Double,
	@SerializedName("humidity") val humidity : Int,
	@SerializedName("temp_kf") val temp_kf : Double
)

data class Weather (
	@SerializedName("id") val id : Int,
	@SerializedName("main") val main : String,
	@SerializedName("description") val description : String,
	@SerializedName("icon") val icon : String
)

data class Clouds (
	@SerializedName("all") val all : Int
)

data class Wind (
	@SerializedName("speed") val speed : Double,
	@SerializedName("deg") val deg : Double
)

data class Rain (
	@SerializedName("3h") val h3: Double
)

data class City (
	@SerializedName("id") val id : Int,
	@SerializedName("name") val name : String,
	@SerializedName("coord") val coord : Coord,
	@SerializedName("country") val country : String
)

data class Coord (
	@SerializedName("lat") val lat : Double,
	@SerializedName("lon") val lon : Double
)

data class Sys (
	@SerializedName("pod") val pod : String
)