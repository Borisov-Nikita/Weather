package nik.borisov.weather.utils

import android.app.AlertDialog
import android.content.Context

interface DialogManager {

    fun locationEnabledDialog(context: Context, onClick: () -> Unit) {
        AlertDialog.Builder(context).create().apply {
            setTitle("Enabled location?")
            setMessage("Location disabled. Do you want enable location?")
            setButton(AlertDialog.BUTTON_POSITIVE, "Ok") { x, y ->
                onClick.invoke()
                this.dismiss()
            }
            setButton(AlertDialog.BUTTON_NEGATIVE, "Cancel") { x, y ->
                this.dismiss()
            }
            show()
        }
    }
}