/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SendSerialPort;


/**
 *
 * @author Burak
 */
public class WriteMessagePacket {
    
    private final int MAX_SIZE_OF_DATA_VALUE = 255;
    private final int SIZE_OF_CRC_VALUE = 4;

    byte commandCode;
    int sectorAddress;
    byte messageLen;
    byte [] data = new byte[MAX_SIZE_OF_DATA_VALUE];
    byte [] crcVal = new byte[SIZE_OF_CRC_VALUE];
    
   
}
