package com.example.mcmid_mgod;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanFilter;
import android.bluetooth.le.ScanResult;
import android.bluetooth.le.ScanSettings;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BluetoothTest extends AppCompatActivity {
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 10000;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    private boolean scanning;
    // Device scan callback.
    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    String nameResult = result.getDevice().getName();
                    String macAdress = result.getDevice().getAddress();
                    Log.i("ScanCallback", ": Found BT LE Device Name: " + nameResult);
                    Log.i("ScanCallback", ": Found BT LE Device Mac: " + macAdress);
                    //leDeviceListAdapter.notifyDataSetChanged();
                }
            };
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        final Button bluetoothSearchButton = (Button) findViewById(R.id.buttonBluetoothSearch);
        //final Button backButton = (Button) findViewById(R.id.buttonReturnWeight);


        bluetoothSearchButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Log.i("Click BTLE Search", "Start");
                startBleScan();
                // Intent startBluetooth = new Intent(BluetoothTest.this, DeviceScanActivity.class);
                // startActivity(startBluetooth);
                //do weight
            }
        });

        //checks if bluetooth is available on the device

        //start finding actual bluetoothLE devices
        //MI Scale 2: Mac: C8:47:8C:F9:3F:BE
        //maybe: A&D Medical NIBP 40:23:43:B8:A5:B6 (Bluetooth Classic)
    }

    public boolean bluetoothAvailible() {
        Log.i("bluetoothAvailibleCheck", "Start");
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            return false;
        } else {
            //checks if bluetooth is enabled
            //if not it starts enabling process
            if (!bluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                int REQUEST_ENABLE_BT = 0;
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);

                return bluetoothAdapter.isEnabled();//looks again if enabled and returns value
            } else return true;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void startBleScan() {
        String filterAdress = "C8:47:8C:F9:3F:BE";
        //checks if bluetooth is available and activated
        ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(filterAdress).build();
        List<ScanFilter> filterList = new ArrayList<ScanFilter>();
        filterList.add(filter);

        ScanSettings setting = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY).build();

        if (bluetoothAvailible()) {  //start scan only if bt available
            //------------- from https://developer.android.com/guide/topics/connectivity/bluetooth/find-ble-devices start
            if (!scanning) {
                // Stops scanning after a predefined scan period.
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scanning = false;
                        bluetoothLeScanner.stopScan(leScanCallback);
                    }
                }, SCAN_PERIOD);

                scanning = true;
                bluetoothLeScanner.startScan(filterList, setting, leScanCallback);
            } else {
                scanning = false;
                bluetoothLeScanner.stopScan(leScanCallback);
            }
        }
        //------------- from https://developer.android.com/guide/topics/connectivity/bluetooth/find-ble-devices end
    }

}