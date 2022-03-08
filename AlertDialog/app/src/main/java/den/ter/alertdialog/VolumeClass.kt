package den.ter.alertdialog

class VolumeClass (
    val values: List<Int>,
    val curInd: Int
){
    companion object{
        fun createVolumeValues(curVal: Int) : VolumeClass{
            val values = (0..100 step 10)
            val curInd = values.indexOf(curVal)
            return if(curInd == -1){
                val list = values + curVal
                VolumeClass(list,list.lastIndex)
            } else{
                VolumeClass(values.toList(),curInd)
            }
        }
    }
}