package com.example.dipchat
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.contentcapture.DataRemovalRequest
import android.widget.Toast
import com.example.dipchat.databinding.ActivityUserProfileBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
class UserProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private lateinit var psychotype: DatabaseReference
    private lateinit var userReference: DatabaseReference
    private lateinit var dialog:Dialog
    private lateinit var user:User
    private lateinit var uid: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityUserProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        uid = auth.currentUser?.displayName.toString()
        Log.d("My log",uid)
        databaseReference = FirebaseDatabase.getInstance().getReference("")
        psychotype = databaseReference.child("psychotype")
        //userReference = psychotype.child("N3KI6XW9ljjtZ_diAbC")
        Log.d("My log", databaseReference.toString())
        if(uid.isNotEmpty()){
            getUserData()
        }
    }
    private fun getUserData() {
        psychotype.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("My log",snapshot.toString())
                for(s in snapshot.children){
                    val user = s.getValue(Profile::class.java)!!
                    Log.d("PSYCHOTYPE", user.name.toString())
                    Log.d("PSYCHOTYPE", user.type.toString())
                    if (user.name == uid) {
                        binding.nameId.setText(user.name)
                        binding.psychotypeId.setText(user.type)
                    }
                }
//                val user = snapshot.getValue(User::class.java)!!
//                binding.nameId.setText(user.message)
//                binding.psychotypeId.setText(user.type)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@UserProfileActivity,"failed to get data",Toast.LENGTH_SHORT).show()
            }
        }
        )

    }
}