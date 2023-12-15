/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Verification;

/**
 *
 * @author Burak
 */
public class CalculateCRC {

    public static int getCRC(byte [] byteArray, int len)
    {
        int Crc = 0xFFFFFFFF;

        for (int dataIndex = 0; dataIndex < len; dataIndex++) {
            int data = byteArray[dataIndex];
            if(data < 0)
            {
                data += 256;   /*byte to int type conversion*/
            }
            Crc = Crc ^ data;

            for (int i = 0; i < 32; i++) { 
                if ((Crc & 0x80000000) != 0) {
                    Crc = (Crc << 1) ^ 0x04C11DB7;
                } else {
                    Crc = (Crc << 1);
                }
            }
        }

        return Crc;
    }
    
    public static int word_to_byte(int addr, int index)
    {
        int shift = 8 * (index - 1);
        int mask = 0x000000FF;

        int value = (addr >> shift) & mask;
        return value;
    }
}
