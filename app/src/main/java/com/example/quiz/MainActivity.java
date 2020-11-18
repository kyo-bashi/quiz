package com.example.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

		private HashMap<Integer,String> mondai_text = new HashMap<>();
		private HashMap<Integer,String> kaito_text = new HashMap<>();
		private HashMap<Integer, Boolean> is_maru = new HashMap<Integer, Boolean>();
		private Integer mondai_number = 0;
		private Button maru;
		private Button batu;
		private TextView number;
		private TextView mondai;
		private TextView kaito;
		private  TextView decision;
		private Button next;



		@Override
		protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.activity_main);

				// kaito_text.put(0,"麒麟の睡眠時間はとても短く、20分です。");
				// kaito_text.put(1,"パンダは鳴きます。意外に変わった鳴き声なのです。");
				// kaito_text.put(2,"コアラには指紋があります。木に登るとき・物をつかむときにはこの指紋が大活躍します。");
				// kaito_text.put(3,"パンダのしっぽの色は白です。イラストや絵などでは誤って黒色にしているものが多いです。");
				// kaito_text.put(4,"バランスをとるわけではなく、水に温度を奪われないようにするためです。");
				// kaito_text.put(5,"キリンの舌の色は黒紫色です。");
				// kaito_text.put(6,"毛細血管がたくさんあり、温度調整をしています。");
				// kaito_text.put(7,"羽毛で隠れていますが、フクロウには目の横に耳があります。");
				// kaito_text.put(8,"4つあります。");
				// kaito_text.put(9,"口は食べたり飲んだりするのに使うだけで、基本的には馬は鼻で呼吸をします。");
				//
				is_maru.put(0,false);
				is_maru.put(1,true);
				is_maru.put(2,true);
				is_maru.put(3,false);
				is_maru.put(4,false);
				is_maru.put(5,false);
				is_maru.put(6,true);
				is_maru.put(7,false);
				is_maru.put(8,true);
				is_maru.put(9,true);

				InputStream is = null;
				BufferedReader br = null;
				String text = "";

				for(int i = 0; i < 10; i++){
						is = null;
						br = null;
						text = "";
						try {
								try {
										String fileName = "mondai" + String.valueOf(i + 1) + ".txt";
										// assetsフォルダ内の sample.txt をオープンする
										is = this.getAssets().open(fileName);
										br = new BufferedReader(new InputStreamReader(is));

										// １行ずつ読み込み、改行を付加する
										String str;
										while ((str = br.readLine()) != null) {
												text += str + "\n";
										}
										mondai_text.put(i,text);
								} finally {
										if (is != null) is.close();
										if (br != null) br.close();
								}
						} catch (Exception e){
								// エラー発生時の処理
						}
				}

				mondai = findViewById(R.id.mondai);
				decision = findViewById(R.id.decision);
				next = findViewById(R.id.next);
				kaito = findViewById(R.id.kaito);
				number =  findViewById(R.id.number);

				maru = findViewById(R.id.maru);
				ButtonClick marulistener = new ButtonClick();
				maru.setOnClickListener(marulistener);
				batu = findViewById(R.id.batu);
				ButtonClick batulistener = new ButtonClick();
				batu.setOnClickListener(batulistener);
				mondai.setText(mondai_text.get(mondai_number));

				Invisible();

				next.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
								mondai_number += 1;
								Invisible();
								number.setText("第" + String.valueOf(mondai_number + 1) + "問");
								mondai.setText(mondai_text.get(mondai_number));
								kaito.setText("");
						}
				});
		}
		private class ButtonClick implements View.OnClickListener{

				@Override
				public void onClick(View v) {

						int id = v.getId();
						switch (id){
								case R.id.maru:
										if (is_maru.get(mondai_number) == true){
												decision.setText("正解");
										}else{
												decision.setText("不正解");
										}
										batu.setVisibility(View.INVISIBLE);
										kaito_indicate(mondai_number);
										visible();
										break;
								case R.id.batu:
										if (is_maru.get(mondai_number) == false){
												decision.setText("正解");
										}else{
												decision.setText("不正解");
										}
										maru.setVisibility(View.INVISIBLE);
										kaito_indicate(mondai_number);
										visible();
										break;
						}
				}
		}

		//非表示
		private void Invisible(){
				decision.setVisibility(View.INVISIBLE);
				next.setVisibility(View.INVISIBLE);
				maru.setVisibility(View.VISIBLE);
				batu.setVisibility(View.VISIBLE);
		}

//    表示
		private void visible(){
				decision.setVisibility(View.VISIBLE);
				next.setVisibility(View.VISIBLE);
		}

//    解答表示
		private void kaito_indicate(int i){
				kaito.setText(kaito_text.get(i));
		}

}
