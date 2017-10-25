package in.andante.drawbm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

//アプリのメイン画面

public class Main extends Activity{
	
	/*Called when the activity is first created.*/
	@Override
public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		
		//avtivity_main.xmlのViewを画面(Activity)に追加
		setContentView(R.layout.avtivity_main);
		
		final TextView textView = (TextView) findViewById(R.id.textView);
		//データをセット
		final EditText editText = (EditText)this.findViewById(R.id.edit_text);
		//リソース名send_buttonのビューのオブジェクトを取得する
		Button sendButton = (Button) findViewById(R.id.send_button);
		
		
		//ボタンがクリックされた時に呼び出されるコールバックリスナーを登録
		sendButton.setOnClickListener(new View.OnClickListener() {
			@Override
			// TODO 自動生成されたメソッド・スタブ(ボタンがクリックされた時
			public void onClick(View v) {
				Intent intent = new Intent(getApplication(), DrawBm.class);
				// エディットテキストのテキストを取得
                String text = editText.getText().toString();
                //取得した文字をTextViewにセット！
                textView.setText(text);
				//intent.putExtra("sendText",editText.getText().toString());
				startActivity(intent);//次の画面へ
				
			}
		});
}

}
