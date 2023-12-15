/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package SendSerialPort;

/**
 *
 * @author Burak
 */
public class WriteMessageValue {
    public static final int BL_FLASH_BASE_ADDRESS = 0x08000000;
    
    public static final int BL_WRITE_FLASH_CMD = 0xBB;
    
    public static final int BL_MASS_ERASE_FLASH = 0xFF;
    
    public static final int SIZE_OF_COMMAND_CODE = 1;
    
    public static final int SIZE_OF_SECTOR_ADDRESS = 4;
    public static final int SIZE_OF_MESSAGE_LENGTH = 1;
    public static final int SIZE_OF_CRC_VALUE = 4;

    public static final int INDEX_OF_COMMAND_CODE = 0;
    public static final int INDEX_OF_SECTOR_ADDRESS = INDEX_OF_COMMAND_CODE + SIZE_OF_COMMAND_CODE;
    public static final int INDEX_OF_MESSAGE_LENGTH = INDEX_OF_SECTOR_ADDRESS + SIZE_OF_SECTOR_ADDRESS;
    public static final int INDEX_OF_DATA_VALUE = INDEX_OF_MESSAGE_LENGTH + SIZE_OF_MESSAGE_LENGTH;
    
    public static final int [] flashSectorOffsetAddTable =
    {
        0x00,
        0x4000,
        0x8000,
        0xC000,
        0x10000,
        0x20000,
        0x40000,
        0x60000,
        0x80000,
        0xA0000,
        0xC0000
    };
    
}
