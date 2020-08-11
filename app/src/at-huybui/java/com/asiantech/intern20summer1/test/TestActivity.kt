import com.asiantech.intern20summer1.w7.model.PlantModel

fun main(args: Array<String>) {
    val list = listOf<PlantModel>(PlantModel(1,"2"))
    val mutableList = list.toMutableList()
    print(mutableList.toString())
}
