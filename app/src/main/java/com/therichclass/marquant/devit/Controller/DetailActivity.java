package com.therichclass.marquant.devit.Controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.therichclass.marquant.devit.R;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

import static com.therichclass.marquant.devit.R.id.username;

/**
 * Created by marquant on 8/28/2017.
 */

public class DetailActivity extends AppCompatActivity {
    TextView Link, Username;
    ImageView imageView;
    Button button;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dev_detail_page);

        imageView = (ImageView) findViewById(R.id.avatar);
        Username = (TextView) findViewById(username);
        Link = (TextView) findViewById(R.id.git_link);
        addListenerOnButton();

        String username = getIntent().getExtras().getString("login");
        String avatarUrl = getIntent().getExtras().getString("avatar_url");
        String link = getIntent().getExtras().getString("html_url");


        Link.setText(link);
        Linkify.addLinks(Link, Linkify.WEB_URLS);

        //set various texts
        Username.setText(username);

        //fetch image using glide to detail activity
        Glide.with(this)
                .load(avatarUrl)
                .placeholder(R.drawable.laptop)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(imageView);

    }
    //button to share
    public void addListenerOnButton() {

        button = (Button) findViewById(R.id.btn_click);

        button.setOnClickListener(new android.view.View.OnClickListener() {

                                      @Override
                                      public void onClick(View arg0) {
                                          String username = getIntent().getExtras().getString("login");
                                          String link = getIntent().getExtras().getString("html_url");
                                          Intent shareIntent = new Intent();
                                          shareIntent.setAction(Intent.ACTION_SEND);
                                          String shareMsg = "Check out this awesome developer @" + username + ", " + link + ".";
                                          shareIntent.putExtra(Intent.EXTRA_TEXT, shareMsg);
                                          shareIntent.setType("text/plain");
                                          startActivity(shareIntent);
                                      }


                                  }
        );

    }


}



