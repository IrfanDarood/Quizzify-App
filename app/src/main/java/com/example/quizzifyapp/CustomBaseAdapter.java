package com.example.quizzifyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CustomBaseAdapter extends BaseAdapter {

    Context context;
    String [] quizquestions,quizcrtopts,quizincrtop1,quizincrtop2,quizincrtop3;
    LayoutInflater inflater;
    public CustomBaseAdapter(Context ctx,String[] quizquestions,
                             String[] quizcrtopts,String[] quizincrtop1,
                             String[] quizincrtop2,String[] quizincrtop3){
        this.context=ctx;
        this.quizquestions=quizquestions;
        this.quizcrtopts=quizcrtopts;
        this.quizincrtop1=quizincrtop1;
        this.quizincrtop2=quizincrtop2;
        this.quizincrtop3=quizincrtop3;
        inflater=LayoutInflater.from(ctx);
    }

    @Override
    public int getCount() {
        return quizquestions.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.activity_custom_list_view,null);
        TextView que=(TextView) convertView.findViewById(R.id.quizque);
        TextView crt=(TextView) convertView.findViewById(R.id.quizcrtop);
        TextView incrt1=(TextView) convertView.findViewById(R.id.quizincrtop1);
        TextView incrt2=(TextView) convertView.findViewById(R.id.quizincrtop2);
        TextView incrt3=(TextView) convertView.findViewById(R.id.quizincrtop3);
        que.setText(quizquestions[position]);
        crt.setText(quizcrtopts[position]);
        incrt1.setText(quizincrtop1[position]);
        incrt2.setText(quizincrtop2[position]);
        incrt3.setText(quizincrtop3[position]);
        return convertView;
    }
}
