package com.example.csulb.wecare;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;
import java.util.Locale;

//Exercise adapter to fit the items with the data.
public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder>{

    List<Exercises> listItems;
    private Context context;
    TextToSpeech textToSpeech;
    int language;


    //Copy constructor
    public ExerciseAdapter(List<Exercises> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

//set the layout view and define the functions to be called.
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_items, parent,false);

//Create a text to speech module.
        language = SharedPrefManager.getmInstance(context).getLanguageCode();
        if(language == 1){
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int i) {
                    if(i != TextToSpeech.ERROR){
                        textToSpeech.setLanguage(Locale.US);
                    }
                }
            });
            return new ViewHolder(v);
        }
        else{
            textToSpeech = new TextToSpeech(context, new TextToSpeech.OnInitListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override
                public void onInit(int i) {
                    if(i != TextToSpeech.ERROR){
                        Locale locSpanish = new Locale("spa");
                        textToSpeech.setLanguage(locSpanish);
                    }
                }
            });
            return new ViewHolder(v);

        }

    }

//Bind the items to the created view.
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Exercises listItem = listItems.get(position);

        holder.title.setText(listItem.getTitle());
        holder.set.setText(listItem.getSet());
        holder.reps.setText(listItem.getReps());
        Glide.with(context).load(listItem.getPhoto()).into(holder.photo);
        holder.description.setText(listItem.getDescription());

        final String toSpeak = holder.description.getText().toString();

        holder.speaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(textToSpeech.isSpeaking()){
                    textToSpeech.stop();
                }else{
                    textToSpeech.speak(toSpeak,TextToSpeech.QUEUE_FLUSH,null);
                }
            }
        });


    }


//get count of the views that are available.
    @Override
    public int getItemCount() {
        return listItems.size();
    }

//Holder class for the view
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView title, set, reps,description;
        ImageView photo;
        ImageButton speaker;
        public ViewHolder(View itemView) {
            super(itemView);

//Initialize the items
            title = (TextView)itemView.findViewById(R.id.exerciseTitleTextView);
            set = (TextView)itemView.findViewById(R.id.exerciseSetTextView);
            reps = (TextView)itemView.findViewById(R.id.exerciseRepsTextView);
            photo = (ImageView)itemView.findViewById(R.id.exercisePhotoImageView);
            speaker = (ImageButton)itemView.findViewById(R.id.exerciseSpeakerImageButton);
            description = (TextView)itemView.findViewById(R.id.exerciseDescriptionTextView);



        }
    }
}
