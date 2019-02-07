package com.example.csulb.wecare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


//Display the terms and condition page.
public class TermsActivity2 extends AppCompatActivity {

//Define the buttons.
    Button agree;

    //onCreate is called when the ativity is created.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.terms_dialog_box);
        super.onCreate(savedInstanceState);

        //Set the id of the button.
        agree = (Button)findViewById(R.id.termsAgreeButton);

        //Provide an onClickListener to call the next activity on agree condition.
        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(TermsActivity2.this,IshiharaActivity.class));
            }
        });
    }
}
