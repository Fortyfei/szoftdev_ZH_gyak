import com.google.gson.Gson
import java.lang.Exception
import java.net.URL


fun main(args: Array<String>){
    try {
        val dataWeatherApi=weatherAPIcall()
        //println (dataWeatherApi.toString()+"\n")
        var dataList=Data()
        for(element in dataWeatherApi.list){
            val listElement=ForecastData(element.main.temp,element.main.humidity,element.clouds.all)
            //println(listElement.toString())
            dataList.addForecastData(listElement)
        }
        dataList.setIrradiances()
        val weatherData=dataList.forecastDataList
        val consumptionData=ConsumptionListBy3Hour().consumptionList

        simulate(weatherData,consumptionData)

    }catch (e: Exception){
        e.printStackTrace()
    }

}

@Throws(Exception::class)
fun weatherAPIcall(): WeatherApiResponse{
    val apiKey="d173fa6868ad753081984ed9bc9f7e0c"
    val cityID="3042925" //Veszprém megye ID-je
    val response= URL("https://api.openweathermap.org/data/2.5/forecast?id=${cityID}&appid=${apiKey}").readText()
    return Gson().fromJson(response, WeatherApiResponse::class.java)
}

fun simulate(weatherData:List<ForecastData>,consumptionData: List<Consumption>){
    val batteryInitialCharge=0.0
    var batteryCurrentCharge=batteryInitialCharge
    var i=0
    var j=1
    for(wd in weatherData){
        var currentCahrge=batteryCurrentCharge+wd.estimatedPVProduction() //a ciklusban felhasznalhato energia=eltarolt energia+a ciklusban termelt energia
        var currencConsumption=consumptionData[i]
        println("Estimated blackouts on day ${j}, between ${i*3}h and ${(i+1)*3}h")
        //profil1 működik
        if(currentCahrge==0.0){
            println("Profile1 blackout!")
        }
        if(currentCahrge>0.0 && currentCahrge<currencConsumption.prof1){
            println("Partial blackout on profile1")
        }
        currentCahrge=Math.max(0.0,currentCahrge-currencConsumption.prof1)

        //profil2 működik
        currentCahrge=Math.max(0.0,currentCahrge-currencConsumption.prof2)
        if(currentCahrge==0.0){
            println("Profile2 blackout!")
        }
        if(currentCahrge>0.0 && currentCahrge<currencConsumption.prof2){
            println("Partial blackout on profile2")
        }
        currentCahrge=Math.max(0.0,currentCahrge-consumptionData[i].prof2)

        //profil3 működik
        currentCahrge=Math.max(0.0,currentCahrge-currencConsumption.prof3)
        if(currentCahrge==0.0){
            println("Profile3 blackout!")
        }
        if(currentCahrge>0.0 && currentCahrge<currencConsumption.prof3){
            println("Partial blackout on profile3")
        }
        currentCahrge=Math.max(0.0,currentCahrge-currencConsumption.prof3)

        //profil4 működik
        currentCahrge=Math.max(0.0,currentCahrge-currencConsumption.prof4)
        if(currentCahrge==0.0){
            println("Profile4 blackout!")
        }
        if(currentCahrge>0.0 && currentCahrge<currencConsumption.prof4){
            println("Partial blackout on profile4")
        }
        currentCahrge=Math.max(0.0,currentCahrge-currencConsumption.prof4)

        batteryCurrentCharge=currentCahrge //a megmaradt energiat eltaroljuk a kovetkezo idoszakhoz


        println()
        i++
        i%=8
        if(i==0) j++
    }
}
