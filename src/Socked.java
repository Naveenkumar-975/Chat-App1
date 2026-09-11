import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
class AVANGA extends Frame implements Runnable, ActionListener {
    TextArea txt ;
    Button send ;
    TextField ft ;
    Socket s ;
    DataInputStream DI ;
    DataOutputStream DO ;
    Thread t ;
    AVANGA () {
        setTitle( "avanga" );
        setSize( 500 , 500 );
        setLayout( new FlowLayout());
// Set layout first
        txt = new TextArea( 20 , 50 );
        send = new Button( "SEND" );
        ft = new TextField( 30 );
        send .addActionListener( this );
        try {
            s = new Socket( "localhost" , 95 );
            DI = new DataInputStream( s .getInputStream());
            DO = new DataOutputStream( s .getOutputStream());
        } catch (Exception e) {
        }
        add( txt );
        add( ft );
        add( send );
        setVisible( true );
        t = new Thread( this );
        t .setDaemon( true );
        t .start();
    }
    @Override
    public void actionPerformed (ActionEvent e) {
        String msg = ft .getText();
        txt .append( "avanga " + msg + " \n " );
        ft .setText( "" );
        try {
            DO .writeUTF(msg);
            DO .flush();
        } catch (IOException ex) {
            txt .append( "Error sending message. \n " );
        }
    }
    public static void main (String[] args) {
        new AVANGA();
    }
    @Override
    public void run () {
        while ( true ) {
            try {
                String msg = DI .readUTF();
                txt .append( "NK" + ": " + msg + " \n " );
            } catch (Exception e) {
                txt .append( "Connection lost or error occurred. \n " );
            }
        }
    }
}
