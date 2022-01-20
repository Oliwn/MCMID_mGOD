package com.example.mcmid_mgod;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
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
import java.util.List;
import java.util.UUID;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class BluetoothTest extends AppCompatActivity {
    // Stops scanning after 10 seconds.
    BluetoothGatt gattSave;
    private static final long SCAN_PERIOD = 10000;
    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();
    private boolean scanning;
    String serviceUuidString = "0000181d-0000-1000-8000-00805f9b34fb";
    private final BluetoothGattCallback gattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String nameResult = gatt.getDevice().getName();
            String macAdress = gatt.getDevice().getAddress();
            Log.i("BluetoothGattCallback", ": onConnectionStateChange: " + newState);
            if (newState == BluetoothProfile.STATE_CONNECTED) {
                // successfully connected to the GATT Server
                Log.i("BluetoothGattCallback", ": Connected to BT LE Device Name: " + nameResult);
                Log.i("BluetoothGattCallback", ": Connected to BT LE Device  Mac: " + macAdress);
                gattSave = gatt;    //reference gatt outside

                //getDataFromBtleDevice();

            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                Log.i("BluetoothGattCallback", ": Disconnected from BT LE Device Name: " + nameResult);
                Log.i("BluetoothGattCallback", ": Disconnected from BT LE Device  Mac: " + macAdress);
                // disconnected from the GATT Server
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            Log.i("BluetoothGattCallback", gatt.getServices().size() + " Services Discovered");
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i("BluetoothGattCallback", "Characteristic Read successfull: " + characteristic.getValue().toString());
            } else {
                Log.i("BluetoothGattCallback", "Characteristic Read failed!: ");
            }
        }
    };


    private Handler handler = new Handler();
    String charUuidString = "00002a2f-0000-1000-8000-00805f9b34fb";
    // Device scan callback.
    private ScanCallback leScanCallback =
            new ScanCallback() {
                @Override
                public void onScanResult(int callbackType, ScanResult result) {
                    super.onScanResult(callbackType, result);
                    Log.i("ScanCallback", ": OnScanResult start");
                    String nameResult = result.getDevice().getName();
                    String macAdress = result.getDevice().getAddress();
                    Log.i("ScanCallback", ": Found BT LE Device Name: " + nameResult);
                    Log.i("ScanCallback", ": Found BT LE Device  Mac: " + macAdress);
                    result.getDevice().connectGatt(getApplicationContext(), false, gattCallback);
                    //leDeviceListAdapter.notifyDataSetChanged();
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth_test);

        final Button bluetoothSearchButton = (Button) findViewById(R.id.buttonBluetoothSearch);



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

    public boolean bluetoothAvailable() {
        Log.i("bluetoothAvailableCheck", "Start");
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
        String filterAddress = "C8:47:8C:F9:3F:BE";
        String filterName = "MI SCALE2";
        //checks if bluetooth is available and activated
        ScanFilter filter = new ScanFilter.Builder().setDeviceAddress(filterAddress).build();
        List<ScanFilter> filterList = new ArrayList<ScanFilter>();
        //filterList.add(filter);

        ScanSettings setting = new ScanSettings.Builder().setScanMode(ScanSettings.SCAN_MODE_BALANCED).build();

        if (bluetoothAvailable()) {  //start scan only if bt available
            //------------- from https://developer.android.com/guide/topics/connectivity/bluetooth/find-ble-devices start
            if (!scanning) {
                // Stops scanning after a predefined scan period.
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        scanning = false;
                        bluetoothLeScanner.stopScan(leScanCallback);
                        Log.i("BTLE Scanner", "Stopped");
                    }
                }, SCAN_PERIOD);

                scanning = true;
                bluetoothLeScanner.startScan(filterList, setting, leScanCallback);
                Log.i("BTLE Scanner", "Started");
            } else {
                scanning = false;
                bluetoothLeScanner.stopScan(leScanCallback);
                Log.i("BTLE Scanner", "Stopped");
            }
        }
        //------------- from https://developer.android.com/guide/topics/connectivity/bluetooth/find-ble-devices end
    }

    public void getDataFromBtleDevice() {
        BluetoothGatt gatt = gattSave;
        Log.i("Gatt service discovery", "Start");
        gatt.discoverServices();
        readBatteryLevel();
    }

    public void readBatteryLevel() {
        UUID serviceUuid = UUID.fromString(serviceUuidString);
        UUID charUuid = UUID.fromString(charUuidString);
        BluetoothGatt gatt = gattSave;
        BluetoothGattCharacteristic batteryLevelChar = gatt.getService(serviceUuid).getCharacteristic(charUuid);
        Log.i("Characteristic", "Start Reading Characteristic");
        if (batteryLevelChar.getPermissions() == BluetoothGattCharacteristic.PERMISSION_READ) {
            Log.i("Characteristic", "Readable");
            gatt.readCharacteristic(batteryLevelChar);
        }
    }

}