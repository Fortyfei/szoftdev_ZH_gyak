import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

class ConsumptionListBy3Hour(){
    var connection:Connection?=null
    var consumptionList= getConsumption()

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
        }catch (e: SQLException) {
            e.printStackTrace()
        }
        catch (e: Exception){
            e.printStackTrace()
        }
    }
    fun executeQuery():List<Consumption>{
        var consumList= mutableListOf<Consumption>()
        val query=
            "SELECT * " +
                    "FROM ConsumptionData "

        try {
            val stmnt=connection!!.prepareStatement(query)
            val resultSet=stmnt.executeQuery()
            var tmp=Consumption(0.0,0.0,0.0,0.0)
            var i=0
            while(resultSet.next()){
                //println(tmp.toString())
                tmp.addConsumption(resultSet.getString("hospital").toDouble(),resultSet.getString("shop").toDouble(),
                    resultSet.getString("school").toDouble(),resultSet.getString("residential").toDouble())
                i++
                if(i%12==0){
                    consumList.add(tmp)
                    //println("${i} - ${tmp}\n")
                    tmp=Consumption(0.0,0.0,0.0,0.0)
                }
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
        return consumList
    }
    fun closeConn(){
        connection?.close()
    }
    fun getConsumption():List<Consumption>{
        var templist= listOf<Consumption>()
        try {
            getConn()
            templist=executeQuery()
            closeConn()

        }catch (e:Exception){
            e.printStackTrace()
        }
        return  templist
    }
}

class Consumption(p1:Double,p2:Double,p3:Double,p4:Double){
    var prof1:Double=0.0
    var prof2:Double=0.0
    var prof3:Double=0.0
    var prof4:Double=0.0

    fun addConsumption(p1:Double,p2:Double,p3:Double,p4:Double){
        prof1+=p1
        prof2+=p2
        prof3+=p3
        prof4+=p4
    }

    fun getMaxConsumption():Double{
        return prof1+prof2+prof3+prof4
    }

    override fun toString(): String {
        return "Consumption(prof1=$prof1, prof2=$prof2, prof3=$prof3, prof4=$prof4)"
    }

}
