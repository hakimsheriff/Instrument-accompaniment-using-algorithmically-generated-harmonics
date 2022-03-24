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
        char c;
        byte b[] = new byte[32];
        for(int j = 0; j < 32; j++) {
            c = settings.charAt(j);
            if(c == '0') {
                b[j] = 48;
            } else if(c == '1') {
                b[j] = 49;
            } else if(c == '2') {
                b[j] = 50;
            } else if(c == '3') {
                b[j] = 51;
            } else if(c == '4') {
                b[j] = 52;
            } else if(c == '5') {
                b[j] = 53;
            } else if(c == '6') {
                b[j] = 54;
            } else if(c == '7') {
                b[j] = 55;
            } else if(c == '8') {
                b[j] = 56;
            } else if(c == '9') {
                b[j] = 57;
            } else if(c == 'A') {
                b[j] = 65;
            } else if(c == 'B') {
                b[j] = 66;
            } else if(c == 'C') {
                b[j] = 67;
            } else if(c == 'D') {
                b[j] = 68;
            } else if(c == 'E') {
                b[j] = 69;
            } else if(c == 'F') {
                b[j] = 70;
            } else if(c == 'G') {
                b[j] = 71;
            } else if(c == 'H') {
                b[j] = 72;
            } else if(c == 'I') {
                b[j] = 73;
            } else if(c == 'J') {
                b[j] = 74;
            } else if(c == 'K') {
                b[j] = 75;
            } else if(c == 'L') {
                b[j] = 76;
            } else if(c == 'M') {
                b[j] = 77;
            } else if(c == 'N') {
                b[j] = 78;
            } else if(c == 'O') {
                b[j] = 79;
            } else if(c == 'P') {
                b[j] = 80;
            } else if(c == 'Q') {
                b[j] = 81;
            } else if(c == 'R') {
                b[j] = 82;
            } else if(c == 'S') {
                b[j] = 83;
            } else if(c == 'T') {
                b[j] = 84;
            } else if(c == 'U') {
                b[j] = 85;
            } else if(c == 'V') {
                b[j] = 86;
            } else if(c == 'W') {
                b[j] = 87;
            } else if(c == 'X') {
                b[j] = 88;
            } else if(c == 'Y') {
                b[j] = 89;
            } else if(c == 'Z') {
                b[j] = 90;
            }
        }

        this.bleController.sendData(b);
    }

    public void alternate(String settings) {
        String tempSettings = settings.toUpperCase();
        byte b[] = new byte[32];

        String digits = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ";

        int val = 0;
        for (int i = 0; i < 32; i++) {
            char c = tempSettings.charAt(i);
            int d = digits.indexOf(c);
            b[i] = (byte) (d + 48);
        }
    }

    public void heartbeat() {
        this.bleController.sendData(createControlWord(HEARTBEAT));
    }
}