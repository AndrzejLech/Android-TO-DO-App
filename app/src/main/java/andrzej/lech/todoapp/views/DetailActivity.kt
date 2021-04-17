package andrzej.lech.todoapp.views

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import andrzej.lech.todoapp.R

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_details)
        val state = intent.getBooleanExtra("state", true)
        val returnButton: Button = findViewById(R.id.returnButton)
        findViewById<TextView>(R.id.detailsTitle).text = intent.getStringExtra("title")
        findViewById<TextView>(R.id.detailsDescription).text = intent.getStringExtra("description")
        findViewById<TextView>(R.id.detailsTimestamp).text = intent.getStringExtra("timestamp")

        if (state) {
            findViewById<TextView>(R.id.detailsStatusValue).text =
                getString(R.string.details_status_value_done)
        } else {
            findViewById<TextView>(R.id.detailsStatusValue).text =
                getString(R.string.details_status_value_not_done)
        }

        returnButton.setOnClickListener {
            val intent = Intent(applicationContext, TaskActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}