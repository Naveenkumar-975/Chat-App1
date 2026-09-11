import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

class NK extends Frame implements Runnable, ActionListener {

    TextArea txt;
    Button send;
    TextField ft;

    ServerSocket sk;
    Socket s;
    DataInputStream DI;
    DataOutputStream DO;

    Thread t;

    NK() {

        setTitle("NK Server");
        setSize(500, 500);
        setLayout(new FlowLayout());

        txt = new TextArea(20, 50);
        send = new Button("SEND");
        ft = new TextField(30);

        send.addActionListener(this);

        add(txt);
        add(ft);
        add(send);

        // Frame first display
        setVisible(true);

        // Start thread
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {

        try {

            txt.append("Server starting...\n");

            sk = new ServerSocket(95);

            txt.append("Waiting for AVANGA...\n");

            s = sk.accept();

            txt.append("AVANGA connected!\n");

            DI = new DataInputStream(s.getInputStream());
            DO = new DataOutputStream(s.getOutputStream());

            while (true) {

                String msg = DI.readUTF();

                txt.append("Avanga: " + msg + "\n");
            }

        } catch (Exception e) {

            txt.append("Error: " + e.getMessage() + "\n");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String msg = ft.getText();

        if (!msg.isEmpty()) {

            txt.append("NK: " + msg + "\n");

            ft.setText("");

            try {

                DO.writeUTF(msg);
                DO.flush();

            } catch (IOException ex) {

                txt.append("Error sending message.\n");
            }
        }
    }

    public static void main(String[] args) {

        new NK();
    }
}

