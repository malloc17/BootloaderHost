/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SendSerialPort;

import Main.BootloaderHostFrame;
import Verification.CalculateCRC;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Burak
 */
public class SendWriteCommand {
    SendCommand sendCommand;
    BootloaderHostFrame blHostFrame;
    int writeFlashMsgCount;

    public SendWriteCommand(BootloaderHostFrame blHostFrame, SendCommand sendCmd) {
        this.sendCommand = sendCmd;
        this.blHostFrame = blHostFrame;
        writeFlashMsgCount = 0;
    }
    
    public void prepareWriteMessage(byte selectedSectorIndex)
    {
        int bytesRead = 0;
        
        int currentSectorAddress = WriteMessageValue.BL_FLASH_BASE_ADDRESS + WriteMessageValue.flashSectorOffsetAddTable[selectedSectorIndex];
          
        while(bytesRead != -1)  /*Dosyada okunacak bir şey kalmadı demek*/
        {
            if(isSectorOverFlow(currentSectorAddress, selectedSectorIndex))   
            {
                /*Yeni sektöre geçilmesi durumunda, yeni sektörü de sil*/ 
                selectedSectorIndex += 1;
                blHostFrame.eraseFlasOperation(selectedSectorIndex);
            }
            WriteMessagePacket writeMessage = new WriteMessagePacket();
            fillCommandCodeWriteMsg(writeMessage);
            currentSectorAddress += bytesRead;
            fillSectorAddress(writeMessage, currentSectorAddress);
            bytesRead = readBinaryDataInFile(writeMessage.data);
            if(isEndOfFile(bytesRead))
            {
                blHostFrame.enableWriteFlashStatus();
                return;
            }
            fillMessageLenWriteMsg(writeMessage, bytesRead);
            byte [] mergedArr = mergeObjectArr(writeMessage, bytesRead);
            calculateAndWriteCrcValue(mergedArr, bytesRead);

            sendCommand.sendByteArray(mergedArr);
            writeFlashMsgCount++;
            while(writeFlashMsgCount != blHostFrame.getWriteFlashSuccessMsgCount()); 
        }  
    }

    private boolean isSectorOverFlow(int currentSectorAddress, byte selectedSectorIndex) { 
        return currentSectorAddress >= WriteMessageValue.BL_FLASH_BASE_ADDRESS + WriteMessageValue.flashSectorOffsetAddTable[selectedSectorIndex + 1];
    }
    
    private void fillSectorAddress(WriteMessagePacket writeMessage, int currentSectorAdd) {
        writeMessage.sectorAddress = currentSectorAdd;
    }

    private int readBinaryDataInFile(byte[] dataField) {
        try {
            return blHostFrame.getInputStream().read(dataField);
        } catch (IOException ex) {
            Logger.getLogger(BootloaderHostFrame.class.getName()).log(Level.SEVERE, null, ex);
            return -1;
        }
    }

    private byte[] mergeObjectArr(WriteMessagePacket writeMessage, int bytesRead) {
        
        byte [] mergedArr = new byte[WriteMessageValue.SIZE_OF_COMMAND_CODE + WriteMessageValue.SIZE_OF_SECTOR_ADDRESS + WriteMessageValue.SIZE_OF_MESSAGE_LENGTH + bytesRead + WriteMessageValue.SIZE_OF_CRC_VALUE];
        mergedArr[0] = writeMessage.commandCode;

        int i = 0;
        while(i < WriteMessageValue.SIZE_OF_SECTOR_ADDRESS)
        {
            mergedArr[WriteMessageValue.INDEX_OF_SECTOR_ADDRESS + i] = (byte) (writeMessage.sectorAddress >> (3-i)*8);
            i++;
        }

        mergedArr[WriteMessageValue.INDEX_OF_MESSAGE_LENGTH] = writeMessage.messageLen;

        System.arraycopy(writeMessage.data, 0, mergedArr, WriteMessageValue.INDEX_OF_DATA_VALUE, bytesRead);
        return mergedArr;
    }

    private void fillCommandCodeWriteMsg(WriteMessagePacket writeMessage) {
        writeMessage.commandCode = (byte) WriteMessageValue.BL_WRITE_FLASH_CMD;
    }

    private void fillCrcFieldWriteMsg(byte[] mergedArr, int crc32, int messageSizeWithoutCrcField) {
        for (int j = 0; j < 4; j++) 
        {
            mergedArr[messageSizeWithoutCrcField + j] = (byte)CalculateCRC.word_to_byte(crc32, j + 1);
        }
    }

    private void fillMessageLenWriteMsg(WriteMessagePacket writeMessage, int bytesRead) {
        writeMessage.messageLen = (byte) bytesRead;
    }

    private boolean isEndOfFile(int bytesRead) {
        return bytesRead == -1;
    }

    private void calculateAndWriteCrcValue(byte[] mergedArr, int bytesRead) {
        int messageSizeWithoutCrcField = WriteMessageValue.SIZE_OF_COMMAND_CODE + WriteMessageValue.SIZE_OF_SECTOR_ADDRESS + WriteMessageValue.SIZE_OF_MESSAGE_LENGTH + bytesRead;
        int crc32 = CalculateCRC.getCRC(mergedArr, messageSizeWithoutCrcField);
        fillCrcFieldWriteMsg(mergedArr, crc32, messageSizeWithoutCrcField); 
    }

    
}
