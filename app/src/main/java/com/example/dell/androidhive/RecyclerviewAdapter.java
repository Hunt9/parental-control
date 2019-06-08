package com.example.dell.androidhive;

//import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class RecyclerviewAdapter extends RecyclerView.Adapter<RecyclerviewAdapter.MyHolder> {

    List<Listdata> listdata;
    private Context mContext;

    public RecyclerviewAdapter(Context context,List<Listdata> listdata) {

        this.listdata = listdata;
        mContext = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.myview,parent,false);

        MyHolder myHolder = new MyHolder(view);
        return myHolder;
    }


    public void onBindViewHolder(MyHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");

        final Listdata data = listdata.get(position);
        holder.vName.setText(data.getName());
        holder.vEmail.setText(data.getEmail());
        holder.vNum.setText(data.getPhone());
        holder.vAge.setText(data.getAge());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("name","HELLO" );

                Intent intent = new Intent(mContext, AfterChildClick.class);
                Bundle bundle = new Bundle();
                bundle.putString("CID",data.getId());
                intent.putExtras(bundle);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{
        TextView vName, vEmail, vNum, vAge;

        ConstraintLayout parentLayout;

        public MyHolder(View itemView) {
            super(itemView);
            vName = itemView.findViewById(R.id.v_name);
            vEmail = itemView.findViewById(R.id.v_emale);
            vNum = itemView.findViewById(R.id.v_phno);
            vAge = itemView.findViewById(R.id.v_age);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
