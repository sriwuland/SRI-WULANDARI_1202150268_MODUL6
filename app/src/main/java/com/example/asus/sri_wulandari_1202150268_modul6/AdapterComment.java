package com.example.asus.sri_wulandari_1202150268_modul6;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Asus on 04/01/2018.
 */

public class AdapterComment {
    Context con;
    List<databaseKomen> list;

    public AdapterComment(Context con, List<databaseKomen> list) {
        this.con = con;
        this.list = list;
    }
    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CommentHolder(LayoutInflater.from(con).inflate(R.layout.cv_comment, parent, false));
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        databaseKomen cur = list.get(position);
        holder.sikomen.setText(cur.getSikomen());
        holder.komen.setText(cur.getKomen());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class CommentHolder extends RecyclerView.ViewHolder {
        TextView sikomen, komen;
        public CommentHolder(View itemView) {
            super(itemView);
            sikomen = itemView.findViewById(R.id.yangkomen);
            komen = itemView.findViewById(R.id.komen);
        }
    }
}

}
