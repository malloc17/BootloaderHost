/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SendSerialPort;

import Verification.CalculateCRC;

/**
 *
 * @author Burak
 */
public class SendEraseCommand {
    SendCommand sendCommand;
    private static final int BL_ERASE_FLASH_CMD = 0xAA;
        
    public SendEraseCommand(SendCommand sendCmd) {
        this.sendCommand = sendCmd;
    }
    
    public void eraseFlash(byte sectorNum)      
    {
        byte[] byteArray = new byte[6];
        byteArray[0] = (byte) BL_ERASE_FLASH_CMD;
        byteArray[1] = sectorNum;
        int crc32 = CalculateCRC.getCRC(byteArray, 2);
        for (int i = 1; i < 5; i++) {
            byteArray[i+1] = (byte)CalculateCRC.word_to_byte(crc32,i);
        }
        
        sendCommand.sendByteArray(byteArray);
    }
}
