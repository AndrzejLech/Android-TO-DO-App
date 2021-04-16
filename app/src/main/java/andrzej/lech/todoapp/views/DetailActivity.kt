package andrzej.lech.todoapp.views

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import andrzej.lech.todoapp.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)

        findViewById<TextView>(R.id.detailsTitle).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.detailsDescription).text = intent.getStringExtra("description")
        findViewById<TextView>(R.id.detailsTimestamp).text = intent.getStringExtra("timestamp")

        val state = intent.getBooleanExtra("state", true)

        if (state) {
            findViewById<TextView>(R.id.detailsStatusValue).text = "done"
        }else{
            findViewById<TextView>(R.id.detailsStatusValue).text = "not done"
        }


    }
}