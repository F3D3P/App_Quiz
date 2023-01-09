package com.app.quiz.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.app.quiz.R
import com.app.quiz.adapters.QuizAdapter
import com.app.quiz.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_login_intro.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


//data non funziona                                 [FIX]       v1.0.0
//immagini stabili                                  []
//date superiori ad odierna deve return errore      [FIX]       v1.1.0
//controllo se quiz non è uscito oggi               [FIX]       v1.2.0
//Implementare pulsanti profilo,followme, rate app  [FIX]       v1.3.0
//ordinare elementi per data                        [FIX]       v1.2.1
//password più lunghe di 6                          [FIX]       v1.1.0
//remove dark theme                                 [FIX]       v1.1.1


class MainActivity : AppCompatActivity() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private var quizList = mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.app.quiz.R.layout.activity_main)
        setUpViews()
    }



    fun setUpViews() {
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()
    }

    private fun setUpDatePicker() {
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormat = SimpleDateFormat("dd-M-yyyy")
                val date = dateFormat.format(Date(it))
                val currentDate = dateFormat.format(Date())
//                Toast.makeText(this, "$date", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, QuestionActivity::class.java)
                    intent.putExtra("DATE", date)
//                  Toast.makeText(this, "$date", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
            }
//            Toast.makeText(this, "This quiz has yet to come out", Toast.LENGTH_SHORT).show()
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }
    }

    private fun setUpFireStore() {
        firestore = FirebaseFirestore.getInstance()
        val collectionReference = firestore.collection("quiz")
        collectionReference.addSnapshotListener { value, error ->
            if(value == null || error != null){
                Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
//          TO DO control items present
            quizList.addAll(value.toObjects(Quiz::class.java))
            //order
            quizList.sortBy { it.title }
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpRecyclerView() {
        adapter = QuizAdapter(this, quizList)
        quizRecyclerView.layoutManager = GridLayoutManager(this, 2)
        quizRecyclerView.adapter = adapter
    }

    fun setUpDrawerLayout() {
        setSupportActionBar(appBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, mainDrawer, com.app.quiz.R.string.app_name, com.app.quiz.R.string.app_name)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.btnFollowMe -> {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/federicopiso/"))
                    startActivity(i)
                    mainDrawer.closeDrawers()
                    true
                }
                R.id.btnProfile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    mainDrawer.closeDrawers()
                    true
                }
                R.id.btnMyApp -> {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse
                        ("https://play.google.com/store/games?gl=IT&utm_source=emea_Med&utm_medium=hasem&utm_content=Dec2020&utm_campaign=Evergreen&pcampaignid=MKT-EDR-emea-it-1001280-Med-hasem-py-Evergreen-Dec2020-Text_Search_BKWS-test_ctrl_ca%7CONSEM_kwid_43700007669214170&gclid=Cj0KCQiA-oqdBhDfARIsAO0TrGFZnL30J0E3qhOIhP3z4yntuxACHdR--pfna6HbUad50HHYFgU1TzEaAhznEALw_wcB&gclsrc=aw.ds"))
                    startActivity(i)
                    mainDrawer.closeDrawers()
                    true
                }
                else -> {
                    false
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }



}