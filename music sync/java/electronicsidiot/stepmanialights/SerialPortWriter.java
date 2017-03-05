package electronicsidiot.stepmanialights;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

import java.io.OutputStream;
import java.util.Enumeration;

public class SerialPortWriter {
    private static final int TIME_OUT = 2000;
    private static final int DATA_RATE = 9600;

    private OutputStream out;

    public SerialPortWriter() {
        SerialPort serialPort;
        CommPortIdentifier portId = null;
        Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();

        while (portEnum.hasMoreElements()) {
            CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
            if (currPortId.getName().startsWith("COM")) {
                portId = currPortId;
            }
        }

        if (portId == null) {
            System.out.printf("Cannot find port");
            System.exit(1);
        }

        try {
            serialPort = (SerialPort) portId.open(this.getClass().getName(), TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            out = serialPort.getOutputStream();
        } catch (Exception e) {
            System.out.println("Cannot connect to port!");
            System.exit(1);
        }

        System.out.println("Connected to: " + portId.getName());
    }

    public void write(byte value) {
        try {
            out.write(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
