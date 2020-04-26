import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ForecastData(_temp:Double, _humidity:Int, _clouds: Int) {
    val temp=_temp
    val humidity=_humidity
    val clouds=_clouds
    var irradiance: Double?=null

    fun getTempInCelsius():Double{
        return temp - 272.15
    }

    override fun toString(): String {
        return "ForecastData(temp=$temp, humidity=$humidity, clouds=$clouds, irradiance=$irradiance)"
    }
    fun setIrradiance(irrad: Double){
        irradiance=irrad
    }
    fun estimatedPVProduction(): Double{
        val panelArea=20 //in square meter
        val yd=0.15 //solar panel yield in percentage
        val pr=0.75 //performance ratio
        var res=3*panelArea*yd* irradiance!! *pr
        return res
    }


}


class Data(){
    var forecastDataList= mutableListOf<ForecastData>()
    var connection:Connection?=null

    fun addForecastData(data: ForecastData){
        forecastDataList.add(data)
    }
    fun setIrradiances(){
        getConn()
        for(el in forecastDataList){
            val irrad=executeQuery(el)
            el.setIrradiance(irrad)
        }
        closeConn()

    }
    fun getConn(){
        try {
            if(connection==null) {
                connection = DriverManager.getConnection(
                    "jdbc:sqlserver://193.6.33.140:1433;"
                            + "database=szfzh2;"
                            + "user=szfzh2;"
                            + "password=Szfzh2_1;"
                            + "encrypt=false;"
                            + "trustServerCertificate=true;"
                            + "loginTimeout=30;"
                )
            }
        }catch (e:SQLException) {
            e.printStackTrace()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun executeQuery(el: ForecastData):Double{
        var ret=0.0
        val query=
            "SELECT irradiance " +
                    "FROM Irradiance " +
                    "WHERE temp_min <= ? AND ? <= temp_max AND clouds_min <= ? AND ? <= clouds_max"

        try {
            val stmnt=connection!!.prepareStatement(query)
            stmnt.setDouble(1, el.getTempInCelsius())
            stmnt.setDouble(2, el.getTempInCelsius())
            stmnt.setInt(3, el.clouds)
            stmnt.setInt(4, el.clouds)
            val resultSet=stmnt.executeQuery()
            while(resultSet.next()){
                var res=resultSet.getString("irradiance")
                ret=res.toDouble()
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return ret
    }
    fun closeConn(){
        connection?.close()
    }


}
