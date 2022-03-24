package com.example.myapplication;

public class RemoteControl {
    private final static byte START = 0x1;
    private final static byte HEARTBEAT = 0x2;
    private final static byte LED_COMMAND = 0x4;

    private final static byte VALUE_OFF = 0x0;
    private final static byte VALUE_ON = (byte)0xFF;

    private BLEController bleController;

    public RemoteControl(BLEController bleController) {
        this.bleController = bleController;
    }

    private byte [] createControlWord(byte type, byte ... args) {
        byte [] command = new byte[args.length + 3];
        command[0] = START;
        command[1] = type;
        command[2] = (byte)args.length;
        for(int i=0; i<args.length; i++)
            command[i+3] = args[i];

        return command;
    }

    public void switchLED(boolean on) {
        this.bleController.sendData(createControlWord(LED_COMMAND, on?VALUE_ON:VALUE_OFF));
    }

    public void sendWord(String settings) {
        byte b[];
        //MEMORY ALLOCATION FOR JAVA BYTE ARRAY
        b = new byte[32];
        //ASSIGNING ELEMENTS TO JAVA BYTE ARRAY
        b[0] = 65;
        b[1] = 0;
        b[2] = 0;
        b[3] = 0;
        b[4] = 0;
        b[5] = 0;
        b[6] = 0;
        b[7] = 0;
        b[8] = 0;
        b[9] = 0;
        b[10] = 0;
        b[11] = 0;
        b[12] = 0;
        b[13] = 0;
        b[14] = 0;
        b[15] = 0;
        b[16] = 0;
        b[17] = 0;
        b[18] = 0;
        b[19] = 0;
        b[20] = 0;
        b[21] = 0;
        b[22] = 0;
        b[23] = 0;
        b[24] = 0;
        b[25] = 0;
        b[26] = 0;
        b[27] = 0;
        b[28] = 0;
        b[29] = 0;
        b[30] = 0;
        b[31] = 0;

        this.bleController.sendData(b);
    }

    public void heartbeat() {
        this.bleController.sendData(createControlWord(HEARTBEAT));
    }
}