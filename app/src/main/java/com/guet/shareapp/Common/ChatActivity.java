package com.guet.shareapp.Common;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.guet.shareapp.Adapter.ChatAdapter;
import com.guet.shareapp.Entity.xx;
import com.guet.shareapp.R;
import com.guet.shareapp.Utils.Link;

import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {
    private EditText text;
    private Button bt;
    private RecyclerView recy;
    public List list;
    private Link lj;
    public Handler hand = new Handler() {//用于在子线程更新UI，收到信息和连接服务器成功时用到
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Toast.makeText(getApplicationContext(), "连接服务器成功", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    recy.setAdapter(new ChatAdapter(list));//设置适配器
                    recy.scrollToPosition(list.size() - 1);//将屏幕移动到RecyclerView的底部
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        lj = new Link(this,"112.124.5.227");
        list = new ArrayList<xx>();
        text = findViewById(R.id.text);
        bt = findViewById(R.id.bt);
        recy = findViewById(R.id.recy);
        LinearLayoutManager lin = new LinearLayoutManager(getApplicationContext());
        recy.setLayoutManager(lin);

        bt.setOnClickListener(new View.OnClickListener() {//给发送按钮设置监听事件
            @Override
            public void onClick(View v) {
                String s = text.getText().toString();
                if (s.equals("")) {
                    Toast.makeText(getApplicationContext(), "发送消息不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    list.add(new xx(s, R.drawable.head, false));
                    //new一个xx类，第一个参数的信息的内容，第二个参数是头像的图片id，第三个参数表示左右
                    //true为左边，false为右
                    lj.fa(s);//发送消息
                    recy.setAdapter(new ChatAdapter(list));//再把list添加到适配器
                    text.setText(null);
                    recy.scrollToPosition(list.size() - 1);//将屏幕移动到RecyclerView的底部
                }
            }
        });
    }


}
