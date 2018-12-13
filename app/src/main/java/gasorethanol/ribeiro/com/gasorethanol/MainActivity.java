package gasorethanol.ribeiro.com.gasorethanol;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class MainActivity extends Activity {
    private EditText gas;
    private EditText ethanol;
    private float valueOfGas;
    private float valueOfEthanol;
    private SharedPreferences sPref;
    private String gasString;
    private String ethanolString;
    private TextView whichIsBetter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        gas = (EditText)findViewById(R.id.gas);
        ethanol = (EditText)findViewById(R.id.ethanol);
        whichIsBetter = (TextView)findViewById(R.id.whichIsBetter);
        sPref = getPreferences(MODE_PRIVATE);


    }
    public void onClickSave(View v){
        SharedPreferences.Editor edit = sPref.edit();
        gasString = gas.getText().toString();
        ethanolString = ethanol.getText().toString();
        if (!gasString.isEmpty() && !ethanolString.isEmpty() && (!gasString.equals("0") && !ethanolString.equals("0"))) {
            valueOfGas = Float.parseFloat(gasString);
            edit.putFloat(getResources().getString(R.string.gas),valueOfGas);

            valueOfEthanol = Float.parseFloat(ethanolString);
            edit.putFloat(getResources().getString(R.string.ethanol), valueOfEthanol);


            gasOrEthanolBetter();
        }else{
            Toast toast = Toast.makeText(this, getResources().getString(R.string.enter_the_value), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        edit.commit();


    }
    public void gasOrEthanolBetter(){
        float factor = valueOfEthanol/valueOfGas;
        String better;
        if(factor >= 0.7) {
            better = getResources().getString(R.string.gas_is_better);
        } else {
            better = getResources().getString(R.string.ethanol_is_better);
        }
        whichIsBetter.setVisibility(View.VISIBLE);
        whichIsBetter.setText(better);
    }
    protected void onResume(){
        super.onResume();
        if (sPref.getFloat(getResources().getString(R.string.gas), 0) != 0){
            valueOfGas = sPref.getFloat(getResources().getString(R.string.gas), 0);
            gas.setText(valueOfGas+"");
        }
        if (sPref.getFloat(getResources().getString(R.string.ethanol), 0) != 0) {
            valueOfEthanol = sPref.getFloat(getResources().getString(R.string.ethanol),0);
            ethanol.setText(valueOfEthanol+"");
        }
        gas.setSelection(gas.getText().length());
        ethanol.setSelection(ethanol.getText().length());
    }

}
