/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ReadSerialPort;

import Main.BootloaderHostFrame;
import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 *
 * @author Burak
 */
public class ReadData {
    BootloaderHostFrame blHostFrame;
    
    private static final int RX_BL_IS_READY        = 0x01;
    private static final int RX_WRITE_SUCCESS_CODE = 0x02;
    private static final int RX_ERASE_SUCCESS_CODE = 0x03;
    private static final int RX_WRITE_FAILED_CODE  = 0x04;
    private static final int RX_ERASE_FAILED_CODE  = 0x05;
    private static final int RX_USER_APP_RUNNING_CODE  = 0x06;

    public ReadData(BootloaderHostFrame blHostFrame, SerialPort serialPort1) {
        Serial_EvenBasedReading(serialPort1);
        this.blHostFrame = blHostFrame;
    }
    
    private void Serial_EvenBasedReading(SerialPort activePort)  
    {
            activePort.addDataListener(new SerialPortDataListener(){

            @Override
            public int getListeningEvents() {
               return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }
            @Override
            public void serialEvent(SerialPortEvent spe){
                byte [] receivedData = spe.getReceivedData();
                updateStatus(receivedData[0]);
            }

            private void updateStatus(byte receivedData) {
                switch(receivedData)
                {
                    case RX_BL_IS_READY:
                        blHostFrame.enableBootloaderStatus();
                        break;
                    case RX_WRITE_SUCCESS_CODE:
                        blHostFrame.addWriteFlashSuccessMsgCount();
                        break;
                    case RX_ERASE_SUCCESS_CODE:
                        blHostFrame.enableEraseFlashStatus();
                        break;
                    case RX_WRITE_FAILED_CODE:
                        blHostFrame.disableWriteFlashStatus();
                        break;
                    case RX_ERASE_FAILED_CODE:
                        blHostFrame.disableEraseFlashStatus();
                        break;
                    case RX_USER_APP_RUNNING_CODE:
                        blHostFrame.enableUserAppStatus();
                        break;
                    default:
                        blHostFrame.disableBootloaderStatus();
                        break;
                }
            }
        });
    }
}
