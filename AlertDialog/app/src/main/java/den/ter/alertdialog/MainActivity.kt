package den.ter.alertdialog

import android.content.DialogInterface
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import den.ter.alertdialog.databinding.ActivityMainBinding
import kotlin.properties.Delegates.notNull

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var volume = 100
    private var color = Color.RED


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also{setContentView(it.root)}

        binding.butDef.setOnClickListener {
            showDefaultAlertDialog()
        }

        binding.butSing.setOnClickListener {
            showDialogWithSingleChoice()
        }

        binding.butMult.setOnClickListener {
            showDialogWithMultiplyChoice()
        }
    }

    private fun showDialogWithMultiplyChoice() {
        val colorItems = resources.getStringArray(R.array.colors)
        val colorComponents = mutableListOf(
            Color.red(this.color),
            Color.green(this.color),
            Color.blue(this.color)
        )
        val checkboxes = colorComponents
            .map{it>0}
            .toBooleanArray()

        val dialog = AlertDialog.Builder(this)
            .setTitle("Dialog with multy choice")
            .setMultiChoiceItems(colorItems, checkboxes){_,which,isChecked ->
                colorComponents[which] = if(isChecked) 255 else 0
                this.color = Color.rgb(
                    colorComponents[0],
                    colorComponents[1],
                    colorComponents[2]
                )
                updateUi()

            }
            .setPositiveButton("close", null)
            .create()
        dialog.show()
    }

    private fun showToast( msg: String){
        Toast.makeText(this,msg,
            Toast.LENGTH_SHORT).show()

    }


    private fun showDialogWithSingleChoice() {
        val volumeItems = VolumeClass.createVolumeValues(volume)
        val volumeTextItems = volumeItems.values
            .map{getString(R.string.desc, it)}
            .toTypedArray()
        val dialog = AlertDialog.Builder(this)
            .setTitle("Single Choice Dialog")
            .setSingleChoiceItems(volumeTextItems,volumeItems.curInd){dialog,which ->
                volume = volumeItems.values[which]
                updateUi()
                dialog.dismiss()
            }
            .create()
        dialog.show()
    }

    private fun updateUi() {
        binding.curVol.text = getString(R.string.curVolume,volume)
        binding.colorView.setBackgroundColor(color)
    }

    private fun showDefaultAlertDialog() {
        val listener = DialogInterface.OnClickListener{_,which ->
            when(which){
                DialogInterface.BUTTON_POSITIVE -> showToast("Wow, you really sure!")
                DialogInterface.BUTTON_NEGATIVE -> showToast("Sad, you dont sure")
                DialogInterface.BUTTON_NEUTRAL -> showToast("uhhhh......")
            }
        }
        val dialog = AlertDialog.Builder(this)
            .setCancelable(false)
            .setIcon(R.drawable.ic_dialog_foreground)
            .setTitle("Dialog Title")
            .setMessage("You sure that you sure?")
            .setPositiveButton("Yes",listener)
            .setNegativeButton("No",listener)
            .setNeutralButton("Idk",listener)
            .create()
        dialog.show()
    }
}
















