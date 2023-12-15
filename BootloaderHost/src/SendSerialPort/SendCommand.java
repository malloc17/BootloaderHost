/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SendSerialPort;

import com.fazecast.jSerialComm.SerialPort;
import java.io.IOException;
import java.io.OutputStream;


/**
 *
 * @author Burak
 */
public class SendCommand {
    private final OutputStream os;
    
    public static final int WRITE_FAILED = 0;
    public static final int WRITE_SUCCESS = 1;
    
    public SendCommand(SerialPort serialPort1) {
        os = serialPort1.getOutputStream();
    }
    
    public int sendByteArray(byte [] sendArr)
    {
        synchronized (os) {
            try {
                os.write(sendArr);
                return WRITE_SUCCESS;
            } catch (IOException ex) {
                return WRITE_FAILED;
            }
        }
    }
    
    
    
}
