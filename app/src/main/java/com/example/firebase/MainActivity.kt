package com.example.firebase

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val database:FirebaseDatabase = FirebaseDatabase.getInstance()
        var myRef : DatabaseReference

        val btn_save = findViewById<Button>(R.id.btn_save)
        val btn_retrieve = findViewById<Button>(R.id.btn_retrieve)
        val editText = findViewById<EditText>(R.id.et_name)
        val textView = findViewById<TextView>(R.id.tv_value)

        btn_save.setOnClickListener(View.OnClickListener {
            myRef = database.getReference("employees")
            val name : String = editText.text.toString()
            editText.setText("")
            myRef.push().setValue(name).addOnSuccessListener {
                Toast.makeText(this,"Done",Toast.LENGTH_LONG).show()
            }
        })

        btn_retrieve.setOnClickListener(View.OnClickListener {
            myRef = database.getReference("message")
            myRef.addValueEventListener(object : ValueEventListener { // here we are inside anonymous class
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val name : String = dataSnapshot.getValue(String::class.java) !!
                    textView.text = name
                }

                override fun onCancelled(error: DatabaseError) { // here the makeText don't know the context so we pass it
                    val err :String =error.message
                    Toast.makeText(applicationContext,err,Toast.LENGTH_LONG).show()
                }
            })

        })
//        myRef.child("message").setValue("Hello, World!")
    }

//        inner class ReadData : ValueEventListener{
//            override fun onDataChange(snapshot: DataSnapshot) {
//                var name : String = snapshot.getValue(String::class.java)!!
//                textView.text = name
//            }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        }

}