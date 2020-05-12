package com.ambeso.syncro_pos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.firebase.ui.auth.data.model.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class Login : AppCompatActivity() {

    val RC_SIGN_IN = 69

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        var current = FirebaseAuth.getInstance().currentUser
        if(current != null){
            var mainAct = Intent(this, MainActivity::class.java)
            startActivity(mainAct)

        }else{

            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build()
            )


// Create and launch sign-in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RC_SIGN_IN)
        }


    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                var user = FirebaseAuth.getInstance().currentUser

                var mainAct = Intent(this, MainActivity::class.java)
                startActivity(mainAct)

//                Log.e("UID", userUID);

                // ...
            } else {

                Toast.makeText(this,response?.error.toString(), Toast.LENGTH_LONG).show()
                Log.e("RC_SIGN_IN", response?.error.toString() );
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
