package com.example.mcmid_mgod;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Random;
import java.util.Set;

public class BluetoothTest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        final Button bluetoothTestButton1 = (Button) findViewById(R.id.buttonTestBluetooth1);
        //final Button backButton = (Button) findViewById(R.id.buttonReturnWeight);


        bluetoothTestButton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent startBluetooth = new Intent(BluetoothTest.this, DeviceScanActivity.class);
                startActivity(startBluetooth);
                //do weight
            }
        });

        //checks if bluetooth is available on the device
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
        }
        else{
            //checks if bluetooth is enabled
            //if not it starts enabling process
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                int REQUEST_ENABLE_BT = 0;
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }

        }

        //start finding actual bluetoothLE devices
        //MI Scale 2: Mac: C8:47:8C:F9:3F:BE
        //maybe: A&D Medical NIBP 40:23:43:B8:A5:B6 (Bluetooth Classic)

        BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
        boolean scanning;
        Handler handler = new Handler();

        // Stops scanning after 10 seconds.
        final long SCAN_PERIOD = 10000;





    }
}
