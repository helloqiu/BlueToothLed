package com.helloqiu.bluetoothled.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.widget.Toast;

/**
 * Created by helloqiu on 16/1/20.
 */
public class BluetoothWorker {
    BluetoothAdapter bluetoothAdapter;
    public BluetoothWorker(){
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            return;
        }
    }
}
