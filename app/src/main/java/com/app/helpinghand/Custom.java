package com.app.helpinghand;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Deepanshu on 12/2/2017.
 */

public class Custom {
    private Context context;
    public Custom(Context context){
        this.context = context;
    }
    public void method(){
        Intent intent = new Intent(context,ReceiverEndActivity.class);
        context.startActivity(intent);

    }
}
